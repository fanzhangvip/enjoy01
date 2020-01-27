package com.zero.wanandroiddemo.login

import com.zero.wanandroiddemo.R

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import com.zero.wanandroiddemo.api.WanAndroidApi
import com.zero.wanandroiddemo.bean.LoginBean
import com.zero.wanandroiddemo.network.*
import com.zero.wanandroiddemo.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : RxAppCompatActivity(),LoginView {

    private val  loginPresenter: LoginPresenterImpl by lazy {
        LoginPresenterImpl(this)
    }

    //private static final String TAG = "Zero";
    companion object{//伴生对象 一个类只能有一个
        val TAG = "Zero"
    }

//    object TAGS{
//        val TAG1 = "Zero"
//    }

    lateinit var presenter:LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        loginPresenter = LoginPresenterImpl(this)
        login.setOnClickListener(onClickListener)
        register.setOnClickListener(onClickListener)
//        loginPresenter.attachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.dettachView()
    }

    private val onClickListener = View.OnClickListener {
        view ->
        when(view.id){
            R.id.login  ->{
                //调用登录
                val usernameStr = username.text.toString()
                val passwordStr = password.text.toString()
                Log.i(TAG,"username: $usernameStr, password: $passwordStr")
                loginPresenter.loginWanAndroid(this@LoginActivity,usernameStr,passwordStr)
            }
            R.id.register ->{
                //调用注册
                val usernameStr = username.text.toString()
                val passwordStr = password.text.toString()
                loginPresenter.registerWanAndroid(this@LoginActivity,usernameStr,passwordStr,passwordStr)
            }
        }

    }

    override fun loginSucces(loginBean: LoginBean) {
        Toast.makeText(this@LoginActivity,loginBean.toString(),Toast.LENGTH_LONG).show()
        Log.i(TAG,"loginBean: $loginBean")
    }

    override fun loginFailure(errorMsg: String) {
        toast("")
        Log.i("Zero","failure")
    }


}
