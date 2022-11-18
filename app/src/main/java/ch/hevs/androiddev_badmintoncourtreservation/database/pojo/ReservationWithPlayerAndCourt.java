package ch.hevs.androiddev_badmintoncourtreservation.database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import ch.hevs.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;

/**
 * Table reservation has relation with player and court
 */
public class ReservationWithPlayerAndCourt {

    @Embedded
    public ReservationEntity reservation;

    @Relation(parentColumn = "playerId", entityColumn = "id", entity = PlayerEntity.class)
    public PlayerEntity player;

    @Relation(parentColumn = "courtId", entityColumn = "id", entity = CourtEntity.class)
    public CourtEntity court;
}
