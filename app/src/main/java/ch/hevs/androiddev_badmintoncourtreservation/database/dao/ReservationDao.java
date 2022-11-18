package ch.hevs.androiddev_badmintoncourtreservation.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import ch.hevs.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import ch.hevs.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;

import java.util.List;

@Dao
public interface ReservationDao {
    @Query("SELECT * FROM reservations")
    public abstract LiveData<List<ReservationEntity>> getAll();

    @Query("SELECT * FROM reservations WHERE id = :id")
    public abstract LiveData<ReservationEntity> getById(Long id);

    /**
     * Use to get reservations with its player and court
     * @return livedata : list of the reservations with its player and court
     */
    @Transaction
    @Query("SELECT * FROM reservations")
    public abstract LiveData<List<ReservationWithPlayerAndCourt>> getReservationsWithPlayerAndCourt();

    /**
     * Use to get reservation with its player and court
     * @return livedata : reservation with its player and court
     */
    @Transaction
    @Query("SELECT * FROM reservations WHERE id = :id")
    public abstract LiveData<ReservationWithPlayerAndCourt> getReservationWithPlayerAndCourt(Long id);

    @Insert
    public abstract long insert(ReservationEntity reservation);

    @Update
    public abstract void update(ReservationEntity reservation);

    @Query("DELETE FROM reservations")
    public abstract void deleteAll();

    @Delete
    void delete(ReservationEntity reservation);
}
