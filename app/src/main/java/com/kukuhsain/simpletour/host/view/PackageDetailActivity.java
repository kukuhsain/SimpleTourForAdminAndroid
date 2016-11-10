package com.kukuhsain.simpletour.host.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kukuhsain.simpletour.host.R;
import com.kukuhsain.simpletour.host.model.pojo.Package;
import com.kukuhsain.simpletour.host.model.remote.SimpleTourApi;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kukuh on 10/11/16.
 */

public class PackageDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.iv_background_image) ImageView ivBackgroundImage;
    @BindView(R.id.tv_description) TextView tvDescription;

    Package onePackage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String packageString = getIntent().getStringExtra("package");
        onePackage = (new Gson()).fromJson(packageString, Package.class);

        collapsingToolbarLayout.setTitle(onePackage.getTitle());
        tvDescription.setText(onePackage.getContent());
        Glide.with(this)
                .load(SimpleTourApi.BASE_URL+onePackage.getImageUrl())
                .into(ivBackgroundImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_packages_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
