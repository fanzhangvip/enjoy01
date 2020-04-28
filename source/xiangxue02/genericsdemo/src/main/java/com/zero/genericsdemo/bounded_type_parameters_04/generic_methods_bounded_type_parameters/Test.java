package com.zero.genericsdemo.bounded_type_parameters_04.generic_methods_bounded_type_parameters;

public class Test {

    public static <T> int countGreaterThan(T[] anArray, T elem) {
        int count = 0;
//        for (T e : anArray)
//            //方法的实现很简单，但是不能编译，因为大于运算符（>）仅适用于基本类型，
//            // 例如short、int、double、long、float、byte和char。你不能使用>运算符比较对象。
//            // 要解决此问题，请使用Comparable<T>接口限定的类型参数
//            if (e > elem)  // compiler error
//                ++count;
        return count;
    }

    //限定为Comparable及其子类
    public static <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
        int count = 0;
        for (T e : anArray)
            if (e.compareTo(elem) > 0)
                ++count;
        return count;
    }

}
