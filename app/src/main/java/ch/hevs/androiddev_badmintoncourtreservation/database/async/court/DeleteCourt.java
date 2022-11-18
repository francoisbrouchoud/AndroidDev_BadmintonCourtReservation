package ch.hevs.androiddev_badmintoncourtreservation.database.async.court;

import android.app.Application;
import android.os.AsyncTask;

import ch.hevs.androiddev_badmintoncourtreservation.BaseApp;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.hevs.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

public class DeleteCourt extends AsyncTask<CourtEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteCourt(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(CourtEntity... params) {
        try {
            for(CourtEntity court : params){
                ((BaseApp) application).getDatabase().courtDao().delete(court);
            }
        } catch (Exception e){
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        if(callback != null)
            if(exception != null){
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
    }

}
