package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofitdemo.api.WanAndroidApi
import com.example.retrofitdemo.bean.ProjectBean
import com.example.retrofitdemo.retrofit.RetrofitClient
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import java.io.File

class MainActivity : AppCompatActivity() {

    private val wanAndroidApi = RetrofitClient.instance.getService(WanAndroidApi::class.java)

    companion object{
        const val TAG = "Zero"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testRetrofit()
    }

    private fun testupload3(){
        val files = listOf<File>()
        val map = mutableMapOf<String,MultipartBody.Part>()
        files.forEachIndexed { index, file ->
            val requestBody = RequestBody.create(MediaType.parse("image/png"),file)
            val part = MultipartBody.Part.createFormData("上传的key${index}",file.name,requestBody)
            map["上传的key${index}"] = part
        }
        wanAndroidApi.upload4(map)
    }

    private fun testupload2(){

        //图片集合
        val files = listOf<File>()
        val map = mutableMapOf<String,RequestBody>()
        files.forEach() {file ->
            val requestBody = RequestBody.create(MediaType.parse("image/png"),file)
            map["file\";filename=\"test.png"] = requestBody
        }
        wanAndroidApi.upload3(map)

    }



    private fun testupload1(){
        val file = File("")
        val requestBody = RequestBody.create(MediaType.parse("image/png"),file)
        val filePart =  MultipartBody.Part.createFormData("上传的key",
            file.name,requestBody)
        val call = wanAndroidApi.upload2(filePart)
        call.execute()

    }

    private fun testupload(){
        //上传单个文件
        val file = File("")
        val requestBody = RequestBody.create(MediaType.parse("image/png"),file)

        val call = wanAndroidApi.upload(requestBody)

        call.enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            }

        })

    }

    private fun testRetrofit(){
        //1. 构建一个retrofit对象
        val retrofit = Retrofit.Builder()
             //Retrofit2的baseUrl 必须以 /(斜杆)结束，抛出一个IllegalArgumentException
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //2. 获取WanAndroidApi接口的代理对象
        val wanAndroidApi = retrofit.create(WanAndroidApi::class.java)

        //3. 获取具体的请求业务方法
        val projectCall = wanAndroidApi.getProject()

        //发起请求
        // 同步
//        val projectBean  = projectCall.execute()
        //异步
        projectCall.enqueue(object : Callback<ProjectBean>{
            override fun onFailure(call: Call<ProjectBean>, t: Throwable) {
                Log.i(TAG,"错误：${t.message}")
            }

            override fun onResponse(call: Call<ProjectBean>, response: Response<ProjectBean>) {
                Log.i(TAG,"成功： ${response.body()}")
            }

        })
    }
}
