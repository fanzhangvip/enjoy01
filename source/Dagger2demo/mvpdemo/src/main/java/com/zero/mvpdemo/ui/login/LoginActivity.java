package com.zero.mvpdemo.ui.login;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zero.mvpdemo.MainActivity;
import com.zero.mvpdemo.R;
import com.zero.mvpdemo.base.BaseActivity;

import butterknife.BindView;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
         new LoginPresenter(this).start();
    }

    @OnClick(R.id.btn_login)
    public void onClick(View v) {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        mPresenter.login(username,password);
    }



    @Override
    public void loginSuccess(String result) {
        Toast.makeText(mContext,result,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        if(mPresenter ==null){
            mPresenter = presenter;
        }
    }
}
