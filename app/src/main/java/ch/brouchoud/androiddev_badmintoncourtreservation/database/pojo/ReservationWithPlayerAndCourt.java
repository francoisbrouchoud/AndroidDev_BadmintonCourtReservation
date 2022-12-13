package ch.brouchoud.androiddev_badmintoncourtreservation.database.pojo;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("court", court);
        result.put("player", player);
        result.put("reservation", reservation);
        return result;
    }
}
