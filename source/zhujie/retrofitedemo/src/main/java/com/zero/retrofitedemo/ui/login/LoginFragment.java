package com.zero.retrofitedemo.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.zero.retrofitedemo.MainActivity;
import com.zero.retrofitedemo.R;
import com.zero.retrofitedemo.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * view层负责界面控件的显示
 */
public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;


    private LoginFragment() {
        // Required empty public constructor
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initData() {

    }

    protected static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @OnClick(R.id.btn_login)
    public void onClick(View v) {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        mPresenter.login(username, password);
    }


    @Override
    public void loginSuccess(String result) {
        Toast.makeText(mActivity, result, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(mActivity, MainActivity.class));
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        if (mPresenter == null) {//P层与V层关联起来
            mPresenter = presenter;
        }
    }
}
