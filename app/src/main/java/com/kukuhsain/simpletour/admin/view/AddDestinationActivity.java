package com.kukuhsain.simpletour.admin.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.kukuhsain.simpletour.admin.R;
import com.kukuhsain.simpletour.admin.model.remote.SimpleTourApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.schedulers.Schedulers;

/**
 * Created by kukuh on 20/10/16.
 */

public class AddDestinationActivity extends AppCompatActivity {
    @BindView(R.id.et_title) EditText etTitle;
    @BindView(R.id.et_content) EditText etContent;
    @BindView(R.id.et_location) EditText etLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_add)
    public void addDestination() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String location = etLocation.getText().toString();

        SimpleTourApi.getInstance()
                .addDestination(title, content, location)
                .subscribeOn(Schedulers.io())
                .subscribe(destination -> {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Successful...", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }, throwable -> {
                    throwable.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                });
    }
}
