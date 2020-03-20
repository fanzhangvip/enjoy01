package com.example.retrofitdemo.retrofit

import com.example.retrofitdemo.BuildConfig
import com.example.retrofitdemo.retrofit.calladapter.LiveDataCallAdapterFactory
import com.example.retrofitdemo.retrofit.converter.MyGsonConvertFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//双重校验锁式
class RetrofitClient private constructor(){

    lateinit var retrofit: Retrofit

    companion object{
        const val TAG = "Zero"
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitClient()
        }

    }

    init {
        createRetrofit()
    }

    private fun createRetrofit(){
        retrofit = Retrofit.Builder()
            //Retrofit2的baseUrl 必须以 /(斜杆)结束，抛出一个IllegalArgumentException
            .baseUrl(BuildConfig.API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
            .addConverterFactory(MyGsonConvertFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    inline fun  <reified T> getService(service: Class<T>): T = retrofit.create(service)

}