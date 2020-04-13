package com.example.instagramclone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instagramclone.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSignUpUsername, edtSignUpPassword;
    private Button btnSignUp, btnLoginActivity;
    private ProgressDialog pdSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtSignUpUsername = findViewById(R.id.edtSignUpUsername);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        btnLoginActivity = findViewById(R.id.btnLoginActiviy);
        btnLoginActivity.setOnClickListener(this);
        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();

        }
        pdSignUp = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                if (edtSignUpUsername.getText().toString().equals("") || edtSignUpPassword.getText().toString().equals("")) {
                    FancyToast.makeText(getApplicationContext(), "Username/Password required!",
                            Toast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                } else {
                    pdSignUp.setMessage("Signup!");
                    pdSignUp.show();
                    final ParseUser appUser = new ParseUser();
                    appUser.setUsername(edtSignUpUsername.getText().toString());
                    appUser.setPassword(edtSignUpPassword.getText().toString());
                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(getApplicationContext(), appUser.getUsername() + " is successfully signed up!",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                socialMediaActivity();
                            } else {
                                FancyToast.makeText(getApplicationContext(), e.getMessage() + "",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            }
                            pdSignUp.dismiss();
                        }

                    });
                }
                break;
            case R.id.btnLoginActiviy:
                Intent intentLoginActivity = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intentLoginActivity);
                break;
        }
    }
    public void onTappedWindow(View view){
        InputMethodManager inputMethodManager1 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager1.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
    private void socialMediaActivity(){
        Intent intent = new Intent(this, SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}
