package com.zero.mvpdagger2demo.di;

import android.app.Application;

import com.zero.mvpdagger2demo.network.api.WanAndroidApi;
import com.zero.mvpdagger2demo.network.di.ApiServiceModule;
import com.zero.mvpdagger2demo.network.di.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

//@Singleton
@Component(modules = {AppModule.class, HttpModule.class, ApiServiceModule.class})
public interface AppComponent {

    Application application();

    WanAndroidApi getWanAndroidApi();
}
