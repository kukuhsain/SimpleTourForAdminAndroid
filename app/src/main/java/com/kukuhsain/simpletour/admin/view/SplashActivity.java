package com.kukuhsain.simpletour.admin.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.kukuhsain.simpletour.admin.R;

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
            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
            finish();
        }, 2000);
    }
}
