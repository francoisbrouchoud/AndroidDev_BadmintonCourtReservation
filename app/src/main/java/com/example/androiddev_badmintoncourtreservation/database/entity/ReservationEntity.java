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
                    value = "playerId"
            ),
            @Index(
                    value = "courtId"
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
    private String resFirstname;
    private String resLastname;
    private String resCourtname;

    @Ignore
    public ReservationEntity() {
    }

    public ReservationEntity(Long courtId, Long playerId, String timeSlot, String reservationDate, String resFirstname, String resLastname, String resCourtname) {
        this.courtId = courtId;
        this.playerId = playerId;
        this.timeSlot = timeSlot;
        this.reservationDate = reservationDate;
        this.resFirstname = resFirstname;
        this.resLastname = resLastname;
        this.resCourtname = resCourtname;
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

    public String getResFirstname() {
        return resFirstname;
    }

    public void setResFirstname(String resFirstname) {
        this.resFirstname = resFirstname;
    }

    public String getResLastname() {
        return resLastname;
    }

    public void setResLastname(String resLastname) {
        this.resLastname = resLastname;
    }

    public String getResCourtname() {
        return resCourtname;
    }

    public void setResCourtname(String resCourtname) {
        this.resCourtname = resCourtname;
    }

    @Override
    public String toString() {
        return reservationDate.toString() + " " + courtId + " " + playerId;
    }
}
