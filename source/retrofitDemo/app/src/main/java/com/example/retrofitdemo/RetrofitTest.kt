package com.example.retrofitdemo

import android.util.Log
import com.example.retrofitdemo.bean.ProjectBean
import com.zero.myretrofit.MyRetrofit
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import java.io.IOException
import java.lang.reflect.Proxy

const val TAG = "Zero"

interface Api1 {
    @GET("project/tree/json")
    fun getProject(): Call<ProjectBean>

    //////图文混传/////
    @Multipart
    @POST("upload/upload")
    fun upload(
        @Part("username") userName: RequestBody,
        @Part("password") password: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<ProjectBean>
}

fun testRetrofit() {
    val retrofit = Retrofit.Builder()//1. 构建一个retrofit对象
        .baseUrl("https://www.wanandroid.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api1 = retrofit.create(Api1::class.java)//2. 获取WanAndroidApi接口的代理对象
    val projectCall = api1.getProject()//3. 获取具体的请求业务方法
//  val projectBean  = projectCall.execute()// 同步
    projectCall.enqueue(object : Callback<ProjectBean> {
        //异步发起请求
        override fun onFailure(call: Call<ProjectBean>, t: Throwable) {
            Log.i(TAG, "错误：${t.message}")
        }

        override fun onResponse(call: Call<ProjectBean>, response: Response<ProjectBean>) {
            Log.i(TAG, "成功： ${response.body()}")
        }
    })
}

fun uploadImagesAndText() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val textType = MediaType.parse("text/plain")
    val name = RequestBody.create(textType, "zero")
    val password = RequestBody.create(textType, "123456")

    val file = File("")
    val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
    val part = MultipartBody.Part.createFormData("上传的key", file.name, requestBody)
    val api1 = retrofit.create(Api1::class.java)
    api1.upload(name, password, part)
}

fun okhttpTest() {
    val client = OkHttpClient()//1.创建一个OkHttpClient
    val request = Request.Builder()//创建一个Request
        .get()
        .url("https://www.wanandroid.com/project/tree/json")
        .build()
    client.newCall(request).enqueue(object : okhttp3.Callback {//newCall返回一个Call进行请求
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            Log.i(TAG, "错误：${e.message}")
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            Log.i(TAG, "成功： ${response.body()?.string()}")
        }

    })
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MyGET(val url: String)

interface Api2{
    @MyGET("https://www.wanandroid.com/project/tree/json")
    fun getProject(): okhttp3.Call

//    @MyGET("")
//    fun xxx(): okhttp3.Call

}
class MyApi2(): Api2{
    override fun getProject(): okhttp3.Call {
        TODO("Not yet implemented")
    }


}

fun mySimpleRetrofit(){

    val api2 = Proxy.newProxyInstance(
        Api2::class.java.classLoader,
        arrayOf<Class<*>>(Api2::class.java)
    ) { _, method, _ ->
        val isPresent = method.isAnnotationPresent(MyGET::class.java)
        val myGET = method.getAnnotation(MyGET::class.java)!!
        val url = myGET.url
        val client = OkHttpClient()
        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        client.newCall(request) as okhttp3.Call
    } as Api2
    api2.getProject().enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            Log.i(TAG, "错误：${e.message}")
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            Log.i(TAG, "成功： ${response.body()?.string()}")
        }

    })
}

interface Api3{
    @GET("project/tree/json")
    fun getProject(): okhttp3.Call
}

private fun testMyRetrofit() {
    val myretrofit = MyRetrofit.Builder()
        .baseUrl("https://www.wanandroid.com/")
        .build()!!

    val api3 = myretrofit.create(Api3::class.java)
    //3. 获取具体的请求业务方法
    val projectCall = api3.getProject()
    //异步
    projectCall.enqueue(object : okhttp3.Callback{
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            Log.i(MainActivity.TAG, "错误：${e.message}")
        }

        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            Log.i(MainActivity.TAG, "成功： ${response.body()?.string()}")
        }

    })
}