package com.zero.mvpdagger2demo01.ui.login;


import com.zero.mvpdagger2demo01.R;
import com.zero.mvpdagger2demo01.base.BaseActivity;
import com.zero.mvpdagger2demo01.ui.UIUtils;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @Inject
    LoginPresenter presenter;

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameLayout);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            UIUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrameLayout);
        }
        loginFragment.setPresenter(presenter);

    }

    @Override
    public void loginSuccess(LoginContract.Model result) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
    }
}
