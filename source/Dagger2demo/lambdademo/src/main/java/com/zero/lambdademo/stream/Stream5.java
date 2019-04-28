package com.zero.lambdademo.stream;


import com.zero.lambdademo.lamdba.lamdba3.Person;

import java.util.Arrays;
import java.util.List;

/**
 * 归约习题
 *
 */
public class Stream5 {


    List<Person> persons = Arrays.asList(
            new Person("CJK",19,90),
            new Person("BODUO",20,80),
            new Person("JZ",21,70),
            new Person("anglebabby",18,60),
            new Person("huangxiaoming",5,50),
            new Person("Deer",18,99)
    );

    public  void test1() {
       // 问题：求所有人员学生的总分
        Integer all = persons.stream().map(Person::getScore).reduce((integer, integer2) -> integer + integer2).get();
    }



}
