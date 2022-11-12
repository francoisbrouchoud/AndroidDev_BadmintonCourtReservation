package com.example.androiddev_badmintoncourtreservation.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.androiddev_badmintoncourtreservation.BaseApp;
import com.example.androiddev_badmintoncourtreservation.database.async.player.CreatePlayer;
import com.example.androiddev_badmintoncourtreservation.database.async.player.DeletePlayer;
import com.example.androiddev_badmintoncourtreservation.database.async.player.UpdatePlayer;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

import java.util.List;

public class ReservationRepository {

    private static ReservationRepository instance;

    public ReservationRepository() {
    }
/*
    public static ReservationRepository getInstance(){
        if(instance == null){
            synchronized (ReservationRepository.class){
                if(instance == null){
                    instance = new ReservationRepository();
                }
            }
        }
        return instance;
    }
/*
    public LiveData<List<ReservationEntity>> getReservation(Application application){
        return ((BaseApp) application).getDatabase().ReservationDao().getAll();
    }
*/
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
