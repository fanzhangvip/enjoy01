package com.example.retrofitmvp.login

import android.content.Context
import com.example.retrofitmvp.beans.LoginBean

class LoginPresenterImpl(private var loginView: LoginView): LoginPresenter ,LoginModule.CallBack{

    private val loginModule = LoginModuleImpl()

    override fun login(context: Context, username: String, password: String) {
        loginModule.login(context,username,password,this)
    }

    override fun loginSuccess(loginBean: LoginBean) {
        loginView.loginSuccess(loginBean)
    }
}