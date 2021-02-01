package com.example.coroutinesdemo

suspend fun main(){


    val thread = Thread{
        fun run(){
            println("this is Thread ...")
        }
    }
    thread.start()

}