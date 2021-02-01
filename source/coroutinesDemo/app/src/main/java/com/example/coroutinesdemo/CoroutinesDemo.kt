package com.example.coroutinesdemo

import android.telecom.Call
import android.util.Log
import com.example.coroutinesdemo.bean.ProjectBean
import com.example.coroutinesdemo.bean.ProjectItem
import com.example.coroutinesdemo.retrofit.RetrofitClient
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class CoroutinesDemo {

    interface WanAndroidApi {


        @GET("project/tree/json")
        fun getProject(): Deferred<ProjectBean>

        @GET("project/list/{pageIndex}/json")
        fun getProjectItem(
            @Path("pageIndex") pageIndex: Int,
            @Query("cid") cid: Int
        ): Deferred<ProjectItem>

        @GET("project/tree/json")
        suspend fun getProject1(): ProjectBean

        @GET("project/list/{pageIndex}/json")
        suspend fun getProjectItem1(
            @Path("pageIndex") pageIndex: Int,
            @Query("cid") cid: Int
        ): ProjectItem


    }

    suspend fun getProjectItem() {
        //cid = 1,cid = 2
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        val api = retrofit.create(WanAndroidApi::class.java)

        GlobalScope.launch {

            val projectBean = async { api.getProject1()
            }.await()
            val projectItem1 = async {
                api.getProjectItem1(1, projectBean?.data?.get(0)!!.id)
            }.await()

            val projectItem2 = async {
                api.getProjectItem1(1, projectBean?.data?.get(0)!!.id)
            }.await()

            val sum = projectItem1.data.toString() + projectItem2.data.toString()


        }


    }

    suspend fun getProject() {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(WanAndroidApi::class.java)
        val projectResult = api.getProject1()
        Log.i(TAG, "projectBean: ${projectResult}")
    }


}