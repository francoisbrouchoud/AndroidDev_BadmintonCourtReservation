package ch.brouchoud.androiddev_badmintoncourtreservation.database.async.court;

import android.app.Application;
import android.os.AsyncTask;

import ch.brouchoud.androiddev_badmintoncourtreservation.BaseApp;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

public class UpdateCourt extends AsyncTask<CourtEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateCourt(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(CourtEntity... params) {
        try {
            for (CourtEntity court : params) {
                ((BaseApp) application).getDatabase().courtDao().update(court);
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
