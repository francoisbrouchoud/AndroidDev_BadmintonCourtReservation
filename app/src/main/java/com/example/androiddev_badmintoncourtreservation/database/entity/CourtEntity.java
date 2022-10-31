package com.example.androiddev_badmintoncourtreservation.database.entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "courtsEntity"/*,
        foreignKeys =
        @ForeignKey(
                entity = ReservationEntity.class,
                parentColumns = "courtId",
                childColumns = "id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(
                        value = {"id"}
                )}*/
)


public class CourtEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String courtsName;
    private String description;
    private String address;
    private String place;
    private String imagePath;
    private double hourlyPrice;

    public CourtEntity() {
    }

    public CourtEntity(String courtsName, String description, String address, String place, String imagePath, double hourlyPrice) {
        this.courtsName = courtsName;
        this.description = description;
        this.address = address;
        this.place = place;
        this.imagePath = imagePath;
        this.hourlyPrice = hourlyPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(double hourlyPrice) {
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
}
