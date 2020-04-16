package com.zero.coroutinesdemo

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class Coroutines1 {
    private suspend fun doSomethingUsefulOne(): Int {
        delay(1000L)
        return 13
    }

    private suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L)
        return 29
    }

    suspend fun testTime1() {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("the answer is ${one + two}")
        }
        println("completed in $time ms")
    }

    suspend fun testTime2() = withContext(Dispatchers.IO) {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("the answer is ${one.await() + two.await()}")
        }
        println("completed in $time ms")
    }

    suspend fun testTime3() = withContext(Dispatchers.IO) {
        val time = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
            val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
            one.start()// start the first one
            two.start()// start the second one
            println("the answer is ${one.await() + two.await()}")
        }
        println("completed in $time ms")
    }

    private fun somethingUsefulOneAsync() = GlobalScope.async {
        doSomethingUsefulOne()
    }

    private fun somethingUsefulTwoAsync() = GlobalScope.async {
        doSomethingUsefulTwo()
    }

    suspend fun testTime4() = withContext(Dispatchers.IO) {
        val time = measureTimeMillis {
            val one = somethingUsefulOneAsync()
            val two = somethingUsefulTwoAsync()

            runBlocking {
                println("the answer is ${one.await() + two.await()}")
            }
        }
        println("completed in $time ms")
    }

    private suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        one.await() + two.await()
    }

    suspend fun testTime5() = withContext(Dispatchers.IO){
        val time = measureTimeMillis {
            println("the answer is ${concurrentSum()}")
        }
        println("completed in $time ms")
    }
}