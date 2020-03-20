package com.zero.lib.kotlin.basic01

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Created by wxk on 2020/2/28.
 */

fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500) // wait 500 ms
    emit("$i: Second")
}

fun fooSlow():Flow<Int> = flow {
    for (i in 1..3){
        delay(100)
        emit(i)
    }
}
fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        fooSlow().buffer()
            .collect {
                delay(300)
                println(it)
            }
    }
}