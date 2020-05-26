package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("NWCKb4UbllU8oDmKvhiHzwJR1itNiS3xvacWDbqO")
                .clientKey("Vrzj999Z7rW0bUTvQQOVIJL9BqSCHPm8WcMSPBnW")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
