package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

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
        if(ParseUser.getCurrentUser()!=null){
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:
                final ProgressDialog pdLogin = new ProgressDialog(this);
                pdLogin.setMessage("Login!");
                pdLogin.show();
                ParseUser.logInInBackground(edtLoginUsername.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user!=null && e == null){
                            FancyToast.makeText(getApplicationContext(),user.getUsername()+ " is successfully logged in!",
                                    Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                            pdLogin.dismiss();
                            Intent intentWelcomeActivity = new Intent(getApplicationContext(),WelcomeActivity.class);
                            startActivity(intentWelcomeActivity);
                        }else{
                            FancyToast.makeText(getApplicationContext(),e.getMessage()+ "",
                                    Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                        }
                    }
                });
                break;
            case R.id.btnSignUpActivity:
                Intent intentSignUp = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intentSignUp);
                break;
        }
    }
}
