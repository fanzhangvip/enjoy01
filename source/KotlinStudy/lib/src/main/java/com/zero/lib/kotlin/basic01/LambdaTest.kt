package com.zero.lib.kotlin.basic01

typealias FunType = () ->Unit
fun main() {

    //lambda
    var temp: (Int,Int) -> Int
//    var temp1: Any

    temp = {x,y -> x * y}
    val t = temp.invoke(2,2)

    temp = {x,y -> x + y}
    println("${temp.invoke(3,4)}")

    println("t: $t")

    var sum = {x:Int,y:Int -> x +y}
    com.zero.lib.kotlin.basic01.sum(1,2)
    var tmp1:(Int) -> Unit

    tmp1 ={ println("$it")
        it+2
    }//默认一个参数

//    fun test(a:String ,display: (String)->Unit){//lambda 也是函数
//        display(a)
//    }
//
//    test("zero"){
//
//    }
//
//
//    fun test1(func:FunType):Unit{//函数是一等公民
//        fun test2(){
//            func()
//        }
//        return test2()
//    }
//
//    fun hello(){
//        println("hello world")
//    }
//
//    test1(::hello)
//
//    //常用的高级函数
//    run{
//        //执行一块独立代码块
//    }
//
//    val str = "kolit"
//    str.length
//    str.run {
//       length
//    }
//
//    with(str){
//
//    }
//
//    str.apply {
//
//        }
//        .apply {
//
//        }
//        .apply {
//            length
//        }
//
//
//    str.also {
//
//        it.length
//    }
//
//    str.let {
//        it.reversed()
//    }
//
//    val st1 = str.takeIf {
//        it.startsWith("sko")
//    }
//
//    lazy {  }


}