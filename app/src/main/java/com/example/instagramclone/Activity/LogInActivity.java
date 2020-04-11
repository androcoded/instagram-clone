package com.example.instagramclone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instagramclone.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginUsername, edtLoginPassword;
    private Button btnLogin, btnSignUpActivity;
    private ProgressDialog pdLogin;

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
        if (ParseUser.getCurrentUser() != null) {
            ParseUser.logOut();
        }
        pdLogin = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                if (edtLoginUsername.getText().toString().equals("") || edtLoginPassword.getText().toString().equals("")) {
                    FancyToast.makeText(getApplicationContext(), "Username/Password required!",
                            Toast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                } else{
                    pdLogin.setMessage("Login!");
                    pdLogin.show();
                    ParseUser.logInInBackground(edtLoginUsername.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                FancyToast.makeText(getApplicationContext(), user.getUsername() + " is successfully logged in!",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                                Intent intentWelcomeActivity = new Intent(getApplicationContext(), SocialMediaActivity.class);
                                startActivity(intentWelcomeActivity);
                            } else {
                                FancyToast.makeText(getApplicationContext(), e.getMessage() + "",
                                        Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            }
                            pdLogin.dismiss();
                        }
                    });
                }
                break;
            case R.id.btnSignUpActivity:
                Intent intentSignUp = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intentSignUp);
                break;

        }
    }

    public void tapOnBlankScreen(View v) {
        InputMethodManager inputMethodManager2 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager2.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
    private void socialMediaActivity(){
        Intent intent = new Intent(this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
