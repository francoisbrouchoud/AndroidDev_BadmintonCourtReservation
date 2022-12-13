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

public class PlayerListLiveData extends LiveData<List<PlayerEntity>> {

    private static final String TAG = "PlayerListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public PlayerListLiveData(DatabaseReference ref) {
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
            setValue(toPlayers(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<PlayerEntity> toPlayers(DataSnapshot snapshot) {
        List<PlayerEntity> players = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            PlayerEntity entity = childSnapshot.getValue(PlayerEntity.class);
            entity.setId(childSnapshot.getKey());
            players.add(entity);
        }
        return players;
    }
}
