package com.zero.lib.kotlin.basic01.vipdemo

//inline fun foo(block:() -> Unit,noinline block1:()->Unit){
//    println("before block")
//    block()
//    block1()
//    println("end block")
//}
//
// inline fun foo1(block:() -> Unit){
//    println("before block")
//    block()
//    println("end block")
//}
//
////非局部返回
//fun localReturn(){
//    return//return只是局部返回
//}
//
//inline fun foo2(crossinline block:() -> Unit){
//    println("before block")
//    block()
//    println("end block")
//}
//
//inline fun <reified T> getType(){
//    println(T::class)
//}

//扩展函数
fun MutableList<Int>.swap(from:Int, to:Int){
    val tmp = this[from]
    this[from] = this[to]
    this[to] = tmp
}

val MutableList<Int>.isOushu: Boolean
    get() = this.sum() % 2 == 0

interface A{
    fun display(msg:String)
}

class AImpl : A{
    override fun display(msg: String) {
        println("***  $msg  **")
    }
}

class Diplayer():A  by AImpl(){

}

fun makeList(ele:String):() -> List<String>{
    val list = mutableListOf<String>()
     return fun():List<String>{
        list.add(ele)
        return list
    }
}

fun main() {
    //kotlin -> java

    val dd1 = makeList("kotlin")
    println(dd1())
    println(dd1())

    val dd11 = makeList("java")
    println(dd11())
    println(dd11())


    val dis = Diplayer()
    dis.display("kotlin")

    var list = mutableListOf(1,2,3,4)
    println(list)
    list.swap(1,2)
    println(list)

    println(list.isOushu)

//    getType<Int>()
    //
    // noinline,  crossinline
//    foo2{
//        return@foo2
//    }

//    println("before return")
//    localReturn()
//    println("atfter return")

//    foo({
//        println("foo  in main1")
//    },{
//        println("foo  in main2")
//    })

}