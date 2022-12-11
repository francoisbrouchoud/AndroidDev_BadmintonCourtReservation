package ch.brouchoud.androiddev_badmintoncourtreservation.viewmodel.player;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ch.brouchoud.androiddev_badmintoncourtreservation.BaseApp;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.repository.PlayerRepository;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;

public class PlayerViewModel extends AndroidViewModel {

    private Application application;
    private PlayerRepository repository;
    private final MediatorLiveData<PlayerEntity> observablePlayer;

    public PlayerViewModel(@NonNull Application application, final String playerId, PlayerRepository playerRepository) {
        super(application);

        this.application = application;
        repository = playerRepository;

        observablePlayer = new MediatorLiveData<>();
        observablePlayer.setValue(null);

        LiveData<PlayerEntity> player;
        player = repository.getPlayer(playerId);
        observablePlayer.addSource(player, observablePlayer::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;
        private final String playerId;
        private final PlayerRepository repository;

        public Factory(@NonNull Application application, String playerId) {
            this.application = application;
            this.playerId = playerId;
            repository = ((BaseApp) application).getPlayerRepository();
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //Create a new view model of the player
            return (T) new PlayerViewModel(application,playerId,repository);
        }
    }

    /**
     * Get the observable player.
     * @return LiveData of the player.
     */
    public LiveData<PlayerEntity> getPlayer(){
        return observablePlayer;
    }

    /**
     * Create a new player.
     * @param player to create.
     * @param callback
     */
    public void createPlayer(PlayerEntity player, OnAsyncEventListener callback){
        repository.insert(player, callback);
    }

    /**
     * Update an existing player.
     * @param player to update.
     * @param callback
     */
    public void updatePlayer(PlayerEntity player, OnAsyncEventListener callback){
        repository.update(player, callback);
    }
}
