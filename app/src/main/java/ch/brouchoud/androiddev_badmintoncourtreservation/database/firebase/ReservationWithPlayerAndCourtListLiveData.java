package ch.brouchoud.androiddev_badmintoncourtreservation.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;

public class ReservationWithPlayerAndCourtListLiveData extends LiveData<List<ReservationWithPlayerAndCourt>> {

    private static final String TAG = "ReservationWithPlayerAndCourtListLiveData";

    private final DatabaseReference reference;
    private final ReservationWithPlayerAndCourtListLiveData.MyValueEventListener listener = new ReservationWithPlayerAndCourtListLiveData.MyValueEventListener();

    public ReservationWithPlayerAndCourtListLiveData(DatabaseReference ref){
        reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toReservationWithPlayerAndCourt(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<ReservationWithPlayerAndCourt> toReservationWithPlayerAndCourt(DataSnapshot snapshot){
        List<ReservationWithPlayerAndCourt> reservationWithPlayerAndCourts = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()){
            ReservationWithPlayerAndCourt reservationWithPlayerAndCourt = new ReservationWithPlayerAndCourt();
            reservationWithPlayerAndCourt.court = childSnapshot.child("court").getValue(CourtEntity.class);
            reservationWithPlayerAndCourt.player = childSnapshot.child("player").getValue(PlayerEntity.class);
            reservationWithPlayerAndCourt.reservation = childSnapshot.child("reservation").getValue(ReservationEntity.class);
            reservationWithPlayerAndCourt.reservation.setId(childSnapshot.child("reservation").getKey());
            reservationWithPlayerAndCourt.setId(childSnapshot.getKey());
            reservationWithPlayerAndCourts.add(reservationWithPlayerAndCourt);
        }
        return reservationWithPlayerAndCourts;
    }
}
