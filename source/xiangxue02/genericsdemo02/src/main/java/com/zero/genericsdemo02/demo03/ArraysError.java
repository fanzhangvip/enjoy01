package com.zero.genericsdemo02.demo03;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraysError {
    public static void main(String[] args) {
        List<Integer> li = Arrays.asList(1, 2, 3);
        li = new ArrayList<>();
        System.out.println(li.getClass());
//        li.add(1000);
        System.out.println("li: " + li.getClass());
        sumOfList(li);
    }

    public static double sumOfList(List<? extends Number> list) {
        System.out.println("sumOfList: " + list.getClass());
//        list.add(null);
        System.out.println("list: " + list.getClass());
        //副作用
//        list.add(1); //上限 in  只读，但这不是严格限制
        //反射调用 最新的不能调用
        // Caused by: java.lang.UnsupportedOperationException
        Class<?> clazz = list.getClass();
        System.out.println(clazz);
        try {
//            方法原型 boolean add(E e)
//            list = new ArrayList<Number>();
//            clazz = list.getClass();
            Method addMethod = clazz.getMethod("add", java.lang.Object.class);
            addMethod.setAccessible(true);
            addMethod.invoke(list,1000);
            System.out.println("list: " + list.toString());

        } catch (Exception e) {
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        double s = 0.0;
        for (Number n : list)
            s += n.doubleValue();
        return s;
    }
}
