package com.zero.lambdademo.lamdba.lamdba3;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Lambda3 {
    public void test1() {
        //实现接口，比较两个的age大小
        Lamdba3Interface<Person> lamdba3Interface =(p1,p2) -> p1.getAge().compareTo(p2.getAge());
    }

    public void test2() {
        //实现接口，比较两个的score大小
        Lamdba3Interface<Person> lamdba3Interface =(p1,p2) -> p1.getScore().compareTo(p2.getScore());

    }

    public void test3() {

        //使用内置的compare接口对数组排序
        Person p1 = new Person("deer",18,90);
        Person p2 = new Person("依娜",20,99);
        Person p3 = new Person("乐乐",95,59);
        List<Person> list = new ArrayList();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        Collections.sort(list,(person1,person2)-> person1.getScore().compareTo(person2.getScore()));



    }
}
