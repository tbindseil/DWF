package com.tjb.dwf;

import android.app.Application;
import android.content.Context;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class DWFApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        DWFApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return DWFApplication.context;
    }
}

