package com.zero.genericsdemo.type_erasure_08.effects_type_erasure_brigde_methods;

import java.lang.reflect.Array;

public class Test {

    //编译java文件 javac MyNode.java Node.java
    //反编译 javap -c MyNode.class
    public static void main(String[] args) {
        /**
         *1. n.setData("Hello");导致在MyNode类的对象上执行setData(Object)方法（
         * MyNode类继承了Node的setData(Object)）。
         *2. 在setData(Object)的主体中，将n引用的对象的数据字段分配给String。
         *3.  可以访问通过mn引用的同一对象的数据字段，并且该字段应该是整数
         * （因为mn是MyNode，它是Node<Integer>）。
         *4. 尝试将String分配给Integer会导致Java编译器在分配时插入的强制转换导致ClassCastException。
         */
        MyNode mn = new MyNode(5);
        Node n = mn;            // A raw type - compiler throws an unchecked warning
        n.setData("Hello");
        Integer x = mn.data;    // Causes a ClassCastException to be thrown.

        Array array;
        int[] a;
        Node[] a1;

//        ArrayList<> 不是 数组 List
        //编译后
//        MyNode mn = new MyNode(5);
//        Node n = (MyNode)mn;         // A raw type - compiler throws an unchecked warning
//        n.setData("Hello");
//        Integer x = (String)mn.data; //


    }
}
