package com.zero.lib.test

interface List<T>{
    var list: Array<in T>
    var length:Int

//    fun initList(init: (Int) -> T){
//        length = 0
//        list = Array<T>(length,init)
//    }
}