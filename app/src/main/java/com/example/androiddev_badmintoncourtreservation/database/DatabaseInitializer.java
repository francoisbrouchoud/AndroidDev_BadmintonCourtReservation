package com.example.androiddev_badmintoncourtreservation.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;

import java.util.Date;

public class DatabaseInitializer {
    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Insertion de données test");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addPlayer(final AppDatabase db, final String firstname, final String lastname, final String birthdate, final String gender, final String phone, final String address){
        PlayerEntity player = new PlayerEntity(firstname, lastname, birthdate, gender, phone, address);
        db.playerDao().insert(player);
    }

    private static void addCourt(final AppDatabase db, final String courtsName, String description, String address, double hourlyPrice){
        CourtEntity court = new CourtEntity(courtsName, description, address, hourlyPrice);
        db.courtDao().insert(court);
    }

    /*
    private static void addReservation(final AppDatabase db, final Long courtId, final Long playerId, String timeSlot, String reservationDate){
        ReservationEntity reservation = new ReservationEntity(courtId, playerId, timeSlot, reservationDate);
        db.reservationDao().insert(reservation);
    }

     */

    private static void populateWithTestData(AppDatabase db) {
        db.playerDao().deleteAll();
/*
        addPlayer(db,
                "Roger", "Federer", "01.01.1980","m", "0790123456", "sampleAddress"
        );
        addPlayer(db,
                "Raf", "Nadal", "01.01.1970","m", "0790523456", "sampleAddress2"
        );
        addPlayer(db,
                "Toto", "Test", "01.01.1960","m", "0790544446", "sampleAddress3"
        );
*/
        addPlayer(db,
                "Luca", "Del Buono", "28.12.2000", "m", "0791234567", "1950 Sion");
        addPlayer(db,
                "François", "Brouchoud", "07.08.1998", "m", "0791234567", "1890 St-Maurice");
        //Avant d'ajouter d'autres données d'autres tables
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addCourt(db, "Centre de Sports Les Iles", "Terrain avec fort éclairage", "Tennis Les Iles, Sion",  24);
        addCourt(db, "Badminton Pont-Chalais", "Prix imbattables", "Rue de Pont-Chalais 30, Sierre",  14);
        addCourt(db, "Centre de Tennis La Moubra", "Salle bien refroidie", "Route de la Moubra 73, Crans-Montana",  15);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       // addReservation(db, 1L, 1L, "16-18", "12.11.2022");
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
