package com.kukuhsain.simpletour.host.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kukuhsain.simpletour.host.R;
import com.kukuhsain.simpletour.host.model.local.PreferencesHelper;
import com.kukuhsain.simpletour.host.model.remote.SimpleTourApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by kukuh on 08/10/16.
 */

public class SignInActivity extends AppCompatActivity {
    @BindView(R.id.et_email) TextInputEditText etEmail;
    @BindView(R.id.et_password) TextInputEditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sign_in)
    public void signIn() {
        Timber.d("Sign In...");
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        SimpleTourApi.getInstance()
                .signIn(email, password)
                .subscribeOn(Schedulers.io())
                .subscribe(accessToken -> {
                    Timber.d("access token...");
                    Timber.d(accessToken);
                    PreferencesHelper.getInstance().putAccessToken(accessToken);
                    runOnUiThread(() -> {
                        startActivity(new Intent(this, DestinationsActivity.class));
                        finish();
                    });
                }, throwable -> {
                    Timber.d("error...");
                    throwable.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show());
                });
    }

    @OnClick(R.id.btn_sign_up)
    public void goToSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
