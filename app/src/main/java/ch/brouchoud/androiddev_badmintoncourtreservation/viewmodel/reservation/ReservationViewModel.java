package ch.brouchoud.androiddev_badmintoncourtreservation.viewmodel.reservation;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ch.brouchoud.androiddev_badmintoncourtreservation.BaseApp;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.repository.CourtRepository;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.repository.PlayerRepository;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.repository.ReservationRepository;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

public class ReservationViewModel extends AndroidViewModel {

    private Application application;
    private ReservationRepository repository;
    // final MediatorLiveData<ReservationEntity> observableReservation;
    private final MediatorLiveData<ReservationWithPlayerAndCourt> observableReservationPlayerCourt;

    public ReservationViewModel(@NonNull Application application, final String reservationId, ReservationRepository reservationRepository, PlayerRepository playerRepository, CourtRepository courtRepository) {
        super(application);

        this.application = application;
        repository = reservationRepository;

        observableReservationPlayerCourt = new MediatorLiveData<>();
        observableReservationPlayerCourt.setValue(null);

        LiveData<ReservationWithPlayerAndCourt> reservationPlayerCourt;
        reservationPlayerCourt = reservationRepository.getReservationWithPlayerAndCourt(reservationId);

        observableReservationPlayerCourt.addSource(reservationPlayerCourt, observableReservationPlayerCourt::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application application;
        private final String reservationId;
        private final ReservationRepository reservationRepository;
        private final PlayerRepository playerRepository;
        private final CourtRepository courtRepository;

        public Factory(@NonNull Application application, String reservationId) {
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
     * Get the observable reservation with player and court.
     * @return LiveData of the reservation with the player and the court.
     */
    public LiveData<ReservationWithPlayerAndCourt> getReservationWithPlayerCourt(){
        return observableReservationPlayerCourt;
    }

    /**
     * Create a new reservation.
     * @param reservation to create.
     * @param callback
     */
    public void createReservation(ReservationEntity reservation, OnAsyncEventListener callback){
        repository.insert(reservation, callback);
    }

    /**
     * Update an existing reservation.
     * @param reservation to update.
     * @param callback
     */
    public void updateReservation(ReservationEntity reservation, OnAsyncEventListener callback){
        repository.update(reservation, callback);
    }
}
