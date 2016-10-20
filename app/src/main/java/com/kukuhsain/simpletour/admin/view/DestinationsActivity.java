package com.kukuhsain.simpletour.admin.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kukuhsain.simpletour.admin.R;
import com.kukuhsain.simpletour.admin.model.local.PreferencesHelper;
import com.kukuhsain.simpletour.admin.model.pojo.Destination;
import com.kukuhsain.simpletour.admin.model.remote.SimpleTourApi;
import com.kukuhsain.simpletour.admin.view.adapter.DestinationAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kukuh on 08/10/16.
 */

public class DestinationsActivity extends AppCompatActivity {
    @BindView(R.id.rv_destinations) RecyclerView rvDestinations;

    RecyclerView.LayoutManager rvLayoutManager;
    RecyclerView.Adapter rvAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinations);
        ButterKnife.bind(this);

        rvLayoutManager = new LinearLayoutManager(this);
        rvDestinations.setLayoutManager(rvLayoutManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }

        Timber.d("access token:... "+PreferencesHelper.getInstance().getAccessToken());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d(SimpleTourApi.BASE_URL);
        SimpleTourApi.getInstance().getDestinations()
                .subscribeOn(Schedulers.io())
                .subscribe(destinations -> {
                    rvAdapter = new DestinationAdapter(this, destinations);
                    runOnUiThread(() -> {
                        rvDestinations.setAdapter(rvAdapter);
                        if (progressDialog.isShowing()) {
                            progressDialog.hide();
                        }
                    });
                }, throwable -> {
                    throwable.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(DestinationsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.hide();
                        }
                    });
                });
    }

    public void onItemClicked(Destination destination) {
        Intent intent = new Intent(this, PackagesActivity.class);
        intent.putExtra("destination", (new Gson()).toJson(destination));
        runOnUiThread(() -> startActivity(intent));
    }

    @OnClick(R.id.fab)
    public void goToAddDestinationActivity() {
        startActivity(new Intent(this, AddDestinationActivity.class));
    }
}
