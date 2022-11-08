package com.example.androiddev_badmintoncourtreservation.ui.court;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.ui.BaseActivity;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import com.example.androiddev_badmintoncourtreservation.viewmodel.court.CourtViewModel;

public class EditCourtActivity extends BaseActivity {

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

        long courtId = getIntent().getLongExtra("courtId", 0);
        if(courtId == 0){
            setTitle(getString(R.string.title_editCourtActivity_new));
            toast = Toast.makeText(this, R.string.toast_editCourtActivity_new, Toast.LENGTH_LONG);
            isEdit = false;
        }else{
            setTitle(getString(R.string.btn_editPlayerActivity_edit));
            button.setText(R.string.btn_editPlayerActivity_edit);
            toast = Toast.makeText(this, R.string.toast_editCourtActivity_edit, Toast.LENGTH_LONG);
            isEdit = true;
        }

        CourtViewModel.Factory factory = new CourtViewModel.Factory(getApplication(), courtId);
        viewModel = new ViewModelProvider(this, factory).get(CourtViewModel.class);

        if(isEdit){
            viewModel.getCourt().observe(this, courtEntity -> {
                if(courtEntity != null){
                    //Get the entity of the court to edit
                    court = courtEntity;
                    //Set the values on the fields
                    etCourtName.setText(court.getCourtsName());
                    etCourtAddress.setText(court.getAddress());
                    etCourtHourlyPrice.setText((int) court.getHourlyPrice());
                    etCourtDescription.setText(court.getDescription());
                }
            });
        }

        button.setOnClickListener(view -> {
            saveChanges(getCourtFromFields());
            onBackPressed();
            toast.show();
        });
    }

    private void saveChanges(CourtEntity courtToSave){
        if (isEdit){
            //Edit an existing court
            viewModel.updateCourt(courtToSave, new OnAsyncEventListener(){
                @Override
                public void onSuccess() {
                    //Log success
                }

                @Override
                public void onFailure(Exception e) {
                    //Log failure
                }
            });
        }else{
            //Create the court
            viewModel.createCourt(courtToSave, new OnAsyncEventListener(){
                @Override
                public void onSuccess() {
                    //Log success
                }

                @Override
                public void onFailure(Exception e) {
                    //Log failure
                }
            });
        }
    }

    private CourtEntity getCourtFromFields(){
        CourtEntity courtFields;
        if(court == null)
            courtFields = new CourtEntity();
        else
            courtFields = court;

        courtFields.setCourtsName(etCourtName.getText().toString());
        courtFields.setAddress(etCourtAddress.getText().toString());
        courtFields.setHourlyPrice(Double.parseDouble(etCourtHourlyPrice.getText().toString()));
        courtFields.setDescription(etCourtDescription.getText().toString());
        return courtFields;
    }
}