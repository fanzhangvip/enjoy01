//@file:JvmMultifileClass("Basic01")
package com.zero.lib.kotlin.basic01

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import kotlin.properties.Delegates

open class A<T>(t: T){
    val aa = A("aaaa")
}

class B: A<String>("abc"){

}
interface OnInterceptListener{
    fun onTopChild(b:Boolean):Boolean
}
class View : OnInterceptListener{
    var parent:View = View()
    override fun onTopChild(b: Boolean): Boolean {
        TODO("Not yet implemented")
    }

}
class Test11{

    val parent:View = View()
    var parentView: WeakReference<View> = WeakReference(parent)

    private fun canScrollVertically(i:Int):Boolean{
        return i == 2
    }
    private  fun test(){
       induceParentOfChildTopStatus()
    }

    private fun induceParentOfChildTopStatus(){
        /** true child在顶部*/
        (parentView?.get() ?: {
            var pv = parent
            while (pv != null) {
                if (pv is OnInterceptListener) {
                    parentView = WeakReference(pv)
                    break
                }
                pv = pv.parent
            }
            pv as? OnInterceptListener
        }.invoke())?.onTopChild(!canScrollVertically(-1))
    }
}



data class Basic01List(val i: Int, val str: String)
data class Basic01(val a: Int, val b: Int, val list: Array<Basic01List>)

class Test constructor(str2:String = "str2") {
    var mI : Int = 0

    lateinit var str: String
//        get() {field}
//    set //不能自定义set get

    var str2: String = str2
        set(value) {
            field = value
        }
        get() = field

//    lateinit var str1 : String?//必须是非空类型
//    lateinit var i: Int //不能是基本类型对应的类型

    init {
        this.str2 = str2
    }

    constructor(i:Int,str2:String):this(str2){
        mI = i
    }

}

class ConstructorOverload {
    var name : String?
    var count: Int

    init {
        println("初始化")
    }

    constructor() {
        name = null
        count = 0
    }

    constructor(name : String, count : Int):this(){
        this.name = name
        this.count = count
    }
}
enum class Color{
    RED,BLUE,GREEN
}
open class Base{
    var name: String
    init {
        println("Base2 init...")
        this.name = "init"
    }
    constructor(){
        println("Base无参构造器")
    }

    constructor(name: String){
        println("Base有参构造: $name")
        this.name = name
    }
    open fun display(){
        println("display: $name")
    }
}

var nstr: String? = null

class V< T :P<*>> {

    var mP :T ?=null
}

class P<T :V<*> > {

    var mV :T?=null
}

fun main() {

    if(nstr!=null){
//        println(nstr.length)
    }

    println("hello world")

    var str3: String? = ""
    str3 = "abc"
    //替换java 三目运算符
    val str4 = if (str3 == null) {
        println("str3 == null")
        "null"
    } else {//作为表达式 else不能省略
        println("str3不为空")
        str3
    }
    println(str4)

    val basic01List = Basic01List(1, "enjoy")
    val basic = Basic01(2, 3, arrayOf(basic01List))
    println(basic)


//    ln@ val ln = readLine()
//    outer@ when {
//        ln!!.matches(Regex("\\d+")) -> println("数字")
//        ln.matches(Regex("[a-zA-Z]+")) -> println("字母")
//        ln.matches(Regex("[a-zA-Z0-9]+")) -> {
//            println("数字和字母")
//            return@outer
////                break@let//break,coutinue只能在循环里面使用
////                continue@let
//        }
//        else -> println("包含特殊字符")
//
//    }

    fun outer() {
        var i: Int = run {
            return@run 1
        }
        i = run {
            return@outer
        }
    }
    outer()


    val intArr = intArrayOf(1, 2, 35, 67, 0)
    for ((index, value) in intArr.withIndex()) {
        println("索引为${index}的元素是:$value")
    }

    GlobalScope.launch {
        println("hello world....")
        delay(1000)
    }

}

