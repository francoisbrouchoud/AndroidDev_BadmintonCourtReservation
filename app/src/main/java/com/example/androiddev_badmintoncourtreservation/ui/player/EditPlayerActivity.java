package com.example.androiddev_badmintoncourtreservation.ui.player;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditPlayerActivity extends ViewModel {

    private final MutableLiveData<String> mText;

    public EditPlayerActivity() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}