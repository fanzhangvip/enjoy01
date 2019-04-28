package com.zero.lambdademo.stream;


import java.util.stream.Collectors;
import java.util.stream.Stream;


//收集练习
public class Stream6 {

    public void test1() {

        Integer[] ary = {1,2,3,4,5,6,7,8,9,10};

        //1.使用Collectors求ary的最大值
        Integer integer = Stream.of(ary).collect(Collectors.maxBy(Integer::compare)).get();
        System.out.println(integer);

        //2.使用Collectors求ary的平均值
        Double collect = Stream.of(ary).collect(Collectors.averagingDouble(Integer::valueOf));
        System.out.println(collect);

        //3.使用Collectors.joining输出”1:2:3:4:5:6:7:8:9:10”
        String collect1 = Stream.of(ary).map(x -> x.toString()).collect(Collectors.joining(":"));
         Stream.of(ary).map(x -> x.toString()).collect(Collectors.joining(":"));

        //4.使用Collectors.reducing求ary数组的总和
        //Stream.of(ary).collect(Collectors.reducing((x,y)->x+y)).get();
        Stream.of(ary).collect(Collectors.reducing(Math::addExact)).get();

        //5.使用Collectors.counting求ary个数
        Long collect2 = Stream.of(ary).collect(Collectors.counting());
        System.out.println(collect2);
    }

}
