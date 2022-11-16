package com.example.androiddev_badmintoncourtreservation.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;


public class ReservationWithPlayerAndCourt {

    @Embedded
    public ReservationEntity reservation;

    @Relation(parentColumn = "playerId", entityColumn = "id", entity = PlayerEntity.class)
    public PlayerEntity player;

    @Relation(parentColumn = "courtId", entityColumn = "id", entity = CourtEntity.class)
    public CourtEntity court;
}
