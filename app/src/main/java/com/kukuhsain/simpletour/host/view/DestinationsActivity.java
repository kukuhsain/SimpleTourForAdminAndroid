package com.kukuhsain.simpletour.host.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kukuhsain.simpletour.host.R;
import com.kukuhsain.simpletour.host.model.local.PreferencesHelper;
import com.kukuhsain.simpletour.host.model.local.RealmHelper;
import com.kukuhsain.simpletour.host.model.pojo.Destination;
import com.kukuhsain.simpletour.host.model.remote.SimpleTourApi;
import com.kukuhsain.simpletour.host.view.adapter.DestinationAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kukuh on 08/10/16.
 */

public class DestinationsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
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

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Destinations");
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Destination> destinations = RealmHelper.getInstance().getAllDestinations();
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
                    RealmHelper.getInstance().addDestinations(destinations);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_destinations_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cart:
                startActivity(new Intent(this, ReservationsActivity.class));
                break;
            case R.id.menu_sign_out:
                PreferencesHelper.getInstance().clearData();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClicked(Destination destination) {
        Intent intent = new Intent(this, PackagesActivity.class);
        intent.putExtra("destination", (new Gson()).toJson(destination));
        startActivity(intent);
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
