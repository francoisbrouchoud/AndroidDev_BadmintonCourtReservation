package ch.hevs.androiddev_badmintoncourtreservation.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import ch.hevs.androiddev_badmintoncourtreservation.BaseApp;
import ch.hevs.androiddev_badmintoncourtreservation.database.async.reservation.CreateReservation;
import ch.hevs.androiddev_badmintoncourtreservation.database.async.reservation.DeleteReservation;
import ch.hevs.androiddev_badmintoncourtreservation.database.async.reservation.UpdateReservation;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import ch.hevs.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;
import ch.hevs.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

import java.util.List;

public class ReservationRepository {

    private static ReservationRepository instance;

    public ReservationRepository() {
    }

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

    public LiveData<List<ReservationEntity>> getReservations(Application application){
        return ((BaseApp) application).getDatabase().reservationDao().getAll();
    }

    public LiveData<ReservationEntity> getReservation(final Long id, Application application){
        return ((BaseApp) application).getDatabase().reservationDao().getById(id);
    }

    /**
     * Live data for reservations with player and court (POJO)
     * @param application
     * @return LiveData : list of reservations with its player and court
     */
    public LiveData<List<ReservationWithPlayerAndCourt>> getReservationsWithPlayerAndCourt(Application application){
        return ((BaseApp) application).getDatabase().reservationDao().getReservationsWithPlayerAndCourt();
    }

    /**
     * Live data for reservations with player and court (POJO)
     * @param application
     * @return LiveData : reservation with its player and court
     */
    public LiveData<ReservationWithPlayerAndCourt> getReservationWithPlayerAndCourt(final Long id, Application application){
        return ((BaseApp) application).getDatabase().reservationDao().getReservationWithPlayerAndCourt(id);
    }

    public void insert(final ReservationEntity reservation, OnAsyncEventListener callback,
                       Application application) {
        new CreateReservation(application, callback).execute(reservation);
    }

    public void update(final ReservationEntity reservation, OnAsyncEventListener callback,
                       Application application) {
        new UpdateReservation(application, callback).execute(reservation);
    }

    public void delete(final ReservationEntity reservation, OnAsyncEventListener callback,
                       Application application) {
        new DeleteReservation(application, callback).execute(reservation);
    }
}
