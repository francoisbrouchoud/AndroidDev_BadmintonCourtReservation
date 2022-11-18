package ch.hevs.androiddev_badmintoncourtreservation.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import ch.hevs.androiddev_badmintoncourtreservation.database.entity.CourtEntity;

import java.util.List;

/**
 * Access the court data
 */
@Dao
public interface CourtDao {
    @Query("SELECT * FROM courts WHERE id = :id")
    public abstract LiveData<CourtEntity> getById(Long id);

    @Query("SELECT * FROM courts")
    public abstract LiveData<List<CourtEntity>> getAll();

    @Insert
    public abstract long insert(CourtEntity courtEntity);

    @Update
    public abstract void update(CourtEntity courtEntity);

    @Query("DELETE FROM courts")
    public abstract void deleteAll();

    @Delete
    void delete(CourtEntity court);
}
