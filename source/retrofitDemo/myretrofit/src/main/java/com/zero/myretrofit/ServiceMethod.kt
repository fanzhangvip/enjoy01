package com.zero.myretrofit

import okhttp3.*
import retrofit2.http.GET
import retrofit2.http.POST
import java.lang.reflect.Method


class ServiceMethod private constructor(private val requestFactory: RequestFactory) {

    companion object {
        @JvmStatic
        fun parseAnnotations(
            retrofit: MyRetrofit?,
            method: Method
        ): ServiceMethod? {
            val requestFactory = RequestFactory.parseAnnotations(retrofit!!, method)
            return ServiceMethod(requestFactory!!)
        }

    }


    operator fun invoke(args: Array<Any?>?): Call {
        val client = OkHttpClient()
        val request = requestFactory.create(args)!!
        return client.newCall(request)
    }
}