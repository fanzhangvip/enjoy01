# 关于 RxJava 背压

## 前言

适用人群：

了解RXJava1.0、RxJava2.0基本用法，了解RxJava的一些常用操作符的小伙伴

**讲些什么：**

1. 了解背压是什么？
2. 了解阻塞形成的原因
3. 怎么去解决阻塞
4. RxJava对背压的处理

**为什么讲：**

学习RxJava2.0用法，熟悉背压机制（我差点信了自己）

**能讲好吗：**

被闹，又不是开车，这我哪知道啊！

*好吧不扯了，开车吧，不不，是开始学习下背压*

### 什么是背压

在RXJava2.0出来以后，就有很多人提’背压’这个东西，看了很多篇文章（其实都是一篇）依旧在云上下不来，到底什么是’背压’呢？

Google上搜索结果如下：

我的第一反应完全是懵逼的，你在说啥？到底想说啥？

其实仔细想想在RxJava中大概是这个意思，

> 被观察者发送消息太快以至于它的操作符或者订阅者不能及时处理相关的消息，从而操作消息的阻塞现象。

或者这样理解：

> 水坝在储水的过程中同样也向下游放水来保持坝内的水位，但是如果发大洪水，上游水量很大，而大坝处理能力有限，坝内的水位必定会上升甚至最终漫过大坝。

在RxJava中，阻塞不一定会出现异常，但是肯定会多少对系统的性能和功能造成一定的影响。

------

### 阻塞是怎么形成的？

正如上面所说的当下游不能快速处理上游发来的事件事件时，而造成的事件阻塞现象。

#### RxJava1.0

在**RxJava1.0**中，Observable是支持背压的，翻下源码，可以看到在Rxjava1.0中的Buffer的大小为16

**Observable.java 3551行**

```
 public final <B> Observable<List<T>> buffer(Observable<B> boundary) {
    return buffer(boundary, 16);
}
```

```
Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override
      public void call(Subscriber<? super Integer> subscriber) {
          for (int i = 0;; i++) {   //无限循环发事件
              subscriber.onNext(i);
          }
      }
  }).subscribe(new Action1<Integer>() {
              @Override
              public void call(Integer integer) {
                  Log.d(TAG, "" + integer);
              }
          });
```

效果如下：

由于缓存池buffer的大小为16，照理说程序运行肯定会抛出个我们熟悉的异常
**MissingBackpressureException**啊，但是结果却是令我们万分诧异，我的内存啊……。

*其实，原因很简单，由于RxJava观察者线程和被观察者处于同一线程，在同一个线程中，被观察需要等待观察者将事件处理完毕后才会继续发送下面的事件，所以上面才会出现这样的情况。*

那么，我们让他们处于不同的线程再试下

```
   Observable.create(new ObservableOnSubscribe<Integer>() {
    @Override
    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        for (int i = 0; ; i++) {   //无限循环发事件
            emitter.onNext(i);
        }
    }
}).subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())
  .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Log.d(TAG, "" + integer);
            }
        });
```

你要的MissingBackpressureException拿去不谢。

什么？你不信Rxjava1.0中的Buffer大小是16？你不信算了……
开玩笑，我是一个不以理服人的人吗？很显然，是的！

继续吃上面的大栗子：

```
Observable.create(new Observable.OnSubscribe<Integer>() {
       @Override
       public void call(Subscriber<? super Integer> subscriber) {
           for (int i = 0;i<16; i++) {   //短时间，发送16个事件
               subscriber.onNext(i);
           }
       }
   }).subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Action1<Integer>() {
               @Override
               public void call(Integer integer) {
                   Log.d(TAG, "" + integer);
               }
           });
```

*看看这是啥？这不就是下游对上游事件的相应吗?请注意，这次我们仅仅发送了16个事件，下游就能正常处理事件了，但是如果我们把循环值改成17，我们再来看看。*

```
Observable.create(new Observable.OnSubscribe<Integer>() {
      @Override
      public void call(Subscriber<? super Integer> subscriber) {
          for (int i = 0;i<17; i++) {//短时间，发送17个事件
              subscriber.onNext(i);
          }
      }
  }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Action1<Integer>() {
              @Override
              public void call(Integer integer) {
                  Log.d(TAG, "" + integer);
              }
          });
```

没错，没给你闹着玩，他真的又抛出大家喜欢的异常了，没毛病。

不知道，大家看完了RxJava1.0的背压，对它有什么看法？下面是我对RxJava1.0背压的一些理解

1. 首先，RxJava1.0的背压事件缓存池很小，只有16，不能够处理较大量的并发事件。
2. Rxjava1.0 中上游（被观察者）无法得知下游（观察者）对事件的处理能力和事件处理进度，只能把事件一股脑的抛给下游。
3. Rxjava1.0有很多事件不被能正确的背压，从而抛出MissingBackpressureException

------

#### RxJava2.0

RXJava2.0中Observable不再支持背压，多出了Flowable来支持背压操作

既然说Observable不再支持背压，那么我们随便搞应该就不会报哪个MissingBackpressureException了吧？

```
  Observable.create(new ObservableOnSubscribe<Integer>() {
    @Override
    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        for (int i = 0; ; i++) {   //无限循环发事件
            emitter.onNext(i);
        }
    }
}).subscribeOn(Schedulers.io())
  .observeOn(AndroidSchedulers.mainThread())
  .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Log.d(TAG, "" + integer);
            }
        });
```

上面的例子中我们创建了一个Observable（被观察者/上游）来发送无限循环的事件，观察者（下游）让下游来处理事件。

虽然说，不报异常了但是这内存也是看的我眼疼，崩溃也是正常现象啊，这么看来背压操作还是很有必要的啊，不然程序分分钟崩溃一次，怪我咯^&

### 如何解决阻塞

*当然，提到如何解决阻塞问题吗，大家肯定会首先想到“背压”啊，好吧背压策略确实很神奇，但是它也不是万能的啊，你不了解也不能乱用啊，它也是哥，不要太迷信它*

在上面的例子里，就上一个，不是其他的！在上面的例子里我们再RxJava2.0中是使用Observable一直发送死循环事件，由于下游没有任何**背压策略**，所以上游的每个事件，下游都要一一进行处理，所以程序的内存就一直开车，最后翻车也再说难免。

> 确实，是因为上游在短时间发送太多的事件，让下游来不及处理就造成了事件的阻塞，那么我们是否可以用一些自己的方法来解决这种阻塞呢？
>
> 使用背压啊！
>
> “你妹的，说好的用自己的办法呢？”

首先，我们分析阻塞形成的原因，无非是因为下面的原因啊：

1. 上游的水流过快（上游发送事件过快）
2. 上游水流量过大（上游发送事件过多）

总结来说就是短时间发送的事件过多，下游忙不过来！

**好吧，首先我们用第一种办法试下，让上游发送事件的速度慢点**

```
   //控制发送速度，减少内存消耗

Observable.create(new ObservableOnSubscribe<Integer>() {
    @Override
    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        for (int i = 0; ; i++) {   //无限循环发事件
            emitter.onNext(i);
            Thread.sleep(1000);
        }
    }
}).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Log.d(TAG, "" + integer);
            }
        });
```

这样来看，我的内存就稳定，老铁稳。

**那么，试试第二种方法，下游少接收点事件**

```
//定时取样
Observable.create(new ObservableOnSubscribe<Integer>() {
    @Override
    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        for (int i = 0; ; i++) {   //无限循环发事件
            emitter.onNext(i);

        }
    }
}).sample(1, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Log.d(TAG, "" + integer);
            }
        });
```

或者是用过滤操作符，过滤掉一些上游事件

```
    //过滤器 过滤操作
Observable.create(new ObservableOnSubscribe<Integer>() {
    @Override
    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        for (int i = 0; ; i++) {   //无限循环发事件
            emitter.onNext(i);

        }
    }
}).filter(new Predicate<Integer>() {
    @Override
    public boolean test(Integer integer) throws Exception {
        return integer % 100 == 0;

    }
})
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Log.d(TAG, "" + integer);
            }
        });
```

------

## 背压策略

上面唠唠叨叨说了那么多，基本上也给大家阐明了阻塞形成的原因和解决阻塞的方法，基本策略就是减少发送事件的频率和减少发送事件的数量。

But……


我们手动让上游发送事件的速度满下来貌似是不可取的，你想让上游的速度十多快呢？上游需要等多久呢？

还有……


我们依旧无法知道下游处理事件的能力，无法很好地处理阻塞的事件。

当然，你们肯定会说RxJava2.0不是很好地支了背压了吗？是的，确实比较好地对阻塞做了处理，咱们来看下吧。

在RxJava2.0中官方，推出了Flowable 和Subscriber用来支持背压，同样的去除了Observable对背压的支持，对的就像你上面看到的，Observable不再支持背压了，即使阻塞崩溃也不会抛出MissingBackpressureException

还是上代码看看，Flowable的用法吧。

```
Flowable.create(FlowableOnSubscribe<T> source, BackpressureStrategy mode)
```

创建Flowable会默认让传入一个FlowableOnSubscribe和一个BackpressureStrategy，FlowableOnSubscribe很好理解就是一个就是Flowable的一个被观察者源，而BackpressureStrategy就是Flowable提供的背压策略

有哪些策略还是上源码看下吧：

```
public enum BackpressureStrategy {
/**
 * OnNext events are written without any buffering or dropping.
 * Downstream has to deal with any overflow.
 * <p>Useful when one applies one of the custom-parameter onBackpressureXXX operators.
 */
MISSING,
/**
 * Signals a MissingBackpressureException in case the downstream can't keep up.
 */
ERROR,
/**
 * Buffers <em>all</em> onNext values until the downstream consumes it.
 */
BUFFER,
/**
 * Drops the most recent onNext value if the downstream can't keep up.
 */
DROP,
/**
 * Keeps only the latest onNext value, overwriting any previous value if the
 * downstream can't keep up.
 */
LATEST
}
```

**MISSING：**
如果流的速度无法保持同步，可能会抛出MissingBackpressureException或IllegalStateException。

**BUFFER**
上游不断的发出onNext请求，直到下游处理完，也就是和Observable一样了，缓存池无限大，最后直到程序崩溃

**ERROR**
会在下游跟不上速度时抛出MissingBackpressureException。

**DROP**
会在下游跟不上速度时把onNext的值丢弃。

**LATEST**
会一直保留最新的onNext的值，直到被下游消费掉。



------

先不看上面的策略，我们最起码先看看Flowable怎么用吧



```
Flowable.create(new FlowableOnSubscribe<Integer>() {
     @Override
     public void subscribe(FlowableEmitter<Integer> e) throws Exception {
         Log.d(TAG, "emit 1");
         emitter.onNext(1);
         Log.d(TAG, "emit 2");
         emitter.onNext(2);
         Log.d(TAG, "emit 3");
         emitter.onNext(3);
         Log.d(TAG, "emit complete");
         emitter.onComplete();

     }
 }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(new Subscriber<Integer>() {
             @Override
             public void onSubscribe(Subscription s) {

             }

             @Override
             public void onNext(Integer s) {
             Log.d(TAG, "onNext: " + integer);
             }

             @Override
             public void onError(Throwable t) {
             Log.d(TAG, "onError"+t);

             }

             @Override
             public void onComplete() {   
             Log.d(TAG, "onComplete");

             }
         });
```

上游 Flowable 构建FlowableEmitter用来发送上游事件，这里的背压策略我们采用ERROR，下游方法中出现了一个和原来

```
@Override
public void onSubscribe(Subscription s) {

               }
```

**Subscription.java**

```
public interface Subscription {

public void request(long n);

public void cancel();

}
```

这里需要重点说明一下，在Flowable中背压采取拉取响应的方式来进行水流控制，也就是说Subscription是控制上下游水流的主要方式，一般的，我们需要调用Subscription.request（）来传入一个下游**目前**能处理的事件的数量

那么，我们不传会怎么样？

**备注：这里上下游是在不同的线程里进行的，如果在同一个线程里，它也会抛出一个MissingBackpressureException，让你去设置 调用request（）方法**

咦，我上游发送的事件，下游一个没收到啊

那么也就是说上游不能发射事件，是因为你没有调用request方法，因为你不调用request（）上游不知道下游能处理事件的能力啊。

那么，也就是说我必须要调用request方法咯，那么我们就调用一下喽，官方说默认推荐使用Long.MAX_VALUE。

好吧，那么我们来试下吧，加上如下代码。

```
@Override
public void onSubscribe(Subscription s) {
           s.request(Long.MAX_VALUE);  //下游处理事件能力值
               }
```

咦，还真正常了啊。

那么，我们设置个2试试？

```
s.request(2);
```

**也就是说我们下游告诉上游我们能处理2个事件，这样上游就缓存池中取出了2个事件给发送给了下游。这点相比Rxjava1.0可以说是智能了很多，并不会一股脑的抛给下游而是又下游来主动拉取事件。**

#### ERROR

Flowable既然可以跑了，那么咱们就来试试背压吧，我们还是采用BackpressureStrategy.ERROR这个策略，如果下游处理不过来就抛出异常。

```
Flowable.create(new FlowableOnSubscribe<Integer>() {
        @Override
        public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
            for (int i = 0;i< 128; i++) {
                Log.d(TAG, "emit " + i);
                emitter.onNext(i);
            }
        }
    }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Integer>() {

                @Override
                public void onSubscribe(Subscription s) {
                    Log.d(TAG, "onSubscribe");

                }

                @Override
                public void onNext(Integer integer) {
                    Log.d(TAG, "onNext: " + integer);
                }

                @Override
                public void onError(Throwable t) {
                    Log.w(TAG, "onError: ", t);
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete");
                }
            });
```

我们，首先让上游发送128个事件，下游不做处理，恩好吧是正常的

现在我们把128改成129，怎么就异常了呢？

好吧，还是看源码吧

纳尼，原来Flowable的缓存池的最大大小是128吧，如果缓存池里有超过128个事件就会抛出异常，提示你去处理这些事件。

#### MISSING

那么，**MISSING**和ERROR有什么区别呢？

我们把缓存策略设置为BackpressureStrategy.MISSING试一下

```
Flowable.create(new FlowableOnSubscribe<Integer>() {
    @Override
    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
        for (int i = 0;i< 129; i++) {
            Log.d(TAG, "emit " + i);
            emitter.onNext(i);
        }
    }
}, BackpressureStrategy.MISSING).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                subscription = s;
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
```

结构还是一样的，不过这次很友好的提示你队列满了



```
io.reactivex.exceptions.MissingBackpressureException: Queue is full?!
```

下面是MISSING策略的备注：

```
/**
* OnNext events are written without any buffering or dropping.
* Downstream has to deal with any overflow.
* <p>Useful when one applies one of the custom-parameter onBackpressureXXX operators.
*/
```

也就是说，这种策略是不丢弃，不缓存的策略，那么我要你也没什么用啊…………

**BUFFER**

BUFFER是一个无限大的缓存池，也就是说我们可以往里面存储无限多的事件

```
Flowable.create(new FlowableOnSubscribe<Integer>() {
       @Override
       public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
           for (int i = 0;i<129 ; i++) {
               Log.d(TAG, "emit " + i);
               emitter.onNext(i);
           }
       }
   }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Subscriber<Integer>() {

               @Override
               public void onSubscribe(Subscription s) {
                   Log.d(TAG, "onSubscribe");

               }

               @Override
               public void onNext(Integer integer) {
                   Log.d(TAG, "onNext: " + integer);
               }

               @Override
               public void onError(Throwable t) {
                   Log.w(TAG, "onError: ", t);
               }

               @Override
               public void onComplete() {
                   Log.d(TAG, "onComplete");
               }
           });
```

但是，如果我们发送无数多的事件，同样要注意内存情况。

#### DROP和LATEST

**首先我们看下Drop**

```
Flowable.create(new FlowableOnSubscribe<Integer>() {
        @Override
        public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
            Log.d(TAG, "requested: " + emitter.requested());
            for (int i = 0; ; i++) {
                emitter.onNext(i);

            }
        }
    }, BackpressureStrategy.DROP).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Integer>() {

                @Override
                public void onSubscribe(Subscription s) {
                    Log.d(TAG, "onSubscribe");
                    subscription = s;

                }

                @Override
                public void onNext(Integer integer) {
                    Log.d(TAG, "onNext: " + integer);
                }

                @Override
                public void onError(Throwable t) {
                    Log.w(TAG, "onError: ", t);
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete");
                }
            });
```

我们把对象subscription放到外面，在外面调用request方法（让事件往下面传递，上面说过！！！），看下输出情况。
每次点击界面上的按钮触发下面的操作。

**假装有按钮**

```
subscription.request(64);
```

可以看到，刚进入时打印了当前的request大小，默认为缓存池的大小128.

当我们点击按钮触发subscription.request(64)时，它会从缓存池中取出64个事件发送给下游，当我们呢再次点击，它又取出了64条。但是，当我们第三次点击按钮时，看到了上面令我们诧异的结果。为什么呢？

> FLowable内部的默认的水缸大小为128, 因此, 它刚开始肯定会把0-127这128个事件保存起来, 然后丢弃掉其余的事件, 当我们request(64)的时候,下游便会处理掉这64个事件，当第二次请求时把水缸里剩余的64个事件清空, 那么上游水缸中又会重新装进新的128个事件。

也就是说，我先存128个，当这128个被清空后从新再装进128吧，那么中间这个过程中上游发送的事件，下游就给全部丢掉了。

**再来看下LATEST**

其实，LATEST和DROP很像都是存不下可就不存，丢事件呗，但是LATEST存储的是最后发送的事件。

```
Flowable.create(new FlowableOnSubscribe<Integer>() {
      @Override
      public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
          Log.d(TAG, "requested: " + emitter.requested());
          for (int i = 0;i<1000 ; i++) {
              emitter.onNext(i);

          }
      }
  }, BackpressureStrategy.LATEST).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Subscriber<Integer>() {

              @Override
              public void onSubscribe(Subscription s) {
                  Log.d(TAG, "onSubscribe");
                  subscription = s;

              }

              @Override
              public void onNext(Integer integer) {
                  Log.d(TAG, "onNext: " + integer);
              }

              @Override
              public void onError(Throwable t) {
                  Log.w(TAG, "onError: ", t);
              }

              @Override
              public void onComplete() {
                  Log.d(TAG, "onComplete");
              }
          });
```

仅仅把策略改成了LATEST,把事件循环设成了1000（因为不设置成固定值，将无法比较和Drop的区别），因为它只保存最后发送的事件

可以看到，它把最后发送的999给存储了下来，至于为什么第三次点击按钮只打印了一个，其实也很简单，由于其他事件早已经发送出去了，事件发送的事件完成的比较快，也就是说当我们第三点击按钮的时候上游的发送事件都已经完成了，所以系统就给我们了最后发送的事件，如果当我们点击按钮的时候事件还没有发送完成，那么他会把正在发送的127个事件和最后的事件发送给下游。

什么？你又不信?
在发送的for循环里添加一个延时

```
Thread.sleep(10);
```

看最后的结果

### 总结

你以为我会老老实实给你写总结？

> 首先我们需要明白阻塞形成的原因，再想办法和解决阻塞。其实从RxJava2.0背压的策略来看真的比RxJava1.0提升了很多，但是真的没有我们想象的那么完美，因为毕竟丢失的事件不一定使我们想要丢失事件，背压不一定就是你的救命稻草，所以正如上面所演示的根据自己的实际需求制定自己的防阻塞策略很关键。

是的，我写了,我老老实实写的！