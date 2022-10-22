package com.example.androiddev_badmintoncourtreservation.ui.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;

import java.util.List;

public class ReservationsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);
    }
}