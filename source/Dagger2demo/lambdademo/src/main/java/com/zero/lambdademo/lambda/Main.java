package com.zero.lambdademo.lambda;

public class Main {

    public static void main(String ... args){
            //TODO:
        int a = 124;
        String str = "";
        // 把一块代码 赋值给 变量
        //匿名的内部类 < java 7
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        };
        // > java 7 lambda /闭包 方法(函数) 是第一等公民
        Runnable runnable1 = () -> {
            System.out.println("hello lambda");
        };

        new Thread(runnable).start();
        new Thread(runnable1).start();
    }
}
