# Android中为什么主线程不会因为Looper.loop()里的死循环卡死？

app程序入口中为主线程准备好了消息队列
![img](Handler%E6%9C%BA%E5%88%B6.assets/ce97597327ffe00c04ce76fc2f41030c_b.png)
而根据Looper.loop()源码可知里面是一个死循环在遍历消息队列取消息
![img](Handler%E6%9C%BA%E5%88%B6.assets/10df1f7c70d0d9382f5c8a4b2b6d66a6_b.png)
而且并也没看见哪里有相关代码为这个死循环准备了一个新线程去运转，但是主线程却并不会因为Looper.loop()中的这个死循环卡死，为什么呢？
————————————补充————————————————
举个例子，像Activity的生命周期这些方法这些都是在主线程里执行的吧，那这些生命周期方法是怎么实现在死循环体外能够执行起来的？

作者：Gityuan
链接：https://www.zhihu.com/question/34652589/answer/90344494
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。



要完全彻底理解这个问题，需要准备以下4方面的知识：Process/Thread，Android Binder IPC，Handler/Looper/MessageQueue消息机制，Linux pipe/epoll机制。



ANR(Application Not responding)，是指应用程序未响应，Android系统对于一些事件需要在一定的时间范围内完成，如果超过预定时间能未能得到有效响应或者响应时间过长，都会造成ANR。一般地，这时往往会弹出一个提示框，告知用户当前xxx未响应，用户可选择继续等待或者Force Close。

那么哪些场景会造成ANR呢？

- Service Timeout:比如前台服务在20s内未执行完成；
- BroadcastQueue Timeout：比如前台广播在10s内未执行完成
- ContentProvider Timeout：内容提供者,在publish过超时10s;
- InputDispatching Timeout: 输入事件分发超时5s，包括按键和触摸事件。



**总结一下楼主主要有3个疑惑：**

1.Android中为什么主线程不会因为Looper.loop()里的死循环卡死？ 

2.没看见哪里有相关代码为这个死循环准备了一个新线程去运转？ 

3.Activity的生命周期这些方法这些都是在主线程里执行的吧，那这些生命周期方法是怎么实现在死循环体外能够执行起来的？

\--------------------------------------------------------------------------------------------------------------------------------------
针对这些疑惑， 

[@hi大头鬼hi](http://www.zhihu.com/people/50b28faaec835947511e005cd6734484)

[@Rocko](http://www.zhihu.com/people/4897651cf72b010294463a1fd113af52)[@陈昱全](http://www.zhihu.com/people/53013cf8f04bf168814156e563beb935) 大家回答都比较精炼，接下来我再更进一步详细地一一解答楼主的疑惑：



**(1) Android中为什么主线程不会因为Looper.loop()里的死循环卡死？** 

这里涉及线程，先说说说进程/线程，**进程：**每个app运行时前首先创建一个进程，该进程是由Zygote fork出来的，用于承载App上运行的各种Activity/Service等组件。进程对于上层应用来说是完全透明的，这也是google有意为之，让App程序都是运行在Android Runtime。大多数情况一个App就运行在一个进程中，除非在AndroidManifest.xml中配置Android:process属性，或通过native代码fork进程。

**线程：**线程对应用来说非常常见，比如每次new Thread().start都会创建一个新的线程。该线程与App所在进程之间资源共享，从Linux角度来说进程与线程除了是否共享资源外，并没有本质的区别，都是一个task_struct结构体**，在CPU看来进程或线程无非就是一段可执行的代码，CPU采用CFS调度算法，保证每个task都尽可能公平的享有CPU时间片**。

有了这么准备，再说说死循环问题：

对于线程既然是一段可执行的代码，当可执行代码执行完成后，线程生命周期便该终止了，线程退出。而对于主线程，我们是绝不希望会被运行一段时间，自己就退出，那么如何保证能一直存活呢？**简单做法就是可执行代码是能一直执行下去的，死循环便能保证不会被退出，**例如，binder线程也是采用死循环的方法，通过循环方式不同与Binder驱动进行读写操作，当然并非简单地死循环，无消息时会休眠。但这里可能又引发了另一个问题，既然是死循环又如何去处理其他事务呢？通过创建新线程的方式。

真正会卡死主线程的操作是在回调方法onCreate/onStart/onResume等操作时间过长，会导致掉帧，甚至发生ANR，looper.loop本身不会导致应用卡死。



**(2) 没看见哪里有相关代码为这个死循环准备了一个新线程去运转？** 

事实上，会在进入死循环之前便创建了新binder线程，在代码ActivityThread.main()中：

```java
public static void main(String[] args) {
        ....

        //创建Looper和MessageQueue对象，用于处理主线程的消息
        Looper.prepareMainLooper();

        //创建ActivityThread对象
        ActivityThread thread = new ActivityThread(); 

        //建立Binder通道 (创建新线程)
        thread.attach(false);

        Looper.loop(); //消息循环运行
        throw new RuntimeException("Main thread loop unexpectedly exited");
    }
```

**thread.attach(false)；便会创建一个Binder线程（具体是指ApplicationThread，Binder的服务端，用于接收系统服务AMS发送来的事件），该Binder线程通过Handler将Message发送给主线程**，具体过程可查看 [startService流程分析](https://link.zhihu.com/?target=http%3A//gityuan.com/2016/03/06/start-service/)，这里不展开说，简单说Binder用于进程间通信，采用C/S架构。关于binder感兴趣的朋友，可查看我回答的另一个知乎问题：
[为什么Android要采用Binder作为IPC机制？ - Gityuan的回答](https://www.zhihu.com/question/39440766/answer/89210950)

另外，**ActivityThread实际上并非线程**，不像HandlerThread类，ActivityThread并没有真正继承Thread类，只是往往运行在主线程，该人以线程的感觉，其实承载ActivityThread的主线程就是由Zygote fork而创建的进程。

**主线程的死循环一直运行是不是特别消耗CPU资源呢？** 其实不然，这里就涉及到**Linux pipe/e****poll机制**，简单说就是在主线程的MessageQueue没有消息时，便阻塞在loop的queue.next()中的nativePollOnce()方法里，详情见[Android消息机制1-Handler(Java层)](https://link.zhihu.com/?target=http%3A//www.yuanhh.com/2015/12/26/handler-message-framework/%23next)，此时主线程会释放CPU资源进入休眠状态，直到下个消息到达或者有事务发生，通过往pipe管道写端写入数据来唤醒主线程工作。这里采用的epoll机制，是一种IO多路复用机制，可以同时监控多个描述符，当某个描述符就绪(读或写就绪)，则立刻通知相应程序进行读或写操作，本质同步I/O，即读写是阻塞的。 **所以说，主线程大多数时候都是处于休眠状态，并不会消耗大量CPU资源。**



**(3) Activity的生命周期是怎么实现在死循环体外能够执行起来的？**

ActivityThread的内部类H继承于Handler，通过handler消息机制，简单说Handler机制用于同一个进程的线程间通信。

**Activity的生命周期都是依靠主线程的Looper.loop，当收到不同Message时则采用相应措施：**
在H.handleMessage(msg)方法中，根据接收到不同的msg，执行相应的生命周期。

​    比如收到msg=H.LAUNCH_ACTIVITY，则调用ActivityThread.handleLaunchActivity()方法，最终会通过反射机制，创建Activity实例，然后再执行Activity.onCreate()等方法；
​    再比如收到msg=H.PAUSE_ACTIVITY，则调用ActivityThread.handlePauseActivity()方法，最终会执行Activity.onPause()等方法。 上述过程，我只挑核心逻辑讲，真正该过程远比这复杂。

**主线程的消息又是哪来的呢？**当然是App进程中的其他线程通过Handler发送给主线程，请看接下来的内容：



\--------------------------------------------------------------------------------------------------------------------------------------
**最后，从进程与线程间通信的角度，****通过一张图****加深大家对App运行过程的理解：**

![img](Handler%E6%9C%BA%E5%88%B6.assets/7fb8728164975ac86a2b0b886de2b872_hd.jpg)![img](Handler%E6%9C%BA%E5%88%B6.assets/7fb8728164975ac86a2b0b886de2b872_720w.jpg)


**system_server进程是系统进程**，java framework框架的核心载体，里面运行了大量的系统服务，比如这里提供ApplicationThreadProxy（简称ATP），ActivityManagerService（简称AMS），这个两个服务都运行在system_server进程的不同线程中，由于ATP和AMS都是基于IBinder接口，都是binder线程，binder线程的创建与销毁都是由binder驱动来决定的。



**App进程则是我们常说的应用程序**，主线程主要负责Activity/Service等组件的生命周期以及UI相关操作都运行在这个线程； 另外，每个App进程中至少会有两个binder线程 ApplicationThread(简称AT)和ActivityManagerProxy（简称AMP），除了图中画的线程，其中还有很多线程，比如signal catcher线程等，这里就不一一列举。

Binder用于不同进程之间通信，由一个进程的Binder客户端向另一个进程的服务端发送事务，比如图中线程2向线程4发送事务；而handler用于同一个进程中不同线程的通信，比如图中线程4向主线程发送消息。

**结合图说说Activity生命周期，比如暂停Activity，流程如下：**

1. 线程1的AMS中调用线程2的ATP；（由于同一个进程的线程间资源共享，可以相互直接调用，但需要注意多线程并发问题）
2. 线程2通过binder传输到App进程的线程4；
3. 线程4通过handler消息机制，将暂停Activity的消息发送给主线程；
4. 主线程在looper.loop()中循环遍历消息，当收到暂停Activity的消息时，便将消息分发给ActivityThread.H.handleMessage()方法，再经过方法的调用，最后便会调用到Activity.onPause()，当onPause()处理完后，继续循环loop下去。

了解下linux的epoll你就知道为什么不会被卡住了，先说结论：阻塞是有的，但是不会卡住
主要原因有2个

1，epoll模型
当没有消息的时候会epoll.wait，等待句柄写的时候再唤醒，这个时候其实是阻塞的。

2，所有的ui操作都通过handler来发消息操作。
比如屏幕刷新16ms一个消息，你的各种点击事件，所以就会有句柄写操作，唤醒上文的wait操作，所以不会被卡死了。



作者：Tmacchen
链接：https://www.zhihu.com/question/34652589/answer/59571257
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。



作者：镰仓
链接：https://www.zhihu.com/question/34652589/answer/157834250
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。



简单一句话是：Android应用程序的主线程在进入消息循环过程前，会在内部创建一个Linux管道（Pipe），这个管道的作用是使得Android应用程序主线程在消息队列为空时可以进入空闲等待状态，并且使得当应用程序的消息队列有消息需要处理时唤醒应用程序的主线程。

\---
这一题是需要从消息循环、消息发送和消息处理三个部分理解Android应用程序的消息处理机制了，这里我对一些要点作一个总结：

​         A. Android应用程序的消息处理机制由消息循环、消息发送和消息处理三个部分组成的。

​         B. Android应用程序的主线程在进入消息循环过程前，会在内部创建一个Linux管道（Pipe），这个管道的作用是使得Android应用程序主线程在消息队列为空时可以进入空闲等待状态，并且使得当应用程序的消息队列有消息需要处理时唤醒应用程序的主线程。

​         C. Android应用程序的主线程进入空闲等待状态的方式实际上就是在管道的读端等待管道中有新的内容可读，具体来说就是是通过Linux系统的Epoll机制中的epoll_wait函数进行的。

​         D. 当往Android应用程序的消息队列中加入新的消息时，会同时往管道中的写端写入内容，通过这种方式就可以唤醒正在等待消息到来的应用程序主线程。

​         E. 当应用程序主线程在进入空闲等待前，会认为当前线程处理空闲状态，于是就会调用那些已经注册了的IdleHandler接口，使得应用程序有机会在空闲的时候处理一些事情。





作者：知乎用户
链接：https://www.zhihu.com/question/34652589/answer/382654129
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。



很多同学都解释了`Looper.loop()`方法里的死循环跟ANR的区别，我补充一下对以下问题的看法：当主线程调用`Looper.loop()`方法开始监听消息，并且当消息队列为空时，主线程被挂起，在这种情况下，谁来唤醒主线程？比如当主线程队列为空被挂起之后，用户点击屏幕，这时主线程是如何被唤醒的？

首先，即使app开发者没有创建其他线程，主线程也不是唯一的线程，还有Binder线程等。这些线程都可以发送消息到主线程来唤醒它处理消息。

其次，在`MessageQueue.next()`方法里，会调用一个native方法：`nativePollOnce(long ptr, int timeoutMillis)`，当主线程没有消息可处理的时候，该方法会阻塞主线程。在这种情况下，用户点击一下屏幕，（打断点）可发现如下调用栈：

```text
at android.view.InputEventReceiver.dispatchInputEvent(InputEventReceiver.java:185)
at android.os.MessageQueue.nativePollOnce(MessageQueue.java:-1)
at android.os.MessageQueue.next(MessageQueue.java:325)
at android.os.Looper.loop(Looper.java:142)
at android.app.ActivityThread.main(ActivityThread.java:6541)
at java.lang.reflect.Method.invoke(Method.java:-1)
at com.android.internal.os.Zygote$MethodAndArgsCaller.run(Zygote.java:240)
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:767)
```

可以看到`nativePollOnce()`方法继续执行了，并且调用了`InputEventReceiver.dispatchInputEvent()`方法。看源码可以发现该方法有如下注释，这是一个被native 代码调用的方法。

```java
// Called from native code.
@SuppressWarnings("unused")
private void dispatchInputEvent(int seq, InputEvent event)
```

由此可推测，调用`nativePollOnce()`方法挂起主线程之后，当有一些事件到来时，native层会唤醒主线程。具体的阻塞和唤醒机制，就是其他答主提到的epoll机制了。



注：以上涉及代码，是android sdk 26上的，其他系统版本可能有所不同。

再注：好奇心重的同学，可以在`MessageQueue.next()`方法里打断点观察主线程在队列为空的情况下如何被阻塞。阻塞之后，可以在`MessageQueue`的各个添加消息到队列中的方法里打断点，观察点击屏幕时产生的第一个消息是如何被添加到队列里的。

小tips：`MessageQueue.enqueueMessage(Message msg, long when)`并不是唯一添加消息的方法。 记得在所有添加消息的方法里打断点，才能观察到第一个消息是如何被添加到队列里的。

对于 epoll 相关的知识题主可以稍后了解，这里主要说下为什么不会“卡死”，我觉得题主认为的卡死就是一个方法长时间没有返回导致 ANR，然后 main 函数中又调用了 loop 方法，这个方法也不会返回，是一个死循环。但是 UI 是事件循环来驱动的，也就是说每一帧的绘制都会通过一次事件循环（Android 后来出现的 Render Thread 或者 macOS、iOS 的 WindowServer 机制不严格是这样），每次触摸操作也要经过一次事件循环，假如某个方法执行时间过长，那么新的触摸或重绘事件就不能得以处理，用户就会觉得卡或死机了。然而事件循环总得有个驱动器，这就需要用到循环，loop 方法中实际上有一个很重要的调用来获取下一个可用的事件，假设没有可用事件就会通过 epoll 来等待，拿到事件后就会通过 Handler 进行派发，派发的最终结果就是你的 Activity  或其他 components 里的一个生命周期方法或回调方法被调用，你就可以在里面做自己的事情了。



作者：Cyandev
链接：https://www.zhihu.com/question/34652589/answer/157878909
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

准确的讲，主线程确实堵塞了，Looper实现了**可阻塞的死循环**。当没有消息的时候，主线程既不能退出，又不能无阻塞死循环执行，最后的办法只能是让他有消息的时候处理消息，没消息的时候进入阻塞状态。

利用Linux的epoll+pipe机制，使得主线程在阻塞的时候，让出CPU资源，同时等待新消息。当我们对系统进行操作（包括各种滑动和点击）的时候，系统就会给主线程发送消息，这个时候就会唤醒主线程（执行onCreate，onResume等方法），当处理完这个消息，就会再次进入阻塞状态。这样系统就能做到随时响应用户的操作。

真正的ANR绝不是Looper轮询消息导致的，而是处理消息过程中的问题。**事件只会阻塞Looper，而Looper不会阻塞事件**，当某个事件在主线程执行了耗时操作，影响了Looper轮询后面的消息，才会发生ANR。



作者：皎洁同学
链接：https://www.zhihu.com/question/34652589/answer/662898834
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。