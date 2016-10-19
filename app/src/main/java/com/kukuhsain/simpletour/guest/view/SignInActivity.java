package com.kukuhsain.simpletour.guest.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;

import com.kukuhsain.simpletour.guest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
        Timber.d(etEmail.getText().toString());
        Timber.d(etPassword.getText().toString());
        startActivity(new Intent(this, DestinationsActivity.class));
        finish();
    }

    @OnClick(R.id.btn_sign_up)
    public void goToSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
