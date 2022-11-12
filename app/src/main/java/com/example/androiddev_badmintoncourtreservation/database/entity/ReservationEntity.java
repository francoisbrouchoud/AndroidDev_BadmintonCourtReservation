package com.example.androiddev_badmintoncourtreservation.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "reservations",
        foreignKeys = {
                @ForeignKey(
                        entity = PlayerEntity.class,
                        parentColumns = "id",
                        childColumns = "playerId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = CourtEntity.class,
                        parentColumns = "id",
                        childColumns = "courtId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
            @Index(
                    value = "playerId", unique = true
            ),
            @Index(
                    value = "courtId", unique = true
            )
        }
)

public class ReservationEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "courtId") @NonNull
    private Long courtId;
    @ColumnInfo(name = "playerId") @NonNull
    private Long playerId;
    @NonNull
    private String timeSlot;
    @NonNull
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
