package com.example.androiddev_badmintoncourtreservation.ui.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.ui.BaseActivity;
import com.example.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;

import java.util.List;

public class ReservationsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_reservations, frameLayout);
        setTitle("Reservations"/*Replace here with the value from the "Strings" file*/);
        navigationView.setCheckedItem(R.id.nav_none);
    }
}