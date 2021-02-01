package com.zero.leakcanarydemo

import java.lang.ref.Reference
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

fun main() {
    println("Hello world!!")
    weakReferenceTest()
}

fun weakReferenceTest() {
    val referenceQueue = ReferenceQueue<Any?>()
    var o: Any? = Object()
    val weakReference = WeakReference<Any?>(o, referenceQueue)
    println("weakReference = $weakReference")
    o = null//o 弱可达 被释放

    Runtime.getRuntime().gc()
    try {
        Thread.sleep(100)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    var ref: WeakReference<Any?>?
    do {
        ref = referenceQueue.poll() as? WeakReference<Any?>
        val obj1 =ref?.get()//ref 弱引用
        println("obj1 = $obj1")
        println("=============\n $ref in queue")
    } while (ref != null)

}