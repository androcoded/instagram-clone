package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("kPGJVXsBVFFacWpCZnjH3OvTSxjwwcjtqecTsTvX")
                .clientKey("E3rX80oPHG7neEB5YK7ZvFftMPtd6dyisIGfvWZW")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
