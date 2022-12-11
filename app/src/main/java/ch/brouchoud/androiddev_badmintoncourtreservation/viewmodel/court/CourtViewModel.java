package ch.brouchoud.androiddev_badmintoncourtreservation.viewmodel.court;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ch.brouchoud.androiddev_badmintoncourtreservation.BaseApp;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.repository.CourtRepository;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

public class CourtViewModel extends AndroidViewModel {

    private Application application;
    private CourtRepository repository;
    private final MediatorLiveData<CourtEntity> observableCourt;

    public CourtViewModel(@NonNull Application application, final String courtId, CourtRepository courtRepository) {
        super(application);

        this.application = application;
        repository = courtRepository;

        observableCourt = new MediatorLiveData<>();
        observableCourt.setValue(null);

        if(courtId != null){
            LiveData<CourtEntity> court;
            court = repository.getCourt(courtId);
            observableCourt.addSource(court, observableCourt::setValue);
        }
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application application;
        private final String courtId;
        private final CourtRepository repository;

        public Factory(@NonNull Application application, String courtId){
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
        ((BaseApp) getApplication()).getCourtRepository().insert(court, callback);
    }

    /**
     * Update an existing court.
     * @param court to update.
     * @param callback
     */
    public void updateCourt(CourtEntity court, OnAsyncEventListener callback){
        ((BaseApp) getApplication()).getCourtRepository().update(court, callback);
    }

    /**
     * Delete a court.
     * @param court to delete.
     * @param callback
     */
    public void deleteCourt(CourtEntity court, OnAsyncEventListener callback){
        ((BaseApp) getApplication()).getCourtRepository().delete(court, callback);
    }
}
