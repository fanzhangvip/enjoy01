package com.zero.genericsdemo.type_inference_06;

import java.io.Serializable;
import java.util.ArrayList;

//Type Inference（类型推断）
public class Test {

    static <T> T pick(T a1, T a2) { return a2; }

    public static void main(String[] args) {
        //public final class String
        //    implements java.io.Serializable, Comparable<String>, CharSequence
//        public class ArrayList<E> extends AbstractList<E>
//                implements List<E>, RandomAccess, Cloneable, java.io.Serializable

//        类型推断是Java编译器查看每个方法调用和相应声明以确定使调用适用的类型参数的能力。
//        推断算法确定参数的类型，以及确定结果是否被分配或返回的类型（如果有）。
//        最后，推断算法尝试找到与所有参数一起使用的最具体的类型
        Serializable s = Test.<Serializable>pick("d", new ArrayList<String>());
    }
}
