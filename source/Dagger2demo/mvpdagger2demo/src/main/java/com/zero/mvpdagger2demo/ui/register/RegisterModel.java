package com.zero.mvpdagger2demo.ui.register;


import com.zero.mvpdagger2demo.network.api.WanAndroidApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Model层负责数据的访问操作，同时开放接口提供给presenter层进行调用
 */
public class RegisterModel implements RegisterContract.Model {
    private static final String TAG = RegisterModel.class.getSimpleName();

    @Inject
    public RegisterModel() {
    }

    public RegisterModel(String user, boolean isSucess, String msg) {
        this.user = user;
        this.isSucess = isSucess;
        this.msg = msg;
    }

    @Inject
    WanAndroidApi wanAndroidApi;

    private String user;
    private boolean isSucess;
    private String msg;

    @Override
    public String getMsg() {
        return msg;
    }


    @Override
    public String getUser() {
        return user;
    }

    @Override
    public boolean isSucess() {
        return isSucess;
    }

    @Override
    public Observable<RegisterContract.Model> register(String mobile, String password, String rePassword) {
        return wanAndroidApi.register(mobile, password, rePassword)
                .map(baseResponse ->
                        (RegisterContract.Model) new RegisterModel(mobile, baseResponse.getErrorCode() == 0 ? true : false, baseResponse.getErrorMsg()))
                .toObservable();
    }
}
