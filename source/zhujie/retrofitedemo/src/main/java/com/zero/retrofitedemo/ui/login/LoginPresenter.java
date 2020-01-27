package com.zero.retrofitedemo.ui.login;



import com.zero.retrofitedemo.base.AbsPresenter;
import com.zero.retrofitedemo.network.HttpUtil;
import com.zero.retrofitedemo.network.api.WanAndroidApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * presenter层负责逻辑功能代码、调用网络数据、本地数据封装层的编写
 */
public class LoginPresenter extends AbsPresenter<LoginContract.Model, LoginContract.View> implements LoginContract.Presenter {


    private Disposable disposable;

    LoginPresenter() {
    }

    @Override
    public void login(String mobile, String password) {

        //通过Model获取数据
        disposable = mModel.login(mobile, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //然后去更新View层的UI
                .subscribe(mView::loginSuccess);

    }

    @Override
    public void start() {
        this.mView.setPresenter(this);
    }

    @Override
    public void onDestory() {
        mView = null;
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    public void setModel(LoginContract.Model model) {
        this.mModel = model;
//        ((LoginModel)mModel).setWanAndroidApi(HttpUtil.getOnlineCookieRetrofit().create(WanAndroidApi.class));


    }

    @Override
    public void setView(LoginContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }
}
