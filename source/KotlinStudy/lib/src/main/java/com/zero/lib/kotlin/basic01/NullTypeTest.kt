package com.zero.lib.kotlin.basic01

val nullStr: String? = null

class User2{
    lateinit var name:String//name交给我自己管理，jvm
    var age:Int = 0

    fun isNameInit(): Boolean{
        return ::name.isInitialized
    }
}

data class User4(var name:String,var sex:String,var age:Int)

fun main() {

    //lateinit修饰的变量，到底是不是初始化啦
    val user4 = User4("Zero","男",18)
    var name:String= ""
    var sex:String = ""
    var age:Int = 0
//    name = user4.name
//    sex = user4.sex
//    age = user4.age

    //
    var (_,_,_) = user4





    val user = User2()

    if(/**/user.isNameInit()/*user::name.isInitialized*/){//是不是初始化这个状态，name自己的
         println(user.name)
    }



    var a: String? = null
    a = ""
//    println(a?.length)
    println(a!!.length)//非空断言
    if(a !=null){
        println(a.length)
    }
    a = ""

    if(nullStr!=null){
        println(nullStr.length)
    }
    var nullString1: String? =null
    if(nullString1!=null){
        println(nullString1.length)
    }

}