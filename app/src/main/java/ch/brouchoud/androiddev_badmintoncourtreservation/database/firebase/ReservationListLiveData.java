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

import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;

public class ReservationListLiveData extends LiveData<List<ReservationEntity>> {

    private static final String TAG = "ReservationListLiveData";

    private final DatabaseReference reference;
    private final ReservationListLiveData.MyValueEventListener listener = new ReservationListLiveData.MyValueEventListener();

    public ReservationListLiveData(DatabaseReference ref) {
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
            toReservations(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<ReservationEntity> toReservations(DataSnapshot snapshot) {
        List<ReservationEntity> reservations = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ReservationEntity entity = childSnapshot.getValue(ReservationEntity.class);
            entity.setId(childSnapshot.getKey());
            reservations.add(entity);
        }
        return reservations;
    }
}
