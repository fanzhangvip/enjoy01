package com.zero.wanandroiddemo.login

import com.zero.wanandroiddemo.bean.LoginBean

interface LoginView{
    fun loginSucces(loginBean: LoginBean)
    fun loginFailure(errorMsg:String)

}