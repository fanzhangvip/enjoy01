package com.zero.xuliehuademo.serializable

import android.telephony.IccOpenLogicalChannelResponse
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.sin
//var变量 可读写
//val变量  只读
//const 常量  类的常量池

data class Course(var name: String, var score: Float) : Serializable {
    companion object {//伴生对象  == java static
        private const val serialVersionUID: Long = 1L
    }

//    @Throws(ClassNotFoundException::class, IOException::class)
//    private fun readObject(inputStream: ObjectInputStream) {
//        println("readObject")
//        name = inputStream.readObject() as String
//        score = inputStream.readFloat()
//    }
//
//    @Throws(IOException::class)
//    private fun writeObject(outputStream: ObjectOutputStream) {
//        println("writeObject")
//        outputStream.writeObject(name + "tag")
//        outputStream.writeFloat(score )
//    }
//
//    private fun readResolve():Any{
//        println("readResolve")
//        return Course("${name}_readResolve",67f)
//    }
//
//    fun writeReplace(): Any {
//        println("writeReplace")
//        return Course("${name}_replace", 56f)
//    }
}

data class Student(
    var name: String,
    var sex: String,
    var age: Int,
    var courses: ArrayList<Course>
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 2L
    }
}

//序列化基本演示
private fun test1() {
    var course = Course("英语", 12f)
    val out = ByteArrayOutputStream()
    val oos = ObjectOutputStream(out)
    oos.writeObject(course)
    //写入第二次
    oos.writeObject(course)
    val bs = out.toByteArray()
    //========================================
    oos.close()
    val ois = ObjectInputStream(ByteArrayInputStream(bs))
    course = ois.readObject() as Course
    val course1 = ois.readObject() as Course
    println("反序列化course: $course, flag = ${course == course1}")
}


class Course3 : Externalizable {

    lateinit var name: String
    var score: Float = 0f

    //必须需要一个无参的构造函数
    constructor()

    constructor(name: String, score: Float) {
        this.name = name
        this.score = score
    }

    companion object {
        private const val serialVersionUID: Long = 2L
    }

    override fun readExternal(oin: java.io.ObjectInput) {
        println("readExternal")
        name = oin.readObject() as String
        score = oin.readFloat()

    }

    override fun writeExternal(out: ObjectOutput) {
        println("writeExternal")
        out.writeObject(name)
        out.writeFloat(score + 12f)
    }

    override fun toString(): String {
        return "Course3(name='$name', score=$score)"
    }


}

//序列化到本地演示
fun test2() {
    val path = "${System.getProperty("user.dir")}/a.out"
    println("path: $path")
    var course = Course("英语", 12f)
    SerializeableUtils.saveObject(course, path)


    course = SerializeableUtils.readObject(path)
    println("反序列化：$course")
}

//序列化到本地演示1
fun test3() {
    val path = "${System.getProperty("user.dir")}/a.out"
    println("path: $path")
//    var course = Course3("英语", 12f)
//    SerializeableUtils.saveObject(course, path)


   var course = SerializeableUtils.readObject<Course3>(path)
    println("反序列化：$course")
}

//多引用写入问题
fun test4() {
    var course = Course("英语", 12f)
    val out = ByteArrayOutputStream()
    val oos = ObjectOutputStream(out)
    oos.writeObject(course)

    course.score = 78f
    fun error() {
        oos.writeObject(course)//这样子写有问题
        //结果 反序列化course1: Course(name=英语, score=12.0)
        //反序列化course2: Course(name=英语, score=12.0)
    }

    fun resolver1() {
        oos.writeUnshared(course)//方案一：Ok
    }

    fun resolver2() {
        //或者 方案二
        oos.reset()
        oos.writeObject(course)
    }

//    error()
    resolver1()

    val bs = out.toByteArray()
    oos.close()
    val ois = ObjectInputStream(ByteArrayInputStream(bs))
    course = ois.readObject() as Course
    println("反序列化course1: $course")

    course = ois.readObject() as Course
    println("反序列化course2: $course")
}

enum class Num {
    ONE, TWO, THREE;

    fun printValues() {
        println("ONE: ${ONE.ordinal}, TWO: ${TWO.ordinal}, THREE: ${THREE.ordinal}")
    }
}

//枚举序列化
fun test5() {
    val bs = SerializeableUtils.serialize(Num.THREE)
    Num.ONE.printValues()
    println("反序列化后")
    val s1 = SerializeableUtils.deserialize<Num>(bs)
    s1.printValues()
}

class Single : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
        private var flag = false

        var single: Single? = null

        @Synchronized fun getInstance(): Single {
            if (single == null) {
                single = Single()
            }
            return single!!
        }
    }

    init {
        if (single == null) {
            synchronized(Single::class) {
                if (!flag) {
                    flag = true
                } else {
                    throw RuntimeException("单例模式被侵犯")
                }
            }
        }
    }

    //解决单例序列化的问题
    private fun readResolve(): Any{
        return single!!
    }
}

//单例问题测试
fun test6(){
    fun copy(instance: Single): Single{
        val bs =SerializeableUtils.serialize(instance)
        return SerializeableUtils.deserialize(bs)
    }
    val instance = Single.getInstance()
    println("hashCode=${instance.hashCode()},copy=${copy(instance).hashCode()}")
}

open class BaseCourse: Serializable{
    private lateinit var tag: String

    //constructor()

    constructor(tag: String){
        this.tag = tag
    }

    override fun toString(): String {
        return "BaseCourse(tag='$tag')"
    }

}

data class Course4(var name: String,var score: Float,@Transient var date: Date = Date() ): BaseCourse(name.hashCode().toString()),Serializable{
    companion object{
        private const val serialVersionUID: Long = 1L
        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-DD HH:MM:SS")
    }

    override fun toString(): String {
        return "Course4(name='$name', score=$score, date=${simpleDateFormat.format(date?:Date())})"
    }
}

fun test7(){
    val path = "${System.getProperty("user.dir")}/b.out"
    var course = Course4("数学", 52f)
    SerializeableUtils.saveObject(course, path)
    println("序列化之前: $course")

    course = SerializeableUtils.readObject(path)
    //Transient字段不会被反序列化
    println("反序列化：$course")//"main" java.lang.NullPointerException
}


@Throws(Exception::class)
fun main() {
    test4()
}