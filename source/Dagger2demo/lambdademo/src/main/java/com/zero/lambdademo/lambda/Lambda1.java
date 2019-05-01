package com.zero.lambdademo.lambda;

public class Lambda1 {

    public static void main(String ... args){
            //TODO:
        //语法格式一：无参数，无返回值
        Runnable runnable = () -> System.out.println("Hello lambda 1");
        new Thread(runnable).start();

        //语法格式二：有一个参数，并且无返回值
        LambdaInterface02 lambdaInterface021 = (s) -> System.out.println(s);
        //语法格式三：若只有一个参数，小括号可以省略不写
        LambdaInterface02 lambdaInterface02 = s -> System.out.println(s);

        //有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
        LambdaInterface03 lambdaInterface03 = (s,i) ->{
            System.out.println("s: " + s);
            System.out.println("i: " + i);
        };

        lambdaInterface02.runTest("hello lambda 2");

//        lambdaInterface03.runTest("hello lambda 3", 3);
        lambda3Test(lambdaInterface03,"hello 3",3);

        LambdaInterface04 lambdaInterface04 = (s,i) -> {
            System.out.println("s: " + s);
            System.out.println("i: " + i);
            return  s + i;
        };
        System.out.println(lambdaInterface04.runTest("hello lambda 4",4));

        // C/C++ 函数指针
        LambdaInterface05 lambdaInterface05 = (s,i) -> {
            System.out.println("s: " + s);
            System.out.println("i: " + i);
            return  s + " _  " + i;
        };

        // :: Method reference(方法引用)

        // Stream API

        //Optional<T>



    }

    public static void lambda3Test(LambdaInterface03 lambdaInterface03,String s,int a){
      //TODO:
        lambdaInterface03.runTest(s,a);
    }

}
