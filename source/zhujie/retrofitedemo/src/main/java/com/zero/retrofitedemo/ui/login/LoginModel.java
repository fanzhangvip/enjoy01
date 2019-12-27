package com.zero.retrofitedemo.ui.login;


import com.zero.retrofitedemo.network.api.WanAndroidApi;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Model层负责数据的访问操作，同时开放接口提供给presenter层进行调用
 */
public class LoginModel implements LoginContract.Model {

    private WanAndroidApi wanAndroidApi;

    public void setWanAndroidApi(WanAndroidApi wanAndroidApi){
        this.wanAndroidApi = wanAndroidApi;
    }

    @Override
    public Observable<String> login(String mobile, String password) {
//        return wanAndroidApi.login(mobile,password)
//                .map( baseResponse -> baseResponse.getData().toString())
//                .toObservable();
        return Observable.just("登录成功: " + mobile).delay(1000, TimeUnit.MILLISECONDS);
    }
}
