package com.zero.mvpdagger2demo01.ui.login;

import com.zero.mvpdagger2demo01.base.AbsPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * presenter层负责逻辑功能代码、调用网络数据、本地数据封装层的编写
 */
public class LoginPresenter extends AbsPresenter<LoginContract.Model,LoginContract.View> implements LoginContract.Presenter {

    private Disposable disposable;


    @Inject
    public LoginPresenter(LoginModel model,LoginActivity view) {
        mModel = model;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void login(String mobile, String password) {

        //通过Model获取数据
        disposable = mModel.login(mobile, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //然后去更新View层的UI
                .subscribe(mView::loginSuccess,s -> System.out.println(s.getMessage()));

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

}
