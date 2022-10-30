package com.example.androiddev_badmintoncourtreservation.ui.player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.adapter.PlayersRecycleAdapter;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.ui.BaseActivity;
import com.example.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PlayersActivity extends BaseActivity {

    private PlayersRecycleAdapter<PlayerEntity> adapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_players, frameLayout);
        setTitle(R.string.playersActivity_homePage);
        navigationView.setCheckedItem(R.id.nav_none);

        recyclerView = findViewById(R.id.PlayerRv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlayersRecycleAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Edit the player
            }

            @Override
            public void onItemLongClick(View v, int position) {
                //Delete the player
            }
        });

        fab = findViewById(R.id.floatingActionButtonPlayers);
        fab.setOnClickListener(view -> {
                    Intent intent = new Intent(PlayersActivity.this, EditPlayerActivity.class);
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
}