package ch.brouchoud.androiddev_badmintoncourtreservation.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;

/**
 * Table reservation has relation with player and court
 */
public class ReservationWithPlayerAndCourt {
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReservationEntity reservation;

    public PlayerEntity player;
    public CourtEntity court;
}
