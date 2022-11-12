package com.example.androiddev_badmintoncourtreservation.database.async.reservation;

import android.app.Application;
import android.os.AsyncTask;

import com.example.androiddev_badmintoncourtreservation.BaseApp;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

public class CreateReservation extends AsyncTask<ReservationEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateReservation(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ReservationEntity... params) {
        try{
            for(ReservationEntity reservation : params)
                ((BaseApp) application).getDatabase().reservationDao().insert(reservation);
        } catch (Exception e){
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        if(callback != null){
            if(exception == null){
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }

}
