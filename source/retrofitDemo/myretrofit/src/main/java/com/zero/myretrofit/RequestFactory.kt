package com.zero.myretrofit

import okhttp3.HttpUrl
import okhttp3.Request
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.IOException
import java.lang.reflect.Method


class RequestFactory private constructor(builder: Builder) {

    private lateinit var method: Method
    private lateinit var baseUrl: HttpUrl
    lateinit var httpMethod: String

    lateinit var relativeUrl: String

    init {
        method = builder.method!!
        baseUrl = builder.retrofit.baseUrl
        httpMethod = builder.httpMethod!!
        relativeUrl = builder.relativeUrl!!
    }

    companion object {
        @JvmStatic
        fun parseAnnotations(
            retrofit: MyRetrofit,
            method: Method?
        ): RequestFactory? {
            return Builder(retrofit, method!!).build()
        }
    }

    @Throws(IOException::class)
    fun create(args: Array<Any?>?): Request? {
        val requestBuilder = RequestBuilder(
            httpMethod, baseUrl, relativeUrl!!,
            hasBody = false, isFormEncoded = false, isMultipart = false
        )
        return requestBuilder.get()
            .build()
    }


    class Builder(val retrofit: MyRetrofit, val method: Method) {
        var httpMethod: String? = null
        var relativeUrl: String? = null
        var hasBody: Boolean? = null
        var methodAnnotations = arrayOf<Annotation>()
        var parameterAnnotationsArray = arrayOf<Array<Annotation>>()

        init {
            methodAnnotations = method.annotations
            parameterAnnotationsArray = method.parameterAnnotations
        }

        fun build(): RequestFactory {

            methodAnnotations.forEach { parseMethodAnnotation(it) }

            return RequestFactory(Builder@ this)!!
        }

        private fun parseMethodAnnotation(annotation: Annotation) {
            if (annotation is GET) {
                parseHttpMethodAndPath("GET", annotation.value, false)
            } else if (annotation is POST) {
                parseHttpMethodAndPath("POST", annotation.value, true)
            }
        }

        private fun parseHttpMethodAndPath(
            httpMethod: String,
            value: String,
            hasBody: Boolean
        ) {
            this.httpMethod = httpMethod
            this.relativeUrl = value
            this.hasBody = hasBody
        }
    }

}