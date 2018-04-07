package com.example.deepak.contactsintentdemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Deepak on 07-Apr-18.
 */

public class AppDelegate extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
