package com.zero.myretrofit

import okhttp3.*


internal class RequestBuilder(
    private val method: String,
    private val baseUrl: HttpUrl,
    private var relativeUrl: String?,
    private val hasBody: Boolean,
    private val isFormEncoded: Boolean,
    private val isMultipart: Boolean
) {
    private var urlBuilder: HttpUrl.Builder? = null
    private val requestBuilder: Request.Builder = Request.Builder()


    private var multipartBuilder: MultipartBody.Builder? = null

    private var formBuilder: FormBody.Builder? = null

    private var body: RequestBody? = null
    fun setRelativeUrl(relativeUrl: Any) {
        this.relativeUrl = relativeUrl.toString()
    }


    fun addQueryParam(
        name: String?,
        value: String?,
        encoded: Boolean
    ) {
        if (relativeUrl != null) {
            urlBuilder = baseUrl.newBuilder(relativeUrl)
            relativeUrl = null
        }
        if (encoded) {
            urlBuilder!!.addEncodedQueryParameter(name, value)
        } else {
            urlBuilder!!.addQueryParameter(name, value)
        }
    }


    fun setBody(body: RequestBody?) {
        this.body = body
    }

    fun get(): Request.Builder {
        val url: HttpUrl?
        val urlBuilder = urlBuilder
        url = if (urlBuilder != null) {
            urlBuilder.build()
        } else {
            baseUrl.resolve(relativeUrl)
        }
        var body = body
        if (body == null) {

        }
        return requestBuilder
            .url(url)
            .method(method, body)
    }

    fun addFormField(name: String?, fieldValue: String, encoded: Boolean) {
        TODO("Not yet implemented")
    }

    init {
        if (isFormEncoded) {
            formBuilder = FormBody.Builder()
        }
    }
}