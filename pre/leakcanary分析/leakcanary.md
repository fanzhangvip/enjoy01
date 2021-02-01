### GCRoot

要解答这个问题我们需要先理解一下Java运行时的程序计数器，虚拟机堆栈，本地方法栈，方法区，堆以及可作为GCRoot的对象。

##### Java运行时数据区

- **程序计数器** 程序计数器就是当前执行字节码的信号的一个指示器，记录的是当前线程执行字节码指令的地址。通常它会改变，然后实现代码的流程控制，顺序执行，循环等。
- **虚拟机栈** 虚拟机栈是Java方法运行过程中的一个内存模型。虚拟机栈会给没一个即将运行的方法创建一个**栈帧**的区域，这块区域存储了方法在运行时所需要的一些信息，主要包括：

1. 局部变量表：包含方法内的非静态变量以及方法形参，基本类型的存储值，引用对象的指向对象的引用。
2. 操作数栈：存储中间的运算结果，方法入参和返回结果。
3. 运行时常量池引用：主要包含的是当前方法对运行时常量池的引用，方便类在加载时进行动态链接，根据引用符号转变为对方法或者变量的引用。
4. 方法出口返回信息：方法执行完毕后，返回调用位置的地址。

- **本地方法栈** 类似于虚拟机栈，但是是由一些Cor汇编操作的一些方法信息，存放这些非Java语言编写的本地方法的栈区。
- **堆** 堆是运行时数据最大的一块区域，里面包含了绝大部分的对象（实例数组等）都在里面存储。堆是跟随者JVM的启动而创建的，我们创建的对象基本都在堆上分配，并且我们不需要主动去释放内存，因为GC会自动帮我们进行管理和销毁。这里GC相关的一些知识我们后面再做讲解。
- **方法区**  主要存储类的元信息（类的名字，方法，字段信息），静态变量，常量等。方法区这块在JDK不同版本有不同的实现方法，存储的东西也有变化，感兴趣的话大家可以自行了解。

##### GCRoot对象

GCRoot就如同字面描述的，GC开始的根对象，将GCRoot对象作为起点，向下搜索，走过的路径就是一个引用链，如果一个对象到GCRoot没有任何引用链，那么GC将会把这个对象的内存进行回收。

那么GCRoot有哪几种类型呢？或者说哪些对象可以作为GCRoot的对象呢？

- 虚拟机栈引用的对象
- 方法区中静态属性引用的对象
- 方法区中常量引用的对象
- 本地方法栈中JNI引用的对象



### Handler引用链

MessageQueue引用了Message，Message引用了Handler，Handler默认引用了外部类Activity的实例。我们也可以在LeakCanary上看到一样的引用链，并且它的GCRoot是一个native层的方法，这块就涉及到MessageQueue的事件发送的机制，以及和Looper以及Looper内的ThreadLocal的机制了

Handler内执行任务的是什么东西，Handler内对象引用的链条是怎么样的，最终持有的对象是什么？

要解答这个问题，我们需要去分析一下Handler的源代码。

首先，Handler作为匿名内部类和非静态内部类创建的时候会持有外部Activity的引用，我们调用Handler的sendMessage方法发送消息，我们先从这个方法看一下。

```
    public final boolean sendEmptyMessage(int what){
        return sendEmptyMessageDelayed(what, 0);
    }

    public final boolean sendEmptyMessageDelayed(int what, long delayMillis) {
        Message msg = Message.obtain();
        msg.what = what;
        return sendMessageDelayed(msg, delayMillis);
    }
复制代码
```

可以看到上面的方法，发送一条Empty的Message都调用的是延迟发送的Message方法，区别只是延时不同。在sendEmptyMessageDelayed方法内，构造了一个Message并且传入了sendMessageDelayed，我们再往下看，看一下sendMessageDelayed方法

```
    public final boolean sendMessageDelayed(@NonNull Message msg, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
    }

    public boolean sendMessageAtTime(@NonNull Message msg, long uptimeMillis) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessageAtTime() called with no mQueue");
            Log.w("Looper", e.getMessage(), e);
            return false;
        }
        return enqueueMessage(queue, msg, uptimeMillis);
    }
复制代码
```

上面的代码我们可以看到，sendMessageAtTime方法内，构造了一个MessageQueue并且这个MessageQueue默认使用的就是该Handler内的MessageQueue，然后调用enqueueMessage去发送这个msg，参数就是这个queue和msg，我们在看一下这个enqueueMessage方法

```
private boolean enqueueMessage(@NonNull MessageQueue queue, @NonNull Message msg,
        long uptimeMillis) {
    msg.target = this;
    msg.workSourceUid = ThreadLocalWorkSource.getUid();

    if (mAsynchronous) {
        msg.setAsynchronous(true);
    }
    return queue.enqueueMessage(msg, uptimeMillis);
}
复制代码
```

在enqueueMessage内，我们终于找到了引用Handler的地方了，构造的这个msg内的target引用的就是当前的Handler，那么这个将要被传递出去的message引用了当前的Handler，那么下面还有接着引用吗？答案是当然，在调用MessageQueue的enqueueMessage方法的时候，会将msg传入。完整代码较长，这边只帖一部分

```
Message p = mMessages;
boolean needWake;
if (p == null || when == 0 || when < p.when) {
    // New head, wake up the event queue if blocked.
    msg.next = p;
    mMessages = msg;
    needWake = mBlocked;
} else {
    // Inserted within the middle of the queue.  Usually we don't have to wake
    // up the event queue unless there is a barrier at the head of the queue
    // and the message is the earliest asynchronous message in the queue.
    needWake = mBlocked && p.target == null && msg.isAsynchronous();
    Message prev;
    for (;;) {
        prev = p;
        p = p.next;
        if (p == null || when < p.when) {
            break;
        }
        if (needWake && p.isAsynchronous()) {
            needWake = false;
        }
    }
    msg.next = p; // invariant: p == prev.next
    prev.next = msg;
}
复制代码
```

这是执行enqueueMessage的一部分代码，我们可以看到这边MessageQueue内构造了一个新的Message  p,并且将这个对象复制给了 传递进来的msg.next，同时在当前MessageQueue的mMessages为空，也就是当前默认情况下没有消息传递的时候，将msg赋值给了mMessages，那么MessageQueue持有了要传递的Message对象


