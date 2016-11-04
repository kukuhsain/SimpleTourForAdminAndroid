package com.kukuhsain.simpletour.host.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kukuhsain.simpletour.host.R;
import com.kukuhsain.simpletour.host.model.local.PreferencesHelper;
import com.kukuhsain.simpletour.host.model.pojo.Destination;
import com.kukuhsain.simpletour.host.model.remote.SimpleTourApi;
import com.kukuhsain.simpletour.host.view.adapter.DestinationAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
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

        Timber.d("access token:... "+PreferencesHelper.getInstance().getAccessToken());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Iterable<Destination> iterableDestinations = Realm.getDefaultInstance()
                .where(Destination.class)
                .findAll();
        List<Destination> destinations = Realm.getDefaultInstance()
                .copyFromRealm(iterableDestinations);
        rvAdapter = new DestinationAdapter(this, destinations);
        rvDestinations.setAdapter(rvAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading();
        Timber.d(SimpleTourApi.BASE_URL);
        SimpleTourApi.getInstance().getDestinations()
                .subscribeOn(Schedulers.io())
                .subscribe(destinations -> {
                    for (Destination destination : destinations) {
                        Realm.getDefaultInstance().executeTransaction(realm1 -> {
                            realm1.copyToRealm(destination);
                        });
                    }
                    rvAdapter = new DestinationAdapter(this, destinations);
                    runOnUiThread(() -> {
                        rvDestinations.setAdapter(rvAdapter);
                        dismissLoading();
                    });
                }, throwable -> {
                    throwable.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(DestinationsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        dismissLoading();
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

    private void showLoading() {
        if (progressDialog != null) {
            progressDialog.show();
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
    }

    private void dismissLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
