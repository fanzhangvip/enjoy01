package com.zero.lib.kotlin.basic01

//逆变super
fun <T> copyIn(dest: Array<in T>, src: Array<T>) {//消费者
//PESC
    // PECS Producer extends Consumer Super
//    取                存
//    ？extends         ？ super
//    out 取出来        in  存进去
//            协变              逆变
    {
        println("lambda")
    }
    src.forEachIndexed { index, value -> dest[index] = src[index] }


}

//协变
fun <T> copyOut(dest: Array<T>, src: Array<out T>) {//生产者
    src.forEachIndexed { index, value -> dest[index] = src[index] }
}

interface Source<out T>{//增强 声明处形变
    fun getT():T
}

fun test(src:Source<String>){
    val dest:Source<Any?> = src
}

fun main() {
    //int[] = new int{1,2,3}
    //Array
    var list = listOf<Int>(1, 2, 3)
    var array = arrayOf(1, 2, 3)


    var destDouble = arrayOf<Double>()
    var srcDouble = arrayOf<Double>(1.2, 3.2, 24.2)

    copyIn(destDouble, srcDouble)

    var destInt = arrayOf<Int>()
    var src = arrayOf(1, 2, 5)

    var dest = arrayOf<Number>(3)
    copyIn(dest, src)
    copyOut(dest, src)

    //Array<Number> - >  Array<Double> 的子类


}