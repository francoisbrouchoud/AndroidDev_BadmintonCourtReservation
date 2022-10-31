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

import java.util.List;

public class PlayerListViewModel extends AndroidViewModel {

    private Application application;
    private PlayerRepository repository;
    private final MediatorLiveData<List<PlayerEntity>> observablePlayers;

    public PlayerListViewModel(@NonNull Application application, PlayerRepository playerRepository) {
        super(application);

        this.application = application;
        repository = playerRepository;

        observablePlayers = new MediatorLiveData<>();
        observablePlayers.setValue(null);

        LiveData<List<PlayerEntity>> players = playerRepository.getPlayers(application);
        observablePlayers.addSource(players, observablePlayers::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;
        private final PlayerRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ((BaseApp) application).getPlayerRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PlayerListViewModel(application, repository);
        }
    }

    public LiveData<List<PlayerEntity>> getPlayers(){
        return observablePlayers;
    }

    public void deletePlayer(PlayerEntity player, OnAsyncEventListener callback){
        repository.delete(player, callback, application);
    }
}
