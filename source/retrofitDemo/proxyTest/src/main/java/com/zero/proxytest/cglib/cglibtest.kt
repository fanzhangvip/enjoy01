package com.zero.proxytest.cglib

import net.sf.cglib.proxy.Enhancer

fun main(){

    //CGLIB通过继承的方式进行代理，无论目标对象有没有实现接口都可以代理，但是无法处理final的情况
    val enhancer = Enhancer()
    enhancer.setSuperclass(MycglibTest::class.java)
    enhancer.setCallback(CglibProxy(object : MycglibTest() {
        override fun method2(count: Int): Int {
            println("count: $count")
            return count * 2
        }
    }))
    val targetObject2 = enhancer.create() as MycglibTest
    println("result: " + targetObject2.method2(2))
}