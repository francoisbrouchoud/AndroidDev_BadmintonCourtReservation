package ch.brouchoud.androiddev_badmintoncourtreservation.database.async.player;

import android.app.Application;
import android.os.AsyncTask;

import ch.brouchoud.androiddev_badmintoncourtreservation.BaseApp;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

public class CreatePlayer extends AsyncTask<PlayerEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreatePlayer(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(PlayerEntity... params) {
        try{
            for(PlayerEntity player : params)
                ((BaseApp) application).getDatabase().playerDao().insert(player);
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
