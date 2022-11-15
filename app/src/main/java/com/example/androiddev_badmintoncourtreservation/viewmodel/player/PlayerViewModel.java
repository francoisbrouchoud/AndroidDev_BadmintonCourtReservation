package com.example.androiddev_badmintoncourtreservation.viewmodel.player;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.androiddev_badmintoncourtreservation.BaseApp;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.database.repository.PlayerRepository;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

public class PlayerViewModel extends AndroidViewModel {

    private Application application;
    private PlayerRepository repository;
    private final MediatorLiveData<PlayerEntity> observablePlayer;

    public PlayerViewModel(@NonNull Application application, final long playerId, PlayerRepository playerRepository) {
        super(application);

        this.application = application;
        repository = playerRepository;

        observablePlayer = new MediatorLiveData<>();
        observablePlayer.setValue(null);

        LiveData<PlayerEntity> player;
        player = repository.getPlayer(playerId, application);
        observablePlayer.addSource(player, observablePlayer::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;
        private final long playerId;
        private final PlayerRepository repository;

        public Factory(@NonNull Application application, long playerId) {
            this.application = application;
            this.playerId = playerId;
            repository = ((BaseApp) application).getPlayerRepository();
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PlayerViewModel(application,playerId,repository);
        }
    }

    public LiveData<PlayerEntity> getPlayer(){
        return observablePlayer;
    }

    public void createPlayer(PlayerEntity player, OnAsyncEventListener callback){
        repository.insert(player, callback, application);
    }

    public void updatePlayer(PlayerEntity player, OnAsyncEventListener callback){
        repository.update(player, callback, application);
    }
}
