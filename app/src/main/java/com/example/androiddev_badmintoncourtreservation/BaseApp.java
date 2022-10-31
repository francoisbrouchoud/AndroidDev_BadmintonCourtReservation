package com.example.androiddev_badmintoncourtreservation;

import android.app.Application;

import com.example.androiddev_badmintoncourtreservation.database.AppDatabase;
import com.example.androiddev_badmintoncourtreservation.database.repository.PlayerRepository;


public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public PlayerRepository getPlayerRepository() {
        return PlayerRepository.getInstance();
    }

}