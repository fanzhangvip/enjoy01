package com.zero.mvpdagger2demo.ui.login;

import com.zero.mvpdagger2demo.base.AbsPresenter;
import com.zero.mvpdagger2demo.base.BaseModel;
import com.zero.mvpdagger2demo.base.BaseView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * presenter层负责逻辑功能代码、调用网络数据、本地数据封装层的编写
 */
public class LoginPresenter extends AbsPresenter<LoginContract.Model,LoginContract.View> implements LoginContract.Presenter {

//    @Inject
//    protected MainContract.Model mModel;
//    @Inject
//    protected MainContract.View mView;

    private Disposable disposable;

    /**
     * 这里由构造方法提供注入实例
     * @param model
     * @param view
     */
    @Inject //@Inject作用二: 用来标记构造函数，
    // Dagger2通过@Inject注解可以在需要这个类实例的时候来找到这个构造函数并把相关实例构造出来
    // ，以此来为被@Inject标记了的变量提供依赖
    public LoginPresenter(LoginContract.Model model,LoginContract.View view) {
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
