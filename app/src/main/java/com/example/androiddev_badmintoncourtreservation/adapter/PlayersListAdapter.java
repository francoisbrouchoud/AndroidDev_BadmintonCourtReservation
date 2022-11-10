package com.example.androiddev_badmintoncourtreservation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androiddev_badmintoncourtreservation.R;

import java.util.List;

public class PlayersListAdapter<T> extends ArrayAdapter<T> {

    private int mResource;
    private List<T> mData;

    public PlayersListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> data){
        super(context, resource, data);
        mResource = resource;
        mData = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public T getItem(int position){
        return mData.get(position);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent){
        PlayersListAdapter.ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(this.getContext()).inflate(mResource, parent, false);
            viewHolder = new PlayersListAdapter.ViewHolder();
            viewHolder.itemView = convertView.findViewById(R.id.tv_spinner_player);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (PlayersListAdapter.ViewHolder) convertView.getTag();
        }

        T item = getItem(position);
        if(item != null){
            viewHolder.itemView.setText(item.toString());
        }


        return convertView;
    }

    private static class ViewHolder {
        TextView itemView;
    }

    public void updateData(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
}
