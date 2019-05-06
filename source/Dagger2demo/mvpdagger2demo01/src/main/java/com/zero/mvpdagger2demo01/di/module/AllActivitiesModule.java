package com.zero.mvpdagger2demo01.di.module;


import com.zero.mvpdagger2demo01.di.scope.ActivityScope;
import com.zero.mvpdagger2demo01.ui.di.LoginActivityModule;
import com.zero.mvpdagger2demo01.ui.di.RegisterActivityModule;
import com.zero.mvpdagger2demo01.ui.login.LoginActivity;
import com.zero.mvpdagger2demo01.ui.register.RegisterActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AllActivitiesModule {

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity contributeLoginActivityInjector();

    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity contributeRegisterActivityInjector();
}
