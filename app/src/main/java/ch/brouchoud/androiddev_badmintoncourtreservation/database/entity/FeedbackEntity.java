package ch.brouchoud.androiddev_badmintoncourtreservation.database.entity;

import androidx.room.Ignore;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * This entity contains all the fields of the court.
 */

public class FeedbackEntity {
    private String id;
    private String sendFeedBackTime;
    private String osVersion;
    private String device;
    private String model;
    private String feedback;

    @Ignore
    public FeedbackEntity() {
    }

    public FeedbackEntity(String sendFeedBackTime, String osVersion, String device, String model, String feedback) {
        this.sendFeedBackTime = sendFeedBackTime;
        this.osVersion = osVersion;
        this.device = device;
        this.model = model;
        this.feedback = feedback;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateSendFeedback() {
        return sendFeedBackTime;
    }

    public void setDateSendFeedback(String dateSendFeedback) {
        this.sendFeedBackTime = sendFeedBackTime;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof FeedbackEntity)) return false;
        FeedbackEntity o = (FeedbackEntity) obj;
        return o.getId().equals(this.getId());
    }

    @Override
    public String toString() {
        return feedback;
    }

}
