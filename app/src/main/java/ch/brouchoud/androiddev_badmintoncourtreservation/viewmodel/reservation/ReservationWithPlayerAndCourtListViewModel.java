package ch.brouchoud.androiddev_badmintoncourtreservation.viewmodel.reservation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ch.brouchoud.androiddev_badmintoncourtreservation.BaseApp;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.repository.ReservationRepository;

public class ReservationWithPlayerAndCourtListViewModel extends AndroidViewModel {

    private ReservationRepository repository;

    private final MediatorLiveData<List<ReservationWithPlayerAndCourt>> observableReservationsWithPlayerAndCourt;

    public ReservationWithPlayerAndCourtListViewModel(@NonNull Application application, ReservationRepository reservationRepository) {
        super(application);

        repository = reservationRepository;

        observableReservationsWithPlayerAndCourt = new MediatorLiveData<>();
        observableReservationsWithPlayerAndCourt.setValue(null);

        LiveData<List<ReservationWithPlayerAndCourt>> reservationsWithPlayerAndCourt = repository.getReservationsWithPlayerAndCourt();
        observableReservationsWithPlayerAndCourt.addSource(reservationsWithPlayerAndCourt, observableReservationsWithPlayerAndCourt::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;
        private final ReservationRepository reservationRepository;


        public Factory(@NonNull Application application) {
            this.application = application;
            reservationRepository = ((BaseApp) application).getReservationRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //Create a new view model for the reservation list
            return (T) new ReservationWithPlayerAndCourtListViewModel(application, reservationRepository);
        }
    }

     public LiveData<List<ReservationWithPlayerAndCourt>> getReservationsWithPlayerAndCourt(){
        return observableReservationsWithPlayerAndCourt;
     }
}
