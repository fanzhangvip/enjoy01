package com.zero.mvpdagger2demo01.ui.register;


import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zero.mvpdagger2demo01.R;
import com.zero.mvpdagger2demo01.base.BaseActivity;
import com.zero.mvpdagger2demo01.ui.login.LoginActivity;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_re_password)
    EditText etRePassword;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Inject
    RegisterPresenter presenter;

    private static int SECOND = 20;
    private Observable<Boolean> registerObservable;

    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {

        registerObservable = RxView.clicks(btnRegister)
                .throttleFirst(SECOND, TimeUnit.SECONDS) //防止20秒内连续点击,或者只使用doOnNext部分
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(o -> false)
                .doOnNext(btnRegister::setEnabled);
        registerObservable.subscribe(s ->
                Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .take(SECOND)
                        .subscribe(aLong ->
                                        RxTextView.text(btnRegister).accept("剩余" + (SECOND - aLong) + "秒")
                                , Throwable::printStackTrace
                                , () -> {
                                    RxTextView.text(btnRegister).accept("注 册");
                                    RxView.enabled(btnRegister).accept(true);
                                }));


        Observable<CharSequence> observableName = RxTextView.textChanges(etUsername);
        Observable<CharSequence> observablePassword = RxTextView.textChanges(etPassword);
        Observable<CharSequence> observableRePassword = RxTextView.textChanges(etRePassword);

        Observable.combineLatest(observableName
                , observablePassword
                , observableRePassword
                , (usr, pwd, rePwd) -> registerValid(usr.toString(), pwd.toString(), rePwd.toString()))
                .subscribe(btnRegister::setEnabled);

        RxView.clicks(btnRegister)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> {
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    presenter.register(username, password, password);
                });


    }

    private static Boolean registerValid(String usr, String pwd, String rePwd) {
        return isUsrValid(usr) && isPasswordValid(pwd, rePwd);
    }

    private static boolean isUsrValid(String usr) {
        return usr.length() == 11;
    }

    private static boolean isPasswordValid(String pwd, String rePwd) {
        return pwd.length() >= 6 && TextUtils.equals(pwd, rePwd);
    }

    @Override
    public void registerSuccess(RegisterContract.Model result) {
        if (result.isSucess()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Toast.makeText(this, result.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {

    }
}
