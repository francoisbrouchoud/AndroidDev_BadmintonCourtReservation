package ch.brouchoud.androiddev_badmintoncourtreservation.database.entity;

import androidx.room.Ignore;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * This entity contains all the fields of the court.
 */

public class CourtEntity {
    private String id;
    private String courtsName;
    private String description;
    private String address;
    private Double hourlyPrice;

    @Ignore
    public CourtEntity() {
    }

    public CourtEntity(String courtsName, String description, String address, Double hourlyPrice) {
        this.courtsName = courtsName;
        this.description = description;
        this.address = address;
        this.hourlyPrice = hourlyPrice;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourtsName() {
        return courtsName;
    }

    public void setCourtsName(String courtsName) {
        this.courtsName = courtsName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(Double hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CourtEntity)) return false;
        CourtEntity o = (CourtEntity) obj;
        return o.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return courtsName;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("courtsName", courtsName);
        result.put("description", description);
        result.put("address", address);
        result.put("hourlyPrice", hourlyPrice);

        return result;
    }
}
