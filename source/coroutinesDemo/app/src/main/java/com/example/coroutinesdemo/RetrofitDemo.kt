package com.example.coroutinesdemo

import android.util.Log
import com.example.coroutinesdemo.bean.ProjectBean
import com.example.coroutinesdemo.bean.ProjectItem
import com.example.coroutinesdemo.retrofit.RetrofitClient
import com.google.gson.Gson
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


class RetrofitDemo {
    interface WanAndroidApi {
        @GET("project/tree/json")
        fun getProject(): Call<ProjectBean>

        @GET("project/list/{pageIndex}/json")
        fun getProjectItem(
            @Path("pageIndex") pageIndex: Int,
            @Query("cid") cid: Int
        ): Call<ProjectItem>

    }
    

    fun getProject(){
        RetrofitClient.instance.getService(WanAndroidApi::class.java).getProject()
            .enqueue(object: Callback<ProjectBean>{
                override fun onFailure(call: Call<ProjectBean>, t: Throwable) {
                }

                override fun onResponse(call: Call<ProjectBean>, response: Response<ProjectBean>) {
                    Log.i(TAG,"${response.body().toString()}")
                }

            })
    }

    fun getProjectItem(){
        RetrofitClient.instance.getService(WanAndroidApi::class.java).getProject()
            .enqueue(object: Callback<ProjectBean>{
                override fun onFailure(call: Call<ProjectBean>, t: Throwable) {
                }

                override fun onResponse(call: Call<ProjectBean>, response: Response<ProjectBean>) {
                    Log.i(TAG,"${response.body().toString()}")
                    val gson = Gson();
                    RetrofitClient.instance.getService(WanAndroidApi::class.java).getProjectItem(1,3)
                }

            })
    }
}

