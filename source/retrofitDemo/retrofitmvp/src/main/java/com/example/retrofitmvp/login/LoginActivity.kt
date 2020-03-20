package com.example.retrofitmvp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofitmvp.R
import com.example.retrofitmvp.api.WanAndroidApi
import com.example.retrofitmvp.beans.LoginBean
import com.example.retrofitmvp.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() ,LoginView{

    private val loginPresenter: LoginPresenter by lazy {
            LoginPresenterImpl(this)
    }

    companion object{
        const val TAG = "Zero"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login.setOnClickListener {
            //实现登录
            loginPresenter.login(MainActivity@this,username.text.toString(),password.text.toString())
        }

        register.setOnClickListener {
            //实现注册

        }

    }

    override fun loginSuccess(loginBean: LoginBean) {
        Log.i(TAG,"$loginBean")
    }

    override fun loginFailure(errorMsg: String) {
    }

}
