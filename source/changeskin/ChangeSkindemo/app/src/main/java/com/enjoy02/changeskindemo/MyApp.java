package com.enjoy02.changeskindemo;

import android.app.Application;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinEngine.getInstance().init(this);
    }
}
