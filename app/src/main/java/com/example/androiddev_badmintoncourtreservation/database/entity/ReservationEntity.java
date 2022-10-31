package com.example.androiddev_badmintoncourtreservation.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "resevations"/*,
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
                        value = {"courtId"}
                ),
                @Index(
                        value = {"playerId"}
                ),
        }
        */
)

public class ReservationEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id2;
    @ColumnInfo(name = "courtId")
    private Long courtId;
    @ColumnInfo(name = "playerId")
    private Long playerId;

    private String reservationDate;

    @Ignore
    public ReservationEntity() {
    }

    public ReservationEntity(Long courtId, Long playerId, String reservationDate) {
        this.courtId = courtId;
        this.playerId = playerId;
        this.reservationDate = reservationDate;
    }

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id) {
        this.id2 = id2;
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

    @Override
    public String toString() {
        return reservationDate.toString() + " " + courtId + " " + playerId;
    }


}
