package com.zero.lib.kotlin.basic01

class ValOrValTest{
    val a:Int = 0//尽量用val
    var b:Int = 1
}

fun strDisplay(str: String?){//可空类型
//    if(str != null)
//    println("str的长度：${str!!.length}")//断言运算符
    println("str的长度：${str?.length}")
}

fun main() {
    val a: Int = 0
//    a = 1 //a 只读的 final

    var b: Int = 1//可读可写
    b = 12

    var a1 = 12//自动推断类型,静态语言 和动态语言
//    a1 = 2.3f
    // ?  NPE
    //

    var a2: Int?
    var a3: Int

    //java 变量 有默认值的 1，2 局部变量是没有默认值的
    // kotlin 都没有默认值

    //kotlin的字符串
    // "asdf" + 0 + "" ...
    val price = 23
    val str1 = "dfaksdljfasdljfweklrj"
    var str = "享学课堂vip的价格: ${price},str1的长度：${str1.length}"
    val str2 = """
        fasdfkljwer
        |fasdlfkjsdklf
         |dflkjsdfklsdj
    """.trimIndent()
    println(str2)

    var str3 : String?
    str3 ="dsfasdfl"
    strDisplay(str3)
//    str3.length



}