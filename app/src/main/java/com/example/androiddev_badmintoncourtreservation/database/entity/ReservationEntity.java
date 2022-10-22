package com.example.androiddev_badmintoncourtreservation.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "resevations",
        foreignKeys = {
        @ForeignKey(
                entity = CourtEntity.class,
                parentColumns = "id",
                childColumns = "courtId",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = PlayerEntity.class,
                parentColumns = "id",
                childColumns = "playerId",
                onDelete = ForeignKey.CASCADE
        )},
        indices = {
                @Index(
                        value = {"id"}
                )}
)

public class ReservationEntity implements Comparable{
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long courtId;
    private Long playerId;
    private Date reservationDate;

    public ReservationEntity() {
    }

    public ReservationEntity(Long courtId, Long playerId, Date reservationDate) {
        this.courtId = courtId;
        this.playerId = playerId;
        this.reservationDate = reservationDate;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String toString() {
        return reservationDate.toString() + " " + courtId + " " + playerId;

    }
}
