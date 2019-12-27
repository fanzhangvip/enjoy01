package com.zero.wanandroiddemo.login

import android.content.Context
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import com.zero.wanandroiddemo.api.WanAndroidApi
import com.zero.wanandroiddemo.bean.LoginBean
import com.zero.wanandroiddemo.network.ApiClient
import com.zero.wanandroiddemo.network.ApiError
import com.zero.wanandroiddemo.network.ApiResponse
import com.zero.wanandroiddemo.network.NetworkScheduler
import io.reactivex.Observer

class LoginModuleImpl : LoginModue{
    override fun cancleRequst() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(context: Context,
                       username: String,
                       password: String,
                       onLoginListener: LoginPresenter.OnLoginListener
    ) {
        ApiClient.instance.getService(WanAndroidApi::class.java).loginWanAndroid(username,password)
            .compose(NetworkScheduler.compose())
            .subscribe(object: ApiResponse<LoginBean>(context){
                override fun success(data: LoginBean) {
                        onLoginListener.loginSuccess(loginBean = valide(data))
                }

                override fun failure(statusCode: Int, apiError: ApiError) {
                        onLoginListener.loginFailed(apiError.message)
                }

            })
    }

    fun valide(loginBean: LoginBean):LoginBean{
        return loginBean
    }

    override fun register(context: Context,
        username: String,
        password: String,
        repassword: String,
        onRegisterListener: LoginPresenter.OnRegisterListener
    ) {
        ApiClient.instance.getService(WanAndroidApi::class.java).registerWanAndroid(username,password,repassword)
            .compose(NetworkScheduler.compose())
            .subscribe(object: ApiResponse<LoginBean>(context){
                override fun success(data: LoginBean) {
                    onRegisterListener.registerSuccess(loginBean = data)
                }

                override fun failure(statusCode: Int, apiError: ApiError) {
                    onRegisterListener.registerFailed(apiError.message)
                }

            })
    }


}