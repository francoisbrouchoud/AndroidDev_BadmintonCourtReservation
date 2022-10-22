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
import com.example.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;

import org.w3c.dom.Text;

import java.util.List;

public class CourtsRecyclerAdapter<T> extends RecyclerView.Adapter<CourtsRecyclerAdapter.ViewHolder> {

    private List<T> data;
    RecyclerViewItemClickListener rvClickListerner;

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

    public CourtsRecyclerAdapter(RecyclerViewItemClickListener rvClickListerner) {
        this.rvClickListerner = rvClickListerner;
    }

    @NonNull
    @Override
    public CourtsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout and give à look to the rows
        TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.court_row, parent, false);
        final ViewHolder vh = new ViewHolder(tv);
        tv.setOnClickListener(view -> rvClickListerner.onItemClick(view,vh.getAdapterPosition()));
        tv.setOnLongClickListener(view -> {
            rvClickListerner.onItemLongClick(view, vh.getAdapterPosition());
            return true;
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CourtsRecyclerAdapter.ViewHolder holder, int position) {
        //Assign values to each row, depends on the position of the recycler view
        T item = data.get(position);
        if(item.getClass().equals(CourtEntity.class))
            //holder.tvCourtName.setText(((CourtEntity) item).getName());
        if(item.getClass().equals(CourtEntity.class))
            //holder.tvCourtAddress.setText(((CourtEntity) item).getAddress());
        if(item.getClass().equals(CourtEntity.class)) {
            //holder.imageView.setImageURI(((CourtEntity) item).getImagePath());
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
