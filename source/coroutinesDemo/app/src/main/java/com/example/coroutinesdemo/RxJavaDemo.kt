package com.example.coroutinesdemo

import android.util.Log
import com.example.coroutinesdemo.bean.ProjectBean
import com.example.coroutinesdemo.bean.ProjectItem
import com.example.coroutinesdemo.retrofit.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.function.BiFunction

const val TAG = "Zero"

class RxJavaDemo {

    interface WanAndroidApi {
        @GET("project/tree/json")
        fun getProject(): Observable<ProjectBean>

        @GET("project/list/{pageIndex}/json")
        fun getProjectItem(
            @Path("pageIndex") pageIndex: Int,
            @Query("cid") cid: Int
        ): Observable<ProjectItem>

    }

    fun getProjectTest() {
        RetrofitClient.instance.getService(WanAndroidApi::class.java).getProject()
            .subscribe { projectbean ->
                Log.i(TAG, projectbean.toString())
            }
    }


    fun getProjectItems() {
        RetrofitClient.instance.getService(WanAndroidApi::class.java).getProject()
            .flatMap { projectbean ->
                Observable.fromIterable(projectbean.data)
            }
            .flatMap { dataBean ->
                RetrofitClient.instance.getService(WanAndroidApi::class.java)
                    .getProjectItem(1, dataBean.id)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { projectItem ->
                Log.i(TAG, projectItem.toString())
            }
    }
}

