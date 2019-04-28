package com.zero.lambdademo.stream;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 中间操作习题
 */
public class Stream3 {



    public  void test1() {
        //1.有个数组Integer[] ary = {1,2,3,4,5,6,7,8,9,10} ，取出中间的第三到第五个元素
        Integer[] ary = {1,2,3,4,5,6,7,8,9,10};

        List<Integer> collect = Arrays.stream(ary).skip(2).limit(3).collect(Collectors.toList());
    }


    public  void test2() {
        //2.有个数组Integer[] ary = {1,2,2,3,4,5,6,6,7,8,8,9,10}，取出里面的偶数，并去除重复
        Integer[] ary = {1,2,2,3,4,5,6,6,7,8,8,9,10};

        List<Integer> list = Arrays.stream(ary).filter(x -> x % 2 == 0).distinct().collect(Collectors.toList());
    }

    public  void test3() {
        //3.有个二维数组，要求把数组组合成一个一维数组，并排序（1，2，3，4，5……12）
        Integer[][] ary = {{3,8,4,7,5}, {9,1,6,2}, {0,10,12,11} };

        Arrays.stream(ary).flatMap(item->Arrays.stream(item)).sorted().forEach(System.out::println);
    }
}
