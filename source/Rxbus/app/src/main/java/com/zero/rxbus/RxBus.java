package com.zero.rxbus;


import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;


public class RxBus {

    private final FlowableProcessor<Object> mBus;

    private static class Holder{//单例
        private static final RxBus BUS = new RxBus();
    }


    private RxBus() {
        //toSerialized 转成一个线程安全的操作
        mBus = PublishProcessor.create().toSerialized();

    }


    public static RxBus get(){
        return Holder.BUS;
    }

    public void post(Object obj) {//发送一个event
        mBus.onNext(obj);
    }

    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Flowable<Object> toFlowable() {
        return mBus;
    }

    public boolean hasSubscribers() {
        return false;
    }

}
