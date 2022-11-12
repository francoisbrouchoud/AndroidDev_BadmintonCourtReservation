package com.example.androiddev_badmintoncourtreservation.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;

import java.util.List;

@Dao
public interface ReservationDao {
    @Query("SELECT * FROM reservations")
    public abstract LiveData<List<ReservationEntity>> getAll();

    @Query("SELECT * FROM reservations WHERE id = :id")
    public abstract LiveData<ReservationEntity> getById(Long id);

    @Insert
    public abstract long insert(ReservationEntity reservation);

    @Update
    public abstract void update(ReservationEntity reservation);

    @Query("DELETE FROM reservations")
    public abstract void deleteAll();

    @Delete
    void delete(ReservationEntity reservation);
}
