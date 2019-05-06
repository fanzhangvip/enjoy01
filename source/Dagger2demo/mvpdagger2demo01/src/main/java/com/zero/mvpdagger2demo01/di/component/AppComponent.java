package com.zero.mvpdagger2demo01.di.component;


import com.zero.mvpdagger2demo01.App;
import com.zero.mvpdagger2demo01.di.module.AllActivitiesModule;
import com.zero.mvpdagger2demo01.di.module.AppModule;
import com.zero.mvpdagger2demo01.network.api.WanAndroidApi;
import com.zero.mvpdagger2demo01.network.di.ApiServiceModule;
import com.zero.mvpdagger2demo01.network.di.HttpModule;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

//@Singleton
@Component(modules = {AndroidInjectionModule.class
        , AndroidSupportInjectionModule.class
        , AllActivitiesModule.class
        , AppModule.class
        , HttpModule.class
        , ApiServiceModule.class})
public interface AppComponent {

    void inject(App application);

    WanAndroidApi getWanAndroidApi();
}
