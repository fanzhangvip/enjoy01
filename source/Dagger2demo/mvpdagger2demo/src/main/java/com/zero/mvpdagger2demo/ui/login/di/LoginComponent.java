package com.zero.mvpdagger2demo.ui.login.di;

import com.zero.mvpdagger2demo.di.AppComponent;
import com.zero.mvpdagger2demo.ui.login.LoginActivity;

import dagger.Component;

/**
 * 注射器
 * 可以理解为快递员，那么他需要送的货物就是modules里面包含的包裹
 */
@Component(modules = LoginModule.class,dependencies = AppComponent.class)
public interface LoginComponent {

    /**
     * 把Component理解为快递员，那么他把包裹送给谁呢
     * 这里的inject方法的LoginActivity 就是送货的地址
     *
     * @param activity 目标容器
     * inject的参数。。。不能是父类，必须是你注入的那个内
     */
    void inject(LoginActivity activity);
}
