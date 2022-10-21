package com.example.androiddev_badmintoncourtreservation.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddev_badmintoncourtreservation.R;

public class PlayersRecycleAdapter<T> extends RecyclerView.Adapter<PlayersRecycleAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {
        //Grabbing the views from the rows, similar to an "onCreate" method

        TextView tvPlayerFirstName, tvPlayerLastName, tvPlayerBirthdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvPlayerFirstName = itemView.findViewById(R.id.PlayerFirstName);
            tvPlayerLastName = itemView.findViewById(R.id.PlayerLastName);
            tvPlayerBirthdate = itemView.findViewById(R.id.PlayerBirthdate);
        }
    }


    @NonNull
    @Override
    public PlayersRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout and give à look to the rows
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersRecycleAdapter.ViewHolder holder, int position) {
        //Assign values to each row, depends on the position of the recycler view
    }

    @Override
    public int getItemCount() {
        //Count the number of items
        return 0;
    }


}
