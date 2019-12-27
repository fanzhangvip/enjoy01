package com.zero.wanandroiddemo.bean
//    "errorCode": 0,
//    "errorMsg": ""
data class ResponseWrapper<out T>(val errorCode:Int,val data:T,val errorMsg:String)