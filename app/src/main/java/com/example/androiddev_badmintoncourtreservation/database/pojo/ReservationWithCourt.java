package com.example.androiddev_badmintoncourtreservation.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;

import java.util.List;

public class ReservationWithCourt {

    @Embedded
    public ReservationEntity reservation;

    @Relation(parentColumn = "courtId", entityColumn = "id", entity = CourtEntity.class)
    public CourtEntity court;
}
