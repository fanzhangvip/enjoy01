package com.example.retrofitwanandroiddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofitwanandroiddemo.network.RetrofitManager
import com.zero.wanandroiddemo.api.WanAndroidApi

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitManager.getInstance().getService(WanAndroidApi::class.java)
            .loginWanAndroid("aaa","aaa")
    }
}
