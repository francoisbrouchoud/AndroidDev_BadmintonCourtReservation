package ch.brouchoud.androiddev_badmintoncourtreservation.ui.player;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import ch.brouchoud.androiddev_badmintoncourtreservation.R;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.ui.BaseActivity;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import ch.brouchoud.androiddev_badmintoncourtreservation.viewmodel.player.PlayerViewModel;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class EditPlayerActivity extends BaseActivity {

    private static final String TAG = "EditPlayerActivity";

    private PlayerEntity player;
    private PlayerViewModel viewModel;

    private EditText etPlayerFirstname;
    private EditText etPlayerLastname;
    private EditText etPlayerBirthdate;
    private Spinner spPlayerGender;
    private TextView tvGender;
    private EditText etPlayerPhoneNbr;
    private EditText etPlayerAddress;
    private Button button;
    private Toast toast;

    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_edit_player, frameLayout);
        navigationView.setCheckedItem(R.id.nav_none);

        //Get the views
        etPlayerFirstname = findViewById(R.id.et_PlayerFirstname);
        etPlayerLastname = findViewById(R.id.et_PlayerLastname);
        etPlayerBirthdate = findViewById(R.id.et_PlayerBirthdate);
        spPlayerGender = findViewById(R.id.sp_Gender);
        tvGender = findViewById(R.id.tv_Gender);
        etPlayerPhoneNbr = findViewById(R.id.et_PlayerPhoneNumber);
        etPlayerAddress = findViewById(R.id.et_PlayerAddress);
        button = findViewById(R.id.button);

        tvGender.setText(R.string.hint_pl_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genders, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spPlayerGender.setAdapter(adapter);

        String playerId = getIntent().getStringExtra("playerId");
        if(Objects.equals(playerId, "0")){
            //If the playerId is 0 we assume that we are creating a new one
            setTitle(getString(R.string.title_editPlayerActivity_new));
            toast = Toast.makeText(this, R.string.toast_editPlayerActivity_new, Toast.LENGTH_LONG);
            isEdit = false;
        }else{
            setTitle(getString(R.string.title_editPlayerActivity_edit));
            button.setText(R.string.btn_editPlayerActivity_edit);
            toast = Toast.makeText(this, R.string.toast_editPlayerActivity_edit, Toast.LENGTH_LONG);
            isEdit = true;
        }

        //Click listener on the editText to display a calendar
        etPlayerBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = 0;
                int month = 0;
                int day = 0 ;

                if(isEdit){
                    //Set the value of the date, month and year corresponding to the player's birthdate
                    String bDate = player.getBirthdate();
                    switch (bDate.length()){
                        case 10:
                            year = Integer.parseInt(bDate.substring(6,10));
                            month = Integer.parseInt(bDate.substring(3,5))-1;
                            day = Integer.parseInt(bDate.substring(0,2));
                            break;
                        case 8:
                            year = Integer.parseInt(bDate.substring(4,8));
                            month = Integer.parseInt(String.valueOf(bDate.charAt(2)))-1;
                            day = Integer.parseInt(String.valueOf(bDate.charAt(0)));
                            break;
                        case 9:
                            if(bDate.charAt(1) == '.'){
                                //Case if day is lower than 10
                                day = Integer.parseInt(String.valueOf(bDate.charAt(0)));
                                month = Integer.parseInt(bDate.substring(2,4))-1;
                            }else{
                                //Case if the month is lower than 10
                                day = Integer.parseInt(bDate.substring(0,2));
                                month = Integer.parseInt(String.valueOf(bDate.charAt(3)))-1;
                            }
                            year = Integer.parseInt(bDate.substring(5,9));
                            break;
                    }
                }else{
                    //Set the values of the current day
                    day = c.get(Calendar.DAY_OF_MONTH);
                    month = c.get(Calendar.MONTH);
                    year = c.get(Calendar.YEAR);
                }

                //Create the datePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditPlayerActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                etPlayerBirthdate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        PlayerViewModel.Factory factory = new PlayerViewModel.Factory(getApplication(), playerId);
        viewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory).get(PlayerViewModel.class);

        if(isEdit){
            //We are editing an existing player
            viewModel.getPlayer().observe(this, playerEntity -> {
                if(playerEntity != null){
                    //Get the entity of the player to edit
                    player = playerEntity;
                    //Set the values on the fields
                    etPlayerFirstname.setText(player.getFirstname());
                    etPlayerLastname.setText(player.getLastname());
                    etPlayerBirthdate.setText(player.getBirthdate());
                    spPlayerGender.setSelection(getIdxFromSpGender(playerEntity.getGender()));
                    etPlayerPhoneNbr.setText(player.getPhone());
                    etPlayerAddress.setText(player.getAddress());
                }
            });
        }

        button.setOnClickListener(view -> {
            PlayerEntity player = getPlayerFromFields();
            if(checkFields(player)){
                saveChanges(player);
                onBackPressed();
                toast.show();
            }
        });
    }

    /**
     * Check the UI fields according to 3 criteria.
     * @param player to check
     * @return false if a criteria is not satisfied, true if all criteria are satisfied.
     */
    private boolean checkFields(PlayerEntity player) {
        //Ensure that the firstname is not empty
        if(TextUtils.isEmpty(player.getFirstname())){
            etPlayerFirstname.setError(getString(R.string.errorRequired_player_firstname));
            etPlayerFirstname.requestFocus();
            return false;
        }
        //Ensure that the lastname is not empty
        if(TextUtils.isEmpty(player.getLastname())){
            etPlayerLastname.setError(getString(R.string.errorRequired_player_lastname));
            etPlayerLastname.requestFocus();
            return false;
        }
        //Ensure that the birthdate is not empty
        if(TextUtils.isEmpty(player.getBirthdate())){
            etPlayerBirthdate.setError(getString(R.string.errorRequired_player_birthdate));
            etPlayerBirthdate.requestFocus();
            return false;
        }
      return true;
    }

    /**
     * Save the changes in the database. If we are editing, we update the player otherwise we create a new one.
     * @param playerToSave in the DB.
     */
    private void saveChanges(PlayerEntity playerToSave){
        if(isEdit){
            //Edit an existing player
            viewModel.updatePlayer(playerToSave, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "update player: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "update player: failure", e);
                }
            });
        }else{
            //Create the player
            viewModel.createPlayer(playerToSave, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "create player: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "create player: failure", e);
                }
            });
        }
    }

    /**
     * Get the player from the UI fields.
     * @return The player from the UI fields.
     */
    private PlayerEntity getPlayerFromFields(){
        PlayerEntity playerFields;
        if(player == null)
            playerFields = new PlayerEntity();
        else
            playerFields = player;

        playerFields.setFirstname(etPlayerFirstname.getText().toString());
        playerFields.setLastname(etPlayerLastname.getText().toString());
        playerFields.setBirthdate(etPlayerBirthdate.getText().toString());
        playerFields.setGender(spPlayerGender.getSelectedItem().toString());
        playerFields.setPhone(etPlayerPhoneNbr.getText().toString());
        playerFields.setAddress(etPlayerAddress.getText().toString());
        return playerFields;
    }

    /**
     * Get the index of the spinner's gender.
     * @param gender String we want to know the id.
     * @return the id.
     */
    private int getIdxFromSpGender(String gender){
        return Arrays.asList(getResources().getStringArray(R.array.genders)).indexOf(gender);
    }
}