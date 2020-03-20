package com.example.retrofitmvp.beans

data class ResponseWrapper<out T>(val errorCode: Int,val errorMsg: String, val data: T)

data class LoginBean(
    var admin: Boolean, var chapterTops: List<*>, var collectIds: List<*>
    , var email: String?, var icon: String?, var id: String?, var nickname: String?
    , var password: String?, var publicName: String?, var token: String?
    , var type: Int, var username: String?
)

data class RegisterBean(
    var admin: Boolean, var chapterTops: List<*>, var collectIds: List<*>
    , var email: String?, var icon: String?, var id: String?, var nickname: String?
    , var password: String?, var publicName: String?, var token: String?
    , var type: Int, var username: String?
)


//{"data":{"admin":false,"chapterTops":[],"collectIds":[],
// "email":"","icon":"","id":39344,"nickname":"Lance_vip",
// "password":"","publicName":"Lance_vip","token":"","type":0,"username":"Lance_vip"}
 //   ,"errorCode":0,"errorMsg":""}

