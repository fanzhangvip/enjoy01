package com.zero.retrofitjava.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    private Object target; //被代理类的引用

    public CglibProxy(Object o){
        this.target = o;
    }


    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        System.out.println("cglib执行流程: " );
        return methodProxy.invoke(target,args);
    }
}
