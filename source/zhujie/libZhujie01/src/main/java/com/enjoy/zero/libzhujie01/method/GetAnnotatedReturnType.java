package com.enjoy.zero.libzhujie01.method;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;

public class GetAnnotatedReturnType {


    public String test() {
        return null;
    }

    public static void main(String[] args) throws Exception {
        Method method = GetAnnotatedReturnType.class.getDeclaredMethod("test");
        /**
         * 返回一个 AnnotatedType 对象，该对象表示使用类型来指定由该可执行文件表示的方法或构造函数的返回类型
         */
        AnnotatedType annotatedReturnType = method.getAnnotatedReturnType();
        //class java.lang.String
        System.out.println(annotatedReturnType.getType());
    }

}
