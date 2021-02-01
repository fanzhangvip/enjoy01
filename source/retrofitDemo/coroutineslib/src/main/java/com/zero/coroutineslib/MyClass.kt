package com.zero.coroutineslib

fun main() {


    val  list = arrayOf(1,2,3)
    list.fold(1){
        acc: Int, i: Int ->
        acc * i
    }
}