package com.kukuhsain.simpletour.view;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kukuhsain.simpletour.R;

/**
 * Created by kukuh on 08/10/16.
 */

public class SignInActivity extends AppCompatActivity {

    TextInputEditText etEmail = (TextInputEditText) findViewById(R.id.et_email);
    TextInputEditText etPassword = (TextInputEditText) findViewById(R.id.et_password);
    Button btnSignIn = (Button) findViewById(R.id.btn_sign_in);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    private void setupSignInButton() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
