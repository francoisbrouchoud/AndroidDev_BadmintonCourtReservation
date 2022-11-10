package com.example.androiddev_badmintoncourtreservation.ui.reservation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.adapter.PlayersListAdapter;
import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.database.repository.PlayerRepository;
import com.example.androiddev_badmintoncourtreservation.ui.BaseActivity;
import com.example.androiddev_badmintoncourtreservation.ui.player.EditPlayerActivity;
import com.example.androiddev_badmintoncourtreservation.viewmodel.court.CourtViewModel;
import com.example.androiddev_badmintoncourtreservation.viewmodel.player.PlayerListViewModel;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class CourtReservationActivity extends BaseActivity {

    private CourtViewModel courtViewModel;
    private CourtEntity court;
    private PlayersListAdapter<PlayerEntity> adapterPlayers;
    private PlayerListViewModel playerListViewModel;
    private List<PlayerEntity> players;

    private TextView tvCourtName;
    private TextView tvCourtPrice;
    private TextView tvTime;
    private TextView tvPlayer;
    private EditText etReservationDate;
    private Spinner spReservationTime;
    private Spinner spReservationPlayer;

    private Button button;
    List<Long> ids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_court_reservation, frameLayout);
        navigationView.setCheckedItem(R.id.nav_none);

        setTitle(R.string.cr_homePage);




        //Get the views
        tvCourtName = findViewById(R.id.tv_courtReservation_courtName);
        tvCourtPrice = findViewById(R.id.tv_courtReservation_courtPrice);
        tvTime = findViewById(R.id.tv_courtReservation_time);
        tvPlayer = findViewById(R.id.tv_courtReservation_player);
        etReservationDate = findViewById(R.id.et_cr_date);
        spReservationTime = findViewById(R.id.sp_cr_time);

        button = findViewById(R.id.bt_cr_confirm);

        //Get the court from the intent
        long courtId = getIntent().getLongExtra("courtId", 0);
        CourtViewModel.Factory factoryCourt = new CourtViewModel.Factory(getApplication(), courtId);
        courtViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factoryCourt).get(CourtViewModel.class);

        //Set the values
        courtViewModel.getCourt().observe(this, courtEntity -> {
            if(courtEntity != null){
                court = courtEntity;
                tvCourtName.setText(court.getCourtsName());
                tvCourtPrice.setText(Double.toString(court.getHourlyPrice()));
            }
        });

        tvTime.setText(getText(R.string.hint_tv_time));
        tvPlayer.setText(getText(R.string.hint_tv_player));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.times, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spReservationTime.setAdapter(adapter);

        setupViewModel();
        setupPlayerSpinner();

        etReservationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CourtReservationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etReservationDate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        button.setOnClickListener(view -> {

        });
    }

    private void setupPlayerSpinner() {
        spReservationPlayer = findViewById(R.id.sp_cr_player);
        adapterPlayers = new PlayersListAdapter<>(this, R.layout.player_spinner_row, new ArrayList<>());
        spReservationPlayer.setAdapter(adapterPlayers);
    }

    private void updatePlayerSpinner(List<PlayerEntity> players){
        adapterPlayers.updateData(new ArrayList<>(players));
    }

    private void setupViewModel(){
        PlayerListViewModel.Factory factory = new PlayerListViewModel.Factory(getApplication());
        playerListViewModel =  new ViewModelProvider(this, (ViewModelProvider.Factory) factory).get(PlayerListViewModel.class);
        playerListViewModel.getPlayers().observe(this, playerEntities -> {
            if(playerEntities != null){
                updatePlayerSpinner(playerEntities);
            }
        });
    }

}