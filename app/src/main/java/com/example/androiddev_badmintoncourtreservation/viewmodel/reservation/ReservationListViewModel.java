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
import com.example.androiddev_badmintoncourtreservation.database.repository.ReservationRepository;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

import java.util.List;

public class ReservationListViewModel extends AndroidViewModel {

    private Application application;
    private ReservationRepository repository;
    private final MediatorLiveData<List<ReservationEntity>> observableReservations;


    public ReservationListViewModel(@NonNull Application application, ReservationRepository reservationRepository) {
        super(application);

        this.application = application;
        repository = reservationRepository;

        observableReservations = new MediatorLiveData<>();
        observableReservations.setValue(null);

        LiveData<List<ReservationEntity>> reservations = reservationRepository.getReservations(application);
        observableReservations.addSource(reservations, observableReservations::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;
        private final ReservationRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ((BaseApp) application).getReservationRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ReservationListViewModel(application, repository);
        }
    }

    public LiveData<List<ReservationEntity>> getReservations(){
        return observableReservations;
    }

    public void deleteReservation(ReservationEntity reservation, OnAsyncEventListener callback){
        repository.delete(reservation, callback, application);
    }
}
