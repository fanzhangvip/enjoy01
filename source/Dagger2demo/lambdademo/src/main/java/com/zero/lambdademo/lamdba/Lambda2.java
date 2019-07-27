package com.zero.lambdademo.lamdba;


import java.util.Comparator;
import java.util.function.Consumer;


//6种语法
public class Lambda2 {

   public  void test1() {
        //一、语法格式一：无参数，无返回值
        Runnable runnable =() -> System.out.println("Hello Lambda!");
   }

    public  void test2() {
        //语法格式二：有一个参数，并且无返回值
        Consumer consumer =  (x) -> System.out.println(x);

    }

    public  void test3() {
        //语法格式三：若只有一个参数，小括号可以省略不写
        Consumer consumer =  x-> System.out.println(x);
    }

    public  void test4() {
        //语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
        Comparator<Integer> com = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
    }

    public  void test5() {
        //语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
    }

    public  void test6() {
        //语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
        Comparator<Integer> com =  (Integer x, Integer y) -> Integer.compare(x, y);

        Comparator<Integer> com2 =  ( x,  y) -> Integer.compare(x, y);
    }
}
