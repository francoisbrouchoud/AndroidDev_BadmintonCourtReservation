package com.example.androiddev_badmintoncourtreservation.ui.court;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.ui.BaseActivity;

public class EditCourtActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_edit_court, frameLayout);
        navigationView.setCheckedItem(R.id.nav_none);


    }
}