package ch.hevs.androiddev_badmintoncourtreservation.viewmodel.court;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ch.hevs.androiddev_badmintoncourtreservation.BaseApp;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.hevs.androiddev_badmintoncourtreservation.database.repository.CourtRepository;
import ch.hevs.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

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
            //Create a new view model of the courts
            return (T) new CourtViewModel(application, courtId, repository);
        }
    }

    /**
     * Get the observable court.
     * @return LiveData of the court.
     */
    public LiveData<CourtEntity> getCourt() { return observableCourt;}

    /**
     * Create a new court.
     * @param court to create.
     * @param callback
     */
    public void createCourt(CourtEntity court, OnAsyncEventListener callback){
        repository.insert(court, callback, application);
    }

    /**
     * Update an existing court.
     * @param court to update.
     * @param callback
     */
    public void updateCourt(CourtEntity court, OnAsyncEventListener callback){
        repository.update(court, callback, application);
    }

    /**
     * Delete a court.
     * @param court to delete.
     * @param callback
     */
    public void deleteCourt(CourtEntity court, OnAsyncEventListener callback){
        repository.delete(court, callback, application);
    }
}
