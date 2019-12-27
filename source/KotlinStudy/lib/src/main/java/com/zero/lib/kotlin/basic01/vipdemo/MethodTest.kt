package com.zero.lib.kotlin.basic01.vipdemo

fun add1(a: Int,b: Int):Int{
    return a + b
}

fun  add(a:Int,b:Int,sum:( a:Int,b:Int) -> Int):Int{
    return sum.invoke(a,b)
}

data class Data(var x:Int,var y:Int){

    operator fun plus(target: Data):Data{
        return Data(x + target.x,y + target.y)
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    fun equals(){

    }

}



operator fun Data.inc():Data{
    return Data(++x,++y)
}

fun test1(a: Int){

    if(a> 3){
        println("a > 3")
    }
//    else{
//        println("a <=3")
//    }

}

fun main() {
//    println(add1(1,2))
//
//    add(1,2){
//       a, b -> a+ b
//    }
//    var i:Int = 10
//    i++
//    ++i
//
//    val data = Data(2,3)
//    val data1 = Data(1,2)
//    println(data)
//    var data2 = data + data1
//    println(data2)
//    ++data2
//    println(data2)
//
//    val a = 3
//
//    var str:String? = null
////    str = "kotlin"
//
//    val str1 = if(str == null){ "null"} else{str}
//    println(str1)


//    for ( i in 2..6){
//        println(i)
//    }

//    for( i in 2 until  6){
//        println(i)
//    }

//    for(  i in 10 downTo  2 step 2){
//        println(i)
//    }

//    var array = arrayOf(2,3,5,6,7)
////    for((index,value) in array.withIndex()){
////        println("index:$index,value: $value")
////    }
//    println(array.all { it > 1 })
//    println(array.any { it > 6 })
//
//    var result= array.associate {  "A$it" to it *2}
//    println(result.toString())



//    val arrayList = arrayListOf("Kotlin")
//    val mutalbeList = mutableListOf("kotlin","java")

    val list1 = listOf(0,1,2,3,4)//惰性集合

    val list2 = list1.map {  it}
    println(list2)

//    val list21 = list1.filter {
//        println("filter: $it")
//        it > 2
//    }.map {
//        it * 2
//        println("map: $it")
//    }.toList()
//    println(list21)
//    println("=========================================")
//    val list2 = list1.asSequence().filter {
//        println("filter: $it")
//        it > 2
//    }.map {
//        it * 2
//        println("map: $it")
//    }
//    println(list2.toList())






}