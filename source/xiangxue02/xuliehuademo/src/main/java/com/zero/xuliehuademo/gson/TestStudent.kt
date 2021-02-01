package com.zero.xuliehuademo.gson

abstract class BaseResult {

    var code: Int = 200
    var data: ByteArray? = null
        set(value) {

            field = value
            doSomething()

        }

    abstract fun doSomething()
}

class Response< T : BaseResult> {

    var code: Int = 400


    /**
     * 这个是实际数据，具体内容需要根据data解析
     * 也就是doSomething() ，这子类进行不同操作
     */
    var  result: T? = null

    var dataAll: ByteArray? = null
        set(value) {
            field = value

            //result=T  请问老师个各位同学 这里kotlin怎么用反射创建泛型实例
            result?.data = value?.copyOfRange(0, 5)

        }
}