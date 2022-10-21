package com.example.androiddev_badmintoncourtreservation.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddev_badmintoncourtreservation.R;

public class CourtsRecyclerAdapter<T> extends RecyclerView.Adapter<CourtsRecyclerAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {
        //Grabbing the views from the rows, similar to an "onCreate" method

        ImageView imageView;
        TextView tvCourtName, tvCourtAddress, tvCourtHourlyPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView3);
            tvCourtName = itemView.findViewById(R.id.CourName);
            tvCourtAddress = itemView.findViewById(R.id.CourtAddress);
            tvCourtHourlyPrice = itemView.findViewById(R.id.CourtHourlyPrice);
        }
    }


    @NonNull
    @Override
    public CourtsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout and give à look to the rows
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CourtsRecyclerAdapter.ViewHolder holder, int position) {
        //Assign values to each row, depends on the position of the recycler view
    }

    @Override
    public int getItemCount() {
        //Count the number of items
        return 0;
    }


}
