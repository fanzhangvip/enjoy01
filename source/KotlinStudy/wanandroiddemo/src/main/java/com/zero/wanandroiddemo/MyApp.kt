package com.zero.wanandroiddemo

import android.app.Application
import com.zero.wanandroiddemo.network.ApiClient

//open class APPLibray{
//    var innerDB:InnerDB? = null
//    get() = field!!
//    set(value) {
//        if(value!=null)
//         field = value
//    }
//    interface InnerDB{}
//}
class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()
        //初始化了这个WanAndroidApi 实例对象
        ApiClient.instance.createRetrofit()
    }

}