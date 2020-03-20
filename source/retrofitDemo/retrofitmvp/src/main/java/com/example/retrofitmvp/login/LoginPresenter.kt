package com.example.retrofitmvp.login

import android.content.Context

interface LoginPresenter {

    fun login(context: Context,username:String,password: String)

}