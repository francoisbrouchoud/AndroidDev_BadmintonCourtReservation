package com.example.androiddev_badmintoncourtreservation.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;

import java.util.List;

@Dao
public interface PlayerDao {
    @Query("SELECT * FROM players")
    public abstract LiveData<List<PlayerEntity>> getAll();

    @Insert
    public abstract long insert(PlayerEntity player);

    @Update
    public abstract void update(PlayerEntity player);

    @Query("DELETE FROM players")
    public abstract void deleteAll();
}
