package com.example.androiddev_badmintoncourtreservation.viewmodel.court;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.androiddev_badmintoncourtreservation.BaseApp;
import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.database.repository.CourtRepository;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import java.util.List;

public class CourtListViewModel extends AndroidViewModel {

    private Application application;
    private CourtRepository repository;
    private final MediatorLiveData<List<CourtEntity>> observableCourts;

    public CourtListViewModel(@NonNull Application application, CourtRepository courtRepository) {
        super(application);

        this.application = application;
        repository = courtRepository;

        observableCourts = new MediatorLiveData<>();
        observableCourts.setValue(null);

        LiveData<List<CourtEntity>> courts = courtRepository.getCourts(application);
        observableCourts.addSource(courts, observableCourts::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;
        private final CourtRepository repository;

        public Factory(@NonNull Application application){
            this.application = application;
            repository = ((BaseApp) application).getCourtRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //Create a new view model for the court list
            return (T) new CourtListViewModel(application, repository);
        }
    }

    /**
     * Get the observable courts.
     * @return LiveData of the courts.
     */
    public LiveData<List<CourtEntity>> getCourts(){
        return observableCourts;
    }
}
