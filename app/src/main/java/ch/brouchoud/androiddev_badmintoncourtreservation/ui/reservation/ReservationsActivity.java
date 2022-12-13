package ch.brouchoud.androiddev_badmintoncourtreservation.ui.reservation;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import ch.brouchoud.androiddev_badmintoncourtreservation.R;
import ch.brouchoud.androiddev_badmintoncourtreservation.adapter.ReservationsRecyclerAdapter;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;
import ch.brouchoud.androiddev_badmintoncourtreservation.ui.BaseActivity;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;
import ch.brouchoud.androiddev_badmintoncourtreservation.viewmodel.reservation.ReservationListViewModel;
import java.util.List;

public class ReservationsActivity extends BaseActivity {

    private static final String TAG = "ReservationsActivity";

    private ReservationsRecyclerAdapter<ReservationWithPlayerAndCourt> adapter;
    private ReservationListViewModel listViewModel;
    private List<ReservationWithPlayerAndCourt> reservationsPlayerCourt;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_reservations, frameLayout);
        navigationView.setCheckedItem(R.id.nav_none);

        setTitle(R.string.reservation_homePage);

        recyclerView = findViewById(R.id.ReservationRv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReservationsRecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Edit the reservation
                Log.d(TAG, "clicked on position: " + position);
                Log.d(TAG, "clicked on reservation: " + reservationsPlayerCourt.get(position).reservation.getId());
                Intent intent = new Intent(ReservationsActivity.this, CourtReservationActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("reservationId", reservationsPlayerCourt.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "long clicked on position: " + position);
                Log.d(TAG, "long clicked on reservation: " + reservationsPlayerCourt.get(position).reservation.getId());
                deleteReservationDialog(position);
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ReservationListViewModel.Factory factoryReservations = new ReservationListViewModel.Factory(getApplication());
        listViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factoryReservations).get(ReservationListViewModel.class);
        listViewModel.getReservationsWithPlayerCourt().observe(this, reservationEntities -> {
            if(reservationEntities != null){
                reservationsPlayerCourt = reservationEntities;
                adapter.setData(reservationsPlayerCourt);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * Create a dialog view to ask the user to confirm the reservation deletion.
     * @param position of the item on which the user has clicked.
     */
    private void deleteReservationDialog(int position) {
        //final ReservationEntity reservation = reservationsPlayerCourt.get(position).reservation;
        final ReservationWithPlayerAndCourt reservationPC = reservationsPlayerCourt.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.alert_reservationActivity_deleteTitle);
        alertDialog.setCancelable(false);
        final TextView tvDeleteMessage = view.findViewById(R.id.tv_delete_item);
        tvDeleteMessage.setText(R.string.alert_reservationActivity_deleteMessage);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alert_playersActivity_btnPositive), (dialog, which) -> {
            Toast toast = Toast.makeText(this, getString(R.string.toast_reservationActivity_delete), Toast.LENGTH_LONG);
            listViewModel.deleteReservation(reservationPC, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "delete reservation: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "delete reservation: failure", e);
                }
            });
            toast.show();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.alert_playersActivity_btnNegative), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }
}