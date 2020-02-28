package com.example.retrofitwanandroiddemo.network

import android.util.Log
import com.example.retrofitwanandroiddemo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

public class RetrofitManager private constructor(){

    companion object{
        const val TAG = "Zero"
        private val INSTANCE = RetrofitManager()

        fun getInstance() = INSTANCE


    }

    internal class HttpLogger : HttpLoggingInterceptor.Logger {
        private val mMessage = StringBuilder()
        override fun log(message: String) { // 请求或者响应开始
            var message = message
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0)
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            val isJson = (message.startsWith("{") && message.endsWith("}")
                    || message.startsWith("[") && message.endsWith("]"))
            if (isJson) {
                message = JsonUtil.formatJson(message)
            }
            mMessage.append(message + "\n")
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                Log.d("Zero", mMessage.toString())
            }
        }
    }

    fun createRetrofit(baseUrl: String): Retrofit{
//        val okHttpClient = OkHttpClient().newBuilder()
//            .addInterceptor(HttpLoggingInterceptor().setLevel(//if else
//                if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
//                else HttpLoggingInterceptor.Level.NONE
//            ))
//            .build()

        val okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(
                HttpLoggingInterceptor().setLevel(//if else
                    if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE))
        }.build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun <T> getService(service: Class<T>,baseUrl: String = "https://www.wanandroid.com/"):T = createRetrofit(baseUrl).create(service)



}