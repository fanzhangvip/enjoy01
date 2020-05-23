package com.zero.proxytest

import java.lang.reflect.Proxy


interface ICET4 {
    fun doCET4()
}

class Zero() : ICET4 {
    override fun doCET4() {
        println("完成CET4考生")
    }
}

//class Qiangshou(private val realSubject: ICET4) : ICET4 {
//
//    private fun before() {
//        println("乔装打扮成Zero")
//    }
//
//    override fun doCET4() {
//        before()
//        realSubject.doCET4()
//        after()
//    }
//
//    private fun after() {
//        println("善后，清除痕迹")
//    }
//}
//
//fun staticTest(){
//    val zero = Zero()
//    val qiangshou = Qiangshou(zero)
//
//    qiangshou.doCET4()
//}

fun proxyTest(){ //Android高级  只停留在用，一定要知道原理
    val zero = Zero()
    val proxy = Proxy.newProxyInstance(//jdk
        zero::class.java.classLoader,//类加载器
        arrayOf<Class<*>>(ICET4::class.java)//业务接口
    ) { _, method, _ ->  //InvokeHandler
        if(method.name.equals("doCET4")){
            println("乔装打扮成Zero")
            method.invoke(zero)
            println("善后，清除痕迹")
        }
    } as ICET4

    Utils.generyProxyFile("proxy$0",arrayOf<Class<*>>(ICET4::class.java))

    proxy.doCET4()
}

fun main() {
//    staticTest()
    proxyTest()
}
