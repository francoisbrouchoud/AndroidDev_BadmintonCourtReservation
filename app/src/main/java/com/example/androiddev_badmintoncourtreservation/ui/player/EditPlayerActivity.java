package com.example.androiddev_badmintoncourtreservation.ui.player;

import android.os.Bundle;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.ui.BaseActivity;

public class EditPlayerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_edit_player, frameLayout);
        navigationView.setCheckedItem(R.id.nav_none);

        TextView textView = findViewById(R.id.textTest);
        textView.setText("here we edit the players");
    }
}