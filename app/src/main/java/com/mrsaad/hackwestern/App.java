package com.mrsaad.hackwestern;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
//import com.parse.ParseInstallation;

public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();

        //setup parse
//        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "--APP ID--", "--CLIENT ID--");
        ParseInstallation.getCurrentInstallation().saveInBackground();


    }
} 