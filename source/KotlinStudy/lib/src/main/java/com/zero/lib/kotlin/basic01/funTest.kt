package com.zero.lib.kotlin.basic01

fun func1(name: String) {//void
    println(name)
}

fun sum(a: Int,b: Int) = a + b

fun main() {//函数 不是在类里面的   在类里面  方法

    val str =func1("zero")
    println(str)

    println("sum: ${sum(1,2)}")
    "sss".apply {  }
}