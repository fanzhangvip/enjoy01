package com.enjoy.zero.libzhujie01.method;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class GetModifiers {

    protected void test() {}

    public static void main(String[] args) throws Exception {
        Method method = GetModifiers.class.getDeclaredMethod("test");
        /**
         * getModifiers()
         *
         * 返回修饰该方法对象修饰符的整数形式，使用 Modifier 类对其进行解码
         */
        // public
        System.out.println(Modifier.toString(method.getModifiers()));
        /**
         * getName()
         *
         * 返回方法对象名称
         */
        // test
        System.out.println(method.getName());
    }

}
