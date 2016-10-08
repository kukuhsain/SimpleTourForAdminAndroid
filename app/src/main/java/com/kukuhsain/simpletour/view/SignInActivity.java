package com.kukuhsain.simpletour.view;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.kukuhsain.simpletour.R;

import timber.log.Timber;

/**
 * Created by kukuh on 08/10/16.
 */

public class SignInActivity extends AppCompatActivity {

    TextInputEditText etEmail;
    TextInputEditText etPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setupView();
    }

    private void setupView() {
        etEmail = (TextInputEditText) findViewById(R.id.et_email);
        etPassword = (TextInputEditText) findViewById(R.id.et_password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);

        setupSignInButton();
    }

    private void setupSignInButton() {
        btnSignIn.setOnClickListener(view -> {
            Timber.d(etEmail.getText().toString());
            Timber.d(etPassword.getText().toString());
        });
    }
}
