package com.zero.coroutinesdemo

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.system.measureTimeMillis

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

val threadLocal = ThreadLocal<String?>() // 声明线程局部变量

@InternalCoroutinesApi
suspend fun main() {

    val coroutines1 = KotlinCPS()

    coroutines1.test()

}

class Repo()

interface GitHubApi {

    @GET("/users/{user}/repos")
    fun listReposDeferred(@Path("user") user: String): Deferred<List<Repo>>

    @GET("/users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>

}

data class Item(val i: String = "item")

class KotlinCPS {

    suspend fun test() {
        GlobalScope.launch {
            postItem(Item("item"))
            println("done")
        }

        println("in main")
        Thread.sleep(2000)
    }

    suspend fun postItem(item: Item) {
        val token = requestToken()
        val post = createPost(token, item)
        processPost(post)
    }

    suspend fun requestToken(): String {
        print("Start request token ...")
        Thread.sleep(1000)
        println("... finish request token")
        return "token"
    }

    suspend fun createPost(token: String, item: Item): String {
        print("Start create Post ... $token, $item")
        Thread.sleep(500)
        println(" ... finish create Post")
        return "ResponsePost"
    }

    fun processPost(post: String) {
        println("process post, post=$post")
    }
}

/**
 * 优点: 异步, 非阻塞
缺点: 回调嵌套, 代码难懂
 */
class CallbackStyle {

    fun postItem(item: Item) {
        requestToken(object : Callback<String> {

            override fun onResult(token: String) {

                createPost(token, item, object : Callback<String> {

                    override fun onResult(post: String) {

                        processPost(post)
                    }
                })
            }
        })
    }

    fun requestToken(callback: Callback<String>) {
        print("Start request token ...")
        Thread.sleep(1000)
        println("... finish request token")
        callback.onResult("token")
    }

    fun createPost(token: String, item: Item, callback: Callback<String>) {
        print("Start create Post ... $token, $item")
        Thread.sleep(500)
        println(" ... finish create Post")
        callback.onResult("ResponsePost")
    }

    fun processPost(post: String) {
        println("process post, post=$post")
    }

    interface Callback<T> {
        fun onResult(value: T)
        fun onError(exception: Exception) {}
    }
}

/**
 * 优点: 顺序, 简单, 直接
缺点: 阻塞
 */
class DirectStyle {
    fun postItem(item: Item) {
        val token = requestToken()
        val post = createPost(token, item)
        processPost(post)
    }

    fun requestToken(): String {
        print("Start request token ...")
        Thread.sleep(1000) // 假设网络请求很耗时
        println("... finish request token")
        return "token"
    }

    fun createPost(token: String, item: Item): String {
        print("Start create Post ... $token, $item")
        Thread.sleep(500) // 假设网络请求很耗时
        println(" ... finish create Post")
        return "ResponsePost"
    }

    fun processPost(post: String) {
        println("process post, post=$post")
    }
}

class Coroutines1 {


    suspend fun jetpackTest() = coroutineScope {

    }

    suspend fun gitHubServiceTest() = coroutineScope {
        val retrofit = Retrofit.Builder()
            .baseUrl(HttpUrl.get("https://api.github.com/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        val gitHubApi = retrofit.create(GitHubApi::class.java)

        GlobalScope.launch {
            val reposDeferred = gitHubApi.listReposDeferred("zerovip").await()
            showReops(reposDeferred)
            val repos = gitHubApi.listRepos("zerovip")
        }

    }

    fun showReops(repos: List<Repo>) {
        Log.i("Zero","${repos.size}")
    }

    class Activity {
        private val mainScope = CoroutineScope(Dispatchers.Default) // use Default for test purposes

        fun destroy() {
            mainScope.cancel()
        }

        fun doSomething() {
            // 在示例中启动了 10 个协程，且每个都工作了不同的时长
            repeat(10) { i ->
                mainScope.launch {
                    delay((i + 1) * 200L) // 延迟 200 毫秒、400 毫秒、600 毫秒等等不同的时间
                    println("Coroutine $i is done")
                }
            }
        }
    }


    @InternalCoroutinesApi
    suspend fun flowTest() = coroutineScope {
        fun foo(): Flow<Int> = flow { // 流构建器
            for (i in 1..5) {
                delay(100) // 假装我们在这里做了一些有用的事情
                emit(i) // 发送下一个值
            }
        }

        launch {
            for (k in 1..3) {
                println("I'm not blocked $k")
                delay(100)
            }
        }
        // 收集这个流
        foo().collect { value -> println(value) }
    }

    suspend fun SequenceTest() {
        fun foo(): Sequence<Int> = sequence { // sequence builder
            for (i in 1..3) {
                Thread.sleep(100) // pretend we are computing it
                yield(i) // yield next value
            }
        }

        suspend fun foo1(): List<Int> {
            delay(1000) // 假装我们在这里做了一些异步的事情
            return listOf(1, 2, 3)
        }
        foo().forEach { value -> println(value) }

        foo1().forEach { value -> println("suspend ,$value") }


    }

    suspend fun threadLocalTest() = coroutineScope {
        threadLocal.set("main")
        println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
            println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            yield()
            println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        }
        job.join()
        println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    }

    suspend fun CoroutineScopeTest() = coroutineScope {
        val activity = Activity()
        activity.doSomething() // 运行测试函数
        println("Launched coroutines")
        delay(500L) // 延迟半秒钟
        println("Destroying activity!")
        activity.destroy() // 取消所有的协程
        delay(1000) // 为了在视觉上确认它们没有工作
    }

    /**
     * 命名协程以用于调试
     */
    suspend fun CoroutineNameTest() = coroutineScope {
        log("Started main coroutine")
// 运行两个后台值计算
        val v1 = launch(Dispatchers.Default + CoroutineName("v1coroutine")) {
            delay(500)
            println("I'm working in thread ${Thread.currentThread().name}")
            log("Computing v1")
            252
        }
        val v2 = async(CoroutineName("v2coroutine")) {
            delay(1000)
            log("Computing v2")
            6
        }
        log("The answer for v1 / v2 = ${v2.await()}")
    }

    /**
     * 父协程的职责
    一个父协程总是等待所有的子协程执行结束。父协程并不显式的跟踪所有子协程的启动，并且不必使用 Job.join 在最后的时候等待它们：
     */
    suspend fun parentCoroutineScopeTest() = coroutineScope {
        // 启动一个协程来处理某种传入请求（request）
        val request = launch {
            repeat(3) { i -> // 启动少量的子作业
                launch {
                    delay((i + 1) * 200L) // 延迟 200 毫秒、400 毫秒、600 毫秒的时间
                    println("Coroutine $i is done")
                }
            }
            println("request: I'm done and I don't explicitly join my children that are still active")
        }
        request.join() // 等待请求的完成，包括其所有子协程
        println("Now processing of the request is complete")
    }

    /**
     * 子协程
    当一个协程被其它协程在 CoroutineScope 中启动的时候， 它将通过 CoroutineScope.coroutineContext 来承袭上下文，并且这个新协程的 Job 将会成为父协程作业的 子 作业。当一个父协程被取消的时候，所有它的子协程也会被递归的取消。

    然而，当使用 GlobalScope 来启动一个协程时，则新协程的作业没有父作业。 因此它与这个启动的作用域无关且独立运作。
     */
    suspend fun childCoroutineScopeTest() = coroutineScope {
        val request = launch {
            // 孵化了两个子作业, 其中一个通过 GlobalScope 启动
            GlobalScope.launch {
                println("job1: I run in GlobalScope and execute independently!")
                delay(1000)
                println("job1: I am not affected by cancellation of the request")
            }
            // 另一个则承袭了父协程的上下文
            launch {
                delay(100)
                println("job2: I am a child of the request coroutine")
                delay(1000)
                println("job2: I will not execute this line if my parent request is cancelled")
            }
        }
        delay(500)
        request.cancel() // 取消请求（request）的执行
        delay(1000) // 延迟一秒钟来看看发生了什么
        println("main: Who has survived request cancellation?")
    }

    suspend fun coroutineContextTest() = coroutineScope {
        println("My job is ${coroutineContext[Job]}")
    }

    suspend fun newSingelThreadTest() = coroutineScope {
        newSingleThreadContext("Ctx1").use { ctx1 ->
            newSingleThreadContext("Ctx2").use { ctx2 ->
                runBlocking(ctx1) {
                    log("Started in ctx1")
                    withContext(ctx2) {
                        log("Working in ctx2")
                    }
                    log("Back to ctx1")
                }
            }
        }
    }

    suspend fun withTimeoutTest() = coroutineScope {
        withTimeout(1300L) {//超过1300毫秒就自动取消任务
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
    }

    suspend fun nonCancellableTest() = coroutineScope {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                withContext(NonCancellable) {// 使用 NonCancellable 让协程不能被取消
                    println("job: I'm running finally")
                    delay(1000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300L) // 延迟一段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消该作业并等待它结束
        println("main: Now I can quit.")
    }


    suspend fun yieldTest() = coroutineScope {
        val single = newSingleThreadContext("Single")
        suspend fun printSomeThingBlock(text: String) {
            println(text)
//            delay(1000)
            Thread.sleep(1000)
        }

        val job = GlobalScope.launch {
            launch {
                withContext(single) {
                    repeat(3) {
                        printSomeThingBlock("Task1")
                        yield()
                    }
                }
            }

            launch {
                withContext(single) {
                    repeat(3) {
                        printSomeThingBlock("Task3")
                        yield()
                    }
                }
            }
        }

        job.join()

    }


    /**
     * 在 finally 中释放资源
     */
    suspend fun jobCancelFinally() = coroutineScope {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                println("job: I'm running finally")
            }
        }
        delay(1300L) // 延迟一段时间
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // 取消该作业并且等待它结束
        println("main: Now I can quit.")
    }

    suspend fun jobCancelAndJoinTest() = coroutineScope {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) { // computation loop, just wastes CPU
                // print a message twice a second

                try {
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        println("job: I'm sleeping ${i++} ...")
//                        nextPrintTime += 500L
//                        yield()
                        delay(500)
                    }
                } catch (e: CancellationException) {
                    println("cancel...")
                    break
                }
                //检测是否取消了
//                if(!this.isActive){
//                    println("cancel ...")
//                    break
//                }

            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }

    suspend fun joinTest() = coroutineScope {
        val job = launch {
            delay(1000L)
            println("world")
        }
        println("Hello, ")
        job.join()
    }

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

    suspend fun testTime5() = withContext(Dispatchers.IO) {
        val time = measureTimeMillis {
            println("the answer is ${concurrentSum()}")
        }
        println("completed in $time ms")
    }


}