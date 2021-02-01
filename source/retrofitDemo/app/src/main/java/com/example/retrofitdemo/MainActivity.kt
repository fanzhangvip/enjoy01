package com.example.retrofitdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val wanAndroidApi = RetrofitClient.instance.getService(WanAndroidApi::class.java)

    companion object {
        const val TAG = "Zero"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        testRetrofit()
//        okhttpTest()
//        testRetrofit()
//        testMyRetrofit()
        mySimpleRetrofit()
    }












































































    @Throws(IOException::class)
    private fun uploadImagesAndText() {
        val retrofit = Retrofit.Builder() //建造者模式
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val wanAndroidApi = retrofit.create(WanAndroidApi::class.java) //代理实例

        val textType = MediaType.parse("text/plain")
        val name = RequestBody.create(textType, "zero")
        val password = RequestBody.create(textType, "123456")

        val file = File("")
        val requestBody =
            RequestBody.create(MediaType.parse("image/png"), file)
        val part = MultipartBody.Part.createFormData(
            "上传的key"
            , file.name, requestBody
        )
        wanAndroidApi
            .upload6(name, password, part)
            .enqueue(object : Callback<ProjectBean> {
                override fun onResponse(
                    call: Call<ProjectBean>,
                    response: Response<ProjectBean>
                ) {
                }

                override fun onFailure(
                    call: Call<ProjectBean>,
                    t: Throwable
                ) {
                }
            })
    }


    private fun testupload3() {
        val files = listOf<File>()
        val map = mutableMapOf<String, MultipartBody.Part>()
        files.forEachIndexed { index, file ->
            val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
            val part = MultipartBody.Part.createFormData("上传的key${index}", file.name, requestBody)
            map["上传的key${index}"] = part
        }
        wanAndroidApi.upload4(map)
    }

    private fun testupload2() {

        //图片集合
        val files = listOf<File>()
        val map = mutableMapOf<String, RequestBody>()
        files.forEach() { file ->
            val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
            map["file\";filename=\"test.png"] = requestBody
        }
        wanAndroidApi.upload3(map)

    }


    private fun testupload1() {
        val file = File("")
        val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
        val filePart = MultipartBody.Part.createFormData(
            "上传的key",
            file.name, requestBody
        )
        val call = wanAndroidApi.upload2(filePart)
        call.execute()

    }

    private fun testupload() {
        //上传单个文件
        val file = File("")
        val requestBody = RequestBody.create(MediaType.parse("image/png"), file)

        val call = wanAndroidApi.upload(requestBody)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            }

        })

    }

}
