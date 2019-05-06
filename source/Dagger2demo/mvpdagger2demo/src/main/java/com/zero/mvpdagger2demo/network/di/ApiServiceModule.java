package com.zero.mvpdagger2demo.network.di;

import com.zero.mvpdagger2demo.network.api.WanAndroidApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import retrofit2.Retrofit;

@Module
public class ApiServiceModule {

//    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        return HttpUrl.parse("https://www.wanandroid.com/");
    }

//    @Singleton
    @Provides
    WanAndroidApi provideUserService(Retrofit retrofit) {
        return retrofit.create(WanAndroidApi.class);
    }
}
