package ch.hevs.androiddev_badmintoncourtreservation.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import ch.hevs.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;

import java.util.List;

/**
 * Access the player data
 */
@Dao
public interface PlayerDao {
    @Query("SELECT * FROM players WHERE id = :id")
    public abstract  LiveData<PlayerEntity> getById(Long id);

    @Query("SELECT * FROM players")
    public abstract LiveData<List<PlayerEntity>> getAll();

    @Insert
    public abstract long insert(PlayerEntity player);

    @Update
    public abstract void update(PlayerEntity player);

    @Query("DELETE FROM players")
    public abstract void deleteAll();

    @Delete
    void delete(PlayerEntity player);
}
