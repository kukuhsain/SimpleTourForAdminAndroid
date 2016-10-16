package com.kukuhsain.simpletour.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kukuhsain.simpletour.R;
import com.kukuhsain.simpletour.model.pojo.Destination;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        @BindView(R.id.tv_name) TextView tvTitle;
        @BindView(R.id.tv_description) TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Destination destination) {
            tvTitle.setText(destination.getTitle());
            tvContent.setText(destination.getContent());
            Timber.d(destination.getImageUrl());
            Timber.d(destination.getLocation());
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
        holder.bind(destinations.get(position));
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }


}
