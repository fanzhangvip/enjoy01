package com.zero.lib.kotlin.basic01

open class Item{
    open var price = 10.0
    open val name = "Item"
    open val validDays = 0
}

class Book: Item {

    override var price = 0.0
    override val name = ""
    override val validDays = 23

    constructor(){
        price = 2.1
    }
}

class Tiger{
    fun foo(){
        println("Tiger类的foo()方法")
    }
}

class Bear{
    fun foo(){
        println("Bear类的foo()方法")
    }

    fun Tiger.test(){
        foo()
        this@Bear.foo()
    }

    fun info(tiger: Tiger){
        tiger.test()
    }

}

fun main() {
    val b = Bear()
    b.info(Tiger())
    val v =fun Tiger.(){
        println("匿名扩展方法")
    }
    val t = Tiger()
    t.v()
}