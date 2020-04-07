package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseInstallation;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtLoginUsername,edtLoginPassword;
    private Button btnLogin,btnSignUpActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtLoginUsername = findViewById(R.id.edtLoginUsername);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnSignUpActivity = findViewById(R.id.btnSignUpActivity);
        btnSignUpActivity.setOnClickListener(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:
                break;
            case R.id.btnSignUpActivity:
                Intent intentSignUp = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intentSignUp);
                break;
        }
    }
}
