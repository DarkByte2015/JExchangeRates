package com.panov.ilya.jexchangerates;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by ilya on 24.05.17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
