package ch.brouchoud.androiddev_badmintoncourtreservation.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;

public class ReservationWithPlayerAndCourtLiveData extends LiveData<ReservationWithPlayerAndCourt> {

    private static final String TAG = "ReservationWithPlayerAndCourtLiveData";

    private final DatabaseReference reference;
    private final ReservationWithPlayerAndCourtLiveData.MyValueEventListener listener = new ReservationWithPlayerAndCourtLiveData.MyValueEventListener();

    public ReservationWithPlayerAndCourtLiveData(DatabaseReference ref){
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

    private ReservationWithPlayerAndCourt toReservationWithPlayerAndCourt(DataSnapshot dataSnapshot) {
            ReservationWithPlayerAndCourt reservationWithPlayerAndCourt = new ReservationWithPlayerAndCourt();
            reservationWithPlayerAndCourt.court = dataSnapshot.child("court").getValue(CourtEntity.class);
            reservationWithPlayerAndCourt.player = dataSnapshot.child("player").getValue(PlayerEntity.class);
            reservationWithPlayerAndCourt.reservation = dataSnapshot.child("reservation").getValue(ReservationEntity.class);
            reservationWithPlayerAndCourt.reservation.setId(dataSnapshot.child("reservation").getKey());
            reservationWithPlayerAndCourt.setId(dataSnapshot.getKey());

        return reservationWithPlayerAndCourt;
    }
}
