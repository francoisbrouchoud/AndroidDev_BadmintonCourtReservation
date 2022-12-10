package ch.brouchoud.androiddev_badmintoncourtreservation.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import ch.brouchoud.androiddev_badmintoncourtreservation.BaseApp;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.async.court.CreateCourt;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.async.court.DeleteCourt;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.async.court.UpdateCourt;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

import java.util.List;

public class CourtRepository {

    private static CourtRepository instance;

    public CourtRepository() {
    }

    public static CourtRepository getInstance(){
        if(instance == null){
            synchronized (CourtRepository.class){
                if(instance == null){
                    instance = new CourtRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<CourtEntity> getCourt(final Long id, Application application){
        return ((BaseApp) application).getDatabase().courtDao().getById(id);
    }

    public LiveData<List<CourtEntity>> getCourts(Application application){
        return ((BaseApp) application).getDatabase().courtDao().getAll();
    }

    public void insert(final CourtEntity court, OnAsyncEventListener callback,
                       Application application) {
        new CreateCourt(application, callback).execute(court);
    }

    public void update(final CourtEntity court, OnAsyncEventListener callback,
                       Application application) {
        new UpdateCourt(application, callback).execute(court);
    }

    public void delete(final CourtEntity court, OnAsyncEventListener callback,
                       Application application) {
        new DeleteCourt(application, callback).execute(court);
    }
}
