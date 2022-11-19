package ch.hevs.androiddev_badmintoncourtreservation.database;

import android.os.AsyncTask;
import android.util.Log;

import ch.hevs.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;

/**
 * Generate sample data
 */
public class DatabaseInitializer {
    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Insert sample data");
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

    private static void addReservation(final AppDatabase db, final Long courtId, final Long playerId, String timeSlot, String reservationDate){
        ReservationEntity reservation = new ReservationEntity(courtId, playerId, timeSlot, reservationDate);
        db.reservationDao().insert(reservation);
    }


    private static void populateWithSampleData(AppDatabase db) {

        addPlayer(db, "Luca", "Del Buono", "28.12.2000", "m", "0791234567", "1950 Sion");
        addPlayer(db, "François", "Brouchoud", "07.08.1998", "m", "0791234567", "1890 St-Maurice");
        addPlayer(db, "Steve", "Jobs", "24.02.1955", "m", "012345678", "San Francisco");
        addPlayer(db, "Elon", "Musk", "28.06.1971", "m", "012345678", "Pretoria");
        addPlayer(db, "Larry", "Page", "26.03.1973", "m", "012345678", "East Lansing");
        addPlayer(db, "Bill", "Gates", "28.10.1955", "m", "012345678", "Seattle");
        addPlayer(db, "Mark", "Zuckerberg", "14.05.1984", "m", "012345678", "New York");
        addPlayer(db, "Virginia", "Rometty", "29.07.1957", "f", "012345678", "Chicago");
        addPlayer(db, "Serena", "Williams", "26.09.1981", "f", "012345678", "Saginaw");
        addPlayer(db, "Bugs", "Bunny", "01.01.1940", "Other", "012345678", "US");
        addPlayer(db, "Homer", "Simpson", "01.01.1980", "Other", "012345678", "US");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addCourt(db, "Les Iles : Terrain 1", "Terrain avec fort éclairage", "Tennis Les Iles, Sion",  24);
        addCourt(db, "Les Iles : Terrain 2", "Terrain avec fort éclairage", "Tennis Les Iles, Sion",  24);
        addCourt(db, "Les Iles : Terrain 3", "Terrain avec fort éclairage", "Tennis Les Iles, Sion",  24);
        addCourt(db, "Badminton Pont-Chalais 1", "Prix imbattables", "Rue de Pont-Chalais 30, Sierre",  14);
        addCourt(db, "Badminton Pont-Chalais 2", "Prix imbattables", "Rue de Pont-Chalais 30, Sierre",  14);
        addCourt(db, "Centre de Tennis La Moubra 1", "Salle bien refroidie", "Route de la Moubra 73, Crans-Montana",  15);
        addCourt(db, "Centre de Tennis La Moubra 2", "Salle bien refroidie", "Route de la Moubra 73, Crans-Montana",  15);
        addCourt(db, "Centre de Tennis La Moubra 3", "Salle bien refroidie", "Route de la Moubra 73, Crans-Montana",  15);
        addCourt(db, "Martigny Sport 1", "Dans la zone industrielle", "Rue du Châble-Bet 22, 1920 Martigny",  20);
        addCourt(db, "Martigny Sport 2", "Dans la zone industrielle", "Rue du Châble-Bet 22, 1920 Martigny",  20);
        addCourt(db, "Martigny Sport 3", "Dans la zone industrielle", "Rue du Châble-Bet 22, 1920 Martigny",  20);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addReservation(db, 2L, 1L, "19:00–20:00", "25.11.2022");
        addReservation(db, 5L, 2L, "14:00–15:00", "26.11.2022");
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db){
            database = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateWithSampleData(database);
            return null;
        }
    }
}
