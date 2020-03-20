package com.example.retrofitmvp.login

import com.example.retrofitmvp.beans.LoginBean

public interface LoginView{

    fun loginSuccess(loginBean: LoginBean)
    fun loginFailure(errorMsg: String)

}