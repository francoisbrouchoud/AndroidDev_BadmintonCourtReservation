package ch.brouchoud.androiddev_badmintoncourtreservation.database.repository;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.firebase.CourtListLiveData;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.firebase.CourtLiveData;
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

    public LiveData<CourtEntity> getCourt(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("courts")
                .child(id);
        return new CourtLiveData(reference);
    }

    public LiveData<List<CourtEntity>> getCourts(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("courts");
        return new CourtListLiveData(reference);
    }

    public void insert(final CourtEntity court, OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("courts");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("courts")
                .child(key)
                .setValue(court, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final CourtEntity court, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("courts")
                .child(court.getId())
                .updateChildren(court.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final CourtEntity court, OnAsyncEventListener callback) {
      FirebaseDatabase.getInstance()
              .getReference("courts")
              .child(court.getId())
              .removeValue((databaseError, databaseReference) -> {
                  if (databaseError != null) {
                      callback.onFailure(databaseError.toException());
                  } else {
                      callback.onSuccess();
                  }
              });
    }
}
