package com.example.androiddev_badmintoncourtreservation.ui.player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;




import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.adapter.PlayersRecycleAdapter;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.ui.BaseActivity;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import com.example.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;
import com.example.androiddev_badmintoncourtreservation.viewmodel.player.PlayerListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PlayersActivity extends BaseActivity {

    private PlayersRecycleAdapter<PlayerEntity> adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private PlayerListViewModel listViewModel;
    private List<PlayerEntity> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_players, frameLayout);
        navigationView.setCheckedItem(R.id.nav_none);

        setTitle(R.string.playersActivity_homePage);

        recyclerView = findViewById(R.id.PlayerRv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PlayersRecycleAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Edit the player
                Intent intent = new Intent(PlayersActivity.this, EditPlayerActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("playerId", players.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                //Delete the player
                deletePlayerDialog(position);
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


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

        PlayerListViewModel.Factory factory = new PlayerListViewModel.Factory(getApplication());
        listViewModel =  new ViewModelProvider(this, factory).get(PlayerListViewModel.class);
        listViewModel.getPlayers().observe(this, playerEntities -> {
            if(playerEntities != null){
                players = playerEntities;
                adapter.setData(players);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == BaseActivity.position) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

        finish();
        return super.onNavigationItemSelected(item);
    }

    private void deletePlayerDialog(final int position){
        final PlayerEntity player = players.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.alert_playersActivity_deleteTitle);
        alertDialog.setCancelable(false);
        final TextView tvDeleteMessage = view.findViewById(R.id.tv_delete_item);
        tvDeleteMessage.setText(String.format(getString(R.string.alert_playersActivity_deleteMessage), player.getFirstname()));

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alert_playersActivity_btnPositive), (dialog, which) -> {
            Toast toast = Toast.makeText(this, getString(R.string.toast_playersActivity_delete), Toast.LENGTH_LONG);
            listViewModel.deletePlayer(player, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    //log success
                }

                @Override
                public void onFailure(Exception e) {
                    //log failure
                }
            });
            toast.show();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.alert_playersActivity_btnNegative), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }
}