package com.kukuhsain.simpletour.host.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kukuhsain.simpletour.host.R;
import com.kukuhsain.simpletour.host.model.pojo.Destination;
import com.kukuhsain.simpletour.host.model.pojo.Package;
import com.kukuhsain.simpletour.host.model.remote.SimpleTourApi;
import com.kukuhsain.simpletour.host.view.adapter.PackageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kukuh on 08/10/16.
 */

public class PackagesActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.iv_background_image) ImageView ivBackgroundImage;
    @BindView(R.id.tv_description) TextView tvDescription;
    @BindView(R.id.rv_packages) RecyclerView rvPackages;

    private Destination destination;
    private ProgressDialog progressDialog;
    private PackageAdapter packageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String destinationString = getIntent().getStringExtra("destination");
        destination = (new Gson()).fromJson(destinationString, Destination.class);

        collapsingToolbarLayout.setTitle(destination.getTitle());
        tvDescription.setText(destination.getContent());
        Glide.with(this)
                .load(SimpleTourApi.BASE_URL+destination.getImageUrl())
                .into(ivBackgroundImage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPackages.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading();
        SimpleTourApi.getInstance().getPackages(destination.getDestinationId())
                .subscribeOn(Schedulers.io())
                .subscribe(packages -> {
                    packageAdapter = new PackageAdapter(this, packages);
                    runOnUiThread(() -> {
                        rvPackages.setAdapter(packageAdapter);
                        dismissLoading();
                    });
                }, throwable -> {
                    throwable.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        dismissLoading();
                    });
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_packages_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onItemClicked(Package onePackage) {
        Timber.d("on package item clicked...");
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
