package com.enjoy.zero.libzhujie01.method;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;

public class GetAnnotatedExceptionTypes {

    public void test() throws NullPointerException, ClassNotFoundException {}

    public static void main(String[] args) throws Exception {
        Method method = GetAnnotatedExceptionTypes.class.getDeclaredMethod("test");
        /**
         * 返回一个 AnnotatedType 对象数组，这些对象表示使用类型来指定由该可执行文件表示的方法或构造函数声明的异常
         */
        AnnotatedType[] annotatedExceptionTypes = method.getAnnotatedExceptionTypes();
        for (AnnotatedType annotatedExceptionType : annotatedExceptionTypes) {
            // class java.lang.NullPointerException
            // class java.lang.ClassNotFoundException
            System.out.println(annotatedExceptionType.getType());
        }
    }

}
