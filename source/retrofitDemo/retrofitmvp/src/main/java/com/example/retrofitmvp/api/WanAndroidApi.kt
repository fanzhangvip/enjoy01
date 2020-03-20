package com.example.retrofitmvp.api

import com.example.retrofitmvp.beans.LoginBean
import com.example.retrofitmvp.beans.RegisterBean
import com.example.retrofitmvp.beans.ResponseWrapper
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WanAndroidApi {

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username")username: String,
              @Field("password")password: String):Observable<ResponseWrapper<LoginBean>>

    @FormUrlEncoded
    @POST("user/register")
    fun  register(@Field("username")username: String,
                  @Field("password")password: String,
                  @Field("repassword")repassword: String):Observable<ResponseWrapper<RegisterBean>>

}