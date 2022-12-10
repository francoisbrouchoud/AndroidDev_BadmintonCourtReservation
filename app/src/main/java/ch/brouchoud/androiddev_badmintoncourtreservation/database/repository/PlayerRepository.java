package ch.brouchoud.androiddev_badmintoncourtreservation.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import ch.brouchoud.androiddev_badmintoncourtreservation.BaseApp;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.async.player.CreatePlayer;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.async.player.DeletePlayer;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.async.player.UpdatePlayer;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
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

    public LiveData<PlayerEntity> getPlayer(final Long id, Application application){
        return ((BaseApp) application).getDatabase().playerDao().getById(id);
    }

    public LiveData<List<PlayerEntity>> getPlayers(Application application){
        return ((BaseApp) application).getDatabase().playerDao().getAll();
    }

    public void insert(final PlayerEntity player, OnAsyncEventListener callback,
                       Application application) {
        new CreatePlayer(application, callback).execute(player);
    }

    public void update(final PlayerEntity player, OnAsyncEventListener callback,
                       Application application) {
        new UpdatePlayer(application, callback).execute(player);
    }

    public void delete(final PlayerEntity player, OnAsyncEventListener callback,
                       Application application) {
        new DeletePlayer(application, callback).execute(player);
    }
}
