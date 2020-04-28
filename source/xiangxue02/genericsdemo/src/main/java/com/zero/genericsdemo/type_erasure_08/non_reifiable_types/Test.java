package com.zero.genericsdemo.type_erasure_08.non_reifiable_types;

import java.util.ArrayList;
import java.util.List;

//Non-Reifiable Types（不可具体化类型）
/**
 * “类型擦除”部分讨论了编译器删除与类型参数和类型参数有关的信息的过程。
 * 类型擦除的结果与变量参数（也称为varargs）方法有关，
 * 这些方法的varargs形式参数具有不可更改的类型
 */
public class Test {
    /**
     * Non-Reifiable Types（不可具体化类型）
     * 具体化类型是其类型信息在运行时完全可用的类型。这包括基本类型，非通用类型，原始（raw）类型以及未绑定通配符的调用。
     * 非具体化类型是指在编译时通过类型擦除法删除了信息的类型（对通用类型的调用没有被定义为非绑定通配符）。
     * 非具体化类型在运行时并不具备所有的信息。非具体化类型的例子是List<String>和List<Number>；
     * JVM在运行时无法区分这些类型。正如对通用类型的限制中所示，在某些情况下，非具体化类型不能使用：
     * 例如，在instanceof表达式中，或者作为数组中的元素
     * @param args
     */
    public static void main(String[] args) {

        //第一组
        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Object> list3 = new ArrayList<>();
        //JVM在运行时无法区分这些类型
        list3 = list3;
        System.out.println("result: " + (list1.equals(list2)));

        //第二组
//        if(list1 instanceof List<Object>){//报错
//
//        }

        //第三组
        //没有泛型数组
//        Box<String>[] arr1 = new Box<String>[]{new Box<String>()};
        Box<?>[] arr2 = new Box<?>[]{new Box()};



    }
}

class Box<T>{
    private T t;
}
