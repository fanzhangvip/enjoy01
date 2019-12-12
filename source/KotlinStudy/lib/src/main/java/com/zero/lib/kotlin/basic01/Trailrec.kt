package com.zero.lib.kotlin.basic01

fun fibonacci(n: Int): Int {
    return if (n <= 2) 1 else fibonacci(n - 2) + fibonacci(n - 1)
}

//尾递归版
tailrec fun fibonacci_tail(n: Int, result1: Int = 1,result2: Int = 1): Int {
    return if (n <= 1) result1 else fibonacci_tail(n - 1, result2,result1+result2)
}

fun IntArray.indexOf(element: Int): Int {
    for (index in indices) {
        if (element == this[index]) {
            return index
        }
    }
    return -1
}

//如何写出尾递归函数
//尾递归与迭代（while、for 等）是等价的，我们可以把迭代语句改写成尾递归的形式。怎么改写呢？我们的迭代语句往往包含了三部分：
//
//迭代条件检查：检查当前状态需不需要进行迭代；
//迭代过程：每次迭代处理的过程；
//状态更新：在每次迭代过程之间或之中更新状态。
//在改写时，我们把不满足迭代条件检查的情况划分为递归中的基准情况，进行特殊处理；把迭代过程写到尾递归函数的主体部分，状态更新则依赖于调用递归时的传递不同的参数。
fun IntArray.indexOf_tail(element: Int): Int =
    if (isEmpty()) -1 else indexOfImpl(element, 0)

private tailrec fun IntArray.indexOfImpl(element: Int, index: Int): Int = when {
    index == size -> -1
    element == this[index] -> index
    else -> indexOfImpl(element, index + 1)
}

fun main() {

    //递归版斐波那契数列
    val start = System.currentTimeMillis()
    val result = fibonacci(10)
    println("result: $result diff: ${System.currentTimeMillis() - start}")

    val result1 = fibonacci_tail(10, 1, 1)
    println("result1: $result1 diff: ${System.currentTimeMillis() - start}")
}