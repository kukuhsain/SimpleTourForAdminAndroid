package com.kukuhsain.simpletour.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.kukuhsain.simpletour.R;
import com.kukuhsain.simpletour.model.pojo.Destination;
import com.kukuhsain.simpletour.model.remote.SimpleTourApi;
import com.kukuhsain.simpletour.view.adapter.DestinationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kukuh on 08/10/16.
 */

public class DestinationsActivity extends AppCompatActivity {
    @BindView(R.id.rv_destinations) RecyclerView rvDestinations;

    RecyclerView.LayoutManager rvLayoutManager;
    RecyclerView.Adapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);
        ButterKnife.bind(this);

        rvLayoutManager = new LinearLayoutManager(this);
        rvDestinations.setLayoutManager(rvLayoutManager);

        rvAdapter = new DestinationAdapter(getDummyDestinations());
        rvDestinations.setAdapter(rvAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d(SimpleTourApi.BASE_URL);
        SimpleTourApi.getInstance().getDestinations()
                .subscribeOn(Schedulers.io())
                .subscribe(destinations -> {
                    Destination[] destinationsArray = destinations.toArray(new Destination[destinations.size()]);
                    rvAdapter = new DestinationAdapter(destinationsArray);
                    runOnUiThread(() -> rvDestinations.setAdapter(rvAdapter));
                }, throwable -> {
                    throwable.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(DestinationsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
                });
    }

    private Destination[] getDummyDestinations() {
        int totalAmount = 10;
        List<Destination> destinations = new ArrayList<>();

        for (int i=1; i<=totalAmount; i++) {
            Destination destination = new Destination();
            destination.setTitle("Destination #"+i);
            destination.setContent("description for Destination #"+i);
            destinations.add(destination);
        }

        Destination[] destinationsArray = destinations.toArray(new Destination[totalAmount]);

        return destinationsArray;
    }
}
