package com.example.instagramclone.ParseServer;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("IWfetQhYvlmWTyVH7lZ7YEkvA7MUhwlK8E0k0VRf")
                .clientKey("JsCW8kD5ftSHz3YltaxJ6i4OTWOdvQj8LmWpClca")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
