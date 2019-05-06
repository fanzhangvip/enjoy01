package com.zero.mvpdemo01.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.zero.mvpdemo01.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity{

    protected static final String TAG = BaseActivity.class.getSimpleName();

    protected App mApplication;
    private Unbinder mUnbinder;
    protected P mPresenter;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplication = (App) getApplication();
        mContext = this;

        setContentView(layoutId());
        mUnbinder = ButterKnife.bind(this);

        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if(mPresenter!=null){
            mPresenter.onDestory();
        }
        this.mPresenter = null;
        this.mUnbinder = null;
        this.mApplication = null;
    }


    protected abstract int layoutId();

    protected abstract void initData();
}
