package ch.brouchoud.androiddev_badmintoncourtreservation.ui.reservation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import ch.brouchoud.androiddev_badmintoncourtreservation.R;
import ch.brouchoud.androiddev_badmintoncourtreservation.adapter.PlayersListAdapter;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;
import ch.brouchoud.androiddev_badmintoncourtreservation.ui.BaseActivity;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.ReservationHelper;
import ch.brouchoud.androiddev_badmintoncourtreservation.viewmodel.court.CourtViewModel;
import ch.brouchoud.androiddev_badmintoncourtreservation.viewmodel.player.PlayerListViewModel;
import ch.brouchoud.androiddev_badmintoncourtreservation.viewmodel.reservation.ReservationListViewModel;
import ch.brouchoud.androiddev_badmintoncourtreservation.viewmodel.reservation.ReservationViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class CourtReservationActivity extends BaseActivity {

    private static final String TAG = "CourtReservationActivity";

    private CourtViewModel courtViewModel;
    private CourtEntity court;

    private PlayersListAdapter<PlayerEntity> adapterPlayers;
    private PlayerListViewModel playerListViewModel;
    private List<PlayerEntity> players;
    private PlayerEntity player;

    private ReservationEntity reservation;
    private ReservationWithPlayerAndCourt reservationWithPlayerAndCourt;
    private ReservationListViewModel reservationListViewModel;
    private ReservationViewModel reservationViewModel;
    private List<ReservationEntity> reservations;

    private TextView tvCourtName;
    private TextView tvCourtPrice;
    private TextView tvPriceTitle;
    private TextView tvTime;
    private TextView tvPlayer;
    private EditText etReservationDate;
    private Spinner spReservationTime;
    private Spinner spReservationPlayer;
    private Button button;
    private Toast toast;

    private boolean isEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_court_reservation, frameLayout);
        navigationView.setCheckedItem(R.id.nav_none);
        setTitle(R.string.cr_homePage);

        //Get the views
        tvCourtName = findViewById(R.id.tv_courtReservation_courtName);
        tvCourtPrice = findViewById(R.id.tv_courtReservation_courtPrice);
        tvPriceTitle = findViewById(R.id.tv_courtReservation_courtPrice_title);
        tvTime = findViewById(R.id.tv_courtReservation_time);
        tvPlayer = findViewById(R.id.tv_courtReservation_player);
        etReservationDate = findViewById(R.id.et_cr_date);
        spReservationTime = findViewById(R.id.sp_cr_time);
        button = findViewById(R.id.bt_cr_confirm);

        //Get the court from the intent
        String courtId = getIntent().getStringExtra("courtId");
        CourtViewModel.Factory factoryCourt = new CourtViewModel.Factory(getApplication(), courtId);
        courtViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factoryCourt).get(CourtViewModel.class);

        //Set the values for a new reservation
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

        String reservationId = getIntent().getStringExtra("reservationId");
        if(reservationId == null){
            setTitle("New court reservation");
            isEdit = false;
        }else{
            setTitle("Edit reservation");
            button.setText(R.string.btn_editPlayerActivity_edit);
            isEdit = true;
        }

        //Click listener on the editText to display a calendar
        etReservationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = 0;
                int month = 0;
                int day = 0;

                if(isEdit){
                    //Set the value of the date, month and year corresponding to the reservation's date
                    String rDate = reservation.getReservationDate();
                    switch (rDate.length()){
                        case 10:
                            year = Integer.parseInt(rDate.substring(6,10));
                            month = Integer.parseInt(rDate.substring(3,5))-1;
                            day = Integer.parseInt(rDate.substring(0,2));
                            break;
                        case 8:
                            year = Integer.parseInt(rDate.substring(4,8));
                            month = Integer.parseInt(String.valueOf(rDate.charAt(2)))-1;
                            day = Integer.parseInt(String.valueOf(rDate.charAt(0)));
                            break;
                        case 9:
                            if(rDate.charAt(1) == '.'){
                                //Case if day is lower than 10
                                day = Integer.parseInt(String.valueOf(rDate.charAt(0)));
                                month = Integer.parseInt(rDate.substring(2,4))-1;
                            }else{
                                //Case if the month is lower than 10
                                day = Integer.parseInt(rDate.substring(0,2));
                                month = Integer.parseInt(String.valueOf(rDate.charAt(3)))-1;
                            }
                            year = Integer.parseInt(rDate.substring(5,9));
                            break;
                    }
                }else{
                    //Set the values of the current day
                    day = c.get(Calendar.DAY_OF_MONTH);
                    month = c.get(Calendar.MONTH);
                    year = c.get(Calendar.YEAR);
                }
                //Create the datePickerDialog
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

        ReservationViewModel.Factory factoryReservation = new ReservationViewModel.Factory(getApplication(),reservationId);
        reservationViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factoryReservation).get(ReservationViewModel.class);

        ReservationListViewModel.Factory factoryReservationList = new ReservationListViewModel.Factory(getApplication());
        reservationListViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factoryReservationList).get(ReservationListViewModel.class);
        reservationListViewModel.getReservations().observe(this, reservationEntities -> {
            if(reservationEntities != null){
                reservations = reservationEntities;
            }
        });

        setupViewModel();
        setupPlayerSpinner();

        if(isEdit){
            reservationViewModel.getReservationWithPlayerCourt().observe(this, reservationPlayerCourt -> {
                        if (reservationPlayerCourt != null) {
                            reservationWithPlayerAndCourt = reservationPlayerCourt;
                            reservation = reservationPlayerCourt.reservation;
                            tvCourtName.setText(reservationWithPlayerAndCourt.court.getCourtsName());
                            etReservationDate.setText(reservationWithPlayerAndCourt.reservation.getReservationDate());
                            spReservationTime.setSelection(getIdxFromSpTimeSlot(reservationWithPlayerAndCourt.reservation.getTimeSlot()));
                            tvCourtPrice.setText(Double.toString(reservationPlayerCourt.court.getHourlyPrice()));
                            int positionPlSp = getIdxFromPlayer(reservationWithPlayerAndCourt.player);
                            spReservationPlayer.setSelection(positionPlSp);
                        }
                    }
            );
        }

        button.setOnClickListener(view -> {
            ReservationEntity reservation = getReservationFromFields();
            if(checkFields(reservation)){
                saveChanges(reservation);
                onBackPressed();
                toast.show();
            }
        });
    }

    /**
     * Check the UI fields according to 5 criteria.
     * @param reservation to check
     * @return false if a criteria is not satisfied, true if all criteria are satisfied.
     */
    private boolean checkFields(ReservationEntity reservation){
        //Check if the fields are not empty
        if(TextUtils.isEmpty(reservation.getReservationDate())){
            etReservationDate.setError(getString(R.string.errorRequired_reservation_date));
            etReservationDate.requestFocus();
            return false;
        }
        //Ensure that the time slot is not empty
        if(reservation.getTimeSlot() == getString(R.string.hint_sp_time)){
            tvTime.setError(getString(R.string.errorRequired_reservation_time));
            spReservationTime.requestFocus();
            return false;
        }
        //Ensure that the time and date are not in the past
        if(ReservationHelper.checkLaterDate(reservation.getReservationDate(), reservation.getTimeSlot())){
            reservationErrorDialog(R.string.dialog_reservation_past);
            return false;
        }

        if(isEdit){
            if(checkDateAndTimeChange(reservation)){
                //If we are editing an existing reservation, we check that there is no reservations for the same court, the same date and the same time
                if(checkReservationForTimeslot(reservation)){
                    reservationErrorDialog(R.string.dialog_reservation_exists);
                    return false;
                }
            }
        }
        else{
            if(checkReservationForTimeslot(reservation)){
                //Even if we are creating a new reservation, we check that there is no reservations for the same court, the same date and the same time
                reservationErrorDialog(R.string.dialog_reservation_exists);
                return false;
            }
        }
        return true;
    }

    /**
     * Create a generic error dialog that displays a custom text.
     * @param id of the string resource to display
     */
    private void reservationErrorDialog(int id){
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.dialog_reservation_exists_title);
        alertDialog.setCancelable(false);
        final TextView tvDeleteMessage = view.findViewById(R.id.tv_delete_item);
        tvDeleteMessage.setText(id);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Close", (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }

    /**
     * Save the changes in the database. If we are editing, we update the reservation otherwise we create a new one.
     * @param reservationToSave in the DB.
     */
    private void saveChanges(ReservationEntity reservationToSave){
        if(isEdit){
            //Update an existing reservation
            reservationViewModel.updateReservation(reservationToSave, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "update reservation: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "update reservation: failure", e);
                }
            });
        }
        else{
            //Create a new reservation
            reservationViewModel.createReservation(reservationToSave, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "create reservation: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "create reservation: failure", e);
                }
            });
        }
    }

    /**
     * Get the reservation from the UI fields.
     * @return The reservation from the UI fields.
     */
    private ReservationEntity getReservationFromFields(){
        ReservationEntity reservationFields;
        if(reservation == null) {
            reservationFields = new ReservationEntity();
            //Get the player from the selection of the spinner
            reservationFields.setCourtId(court.getId());
        }
        else{
            //If reservation not null, we retrieve the existing reservation
            reservationFields = reservation;
        }
        player = players.get(spReservationPlayer.getSelectedItemPosition());
        //Set the values
        reservationFields.setPlayerId(player.getId());
        reservationFields.setTimeSlot(spReservationTime.getSelectedItem().toString());
        reservationFields.setReservationDate(etReservationDate.getText().toString());

        return reservationFields;
    }

    /**
     * Check if there is already a reservation at the same date and time for the court of the reservation.
     * @param reservation to check time and date.
     * @return true if there already is a reservation for the same court at the same time and date. False otherwise.
     */
    private boolean checkReservationForTimeslot(ReservationEntity reservation){
        for(ReservationEntity r : reservations){
            if(Objects.equals(r.getCourtId(), reservation.getCourtId()) && Objects.equals(r.getReservationDate(), reservation.getReservationDate()) && Objects.equals(r.getTimeSlot(), reservation.getTimeSlot())){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the time or date have changed.
     * @param r reservation to check.
     * @return false if time and date haven't changed. True if they have.
     */
    private boolean checkDateAndTimeChange(ReservationEntity r){
        ReservationEntity reservationDb = getReservationFromDb(r.getId());
        if(reservationDb.getReservationDate().equals(r.getReservationDate()) && reservationDb.getTimeSlot().equals(r.getTimeSlot())){
            return false;
        }
        return true;
    }

    /**
     * Get the reservation from the DB.
     * @param id of the reservation to retrieve.
     * @return the reservation from the DB.
     */
    private ReservationEntity getReservationFromDb(String id){
        for (ReservationEntity r : reservations){
            if(Objects.equals(r.getId(), id)){
                return r;
            }
        }
        return null;
    }

    /**
     * Setup the players spinner form the adapter.
     */
    private void setupPlayerSpinner() {
        spReservationPlayer = findViewById(R.id.sp_cr_player);
        adapterPlayers = new PlayersListAdapter<>(this, R.layout.player_spinner_row, new ArrayList<>());
        spReservationPlayer.setAdapter(adapterPlayers);
    }

    /**
     * Update the data of the adapter.
     * @param players list with the new values.
     */
    private void updatePlayerSpinner(List<PlayerEntity> players){
        adapterPlayers.updateData(new ArrayList<>(players));
    }

    /**
     * Retrieve the players from the DB using a view-model called by the factory.
     */
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

    /**
     * Get the index of the time-slot spinner.
     * @param timeSlot to retrieve the index.
     * @return index of the time slot in the spinner.
     */
    private int getIdxFromSpTimeSlot(String timeSlot) {
        return Arrays.asList(getResources().getStringArray(R.array.times)).indexOf(timeSlot);
    }

    /**
     * Get the index of the player to set it on the spinner adapter.
     * @param player to find the index.
     * @return index of the player in the spinner.
     */
    private int getIdxFromPlayer(PlayerEntity player) {
        if (players != null) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).equals(player)) {
                    return i;
                }
            }
        }
        return 0;
    }

}