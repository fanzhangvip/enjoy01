package com.zero.mvpdemo.ui.login;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    private Disposable disposable;

    LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void login(String mobile, String password) {

        disposable = Observable.just("登录成功: " + mobile)
                .debounce(500, TimeUnit.MILLISECONDS)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::loginSuccess);

    }

    @Override
    public void start() {

    }

    @Override
    public void onDestory() {
        view = null;
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }
}
