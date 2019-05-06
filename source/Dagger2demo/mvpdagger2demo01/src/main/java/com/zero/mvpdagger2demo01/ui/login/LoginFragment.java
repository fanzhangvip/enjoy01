package com.zero.mvpdagger2demo01.ui.login;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import com.zero.mvpdagger2demo01.R;
import com.zero.mvpdagger2demo01.base.BaseFragment;
import com.zero.mvpdagger2demo01.ui.main.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * view层负责界面控件的显示
 */
public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btnLogin;


    private LoginFragment() {
        // Required empty public constructor
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initData() {
        Observable<CharSequence> ObservableName = RxTextView.textChanges(et_username);
        Observable<CharSequence> ObservablePassword = RxTextView.textChanges(et_password);

        Observable.combineLatest(ObservableName, ObservablePassword
                , (phone, password) -> isUsrValid(phone.toString()) && isPasswordValid(password.toString()))
                .subscribe(btnLogin::setEnabled);

        RxView.clicks(btnLogin)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> {
                    String username = et_username.getText().toString();
                    String password = et_password.getText().toString();
                    mPresenter.login(username, password);
                });
    }

    protected static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private static boolean isUsrValid(String usr) {
        return usr.length() == 11;
    }

    private static boolean isPasswordValid(String pwd) {
        return pwd.length() >= 6;
    }


    @Override
    public void loginSuccess(LoginContract.Model result) {
        if (result.isSucess()) {
            startActivity(new Intent(mActivity, MainActivity.class));
        } else {
            Toast.makeText(mActivity, result.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        if (mPresenter == null) {//P层与V层关联起来
            mPresenter = presenter;
        }
    }
}
