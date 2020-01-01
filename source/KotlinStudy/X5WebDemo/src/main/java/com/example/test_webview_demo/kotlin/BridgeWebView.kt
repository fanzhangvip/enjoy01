package com.aurora.common.widget.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.JavascriptInterface
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.CookieSyncManager
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * @author: cherish
 * @description: 网页
 * @date: 2019/9/16 10:39
 * @version: 2.0
 */
open class BridgeWebView : WebView {

    private var onWebResultCallBack: ((currentImage: String, images: Array<String>) -> Unit)? = null

    constructor(p0: Context?) : this(p0, null)
    constructor(p0: Context?, p1: AttributeSet?) : super(p0, p1) {
        init()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
       isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        settings.javaScriptEnabled = true
        setWebContentsDebuggingEnabled(true)
        settings.apply {
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            loadWithOverviewMode=true
            useWideViewPort = true
            textSize = WebSettings.TextSize.NORMAL
            loadsImagesAutomatically = true
            userAgentString = settings.userAgentString
            domStorageEnabled = true
            allowContentAccess = true
            setAppCacheEnabled(true)
            settings.textZoom = 100
        }
        addJavascriptInterface(this, "jsCallJavaObj")
        webViewClient = webClient
    }

    private val webClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
            p0?.loadUrl(url)
            return true
        }

        override fun onPageFinished(webView: WebView?, p1: String?) {
            super.onPageFinished(webView, p1)
            webView?.apply {
                loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName(\"img\"); " +
                        " var array=new Array(); " +
                        " for(var j=0;j<objs.length;j++){ array[j]=objs[j].src; }" +
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        + "    objs[i].onclick=function()  " +
                        "    {  "
                        + "        window.jsCallJavaObj.jsInvokeJava(this.src,array);  " +
                        "    }  " +
                        "}" +
                        "})()")
            }
            // 动态注入js 给webview 中图片添加点击事件 并获取src值 图片地址

        }

        override fun onReceivedError(p0: WebView?, p1: WebResourceRequest?, p2: WebResourceError?) {
            super.onReceivedError(p0, p1, p2)
            p1?.let {
                val url="file:///android_res/drawable/ic_default_aurora.png"
                val imageUri =  "<img alt=\"ic_default_aurora.jpg\" src=\"$url\"/>"

                val html = "<html>" +
                        "<style>img{width:98%;height:auto;margin:5px 5px;}</style>" +
                        "<body style=\"word-wrap:break-word; font-family:Arial\">" +
                        imageUri +
                        "</body>" +
                        "</html>"

               p0?.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
            }
        }

    }


    @JavascriptInterface
    fun jsInvokeJava(currentImage: String, images: Array<String>) {
        onWebResultCallBack?.let {
            it.invoke(currentImage, images)
        }

    }

    fun onWebResultCallBack(callBack: (currentImage: String, images: Array<String>) -> Unit) {
        onWebResultCallBack = callBack
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }

        }
        return super.onTouchEvent(event)
    }
}