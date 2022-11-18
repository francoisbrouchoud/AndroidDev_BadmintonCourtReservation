package ch.hevs.androiddev_badmintoncourtreservation;

import android.app.Application;
import ch.hevs.androiddev_badmintoncourtreservation.database.AppDatabase;
import ch.hevs.androiddev_badmintoncourtreservation.database.repository.CourtRepository;
import ch.hevs.androiddev_badmintoncourtreservation.database.repository.PlayerRepository;
import ch.hevs.androiddev_badmintoncourtreservation.database.repository.ReservationRepository;

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

    public CourtRepository getCourtRepository() {
        return CourtRepository.getInstance();
    }

    public ReservationRepository getReservationRepository() {
        return ReservationRepository.getInstance();
    }

}