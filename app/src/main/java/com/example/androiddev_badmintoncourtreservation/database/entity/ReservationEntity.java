package com.example.androiddev_badmintoncourtreservation.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "resevations",
        primaryKeys = {"courtId", "playerId", "timeSlot", "reservationDate"})

public class ReservationEntity {
    @ColumnInfo(name = "courtId")
    private Long courtId;
    @ColumnInfo(name = "playerId")
    private Long playerId;

    private String timeSlot;
    private String reservationDate;

    @Ignore
    public ReservationEntity() {
    }

    public ReservationEntity(Long courtId, Long playerId, String timeSlot, String reservationDate) {
        this.courtId = courtId;
        this.playerId = playerId;
        this.timeSlot = timeSlot;
        this.reservationDate = reservationDate;
    }

    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public String toString() {
        return reservationDate.toString() + " " + courtId + " " + playerId;
    }
}
