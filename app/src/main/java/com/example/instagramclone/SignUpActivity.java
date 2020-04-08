package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtSignUpUsername,edtSignUpPassword;
    private Button btnSignUp,btnLoginActivity;

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

        if(ParseUser.getCurrentUser()!=null){
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(edtSignUpUsername.getText().toString());
                appUser.setPassword(edtSignUpPassword.getText().toString());
                final ProgressDialog pdSignup = new ProgressDialog(this);
                pdSignup.setMessage("Sign up!");
                pdSignup.show();
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            FancyToast.makeText(getApplicationContext(),appUser.getUsername()+ " is successfully signed up!",
                                    Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                            pdSignup.dismiss();
                        }else{
                            FancyToast.makeText(getApplicationContext(),e.getMessage()+ "",
                                    Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                        }
                    }
                });
                break;
            case R.id.btnLoginActiviy:
                Intent intentLoginActivity = new Intent(getApplicationContext(),LogInActivity.class);
                startActivity(intentLoginActivity);
                break;
        }


    }
}
