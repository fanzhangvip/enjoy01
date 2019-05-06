package com.zero.mvpdemo.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    protected static final String TAG = BaseActivity.class.getSimpleName();

    private Unbinder mUnbinder;
    protected P mPresenter;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(layoutId());
        mUnbinder = ButterKnife.bind(this);

        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
        this.mPresenter = null;
        this.mUnbinder = null;
        this.mContext = null;
    }


    protected abstract int layoutId();

    protected abstract void initData();
}
