package com.kukuhsain.simpletour.admin.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kukuhsain.simpletour.admin.R;
import com.kukuhsain.simpletour.admin.model.pojo.Destination;
import com.kukuhsain.simpletour.admin.model.remote.SimpleTourApi;
import com.kukuhsain.simpletour.admin.view.DestinationsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by kukuh on 08/10/16.
 */

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {
    private Context context;
    private List<Destination> destinations;

    public DestinationAdapter(Context context, List<Destination> destinations) {
        this.context = context;
        this.destinations = destinations;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_card_image) ImageView ivCardImage;
        @BindView(R.id.tv_name) TextView tvTitle;
        @BindView(R.id.tv_description) TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Context context, Destination destination) {
            tvTitle.setText(destination.getTitle());
            tvContent.setText(destination.getContent());
            itemView.setOnClickListener(view -> {
                ((DestinationsActivity) context).onItemClicked(destination);
            });
            Timber.d(destination.getImageUrl());
            Timber.d(destination.getLocation());
            Glide.with(context)
                    .load(SimpleTourApi.BASE_URL+destination.getImageUrl())
                    .into(ivCardImage);
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
        holder.bind(context, destinations.get(position));
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }


}
