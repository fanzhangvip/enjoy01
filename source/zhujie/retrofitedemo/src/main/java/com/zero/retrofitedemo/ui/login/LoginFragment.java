package com.zero.retrofitedemo.ui.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.zero.retrofitedemo.MainActivity;
import com.zero.retrofitedemo.R;
import com.zero.retrofitedemo.base.BaseFragment;
import com.zero.retrofitedemo.network.api.WanAndroidApi;
import com.zero.retrofitedemo.network.bean.ProjectBean;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        //MVP retrofit + rxjava
//        mPresenter.login(username, password);

        //断点调试大法
        //基本用法
        Retrofit retrofit = new Retrofit.Builder()//建造者模式
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WanAndroidApi wanAndroidApi = retrofit.create(WanAndroidApi.class);//代理实例

        Call<ProjectBean> call = wanAndroidApi.getProject1();//获取具体的某个业务
        call.enqueue(new Callback<ProjectBean>() {
            @Override
            public void onResponse(final Call<ProjectBean> call, final Response<ProjectBean> response) {
                Log.i("Zero","response: " + response.body());
            }

            @Override
            public void onFailure(final Call<ProjectBean> call, final Throwable t) {

            }
        });

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
