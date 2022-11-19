package ch.hevs.androiddev_badmintoncourtreservation.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import ch.hevs.androiddev_badmintoncourtreservation.database.dao.CourtDao;
import ch.hevs.androiddev_badmintoncourtreservation.database.dao.PlayerDao;
import ch.hevs.androiddev_badmintoncourtreservation.database.dao.ReservationDao;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;

import java.util.concurrent.Executors;

@Database(entities = {PlayerEntity.class, CourtEntity.class, ReservationEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    private static final String TAG = "AppDatabase";
    private static AppDatabase instance;
    private static final String DATABASE_NAME = "BadmintonReservation_Database";

    public abstract PlayerDao playerDao();
    public abstract CourtDao courtDao();
    public abstract ReservationDao reservationDao();
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private static AppDatabase buildDatabase(final Context appContext) {
        Log.i(TAG, "Database will be initialized.");
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(appContext);
                            initializeDemoData(database);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    public static void initializeDemoData(final AppDatabase database) {
        Executors.newSingleThreadExecutor().execute(() -> {
            database.runInTransaction(() -> {
                Log.i(TAG, "Wipe database.");
                database.playerDao().deleteAll();
                database.courtDao().deleteAll();
                database.reservationDao().deleteAll();

                DatabaseInitializer.populateDatabase(database);
            });
        });
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            Log.i(TAG, "Database initialized.");
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }
}
