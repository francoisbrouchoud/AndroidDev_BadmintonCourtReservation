package com.example.androiddev_badmintoncourtreservation.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "accounts",
        foreignKeys =
        @ForeignKey(
                entity = CourtEntity.class,
                parentColumns = "",
                childColumns = "",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(
                        value = {""}
                )}
)
public class PlayerEntity {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String gender;
    private String phone;
    private String address;
    private String place;
    private int level;

    public PlayerEntity() {
    }

    public PlayerEntity(String firstname, String lastname, Date birthdate, String gender, String phone, String address, String place, int level) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.place = place;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setAge(int age) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CourtEntity)) return false;
        PlayerEntity o = (PlayerEntity) obj;
        return o.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
