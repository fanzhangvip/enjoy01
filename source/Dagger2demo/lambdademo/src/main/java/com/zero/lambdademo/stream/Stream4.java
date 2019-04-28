package com.zero.lambdademo.stream;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查找与匹配习题
 */
public class Stream4 {



    public  void test1() {
       Integer[] ary = {1,2,3,4,5,6,7,8,9,10};
//        1.检查是否所有元素都小于10
        boolean b = Arrays.stream(ary).allMatch(x -> x < 10);

//        2.检查是否至少有一个元素小于2
        boolean b1 = Arrays.stream(ary).anyMatch(x -> x < 2);

//        3.检查是不是没一个元素大于10
        boolean b2 = Arrays.stream(ary).anyMatch(x -> x > 10);

//        4.返回第一个元素
        Integer integer = Arrays.stream(ary).findFirst().get();

//        5.ary 有多少个元素
        long count = Arrays.stream(ary).count();

//        6.求ary里面最大值
        Arrays.stream(ary).max(Integer::compare).get();

//        7.求ary里面最小值
        Arrays.stream(ary).min(Integer::compare).get();

//        8.循环遍历打出ary 里面偶数
        Arrays.stream(ary).filter(x->x%2==0).forEach(System.out::println);

    }



}
