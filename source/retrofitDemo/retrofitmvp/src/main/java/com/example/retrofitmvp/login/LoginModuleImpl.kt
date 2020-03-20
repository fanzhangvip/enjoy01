package com.example.retrofitmvp.login

import android.content.Context
import android.util.Log
import com.example.retrofitmvp.MainActivity
import com.example.retrofitmvp.api.WanAndroidApi
import com.example.retrofitmvp.beans.LoginBean
import com.example.retrofitmvp.beans.RegisterBean
import com.example.retrofitmvp.network.ApiError
import com.example.retrofitmvp.network.ApiResponse
import com.example.retrofitmvp.network.NetworkScheduler
import com.example.retrofitmvp.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*

class LoginModuleImpl : LoginModule {
    override fun login(context: Context, username: String, password: String,callBack: LoginModule.CallBack) {
        RetrofitClient.instance.getService(WanAndroidApi::class.java).login(username,password)
            .compose(NetworkScheduler.compose())
            .subscribe(object: ApiResponse<LoginBean>(context){
                override fun success(data: LoginBean) {
                    callBack.loginSuccess(data)
                }

                override fun failure(statusCode: Int, apiError: ApiError) {

                }
            })
    }

    override fun register() {


    }
}