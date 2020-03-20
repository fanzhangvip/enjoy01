package com.example.retrofitmvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofitmvp.api.WanAndroidApi
import com.example.retrofitmvp.beans.LoginBean
import com.example.retrofitmvp.beans.RegisterBean
import com.example.retrofitmvp.login.LoginModule
import com.example.retrofitmvp.login.LoginModuleImpl
import com.example.retrofitmvp.network.ApiError
import com.example.retrofitmvp.network.ApiResponse
import com.example.retrofitmvp.network.NetworkScheduler
import com.example.retrofitmvp.network.RetrofitClient
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class MainActivity : AppCompatActivity() ,LoginModule.CallBack{


    private val loginPresenter by lazy {

    }


    companion object{
        const val TAG = "Zero"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login.setOnClickListener {
            //实现登录

        }

        register.setOnClickListener {
            //实现注册


        }

    }

    override fun loginSuccess(loginBean: LoginBean) {
        Log.i(TAG,"$loginBean")
    }
}
