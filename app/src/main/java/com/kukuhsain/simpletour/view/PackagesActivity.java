package com.kukuhsain.simpletour.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kukuhsain.simpletour.R;
import com.kukuhsain.simpletour.model.pojo.Destination;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kukuh on 08/10/16.
 */

public class PackagesActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tv_description) TextView tvDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String destinationString = getIntent().getStringExtra("destination");
        Destination destination = (new Gson()).fromJson(destinationString, Destination.class);
        collapsingToolbarLayout.setTitle(destination.getTitle());
        tvDescription.setText(destination.getContent());
    }
}
