package com.zero.genericsdemo.why_use_generics_01;

import java.util.ArrayList;
import java.util.List;

/**
 * Why Use Generics? 为什么使用泛型
 */
public class Generics01 {

    public static void main(String[] args) {
        demo01();
        demo02();
    }

    static void demo01(){
        //不使用泛型
        List list = new ArrayList();
        list.add("hello");
        String s = (String) list.get(0);//需要强转（向下转型）
    }

    static void demo02(){
        //使用泛型
        List<String> list = new ArrayList<String>();
        list.add("hello");
        String s = list.get(0);   //不需要强转
    }

}
