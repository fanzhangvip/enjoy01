package com.example.uiperf;

import android.app.Application;

import com.example.uiperflib.UIPerfMonitor;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UIPerfMonitor.getInstance().startMonitor();
    }
}
