package com.zero.lib.java;

import java.util.ArrayList;

public class Fanxing {

    static class Fruit{}//定义水果类

    static class Apple extends Fruit{}//苹果肯定是一种水果

    static class Banana extends Fruit{}

    static class Panzi<T>{
        private T item;
        public Panzi(T t){
            item = t;
        }

        public void set(T t){
            item = t;
        }

        public T get(){
            return item;
        }
    }

    public void test(){
        //装苹果的盘子 是一个装水果的盘子
        // ? extends 上界通配符
        Panzi<? extends Fruit> panzi = new Panzi<Apple>(new Apple());
        Panzi<? extends Fruit> panzi1 = new Panzi<Banana>(new Banana());

        //副作用? extends Fruit == Fruit
//        panzi.set(new Apple());//泛型 伪泛型
//        panzi.set(new Fruit());
        //占位符 Fruit
        Fruit fruit = panzi.get();//取
        Panzi<? super Fruit> panzi2 = new Panzi<Fruit>(new Banana());

        panzi2.set(new Banana());//存放

        Object fruit1 = panzi2.get();
        // PECS Producer extends Consumer Super
        //泛型 擦除 上下界
        //T <? super B> 下界  ？元素类型B 基类 没有统一的基类 Object
        //T <? extend B> 上界 ? B 是所以元素的基类



    }

//    interface  Source<? extends T>{//声明处形变
//        T getT();
//    }
//
//    void test(Source<String> str){
//        Source<Object> obj = str;
//    }



    public static void main(String ... args){
            //TODO:




        //集合
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("fasd");
//        System.out.println(arrayList.get(0));
//
//        String str = "Zero";
//        Object obj = str;
//        ArrayList<Object> arrayList1 = new ArrayList<>();
//        arrayList1 = arrayList;

//        arrayList.add(1);

    }

}
