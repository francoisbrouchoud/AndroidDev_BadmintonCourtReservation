package com.example.androiddev_badmintoncourtreservation.ui.court;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditCourtActivity extends ViewModel {

    private final MutableLiveData<String> mText;

    public EditCourtActivity() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}