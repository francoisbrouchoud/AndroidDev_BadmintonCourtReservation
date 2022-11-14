package com.example.androiddev_badmintoncourtreservation.ui.player;

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

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.ui.BaseActivity;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import com.example.androiddev_badmintoncourtreservation.viewmodel.player.PlayerViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

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

        etPlayerBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditPlayerActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                etPlayerBirthdate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        long playerId = getIntent().getLongExtra("playerId", 0);
        if(playerId == 0){
            setTitle(getString(R.string.title_editPlayerActivity_new));
            toast = Toast.makeText(this, R.string.toast_editPlayerActivity_new, Toast.LENGTH_LONG);
            isEdit = false;
        }else{
            setTitle(getString(R.string.title_editPlayerActivity_edit));
            button.setText(R.string.btn_editPlayerActivity_edit);
            toast = Toast.makeText(this, R.string.toast_editPlayerActivity_edit, Toast.LENGTH_LONG);
            isEdit = true;
        }

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

    private boolean checkFields(PlayerEntity player) {
        if(TextUtils.isEmpty(player.getFirstname())){
            etPlayerFirstname.setError(getString(R.string.errorRequired_player_firstname));
            etPlayerFirstname.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(player.getLastname())){
            etPlayerLastname.setError(getString(R.string.errorRequired_player_lastname));
            etPlayerLastname.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(player.getBirthdate())){
            etPlayerBirthdate.setError(getString(R.string.errorRequired_player_birthdate));
            etPlayerBirthdate.requestFocus();
            return false;
        }
      return true;
    }

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

    private int getIdxFromSpGender(String gender){
        return Arrays.asList(getResources().getStringArray(R.array.genders)).indexOf(gender);
    }
}