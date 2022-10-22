package com.example.androiddev_badmintoncourtreservation.ui.reservation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReservationsActivity_old extends ViewModel {

    private final MutableLiveData<String> mText;

    public ReservationsActivity_old() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}