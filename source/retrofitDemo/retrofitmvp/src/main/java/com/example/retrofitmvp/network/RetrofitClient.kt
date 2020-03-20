package com.example.retrofitmvp.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(){

    private lateinit var retrofit: Retrofit

    companion object{
        const val TAG = "Zero"
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitClient()
        }
    }

    init {
        createRetrofit()
    }

    private fun createRetrofit(){
         retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> getService(service: Class<T>): T = retrofit.create(service)

}