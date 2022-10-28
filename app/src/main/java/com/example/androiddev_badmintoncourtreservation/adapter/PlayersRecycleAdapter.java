package com.example.androiddev_badmintoncourtreservation.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;

import java.util.List;

public class PlayersRecycleAdapter<T> extends RecyclerView.Adapter<PlayersRecycleAdapter.ViewHolder> {

    private List<T> data;
    RecyclerViewItemClickListener rvClickListerner;

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

    public PlayersRecycleAdapter(RecyclerViewItemClickListener rvClickListerner) {
        this.rvClickListerner = rvClickListerner;
    }


    @NonNull
    @Override
    public PlayersRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout and give à look to the rows
        TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.player_row, parent, false);
        final PlayersRecycleAdapter.ViewHolder vh = new PlayersRecycleAdapter.ViewHolder(tv);
        tv.setOnClickListener(view -> rvClickListerner.onItemClick(view,vh.getAdapterPosition()));
        tv.setOnLongClickListener(view -> {
            rvClickListerner.onItemLongClick(view, vh.getAdapterPosition());
            return true;
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersRecycleAdapter.ViewHolder holder, int position) {
        //Assign values to each row, depends on the position of the recycler view
        T item = data.get(position);
        if(item.getClass().equals(PlayerEntity.class))
            holder.tvPlayerFirstName.setText(((PlayerEntity) item).getFirstname());
        if(item.getClass().equals(PlayerEntity.class))
            holder.tvPlayerLastName.setText(((PlayerEntity) item).getLastname());
        if(item.getClass().equals(PlayerEntity.class)) {
            holder.tvPlayerBirthdate.setText((CharSequence) ((PlayerEntity) item).getBirthdate());
        }
    }

    @Override
    public int getItemCount() {
        //Count the number of items
        if(data != null)
            return data.size();
        else
            return 0;
    }

}
