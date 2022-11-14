package com.example.androiddev_badmintoncourtreservation.ui.reservation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.adapter.PlayersListAdapter;
import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import com.example.androiddev_badmintoncourtreservation.database.repository.PlayerRepository;
import com.example.androiddev_badmintoncourtreservation.ui.BaseActivity;
import com.example.androiddev_badmintoncourtreservation.ui.player.EditPlayerActivity;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import com.example.androiddev_badmintoncourtreservation.viewmodel.court.CourtViewModel;
import com.example.androiddev_badmintoncourtreservation.viewmodel.player.PlayerListViewModel;
import com.example.androiddev_badmintoncourtreservation.viewmodel.reservation.ReservationListViewModel;
import com.example.androiddev_badmintoncourtreservation.viewmodel.reservation.ReservationViewModel;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class CourtReservationActivity extends BaseActivity {

    private CourtViewModel courtViewModel;
    private CourtEntity court;
    private PlayersListAdapter<PlayerEntity> adapterPlayers;
    private PlayerListViewModel playerListViewModel;
    private List<PlayerEntity> players;
    private PlayerEntity player;

    private ReservationListViewModel reservationListViewModel;
    private ReservationViewModel reservationViewModel;
    private List<ReservationEntity> reservations;

    private TextView tvCourtName;
    private TextView tvCourtPrice;
    private TextView tvTime;
    private TextView tvPlayer;
    private EditText etReservationDate;
    private Spinner spReservationTime;
    private Spinner spReservationPlayer;

    private Button button;
    private Toast toast;

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

        toast = Toast.makeText(this, R.string.toast_reservation_new, Toast.LENGTH_LONG);

        ReservationViewModel.Factory factoryReservation = new ReservationViewModel.Factory(getApplication(),0);
        reservationViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factoryReservation).get(ReservationViewModel.class);

        ReservationListViewModel.Factory factory = new ReservationListViewModel.Factory(getApplication());
        reservationListViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory).get(ReservationListViewModel.class);
        reservationListViewModel.getReservations().observe(this, reservationEntities -> {
            if(reservationEntities != null){
                reservations = reservationEntities;
            }
        });

        button.setOnClickListener(view -> {
            ReservationEntity reservation = getReservationFromFields();
            if(checkFields(reservation)){
                saveChanges(reservation);
                onBackPressed();
                toast.show();
            }
        });
    }

    private boolean checkFields(ReservationEntity reservation){
        //Check if the fields are not empty
        if(TextUtils.isEmpty(reservation.getReservationDate())){
            etReservationDate.setError(getString(R.string.errorRequired_reservation_date));
            etReservationDate.requestFocus();
            return false;
        }
        if(reservation.getTimeSlot() == getString(R.string.hint_sp_time)){
            tvTime.setError(getString(R.string.errorRequired_reservation_time));
            spReservationTime.requestFocus();
            return false;
        }

        if(checkLaterDate(reservation.getReservationDate(), reservation.getTimeSlot())){
            tvTime.setError(getString(R.string.errorRequired_reservation_time));
            spReservationTime.requestFocus();
            return false;
        }

        if(checkReservationForTimeslot(reservation)){
            reservationConflictDialog();
            return false;
        }
        //Check if the date is in the past -> return true
        return true;
    }

    private void reservationConflictDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.dialog_reservation_exists_title);
        alertDialog.setCancelable(false);
        final TextView tvDeleteMessage = view.findViewById(R.id.tv_delete_item);
        tvDeleteMessage.setText(R.string.dialog_reservation_exists);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Close", (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void saveChanges(ReservationEntity reservationToSave){
        reservationViewModel.createReservation(reservationToSave, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private ReservationEntity getReservationFromFields(){
        ReservationEntity reservationFields = new ReservationEntity();
        //Get the player from the selection of the spinner
        player = players.get(spReservationPlayer.getSelectedItemPosition());
        //Set the values
        reservationFields.setCourtId(court.getId());
        reservationFields.setPlayerId(player.getId());
        reservationFields.setTimeSlot(spReservationTime.getSelectedItem().toString());
        reservationFields.setReservationDate(etReservationDate.getText().toString());
        reservationFields.setResFirstname(player.getFirstname());
        reservationFields.setResLastname(player.getLastname());
        reservationFields.setResCourtname(court.getCourtsName());
        return reservationFields;
    }

    private boolean checkReservationForTimeslot(ReservationEntity reservation){

        //Check if there is already a reservation at the same date and time for the court
        for(ReservationEntity r : reservations){
            if(Objects.equals(r.getCourtId(), reservation.getCourtId()) && Objects.equals(r.getReservationDate(), reservation.getReservationDate()) && Objects.equals(r.getTimeSlot(), reservation.getTimeSlot())){
                return true;
            }
        }
        return false;
    }

    private boolean checkLaterDate(String reservationDate, String timeSlot) {
        try {
            LocalDate today = LocalDate.now();
            String beginTimeSlot = timeSlot.substring(0, 2);
            int beginHour = Integer.parseInt(beginTimeSlot);

            Date reservationInputDate = new SimpleDateFormat("dd.MM.yyyy").parse(reservationDate);
            Date timeNow = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

            //Check date
            if(reservationInputDate.before(timeNow)){
                return true;
            }
            //Check timeslot if same day
            if (reservationInputDate.equals(timeNow) && beginHour <= LocalDateTime.now(ZoneId.of("Europe/Zurich")).getHour()) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
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
                players = playerEntities;
            }
        });
    }

}