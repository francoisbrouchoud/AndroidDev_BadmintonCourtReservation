package ch.hevs.androiddev_badmintoncourtreservation.viewmodel.reservation;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ch.hevs.androiddev_badmintoncourtreservation.BaseApp;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import ch.hevs.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;
import ch.hevs.androiddev_badmintoncourtreservation.database.repository.CourtRepository;
import ch.hevs.androiddev_badmintoncourtreservation.database.repository.PlayerRepository;
import ch.hevs.androiddev_badmintoncourtreservation.database.repository.ReservationRepository;
import ch.hevs.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

public class ReservationViewModel extends AndroidViewModel {

    private Application application;
    private ReservationRepository repository;
    private final MediatorLiveData<ReservationEntity> observableReservation;
    private final MediatorLiveData<ReservationWithPlayerAndCourt> observableReservationPlayerCourt;

    public ReservationViewModel(@NonNull Application application, final long reservationId, ReservationRepository reservationRepository, PlayerRepository playerRepository, CourtRepository courtRepository) {
        super(application);

        this.application = application;
        repository = reservationRepository;

        observableReservation = new MediatorLiveData<>();
        observableReservationPlayerCourt = new MediatorLiveData<>();
        observableReservation.setValue(null);
        observableReservationPlayerCourt.setValue(null);

        LiveData<ReservationEntity> reservation;
        reservation = repository.getReservation(reservationId, application);

        LiveData<ReservationWithPlayerAndCourt> reservationPlayerCourt = reservationRepository.getReservationWithPlayerAndCourt(reservationId, application);

        observableReservation.addSource(reservation, observableReservation::setValue);
        observableReservationPlayerCourt.addSource(reservationPlayerCourt, observableReservationPlayerCourt::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application application;
        private final long reservationId;
        private final ReservationRepository reservationRepository;
        private final PlayerRepository playerRepository;
        private final CourtRepository courtRepository;

        public Factory(@NonNull Application application, long reservationId) {
            this.application = application;
            this.reservationId = reservationId;
            reservationRepository = ((BaseApp) application).getReservationRepository();
            playerRepository = ((BaseApp) application).getPlayerRepository();
            courtRepository = ((BaseApp) application).getCourtRepository();
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //Create a new view model of the reservation
            return (T) new ReservationViewModel(application, reservationId, reservationRepository, playerRepository, courtRepository);
        }
    }

    /**
     * Get the observable reservation.
     * @return LiveData of the reservation.
     */
    public LiveData<ReservationEntity> getReservation(){
        return observableReservation;
    }


    public LiveData<ReservationWithPlayerAndCourt> getReservationWithPlayerCourt(){
        return observableReservationPlayerCourt;
    }


    /**
     * Create a new reservation.
     * @param reservation to create.
     * @param callback
     */
    public void createReservation(ReservationEntity reservation, OnAsyncEventListener callback){
        repository.insert(reservation, callback, application);
    }

    /**
     * Update an existing reservation.
     * @param reservation to update.
     * @param callback
     */
    public void updateReservation(ReservationEntity reservation, OnAsyncEventListener callback){
        repository.update(reservation, callback, application);
    }
}
