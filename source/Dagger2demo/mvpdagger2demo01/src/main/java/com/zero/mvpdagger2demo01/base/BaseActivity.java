package com.zero.mvpdagger2demo01.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.zero.mvpdagger2demo01.App;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;


public abstract class BaseActivity extends AppCompatActivity {

    protected static final String TAG = BaseActivity.class.getSimpleName();

    protected App mApplication;
    private Unbinder mUnbinder;

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
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
        this.mUnbinder = null;
        this.mApplication = null;
    }


    protected abstract int layoutId();

    protected abstract void initData();
}
