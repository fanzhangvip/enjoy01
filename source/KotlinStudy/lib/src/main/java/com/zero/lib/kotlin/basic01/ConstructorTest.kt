package com.zero.lib.kotlin.basic01

import kotlin.reflect.KProperty0

class User(id: Int) {
    //字段 属性
    var id: Int
        // 是一个属性  + get + set
        get() = 0
        set(value) {
            id = value
        }
    var name: String

    init {
        this.id = id
        println("1")
    }

    init {
        this.name = ""
        println("2")
    }


    constructor(id: Int, name: String) : this(id) {//次构造函数
        this.id = id
        this.name = name
    }
}

class BackingProperty(name: String) {
    private var _name: String = name

    var age: Int = 0
        set(value) {
            if (value > 0)
                age = value
        }
    var name
        //不保存状态
        get() = _name
        set(value) {
            _name = value
        }
}

open class User1 {
    lateinit var name: String
    var age: Int? = 0//八种基本
    lateinit var sex: String

    fun test(){

        if(::name.isInitialized){
            println("name: $name")
        }
    }

    fun isInit():Boolean{
        return this::name.isInitialized
    }
}


fun main() {


    var p = BackingProperty("Zero")
    println(p.name)
    p.name = "Lance"
    println(p.name)

    var user = User1()
    user.age = 33
    user.sex = "男"
//    user.name="Zero"
    user.test()

    val property =user::name
    println(user::name.runCatching { println(user.name) })

    //作业

}