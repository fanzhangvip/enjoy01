package com.zero.lambdademo.stream;


import com.zero.lambdademo.lamdba.lamdba3.Person;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//收集练习
public class Stream7 {

    List<Person> persons = Arrays.asList(
            new Person("CJK",19,90),
            new Person("BODUO",20,80),
            new Person("JZ",21,70),
            new Person("anglebabby",18,60),
            new Person("huangxiaoming",5,50),
            new Person("Deer",18,99)
    );

    public void test1() {
        //1.取出Person对象的所有名字，放到List集合中
        List<String> collect2 = persons.stream().map(Person::getName).collect(Collectors.toList());
    }

    public void test2() {
       // 2.求Person对象集合的分数的平均分、总分、最高分，最低分，分数的个数
        IntSummaryStatistics collect = persons.stream().collect(Collectors.summarizingInt(Person::getScore));
        System.out.println(collect);
    }

    public void test3() {
        //3.根据成绩分组，及格的放一组，不及格的放另外一组
        Map<Boolean, List<Person>> collect1 = persons.stream().collect(Collectors.partitioningBy(person -> person.getScore() >= 60));
        System.out.println(collect1);
    }

}
