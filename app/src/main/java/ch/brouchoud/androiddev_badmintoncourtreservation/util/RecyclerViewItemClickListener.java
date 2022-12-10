package ch.brouchoud.androiddev_badmintoncourtreservation.util;

import android.view.View;

/**
 * Listener of the actions executed on the recycler views.
 */
public interface RecyclerViewItemClickListener {
    void onItemClick(View v, int position);
    void onItemLongClick(View v, int position);
}
