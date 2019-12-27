package com.zero.wanandroiddemo.login

import android.content.Context
import com.zero.wanandroiddemo.bean.LoginBean
import io.reactivex.Observer

interface LoginModue{
    fun cancleRequst()
    fun login(context: Context, username:String, password:String, onLoginListener: LoginPresenter.OnLoginListener)
    fun register(context: Context,username:String,password:String,repassword:String,onRegisterListener: LoginPresenter.OnRegisterListener)
}