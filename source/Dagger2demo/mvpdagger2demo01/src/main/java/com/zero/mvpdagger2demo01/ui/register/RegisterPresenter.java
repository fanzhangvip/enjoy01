package com.zero.mvpdagger2demo01.ui.register;

import com.zero.mvpdagger2demo01.base.AbsPresenter;
import com.zero.mvpdagger2demo01.ui.di.RegisterActivityModule;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * presenter层负责逻辑功能代码、调用网络数据、本地数据封装层的编写
 */
public class RegisterPresenter extends AbsPresenter<RegisterModel, RegisterActivity> implements RegisterContract.Presenter {


    private Disposable disposable;


    @Inject
    public RegisterPresenter(RegisterModel model, RegisterActivity view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void register(String mobile, String password, String rePassword) {

        //通过Model获取数据
        disposable = mModel.register(mobile, password,rePassword )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //然后去更新View层的UI
                .subscribe(mView::registerSuccess,s -> System.out.println(s.getMessage()));

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
