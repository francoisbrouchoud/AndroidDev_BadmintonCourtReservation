package ch.brouchoud.androiddev_badmintoncourtreservation.ui.court;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import ch.brouchoud.androiddev_badmintoncourtreservation.R;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.ui.BaseActivity;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import ch.brouchoud.androiddev_badmintoncourtreservation.viewmodel.court.CourtViewModel;

public class EditCourtActivity extends BaseActivity {

    private static final String TAG = "EditCourtActivity";

    private static final int DELETE_COURT = 1;

    private CourtEntity court;
    private CourtViewModel viewModel;

    private EditText etCourtName;
    private EditText etCourtAddress;
    private EditText etCourtHourlyPrice;
    private EditText etCourtDescription;

    private Button button;
    private Toast toast;

    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_edit_court, frameLayout);
        navigationView.setCheckedItem(R.id.nav_none);

        //Get the views
        etCourtName = findViewById(R.id.et_CourtsName);
        etCourtAddress = findViewById(R.id.et_CourtsAddress);
        etCourtHourlyPrice = findViewById(R.id.et_CourtsPrice);
        etCourtDescription = findViewById(R.id.et_CourtsDescription);
        button = findViewById(R.id.button);

        String courtId = getIntent().getStringExtra("courtId");
        if(Objects.equals(courtId, "0")){
            //If the courtId is 0 we assume that we are creating a new one
            setTitle(getString(R.string.title_editCourtActivity_new));
            toast = Toast.makeText(this, R.string.toast_editCourtActivity_new, Toast.LENGTH_LONG);
            isEdit = false;
        }else{
            setTitle(getString(R.string.title_editCourtActivity_edit));
            button.setText(R.string.btn_editPlayerActivity_edit);
            toast = Toast.makeText(this, R.string.toast_editCourtActivity_edit, Toast.LENGTH_LONG);
            isEdit = true;
        }

        CourtViewModel.Factory factory = new CourtViewModel.Factory(getApplication(), courtId);
        viewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory).get(CourtViewModel.class);

        if(isEdit){
            viewModel.getCourt().observe(this, courtEntity -> {
                if(courtEntity != null){
                    //Get the entity of the court to edit
                    court = courtEntity;
                    //Set the values on the fields
                    etCourtName.setText(court.getCourtsName());
                    etCourtAddress.setText(court.getAddress());
                    etCourtHourlyPrice.setText(Double.toString(court.getHourlyPrice()));
                    etCourtDescription.setText(court.getDescription());
                }
            });
        }

        button.setOnClickListener(view -> {
            CourtEntity court = getCourtFromFields();
            if (checkFields(court)) {
                saveChanges(getCourtFromFields());
                onBackPressed();
                toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(isEdit){
            menu.add(0, DELETE_COURT, Menu.NONE, getString(R.string.editCourt_action_delete))
                    .setIcon(R.drawable.ic_delete_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == DELETE_COURT){
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(R.string.editCourt_action_delete);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.alert_editCourtActivity_deleteMessage));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alert_playersActivity_btnPositive), ((dialog, which) -> {
                viewModel.deleteCourt(court, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "delete court: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "delete court: failure", e);
                    }
                });
                toast = Toast.makeText(this, R.string.toast_editCourtActivity_delete, Toast.LENGTH_LONG);
                onBackPressed();
            }));
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,getString(R.string.alert_playersActivity_btnNegative), ((dialog, which) -> alertDialog.dismiss()));
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Save the changes in the database. If we are editing, we update the court otherwise we create a new one.
     * @param courtToSave in the DB.
     */
    private void saveChanges(CourtEntity courtToSave){
        if (isEdit){
            //Edit an existing court
            viewModel.updateCourt(courtToSave, new OnAsyncEventListener(){
                @Override
                public void onSuccess() {
                    Log.d(TAG, "update court: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "update court: failure", e);
                }
            });
        }else{
            //Create the court
            viewModel.createCourt(courtToSave, new OnAsyncEventListener(){
                @Override
                public void onSuccess() {
                    Log.d(TAG, "create court: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "create court: failure", e);
                }
            });
        }
    }

    /**
     * Get the courts from the UI fields.
     * @return The court from the UI fields
     */
    private CourtEntity getCourtFromFields(){
        CourtEntity courtFields;
        if(court == null)
            courtFields = new CourtEntity();
        else
            //If not null, we retrieve the elements not shown on the UI such as the ID
            courtFields = court;

        courtFields.setCourtsName(etCourtName.getText().toString());
        courtFields.setAddress(etCourtAddress.getText().toString());
        if(!etCourtHourlyPrice.getText().toString().isEmpty())
            courtFields.setHourlyPrice(Double.parseDouble(etCourtHourlyPrice.getText().toString()));
        courtFields.setDescription(etCourtDescription.getText().toString());
        return courtFields;
    }

    /**
     * Check the UI fields according to 3 criteria.
     * @param court to check
     * @return false if a criteria is not satisfied, true if all criteria are satisfied.
     */
    private boolean checkFields(CourtEntity court) {
        //Ensure that the courts name is not empty
        if(TextUtils.isEmpty(court.getCourtsName())){
            etCourtName.setError(getString(R.string.errorRequired_court_name));
            etCourtName.requestFocus();
            return false;
        }
        //Ensure that the courts address is not empty
        if(TextUtils.isEmpty(court.getAddress())){
            etCourtAddress.setError(getString(R.string.errorRequired_court_address));
            etCourtAddress.requestFocus();
            return false;
        }
        //Ensure that the courts description is not empty
        if(TextUtils.isEmpty(court.getDescription())){
            etCourtDescription.setError(getString(R.string.errorRequired_court_description));
            etCourtDescription.requestFocus();
            return false;
        }
        return true;
    }
}