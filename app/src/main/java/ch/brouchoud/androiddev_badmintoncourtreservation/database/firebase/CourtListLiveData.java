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

public class CourtListLiveData extends LiveData<List<CourtEntity>> {

    private static final String TAG = "CourtListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public CourtListLiveData(DatabaseReference ref) {
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
            setValue(toCourts(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<CourtEntity> toCourts(DataSnapshot snapshot) {
        List<CourtEntity> courts = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            CourtEntity entity = childSnapshot.getValue(CourtEntity.class);
            entity.setId(childSnapshot.getKey());
            courts.add(entity);
        }
        return courts;
    }
}
