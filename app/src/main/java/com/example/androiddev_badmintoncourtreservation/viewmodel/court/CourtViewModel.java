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

public class CourtViewModel extends AndroidViewModel {

    private Application application;
    private CourtRepository repository;
    private final MediatorLiveData<CourtEntity> observableCourt;

    public CourtViewModel(@NonNull Application application, final long courtId, CourtRepository courtRepository) {
        super(application);

        this.application = application;
        repository = courtRepository;

        observableCourt = new MediatorLiveData<>();
        observableCourt.setValue(null);

        LiveData<CourtEntity> court;
        court = repository.getCourt(courtId, application);
        observableCourt.addSource(court, observableCourt::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application application;
        private final long courtId;
        private final CourtRepository repository;

        public Factory(@NonNull Application application, long courtId){
            this.application = application;
            this.courtId = courtId;
            repository = ((BaseApp) application).getCourtRepository();
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
            return (T) new CourtViewModel(application, courtId, repository);
        }
    }

    public LiveData<CourtEntity> getCourt() { return observableCourt;}

    public void createCourt(CourtEntity court, OnAsyncEventListener callback){
        repository.insert(court, callback, application);
    }

    public void updateCourt(CourtEntity court, OnAsyncEventListener callback){
        repository.update(court, callback, application);
    }

    public void deleteCourt(CourtEntity court, OnAsyncEventListener callback){
        repository.delete(court, callback, application);
    }
}
