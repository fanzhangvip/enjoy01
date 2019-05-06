package com.zero.mvpdemo01.ui.login;

import com.zero.mvpdemo01.R;
import com.zero.mvpdemo01.base.BaseActivity;
import com.zero.mvpdemo01.ui.UIUtils;


public class LoginActivity extends BaseActivity<LoginContract.Presenter> {


    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameLayout);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            UIUtils.addFragmentToActivity(getSupportFragmentManager(),loginFragment , R.id.contentFrameLayout);
        }
        //初始化Presenter
        mPresenter = new LoginPresenter(loginFragment);

    }


}
