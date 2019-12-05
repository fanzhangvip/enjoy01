import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

println("Hello world!")
println("享学课堂")
var b:Int = 0
var name  = "享学课堂"
//name = 12
val str12 = "欢迎大家"
//使用val声明的变量是不可变变量，不能重新赋值
//str = "a"
var a1 = 34
//超出Int的范围
//var bigValue: Int  =2999999999
var bigValue2 : Long = 299999999999
println(bigValue2)

val a: Int = 10000
println(a === a) // 输出“true”
val boxedA: Int? = a
val anotherBoxedA: Int? = a
println(boxedA === anotherBoxedA) // ！！！输出“false”！！！
println(boxedA == anotherBoxedA) // 输出“true”

// 假想的代码，实际上并不能编译：
val a2: Int? = 1 // 一个装箱的 Int (java.lang.Integer)
//val b2: Long? = a2 // 隐式转换产生一个装箱的 Long (java.lang.Long)
//print(b2 == a2) // 惊！这将输出“false”鉴于 Long 的 equals() 会检测另一个是否也为 Long
//val b3 : Long? = (Int?)a2
val a3 : Int = 2
val b3 : Long = a3.toLong()

//字符串类型
val str : String = "享学课堂"
println(str)

//原始字符串
val str1 = """
    |秋风吹，北风凉
    |谁家娇妻守空房
    |你有困难我帮忙
    |我住隔壁我姓王
""".trimMargin()
println(str1)

//字符串模板
val date = Date()
val sdf  = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
val dateStr ="今天是 ${sdf.format(date)}, length:${sdf.toString().length}"
println("dateStr: $dateStr")

val hashMap = HashMap<String,Int>()

data class Box(val a : Int,val b : Int)
val box1 = Box(1,2)
val box2 = Box(3,4)
operator fun Box.plus(other : Box) :Box{
    return Box(a + other.a,b + other.b)
}
val box3 = box1 + box2
println("运算符重载box3: $box3")

val bool = true
var str3 : String?  = ""
str3 = "abc"

val str4 = if(str3 ==null) {
    println("str3 == null")
    "null"
}else {
    println("str3不为空")
    str3
}
println(str4)

for(i in 0 .. 6){
    print(i)
}
println()




