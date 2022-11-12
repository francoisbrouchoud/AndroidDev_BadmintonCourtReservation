package com.example.androiddev_badmintoncourtreservation.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;

import java.util.Date;

public class DatabaseInitializer {
    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Insertion de données test");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addPlayer(final AppDatabase db, final String firstname, final String lastname, final String birthdate, final String gender, final String phone, final String address, final String place, final int level){
        PlayerEntity player = new PlayerEntity(firstname, lastname, birthdate, gender, phone, address, place, level);
        db.playerDao().insert(player);
    }

    private static void addCourt(final AppDatabase db, final String courtsName, String description, String address, String place, String imagePath, double hourlyPrice){
        CourtEntity court = new CourtEntity(courtsName, description, address, place, imagePath, hourlyPrice);

    }

    private static void populateWithTestData(AppDatabase db) {
        db.playerDao().deleteAll();

        addPlayer(db,
                "Roger", "Federer", "01.01.1980","m", "0790123456", "sampleAddress", "Basel", 10
        );
        addPlayer(db,
                "Raf", "Nadal", "01.01.1970","m", "0790523456", "sampleAddress2", "Madrid", 9
        );
        addPlayer(db,
                "Toto", "Test", "01.01.1960","m", "0790544446", "sampleAddress3", "Here", 89
        );

        //Avant d'ajouter d'autres données d'autres tables
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addCourt(db, "Badminton des Iles", "Terrain avec fort éclairage", "Les Iles", "1950 Sion", "imageSion.png", 20);
        addCourt(db, "Badminton de Chippis", "claqué au sol", "L'Usine 41", "Chippis", "pasbeau.png", 10);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db){
            database = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateWithTestData(database);
            return null;
        }
    }
}
