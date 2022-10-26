package com.example.androiddev_badmintoncourtreservation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.ui.court.CourtsActivity;
import com.example.androiddev_badmintoncourtreservation.ui.management.SettingsActivity;
import com.example.androiddev_badmintoncourtreservation.ui.player.PlayersActivity;
import com.example.androiddev_badmintoncourtreservation.ui.reservation.ReservationsActivity;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected FrameLayout frameLayout;
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;

    protected static int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = findViewById(R.id.flContent);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        position = 0;
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == position){
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

        position = id;
        Intent intent = null;
        navigationView.setCheckedItem(id);

        switch (id){
            case R.id.nav_court:
                intent = new Intent(this, CourtsActivity.class);
                break;
            case R.id.nav_player:
                intent = new Intent(this, PlayersActivity.class);
            case R.id.nav_reservation:
                intent = new Intent(this, ReservationsActivity.class);
        }

        if(intent != null)
            startActivity(intent);

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
