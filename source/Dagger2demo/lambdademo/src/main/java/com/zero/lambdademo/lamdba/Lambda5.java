package com.zero.lambdademo.lamdba;


import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;


//方法引用
public class Lambda5 {

    //对象 :: 实例方法
   public  void test1() {

        Consumer<String>  c0 = new Consumer<String>() {
            @Override
            public void accept(String x) {
                System.out.println(x);
            }
        };

        //lambda方式
        Consumer<String>  c1 = x->System.out.println(x);

        //对象 :: 实例方法
        Consumer<String> c2 = System.out::print;
   }

    //类 :: 静态方法
    public  void test2() {

        BinaryOperator<Double> bo0 = new BinaryOperator<Double>() {
            @Override
            public Double apply(Double n1, Double n2) {
                return Math.pow(n1,n2);
            }
        };

        BinaryOperator<Double> bo1 = (n1, n2) ->Math.pow(n1,n2);

        //类 :: 静态方法
        BinaryOperator<Double> bo2 = Math::pow;

    }

    //类 ::  实例方法
    public  void test3() {
        BiPredicate<String,String> bp0 =new BiPredicate<String, String>() {
            @Override
            public boolean test(String str1, String str2) {
                return str1.equals(str2);
            }
        };

        BiPredicate<String,String> bp1 = (str1, str2) ->str1.equals(str2);

        //类 ::  实例方法
        BiPredicate<String,String> bp2 = String::equals;
    }


}
