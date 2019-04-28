# 从观察者模式出发，聊聊RxJava

## 前言

### RxJava 是什么

> RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.

以上是RxJava在[Github](https://link.juejin.im/?target=https%3A%2F%2Fgithub.com%2FReactiveX%2FRxJava)上的介绍，大概意思是，**针对于JVM(Java虚拟机）的响应式扩展实现，一个在Java VM上使用可观察的序列来组合实现异步的、基于事件编程的库。**

RxJava现在大家用的都应该已经很溜了，用法这里就不再多说了。我们都知道RxJava是对**观察者模式**的扩展，下面就从观察者模式的实现机制出发，了解一下RxJava2的实现逻辑。只有真正了解了RxJava 的实现原理，我们才能在遇到问题的时候，更快速更准确的定位的到问题。

> 此次源码分析基于 [RxJava Release 2.1.7](https://link.juejin.im/?target=https%3A%2F%2Fgithub.com%2FReactiveX%2FRxJava%2Freleases%2Ftag%2Fv2.1.7)

### 观察者模式

这里简单回顾一下观察者模式的组成及使用方式，通过之前[观察者模式](https://link.juejin.im/?target=https%3A%2F%2Fwww.jianshu.com%2Fp%2F6fcce09cc1ce)一文中的分析，我们知道观察者模式中有四个重要的角色：

- 抽象主题：**定义添加和删除观察者的功能，也就是注册和解除注册的功能**
- 抽象观察者：**定义观察者收到主题通知后要做什么事情**
- 具体主题：**抽象主题的实现**
- 具体观察者：**抽象观察者的实现**

当我们创建好了具体主题和观察者类，就可以使用观察者模式了，下面是一个最简单的测试demo。

```
public class TestObservePattern {

    public static void main(String[] args) {
		// 创建主题(被观察者)
        ConcreteSubject concreteSubject = new ConcreteSubject();
		// 创建观察者
        ObserverOne observerOne=new ObserverOne();
        // 为主题添加观察者
        concreteSubject.addObserver(observerOne);        
        //主题通知所有的观察者
        concreteSubject.notifyAllObserver("wake up,wake up");
    }

}
复制代码
```

以上就是观察者模式的使用方式，很简单是吧。现在就让我们带着以下几个问题，看看RxJava是如何使用观察者模式的。

用RxJava这么久了，你可以思考一下如下几个问题：

1. RxJava 中上面提到的四个重要角色是如何定义的？
2. RxJava 中具体的主题，具体的观察者是如何实例化的？
3. RxJava 中观察者和主题是如何实现订阅的？
4. RxJava 中上游是怎么发送事件的，下游又是怎样接收到的?
5. RxJava 中对常规的观察者模式做了怎样调整，带来了什么好处？

*如果对以上几个问题，你有明确的答案，恭喜你，以下内容你就不用再看了，O(∩_∩)O哈哈~。*

> 很多开发者对RxJava的学习可能是从**上游**和**下游**的角度开始，这里可以认为这样的叙述更偏重RxJava 事件序列的特征。本文从**被观察者（主题）和观察者**的角度出发，可以说是更偏向于RxJava 观察者模式的特征。这里的主题就是上游，观察者就是下游。无论从哪个角度出发去理解，源码就那么一份，无所谓对错，只是每个人的认知角度不同而已，选择一种自己更容易了解的方式即可。

好了，如果你看到了这里，说明你对以上几个问题，还有些许疑问，那么我们就从这几个问题出发，了解一下RxJava的源码实现。

### RxJava2 的观察者模式实现

我们就带着上述几个问题，依次来看看RxJava到底是怎么一回事儿。为了方便叙述和记忆，我们首先看一段RxJava2 最最基础的使用方式。

```
private void basicRxjava2() {
        Observable mObservable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onNext("4");
                e.onComplete();
            }
        });

        Observer mObserver = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: d=" + d);
                sb.append("\nonSubcribe: d=" + d);
            }

            @Override
            public void onNext(Object s) {
                Log.e(TAG, "onNext: " + s);
                sb.append("\nonNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e);
                sb.append("\nonError: " + e.toString());
                logContent.setText(sb.toString());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
                sb.append("\nonComplete: ");
                logContent.setText(sb.toString());
            }
        };

        mObservable.subscribe(mObserver);
    }
复制代码
```

上面这段代码，应该很容易理解了，输出结果大家闭着眼睛也能想出来吧。我们就以这段代码为基础，结合上面提到的问题依次展开对RxJava的分析。

#### 四个重要的角色

首先看看RxJava 中四个重要的角色是如何定义的。

- 抽象主题

首先可以看看这个Observable类。

```
public abstract class Observable<T> implements ObservableSource<T> {
……
}
复制代码
```

他实现了ObservableSource接口，接着看ObservableSource

```
public interface ObservableSource<T> {

    /**
     * Subscribes the given Observer to this ObservableSource instance.
     * @param observer the Observer, not null
     * @throws NullPointerException if {@code observer} is null
     */
    void subscribe(@NonNull Observer<? super T> observer);
}
复制代码
```

这里很明显了，ObservableSource 就是抽象主题（被观察者）的角色。按照之前观察者模式中约定的职责，subscribe 方法就是用来实现订阅观察者（Observer）角色的功能。从这里我们也可以看出，抽象观察者的角色就是Observer了。

这里，你也许会有疑问，这么简单？抽象主题（上游）不是需要发送事件吗？onNext(),onComplete()以及onError()跑哪儿去了？别着急，我们后面慢慢看。

- 具体主题

回过头来继续看Observable，他实现了ObservableSource接口，并且实现了其subscribe方法，但是它并没有真正的去完成**主题**和**观察者**之间的订阅关系，而是把这个功能，转接给了另一个抽象方法subscribeActual（具体细节后面分析）。

因此，Observable依旧是一个抽象类，我们知道**抽象类是不能被实例化的**，因此从理论上来说，他好像不能作为具体主题的角色。其实不然，Observable内部提供了create,defer,fromXXX,repeat,just等一系列**创建型操作符**， 用来创建各种Observable。

```
    public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
        ObjectHelper.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new ObservableCreate<T>(source));
    }
复制代码
```

在RxJava内有很多他的子类。



![Observable子类](https://user-gold-cdn.xitu.io/2018/1/7/160d0dafe04868f6?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)



诚然，你可以认为，这些子类其实才是真正的具体主题。但是，换一个角度，从[代理模式](https://juejin.im/post/5a4e4725f265da3e2c37e36e)的角度出发，我们可以把Observable当做是一个代理类，客户端你只管调用create 方法，想要什么样的 Observable告诉我一声就可以，不同Observable之间的差异你不用管，包在我身上，保证给你返回你想要的Observable实例。

同时，Observable另一个巨大的贡献，就是定义了很多的操作符，我们平时常用的map,flatMap,distinct等，也是在这里定义。并且这些方法都是final类型的，因此他的所有子类都会继承同时也无法改变这些操作符的实现。

因此，Observable 就是具体主题。

- 抽象观察者

在抽象主题里已经提过了，Observer就是抽象观察者的角色。

```
public interface Observer<T> {

    void onSubscribe(@NonNull Disposable d);

    void onNext(@NonNull T t);

    void onError(@NonNull Throwable e);

    void onComplete();

}

复制代码
```

非常符合观察者模式中抽象观察者的职责描述，Observer 定义了观察者（下游）收到主题（上游）通知后该做什么事情。这里需要注意的是onSubscribe 也是定义在这里的。

- 具体的观察者

这个具体的观察者，o(╯□╰)oo(╯□╰)o，就不多说了吧。大家平时使用应该都是直接用new一个Observer的实例。RxJava内部有很多Observer的子类，有兴趣的同学可以具体了解一下。这里其实可以引申出一个有意思的问题，同样是抽象类，为什么接口可以直接实例化，而用abstract修饰过的类就不可以？

#### 具体的观察者是如何实例化的

我们看一下这段代码：

```
    Observable mObservable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
            }
        });

    public static <T> Observable<T> create(ObservableOnSubscribe<T> source) {
        ObjectHelper.requireNonNull(source, "source is null");
        return RxJavaPlugins.onAssembly(new ObservableCreate<T>(source));
    }
	
	
    public static <T> Observable<T> onAssembly(@NonNull Observable<T> source) {
        Function<? super Observable, ? extends Observable> f = onObservableAssembly;
		// 是否有别的其他操作符运算，有的话，在此Observable上执行一遍
        if (f != null) {
            return apply(f, source);
        }
        return source;
    }

复制代码
```

**RxJava的代码里，很多时候会有ObjectHelper.requireNonNull这种空检查的地方，一律都是为了最大程度的防止NPE的出现，后面出现就不再赘述了**.

我们使用create操作符创建Observable的过程中，看似经历了很多方法，**在不考虑任何其他操作符的前提下**，整个过程简化一下的话就这么一句代码

```
  Observable mObservable=new ObservableCreate(new ObservableOnSubscribe())
复制代码
```

从之前的分析，我们也看到了ObservableCreate 就是Observeable抽象类的一个子类。我们简单看一下他的实现。

```
public final class ObservableCreate<T> extends Observable<T> {
    final ObservableOnSubscribe<T> source;

    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        ……
    }
}

复制代码
```

可以看到，他唯一的构造函数需要一个ObservableOnSubscribe实例，同时他实现subscribeActual方法，说明他真正处理**主题**和**观察者**之间实现订阅的逻辑。

看了半天，你可能一直很好奇，这个ObservableOnSubscribe是个什么东西呢？他其实很简单。

```
/**
 * A functional interface that has a {@code subscribe()} method that receives
 * an instance of an {@link ObservableEmitter} instance that allows pushing
 * events in a cancellation-safe manner.
 *
 * @param <T> the value type pushed
 */
public interface ObservableOnSubscribe<T> {

    /**
     * Called for each Observer that subscribes.
     * @param e the safe emitter instance, never null
     * @throws Exception on error
     */
    void subscribe(@NonNull ObservableEmitter<T> e) throws Exception;
}
复制代码
```

ε=(´ο｀*)))唉，怎么又一个subscribe，这又是啥？不要慌，看注释。意思是说，这里的subscribe 接收到一个ObservableEmitter实例后，就会允许他以一种可以安全取消（也就是一定能取消）的形式发送事件。

**就是说会有某个对象，给他一个ObservableEmitte的实例，没给他之前他是不会主动发送事件的，会一直憋着。**，到这里，你是不是想到了什么，我们知道在RxJava 中只有观察者（下游）订阅(subscribe)了主题（上游），主题才会发送事件。这就是和普通的观察者模式有区别的地方之一。

好了，最后再来看看这个神秘的ObservableEmitter是个什么鬼？

```
public interface ObservableEmitter<T> extends Emitter<T> {

    void setDisposable(@Nullable Disposable d);


    void setCancellable(@Nullable Cancellable c);


    boolean isDisposed();

    ObservableEmitter<T> serialize();

      /**
     * Attempts to emit the specified {@code Throwable} error if the downstream
     * hasn't cancelled the sequence or is otherwise terminated, returning false
     * if the emission is not allowed to happen due to lifecycle restrictions.
     * <p>
     * Unlike {@link #onError(Throwable)}, the {@code RxJavaPlugins.onError} is not called
     * if the error could not be delivered.
     * @param t the throwable error to signal if possible
     * @return true if successful, false if the downstream is not able to accept further
     * events
     * @since 2.1.1 - experimental
     */
    boolean tryOnError(@NonNull Throwable t);
}
复制代码
```

**这里可以关注一下tryOnError这个方法，可以看到他会把某些类型的error传递到下游。**

o(╥﹏╥)o，又是一个接口，而且还继承了另一个接口，什么情况？继续看

```
public interface Emitter<T> {

    void onNext(@NonNull T value);

  
    void onError(@NonNull Throwable error);

   
    void onComplete();
}
复制代码
```

惊不惊喜，意不意外？ 哈哈，终于找到你了，熟悉的onNext，onError,onComplete.原来在这里。

**这里有个问题可以思考一下，在抽象观察者中，定义了四个处理事件的方法，这里只有三个，按照对应关系来说似乎缺了一个onSubscribe，这又是怎么回事呢？后面会有分析，可以自己先想想**

这两个接口的含义很明显了，总结一下：

- Emitter 定义了可以发送的事件的三种机制
- ObservableEmitter 在Emitter 做了扩展，添加了Disposable相关的方法，可以用来取消事件的发送。

好了，绕了一大圈，就为了一行代码：

```
  Observable mObservable=new ObservableCreate(new ObservableOnSubscribe())
复制代码
```

总结一下具体主题（上游）的到底干了啥：

- 创建了一个ObservableCreate 的实例对象
- ObservableCreate 内持有ObservableOnSubscribe 对象的引用
- ObservableOnSubscribe 是一个接口，内部有一个subscribe方法，调用他之后，会用其ObservableEmitter实例开始发送事件。
- ObservableEmitter 继承自Emitte。

#### 如何实现订阅、发送事件和接收事件

为了方便叙述，把问题3和4连在一起说了。

通过上面的叙述，现在具体主题和具体的观察者都创建好了，接下来就是实现二者的订阅关系。

```
mObservable.subscribe(mObserver);
复制代码
```

> 这里需要明确的一点是，是观察者（下游）订阅了主题（上游），虽然从代码上看好像了前者订阅了后者，不要搞混了。

我们看Observable的subscribe() 方法：

```
    public final void subscribe(Observer<? super T> observer) {
        ObjectHelper.requireNonNull(observer, "observer is null");
        try {
            observer = RxJavaPlugins.onSubscribe(this, observer);

            ObjectHelper.requireNonNull(observer, "Plugin returned null Observer");

            subscribeActual(observer);
        } catch (NullPointerException e) { // NOPMD
            throw e;
        } catch (Throwable e) {
         ……
        }
    }
复制代码
```

这个前面已经提到过了，Observable并没有真正的去实现subscribe,而是把他转接给了subscribeActual()方法。

前面已经说过，Observable的实例是一个ObservableCreate对象，那么我们就到这个类里去看看subscribeActual()的实现。

```
	// 为了方便，顺便再看一眼构造函数
    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }
    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        CreateEmitter<T> parent = new CreateEmitter<T>(observer);
        observer.onSubscribe(parent);

        try {
            source.subscribe(parent);
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            parent.onError(ex);
        }
    }
复制代码
```

CreateEmitter 实现了之前提到的ObservableEmitter接口。这里有一句关键的代码：

```
observer.onSubscribe(parent);
复制代码
```

之前在看到Emitter的定义时，我们说缺少了onSubscribe方法，到这里就明白了。**onSubscribe并不是由主题（上游）主动发送的事件，而是有观察者（下游）自己调用的一个事件，只是为了方便获取Emitter的实例对象，准确的说应该是Disposable的实例对象，这样下游就可以控制上游了。**

接下来就更简单了，source 是ObservableOnSubscribe，按照之前的逻辑，调用其subscribe方法，给他一个ObservableEmitter对象实例，ObservableEmitter就会开始发送事件序列。这样，一旦开始订阅了，主题（上游）就开始发送事件了。也就是我们熟悉的onNext,onComplete,onError 方法真正的开始执行了。

接着看看CreateEmitter的实现。

```
public final class ObservableCreate<T> extends Observable<T> {
    final ObservableOnSubscribe<T> source;

    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        CreateEmitter<T> parent = new CreateEmitter<T>(observer);
        observer.onSubscribe(parent);
        ……
    }

    static final class CreateEmitter<T>
    extends AtomicReference<Disposable>
    implements ObservableEmitter<T>, Disposable {


        private static final long serialVersionUID = -3434801548987643227L;

        final Observer<? super T> observer;

        CreateEmitter(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(T t) {
            if (t == null) {
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                return;
            }
            if (!isDisposed()) {
                observer.onNext(t);
            }
        }

        @Override
        public void onError(Throwable t) {
            if (!tryOnError(t)) {
                RxJavaPlugins.onError(t);
            }
        }

        @Override
        public boolean tryOnError(Throwable t) {
            if (t == null) {
                t = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (!isDisposed()) {
                try {
                    observer.onError(t);
                } finally {
                    dispose();
                }
                return true;
            }
            return false;
        }

        @Override
        public void onComplete() {
            if (!isDisposed()) {
                try {
                    observer.onComplete();
                } finally {
                    dispose();
                }
            }
        }

        @Override
        public void setDisposable(Disposable d) {
            DisposableHelper.set(this, d);
        }

        @Override
        public void setCancellable(Cancellable c) {
            setDisposable(new CancellableDisposable(c));
        }

        @Override
        public ObservableEmitter<T> serialize() {
            return new SerializedEmitter<T>(this);
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }
    }
}
复制代码
```

- 他的构造函数，需要一个观察者的实例；
- 他实现了ObservableEmitter接口，并依次实现他的三个方法；
  - 在每一次的onNext事件中，他不再接受参数为null的类型，在事件序列没有中断的情况下会把主题（上游）发送的事件T原封不动的传递给观察者(下游）。
  - onComplete事件发生时，他也会通知下游，如果发生异常，则中断事件序列
  - onError 事件发生时，并没有直接传递到下游，而是在其内部处理
  - tryOnError 事件发生时，才会把某些特定类型的错误传递到下游。
- 他实现了Disposable接口，下游根据获取到的Emitter的实例对象，可以方便的获取事件序列的信息，甚至是可以主动关闭事件序列，及断开观察者模式中主题和观察者间的订阅关系。

#### RxJava 中对常规的观察者模式做了怎样调整，带来了什么好处？

最后再来简单说一下，RxJava中对常规的观察者模式做了怎样的调整，有什么值得借鉴的地方。大部分优点在上面已经提及了，这里就来总结一下。

- 观察者订阅主题后，主题才会开始发送事件
- RxJava中Observer通过onSubscribe获取了发送事件中的Disposable对象，这样他就可以主动的获取订阅关系中二者的状态，甚至是控制或者是中断事件序列的发送。在常规的观察者模式中，主题有权利添加订阅者，但也能是由他移除特定的订阅者，因为只有他持有所有订阅者的集合
- 抽象主题（上游）并没有直接控制onNext，onComplete,onError事件的发送，而是只关注Emitter 实例的发送，ObservableOnSubscribe接口监听ObservableEmitter对象的发送，一旦接受到此对象就会通过他开始发送具体的事件，这里可以有点观察者模式嵌套的意味。

------

好了，以上就是从观察者模式的角度出发，对RxJava的一次解读，有什么疏漏或理解错误的地方，欢迎读者指出，共同进步！

RxJava 并非是纯粹的观察者模式，甚至可以说并不是观察者模式。非得类比的话，我觉得更像是消息的拉取模式，需要获取消息的时候，并非是通过主动推送的方式，而是拉取。拉取成功后，通过反向触发rxjava回调函数，一层一层逆推回去，每一层函数对消息做变换、组合处理，最终送达给拉取者。

采用主动拉取的方式，而不是被动等待回调，方向触发回调函数，这样就很巧妙的避免了回调地狱的出现。