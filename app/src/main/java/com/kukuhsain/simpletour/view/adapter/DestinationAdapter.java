package com.kukuhsain.simpletour.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kukuhsain.simpletour.R;
import com.kukuhsain.simpletour.model.pojo.Destination;

import java.util.List;

import timber.log.Timber;

/**
 * Created by kukuh on 08/10/16.
 */

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {
    private List<Destination> destinations;

    public DestinationAdapter(List<Destination> destinations) {
        this.destinations = destinations;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_destination, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(destinations.get(position).getTitle());
        holder.tvDescription.setText(destinations.get(position).getContent());
        Timber.d(destinations.get(position).getImageUrl());
        Timber.d(destinations.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }
}
