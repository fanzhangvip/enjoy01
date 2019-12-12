package com.zero.lib.kotlin.basic01

interface Outputable{
    fun output(msg: String)
    var type: String
}

class DefaultOutput : Outputable{
    override fun output(msg: String) {
        for(i in 1..6){
            println("<h$i>$msg</h$i>")
        }
    }

    override var type: String = "输出设备"
}