package com.example.androiddev_badmintoncourtreservation.ui.court;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.adapter.CourtsRecyclerAdapter;
import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.ui.BaseActivity;
import com.example.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CourtsActivity extends BaseActivity {

    List<CourtEntity> courts = new ArrayList<>();
    private CourtsRecyclerAdapter<CourtEntity> adapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_courts, frameLayout);
        setTitle("Courts"/*Replace here with the value from the "Strings" file*/);
        navigationView.setCheckedItem(R.id.nav_none);

        recyclerView = findViewById(R.id.CourtRv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //setUpModelData();

        adapter = new CourtsRecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Access the ReservationsActivity
            }

            @Override
            public void onItemLongClick(View v, int position) {
                //Access the EditCourtActivity
            }
        });

        fab = findViewById(R.id.floatingActionButtonCourts);
        fab.setOnClickListener(view -> {
                    Intent intent = new Intent(CourtsActivity.this, EditCourtActivity.class);
                    intent.setFlags(
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY
                    );
                    startActivity(intent);
                }
        );

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        /*
        The activity has to be finished manually in order to guarantee the navigation hierarchy working.
        */
        finish();
        return super.onNavigationItemSelected(item);
    }

    private void setUpModelData(){
        CourtEntity court1 = new CourtEntity("Name of the court", "Description", "Address", "Sion", "image.png", 555);
        CourtEntity court2 = new CourtEntity("Name of the court2", "Description2", "Address", "Sion", "image.png",555);
        CourtEntity court3 = new CourtEntity("Name of the court3", "Description3", "Address", "Sion", "image.png", 222);
        CourtEntity court4 = new CourtEntity("Name of the court4", "Description4", "Address", "Sion", "image.png",111);
        courts.add(court1);
        courts.add(court2);
        courts.add(court3);
        courts.add(court4);
    }
}