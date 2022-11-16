package com.example.androiddev_badmintoncourtreservation.viewmodel.reservation;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.androiddev_badmintoncourtreservation.BaseApp;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import com.example.androiddev_badmintoncourtreservation.database.pojo.ReservationWithCourt;
import com.example.androiddev_badmintoncourtreservation.database.repository.PlayerRepository;
import com.example.androiddev_badmintoncourtreservation.database.repository.ReservationRepository;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import java.util.List;

public class ReservationListViewModel extends AndroidViewModel {

    private Application application;
    private ReservationRepository repository;
    private final MediatorLiveData<List<ReservationEntity>> observableReservations;
    private final MediatorLiveData<List<ReservationWithCourt>> observableReservationsWithCourt ;

    public ReservationListViewModel(@NonNull Application application, final Long id, ReservationRepository reservationRepository, PlayerRepository playerRepository) {
        super(application);

        this.application = application;
        repository = reservationRepository;

        observableReservations = new MediatorLiveData<>();
        observableReservationsWithCourt = new MediatorLiveData<>();

        observableReservations.setValue(null);
        observableReservationsWithCourt.setValue(null);

        LiveData<List<ReservationEntity>> reservations = reservationRepository.getReservations(application);
        LiveData<List<ReservationWithCourt>> reservationsCourt = reservationRepository.getReservationsWithCourt(application);

        observableReservations.addSource(reservations, observableReservations::setValue);
        observableReservationsWithCourt.addSource(reservationsCourt, observableReservationsWithCourt::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;

        private final ReservationRepository reservationRepository;

        private final PlayerRepository playerRepository;

        private final Long id;

        public Factory(@NonNull Application application, Long id) {
            this.application = application;
            this.id = id;
            reservationRepository = ((BaseApp) application).getReservationRepository();
            playerRepository = ((BaseApp) application).getPlayerRepository();
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //Create a new view model for the reservation list
            return (T) new ReservationListViewModel(application, id, reservationRepository, playerRepository);
        }
    }

    /**
     * Get the observable reservations.
     * @return LiveData of the reservations.
     */
    public LiveData<List<ReservationEntity>> getReservations(){
        return observableReservations;
    }

    public LiveData<List<ReservationWithCourt>> getReservationsWithCourt(){
        return observableReservationsWithCourt;
    }

    /**
     * Delete a reservation.
     * @param reservation to delete.
     * @param callback
     */
    public void deleteReservation(ReservationEntity reservation, OnAsyncEventListener callback){
        repository.delete(reservation, callback, application);
    }
}
