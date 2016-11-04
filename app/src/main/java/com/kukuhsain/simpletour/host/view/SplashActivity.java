package com.kukuhsain.simpletour.host.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.kukuhsain.simpletour.host.R;
import com.kukuhsain.simpletour.host.model.local.PreferencesHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            if (PreferencesHelper.getInstance().getLoggedInStatus()) {
                startActivity(new Intent(SplashActivity.this, DestinationsActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                finish();
            }
        }, 2000);
    }
}
