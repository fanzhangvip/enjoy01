package com.example.retrofitmvp.login

import android.content.Context
import com.example.retrofitmvp.beans.LoginBean

interface LoginModule {

    interface CallBack{
        fun loginSuccess(loginBean: LoginBean)
    }

    fun login(context: Context,username: String,password: String,callBack: CallBack)
    fun register()
}