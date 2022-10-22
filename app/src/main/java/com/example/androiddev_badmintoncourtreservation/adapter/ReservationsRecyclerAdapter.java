package com.example.androiddev_badmintoncourtreservation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.ui.reservation.ReservationsActivity;
import com.example.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;

import java.util.List;

public class ReservationsRecyclerAdapter<T> extends RecyclerView.Adapter<ReservationsRecyclerAdapter.ViewHolder> {

    private List<T> data;
    RecyclerViewItemClickListener rvClickListerner;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvCourtName, tvPlayerName, tvDate, tvHour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourtName = itemView.findViewById(R.id.CourtName);
            tvPlayerName = itemView.findViewById(R.id.PlayerName);
            tvDate = itemView.findViewById(R.id.Date);
            tvHour = itemView.findViewById(R.id.Hour);
        }
    }

    public ReservationsRecyclerAdapter(RecyclerViewItemClickListener rvClickListerner) {
        this.rvClickListerner = rvClickListerner;
    }

    @NonNull
    @Override
    public ReservationsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout and give à look to the rows
        TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_row, parent, false);
        final ReservationsRecyclerAdapter.ViewHolder vh = new ReservationsRecyclerAdapter.ViewHolder(tv);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationsRecyclerAdapter.ViewHolder holder, int position) {
        T item = data.get(position);
        //if(item.getClass().equals(ReservationEntity.class))
            //set le text du player name en allant cherhcer le nom du player par son id
            //Faire la même chose pour le nom du terrain


    }

    @Override
    public int getItemCount() {
        if(data != null)
            return data.size();
        else
            return 0;
    }


}
