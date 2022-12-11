package ch.brouchoud.androiddev_badmintoncourtreservation.database.repository;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.firebase.ReservationListLiveData;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.firebase.ReservationWithPlayerAndCourtListLiveData;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.firebase.ReservationWithPlayerAndCourtLiveData;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

import java.util.List;

public class ReservationRepository {

    private static ReservationRepository instance;

    public ReservationRepository() {
    }

    public static ReservationRepository getInstance(){
        if(instance == null){
            synchronized (ReservationRepository.class){
                if(instance == null){
                    instance = new ReservationRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<ReservationEntity>> getReservations(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("reservations");
        return new ReservationListLiveData(reference);
    }

    /**
     * Live data for reservations with player and court (POJO)
     * @return LiveData : list of reservations with its player and court
     */
    public LiveData<List<ReservationWithPlayerAndCourt>> getReservationsWithPlayerAndCourt(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("reservations");
        return new ReservationWithPlayerAndCourtListLiveData(reference);
    }

    /**
     * Live data for reservations with player and court (POJO)
     * @return LiveData : reservation with its player and court
     */
    public LiveData<ReservationWithPlayerAndCourt> getReservationWithPlayerAndCourt(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("reservations")
                .child(id);
        return new ReservationWithPlayerAndCourtLiveData(reference);
    }

    public void insert(final ReservationEntity reservation, OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("reservations");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("reservations")
                .child(key)
                .setValue(reservation, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final ReservationEntity reservation, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("reservations")
                .child(reservation.getId())
                .updateChildren(reservation.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ReservationEntity reservation, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("reservations")
                .child(reservation.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
