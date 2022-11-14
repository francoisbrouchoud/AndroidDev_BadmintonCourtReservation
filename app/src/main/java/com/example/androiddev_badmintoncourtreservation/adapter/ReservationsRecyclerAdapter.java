package com.example.androiddev_badmintoncourtreservation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import com.example.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;
import com.example.androiddev_badmintoncourtreservation.viewmodel.player.PlayerViewModel;

import java.util.List;
import java.util.Objects;

public class ReservationsRecyclerAdapter<T> extends RecyclerView.Adapter<ReservationsRecyclerAdapter.ViewHolder> {

    private List<T> data;
    RecyclerViewItemClickListener rvClickListerner;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvCourtName, tvPlayerName, tvDate, tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourtName = itemView.findViewById(R.id.CourtName);
            tvPlayerName = itemView.findViewById(R.id.PlayerName);
            tvDate = itemView.findViewById(R.id.ReseravtionDate);
            tvTime = itemView.findViewById(R.id.ReservationTime);
        }
    }

    public ReservationsRecyclerAdapter(RecyclerViewItemClickListener rvClickListerner) {
        this.rvClickListerner = rvClickListerner;
    }

    @NonNull
    @Override
    public ReservationsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout and give à look to the rows
        //TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_row, parent, false);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.reservation_row, parent, false);

        final ReservationsRecyclerAdapter.ViewHolder vh = new ReservationsRecyclerAdapter.ViewHolder(view);
        view.setOnClickListener(v -> rvClickListerner.onItemClick(view, vh.getAdapterPosition()));
        view.setOnLongClickListener(v -> {
            rvClickListerner.onItemLongClick(view, vh.getAdapterPosition());
            return true;
        });
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull ReservationsRecyclerAdapter.ViewHolder holder, int position) {
        T item = data.get(position);

        if(item.getClass().equals(ReservationEntity.class))
            holder.tvCourtName.setText(((ReservationEntity) item).getResCourtname());
        if(item.getClass().equals(ReservationEntity.class))
            holder.tvPlayerName.setText(((ReservationEntity) item).getResFirstname() + " " +((ReservationEntity) item).getResLastname());
        if(item.getClass().equals(ReservationEntity.class))
            holder.tvDate.setText(((ReservationEntity) item).getReservationDate());
        if(item.getClass().equals(ReservationEntity.class))
            holder.tvTime.setText(((ReservationEntity) item).getTimeSlot());
    }

    @Override
    public int getItemCount() {
        if(data != null)
            return data.size();
        else
            return 0;
    }

    public void setData(List<T> reservations){
        if(data == null){
            data = reservations;
            notifyItemRangeInserted(0, reservations.size());
        }else{
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return data.size();
                }

                @Override
                public int getNewListSize() {
                    return reservations.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if(data instanceof ReservationEntity)
                        return ((ReservationEntity) data.get(oldItemPosition)).getId().equals(((ReservationEntity)data.get(newItemPosition)).getId());
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if(data instanceof ReservationEntity){
                        ReservationEntity newReservation = (ReservationEntity) reservations.get(newItemPosition);
                        ReservationEntity oldReservation = (ReservationEntity) data.get(newItemPosition);
                        return newReservation.getId().equals(oldReservation.getId())
                                && Objects.equals(newReservation.getCourtId(),oldReservation.getCourtId())
                                && Objects.equals(newReservation.getPlayerId(),oldReservation.getPlayerId())
                                && Objects.equals(newReservation.getTimeSlot(),oldReservation.getTimeSlot())
                                && Objects.equals(newReservation.getReservationDate(),oldReservation.getReservationDate());
                    }
                    return false;
                }
            });
            data = reservations;
            result.dispatchUpdatesTo(this);
        }
    }


}
