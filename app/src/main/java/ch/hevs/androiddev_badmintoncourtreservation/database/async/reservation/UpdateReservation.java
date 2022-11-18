package ch.hevs.androiddev_badmintoncourtreservation.database.async.reservation;

import android.app.Application;
import android.os.AsyncTask;

import ch.hevs.androiddev_badmintoncourtreservation.BaseApp;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import ch.hevs.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

public class UpdateReservation extends AsyncTask<ReservationEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateReservation(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ReservationEntity... params) {
        try {
            for (ReservationEntity reservation : params) {
                ((BaseApp) application).getDatabase().reservationDao().update(reservation);
            }
        } catch (Exception e) {
            this.exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        if(callback !=null){
            if(exception == null){
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
