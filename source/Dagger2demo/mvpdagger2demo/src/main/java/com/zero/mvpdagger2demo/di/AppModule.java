package com.zero.mvpdagger2demo.di;


import android.app.Application;

import com.zero.mvpdagger2demo.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    public AppModule(App application) {
        this.mApplication = application;
    }

    private Application mApplication;

//    @Singleton
    @Provides
    public Application provideApplication() {
        return mApplication;
    }
}
