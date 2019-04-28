package com.zero.lambdademo.lamdba;


import java.util.Comparator;
import java.util.TreeSet;


//Lamdba入门
public class Lambda1 {


    public void test1() {

        //匿名内部类
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.print("hello enjoy");
            }
        };

        //lambda
        Runnable r2 = ()->System.out.print("hello enjoy");
    }


    public void test2() {
//匿名内部类
        TreeSet<String> ts = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Long.compare(o1.length(),o2.length());
            }
        });

//lambda
        TreeSet<String> ts2 = new TreeSet<>((o1,o2)-> Long.compare(o1.length(),o2.length()));
    }


}
