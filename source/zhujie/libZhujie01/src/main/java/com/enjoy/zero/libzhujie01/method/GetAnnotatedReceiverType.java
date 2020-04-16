package com.enjoy.zero.libzhujie01.method;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;

public class GetAnnotatedReceiverType {

    public void test() {}

    public static void main(String[] args) throws Exception {
        Method method = GetAnnotatedReceiverType.class.getDeclaredMethod("test");
        /**
         * 返回一个 AnnotatedType 对象，该对象表示使用类型来指定该可执行对象表示的方法或构造函数的接收者类型
         */
        AnnotatedType annotatedReceiverType = method.getAnnotatedReceiverType();
        //class com.enjoy.zero.libzhujie01.method.GetAnnotatedReceiverType
        System.out.println(annotatedReceiverType.getType());
    }
}
