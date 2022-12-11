package ch.brouchoud.androiddev_badmintoncourtreservation.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.firebase.CourtListLiveData;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.firebase.PlayerListLiveData;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.firebase.PlayerLiveData;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

import java.util.List;

public class PlayerRepository {

    private static PlayerRepository instance;

    public PlayerRepository() {
    }

    public static PlayerRepository getInstance(){
        if(instance == null){
            synchronized (PlayerRepository.class){
                if(instance == null){
                    instance = new PlayerRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<PlayerEntity> getPlayer(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("players")
                .child(id);
        return new PlayerLiveData(reference);
    }

    public LiveData<List<PlayerEntity>> getPlayers(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("players");
        return new PlayerListLiveData(reference);
    }

    public void insert(final PlayerEntity player, OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("players");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("players")
                .child(key)
                .setValue(player, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final PlayerEntity player, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("players")
                .child(player.getId())
                .updateChildren(player.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final PlayerEntity player, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("players")
                .child(player.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
