package ch.brouchoud.androiddev_badmintoncourtreservation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import ch.brouchoud.androiddev_badmintoncourtreservation.R;
import ch.brouchoud.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import ch.brouchoud.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;
import java.util.List;
import java.util.Objects;

public class PlayersRecycleAdapter<T> extends RecyclerView.Adapter<PlayersRecycleAdapter.ViewHolder> {

    private List<T> data;
    RecyclerViewItemClickListener rvClickListerner;

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Grab the views from the rows, similar to an "onCreate" method
        TextView tvPlayerFirstName, tvPlayerLastName, tvPlayerBirthdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayerFirstName = itemView.findViewById(R.id.tv_pl_firstname);
            tvPlayerLastName = itemView.findViewById(R.id.tv_pl_lastname);
            tvPlayerBirthdate = itemView.findViewById(R.id.tv_pl_bithdate);
        }
    }

    public PlayersRecycleAdapter(RecyclerViewItemClickListener rvClickListerner) {
        this.rvClickListerner = rvClickListerner;
    }

    @NonNull
    @Override
    public PlayersRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout and give à look to the rows
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.player_row, parent, false);
        final PlayersRecycleAdapter.ViewHolder vh = new PlayersRecycleAdapter.ViewHolder(view);
        view.setOnClickListener(v -> rvClickListerner.onItemClick(view,vh.getAdapterPosition()));
        view.setOnLongClickListener(v -> {
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

    /**
     * Set data on the adapter.
     * @param players list containing the data.
     */
    public void setData(List<T> players) {
        if(data == null){
            data = players;
            notifyItemRangeInserted(0, players.size());
        }else{
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return data.size();
                }

                @Override
                public int getNewListSize() {
                    return players.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if(data instanceof PlayerEntity)
                        return ((PlayerEntity) data.get(oldItemPosition)).getId().equals(((PlayerEntity)data.get(newItemPosition)).getId());
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if(data instanceof PlayerEntity){
                        PlayerEntity newPlayer = (PlayerEntity) players.get(newItemPosition);
                        PlayerEntity oldPlayer = (PlayerEntity) data.get(newItemPosition);
                        return newPlayer.getId().equals(oldPlayer.getId())
                                && Objects.equals(newPlayer.getFirstname(), oldPlayer.getFirstname())
                                && Objects.equals(newPlayer.getLastname(), oldPlayer.getLastname())
                                && Objects.equals(newPlayer.getBirthdate(), oldPlayer.getBirthdate())
                                && Objects.equals(newPlayer.getGender(), oldPlayer.getGender())
                                && Objects.equals(newPlayer.getPhone(), oldPlayer.getPhone())
                                && Objects.equals(newPlayer.getAddress(), oldPlayer.getAddress());
                    }
                    return false;
                }
            });
            data = players;
            result.dispatchUpdatesTo(this);
        }
    }

}
