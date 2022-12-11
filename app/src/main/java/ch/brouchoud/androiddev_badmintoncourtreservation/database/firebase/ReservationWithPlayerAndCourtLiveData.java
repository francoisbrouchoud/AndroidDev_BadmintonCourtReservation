package ch.brouchoud.androiddev_badmintoncourtreservation.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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
            ReservationEntity entity = dataSnapshot.getValue(ReservationEntity.class);
            entity.setId(dataSnapshot.getKey());
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
