package ch.brouchoud.androiddev_badmintoncourtreservation.database.entity;

import androidx.room.Ignore;
import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * This entity contains all the fields required for a reservation.
 */

public class ReservationEntity {
    private String id;
    private String courtId;
    private String playerId;
    private String timeSlot;
    private String reservationDate;

    @Ignore
    public ReservationEntity() {
    }

    public ReservationEntity(String courtId, String playerId, String timeSlot, String reservationDate) {
        this.courtId = courtId;
        this.playerId = playerId;
        this.timeSlot = timeSlot;
        this.reservationDate = reservationDate;
    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourtId() {
        return courtId;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
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
        return reservationDate + " " + courtId + " " + playerId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeSlot", timeSlot);
        result.put("reservationDate", reservationDate);
        result.put("courtId", courtId);
        result.put("playerId", playerId);

        return result;
    }
}
