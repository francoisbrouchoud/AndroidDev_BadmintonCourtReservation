package ch.brouchoud.androiddev_badmintoncourtreservation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import ch.brouchoud.androiddev_badmintoncourtreservation.R;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;
import java.util.List;
import java.util.Objects;

public class ReservationsRecyclerAdapter<T> extends RecyclerView.Adapter<ReservationsRecyclerAdapter.ViewHolder> {

    private List<T> data;
    RecyclerViewItemClickListener rvClickListerner;

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Grab the views from the rows, similar to an "onCreate" method
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
        //Assign values to each row, depends on the position of the recycler view
        T item = data.get(position);
        if(item.getClass().equals(ReservationWithPlayerAndCourt.class))
            holder.tvCourtName.setText(((ReservationWithPlayerAndCourt) item).court.getCourtsName());
        if(item.getClass().equals(ReservationWithPlayerAndCourt.class))
            holder.tvPlayerName.setText(((ReservationWithPlayerAndCourt) item).player.getFirstname() + " " +((ReservationWithPlayerAndCourt) item).player.getLastname());
        if(item.getClass().equals(ReservationWithPlayerAndCourt.class))
            holder.tvDate.setText(((ReservationWithPlayerAndCourt) item).reservation.getReservationDate());
        if(item.getClass().equals(ReservationWithPlayerAndCourt.class))
            holder.tvTime.setText(((ReservationWithPlayerAndCourt) item).reservation.getTimeSlot());
    }

    @Override
    public int getItemCount() {
        if(data != null)
            return data.size();
        else
            return 0;
    }

    /**
     * Set data on the adapter.
     * @param reservationsPlayerCourt list containing the data.
     */
    public void setData(List<T> reservationsPlayerCourt){
        if(data == null){
            data = reservationsPlayerCourt;
            notifyItemRangeInserted(0, reservationsPlayerCourt.size());
        }else{
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return data.size();
                }

                @Override
                public int getNewListSize() {
                    return reservationsPlayerCourt.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if(data instanceof ReservationWithPlayerAndCourt)
                        return ((ReservationWithPlayerAndCourt) data.get(oldItemPosition)).reservation.getId().equals(((ReservationWithPlayerAndCourt)data.get(newItemPosition)).reservation.getId());
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if(data instanceof ReservationWithPlayerAndCourt){
                        ReservationWithPlayerAndCourt newReservation = (ReservationWithPlayerAndCourt) reservationsPlayerCourt.get(newItemPosition);
                        ReservationWithPlayerAndCourt oldReservation = (ReservationWithPlayerAndCourt) data.get(newItemPosition);
                        return newReservation.reservation.getId().equals(oldReservation.reservation.getId())
                                && Objects.equals(newReservation.reservation.getCourtId(),oldReservation.reservation.getCourtId())
                                && Objects.equals(newReservation.reservation.getPlayerId(),oldReservation.reservation.getPlayerId())
                                && Objects.equals(newReservation.reservation.getTimeSlot(),oldReservation.reservation.getTimeSlot())
                                && Objects.equals(newReservation.reservation.getReservationDate(),oldReservation.reservation.getReservationDate());
                    }
                    return false;
                }
            });
            data = reservationsPlayerCourt;
            result.dispatchUpdatesTo(this);
        }
    }
}
