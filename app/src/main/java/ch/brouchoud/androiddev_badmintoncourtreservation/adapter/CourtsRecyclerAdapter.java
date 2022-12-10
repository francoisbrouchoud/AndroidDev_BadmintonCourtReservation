package ch.brouchoud.androiddev_badmintoncourtreservation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import ch.brouchoud.androiddev_badmintoncourtreservation.R;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.CourtEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;
import java.util.List;
import java.util.Objects;

public class CourtsRecyclerAdapter<T> extends RecyclerView.Adapter<CourtsRecyclerAdapter.ViewHolder> {

    private List<T> data;
    RecyclerViewItemClickListener rvClickListerner;

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Grab the views from the rows, similar to an "onCreate" method
        TextView tvCourtName, tvCourtAddress, tvCourtHourlyPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourtName = itemView.findViewById(R.id.tv_pl_courtsname);
            tvCourtAddress = itemView.findViewById(R.id.tv_pl_courtsaddress);
            tvCourtHourlyPrice = itemView.findViewById(R.id.tv_pl_price);
        }
    }

    public CourtsRecyclerAdapter(RecyclerViewItemClickListener rvClickListerner) {
        this.rvClickListerner = rvClickListerner;
    }

    @NonNull
    @Override
    public CourtsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout and give à look to the rows
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.court_row, parent, false);
        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(v -> rvClickListerner.onItemClick(view,vh.getAdapterPosition()));
        view.setOnLongClickListener(v -> {
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
            holder.tvCourtName.setText(((CourtEntity) item).getCourtsName());
        if(item.getClass().equals(CourtEntity.class))
            holder.tvCourtAddress.setText(((CourtEntity) item).getAddress());
       if(item.getClass().equals(CourtEntity.class)) {
           holder.tvCourtHourlyPrice.setText(((CourtEntity) item).getHourlyPrice() + " CHF");
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

    /**
     * Set data on the adapter.
     * @param courts list containing the data.
     */
    public void setData(List<T> courts){
        if(data == null){
            data = courts;
            notifyItemRangeInserted(0,courts.size());
        }else{
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return data.size();
                }

                @Override
                public int getNewListSize() {
                    return courts.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if(data instanceof CourtEntity)
                        return ((CourtEntity) data.get(oldItemPosition)).getId().equals(((CourtEntity) data.get(newItemPosition)).getId());
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if(data instanceof CourtEntity){
                        CourtEntity newCourt = (CourtEntity) courts.get(newItemPosition);
                        CourtEntity oldCourt = (CourtEntity) data.get(newItemPosition);
                        return newCourt.getId().equals(oldCourt.getId())
                                && Objects.equals(newCourt.getCourtsName(), oldCourt.getCourtsName())
                                && Objects.equals(newCourt.getAddress(), oldCourt.getAddress())
                                && Objects.equals(newCourt.getDescription(), oldCourt.getDescription())
                                && Objects.equals(newCourt.getHourlyPrice(), oldCourt.getHourlyPrice());
                    }
                    return false;
                }
            });
            data = courts;
            result.dispatchUpdatesTo(this);
        }
    }
}
