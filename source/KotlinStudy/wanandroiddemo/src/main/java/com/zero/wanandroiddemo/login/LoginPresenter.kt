package com.zero.wanandroiddemo.login

import android.content.Context
import com.zero.wanandroiddemo.base.IBasePresenter
import com.zero.wanandroiddemo.bean.LoginBean

interface LoginPresenter : IBasePresenter{

    fun loginWanAndroid(context: Context,username:String,password:String)
    fun registerWanAndroid(context: Context,username:String,password:String,repassword:String)

    interface OnLoginListener{
        fun loginSuccess(loginBean:LoginBean)
        fun loginFailed(errorMsg:String)
    }

    interface OnRegisterListener{
        fun registerSuccess(loginBean:LoginBean)
        fun registerFailed(errorMsg:String)
    }
}