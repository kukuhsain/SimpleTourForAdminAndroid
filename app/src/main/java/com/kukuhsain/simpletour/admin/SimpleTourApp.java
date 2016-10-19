package com.kukuhsain.simpletour.admin;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by kukuh on 08/10/16.
 */

public class SimpleTourApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
