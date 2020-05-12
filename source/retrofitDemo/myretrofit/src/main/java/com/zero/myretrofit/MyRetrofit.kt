package com.zero.myretrofit

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.net.URL
import java.util.Collections.unmodifiableList


const val TAG = "Zero"

class MyRetrofit private constructor(val callFactory: Call.Factory, val baseUrl: HttpUrl) {


    private val serviceMethodCache = mutableMapOf<Method, ServiceMethod>()

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { _, method, args ->
            val emptyArgs = arrayOfNulls<Any>(0)
            loadServiceMethod(method)?.invoke(args ?: emptyArgs)
        } as T
    }

    private fun loadServiceMethod(method: Method?): ServiceMethod? {
        var result: ServiceMethod? = serviceMethodCache[method]
        if (result != null) return result
        synchronized(serviceMethodCache) {
            result = serviceMethodCache[method]
            if (result == null) {
                result = ServiceMethod.parseAnnotations(this, method!!)
                serviceMethodCache[method] = result!!
            }
        }
        return result
    }

    class Builder {
        private var callFactory: Call.Factory? = null

        private var baseUrl: HttpUrl? = null

        fun baseUrl(baseUrl: URL): Builder {
            return baseUrl(HttpUrl.get(baseUrl.toString()))
        }

        fun baseUrl(baseUrl: String?): Builder {
            return baseUrl(HttpUrl.get(baseUrl))
        }


        fun baseUrl(baseUrl: HttpUrl): Builder {
            val pathSegments = baseUrl.pathSegments()
            require("" == pathSegments[pathSegments.size - 1]) { "baseUrl must end in /: $baseUrl" }
            this.baseUrl = baseUrl
            return this
        }

        fun build(): MyRetrofit? {
            checkNotNull(baseUrl) { "Base URL required." }
            var callFactory = callFactory
            if (callFactory == null) {
                callFactory = OkHttpClient()
            }
            return MyRetrofit(
                callFactory,
                baseUrl!!
            )
        }
    }


}