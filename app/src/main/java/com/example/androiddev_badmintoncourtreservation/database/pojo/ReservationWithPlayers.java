package com.example.androiddev_badmintoncourtreservation.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;

import java.util.List;

public class ReservationWithPlayers {

    @Embedded
    public ReservationEntity reservation;

    @Relation(parentColumn = "playerId", entityColumn = "id", entity = PlayerEntity.class)
    public List<PlayerEntity> players;
}
