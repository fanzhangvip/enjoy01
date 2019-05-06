package com.zero.mvpdagger2demo01.ui.login;



import com.zero.mvpdagger2demo01.network.api.WanAndroidApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Model层负责数据的访问操作，同时开放接口提供给presenter层进行调用
 */
public class LoginModel implements LoginContract.Model {
    private static final String TAG = LoginModel.class.getSimpleName();

    @Inject
    public LoginModel() {
    }

    public LoginModel(String user, boolean isSucess, String msg) {
        this.user = user;
        this.isSucess = isSucess;
        this.msg = msg;
    }

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

    @Inject
    WanAndroidApi wanAndroidApi;

    @Override
    public Observable<LoginContract.Model> login(String mobile, String password) {
        return wanAndroidApi.login(mobile, password)
                .map(baseResponse -> (LoginContract.Model) new LoginModel(mobile, baseResponse.getErrorCode() == 0 ? true : false, baseResponse.getErrorMsg()))
                .toObservable();
    }
}
