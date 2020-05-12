package com.zero.leakcanarydemo

import java.lang.Exception
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.*

fun main() {

    class MyKeyWeakReference(
        referent: Any, val key: String,
        val name: String,
        referenceQueue: ReferenceQueue<Any>
    ) : WeakReference<Any>(
        referent, referenceQueue
    ) {
        val className: String = referent.javaClass.name

        override fun toString(): String {
            return "{key=$key,className=$className}"
        }
    }

    //需要观察的对象放这里
    val watchedReferences = mutableMapOf<String, MyKeyWeakReference>()
    //如果最后retainedReferences存在引用，则说明泄漏了
    val retainedReferences = mutableMapOf<String, MyKeyWeakReference>()
    //当与之关联的弱引用中的实例被回收，则会加入到queue
    val queue = ReferenceQueue<Any>()

    fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: Exception) {
        }
    }

    fun gc() {
        println("执行gc...")
        Runtime.getRuntime()//主要这里不是使用System.gc,因为它仅仅是通知系统在合适的时间进行一次垃圾回收操作
            .gc()//实际上并不保证一定执行
        sleep(100)
        System.runFinalization()
    }

    fun removeWeaklyReachableReferences() {
        println("13. removeWeaklyReachableReferences()")
        var ref: MyKeyWeakReference?
        do {
            ref = queue.poll() as MyKeyWeakReference? // 队列 queue 中的对象都是会被 GC 的
            println("ref=$ref,如果ref为null,说明对象还有强引用")
            if (ref != null) {//说明被释放了
                println("ref=$ref, 对象被释放了,key=${ref.key}")
                val removedRef = watchedReferences.remove(ref.key)
                println("removedRef=$removedRef, 如果removedRef为null,说明已经不在watchedReferences了,key=${ref.key}")
                if (removedRef == null) {
                    //不在watchedReferences则说明在retainedReferences
                    retainedReferences.remove(ref.key)
                }
            }
        } while (ref != null)
    }

    @Synchronized
    fun moveToRetained(key: String) {
        println("2. moveToRetained,key=$key")
        removeWeaklyReachableReferences()
        val retainedRef = watchedReferences.remove(key)
        println("retainedRef =$retainedRef 说明没有被释放")
        if (retainedRef != null) {//添加到retainedReferences
            retainedReferences[key] = retainedRef
        }
    }


    fun watch(
        watchedReference: Any,
        referenceName: String = ""
    ) {
        println("1. watch()")
        removeWeaklyReachableReferences()
        val key = UUID.randomUUID()
            .toString()
        println("key=$key")
        val reference = MyKeyWeakReference(watchedReference, key, referenceName, queue)
        println("reference=$reference")
        //加入观察列表
        watchedReferences[key] = reference
        // 过段时间去观察看是否释放了
        Thread {
            sleep(5000)
            moveToRetained(key)
        }.start()
    }

    var o: Any? = Object()
    println("创建一个对象o=$o")
    watch(o!!, "")
    sleep(2000)
//    o = null
    if (o == null) {
        println("o=$o  释放了")
    }
    gc()
    sleep(5000)
    println("watchedReferences=$watchedReferences")
    println("retainedReferences=$retainedReferences")
    println("执行完毕")

}

