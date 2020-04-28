package com.zero.genericsdemo.type_erasure_08.non_reifiable_types;

import java.util.Arrays;
import java.util.List;

public class ArrayBuilder {


    public static <T> void addToList (List<T> listArg, T... elements) {
        for (T x : elements) {
            listArg.add(x);
        }
    }

    public static void faultyMethod(List<String>... l) {
        //，它将varargs形式参数转换为数组。但是，Java编程语言不允许创建参数化类型的数组。
        // 在方法ArrayBuilder.addToList中，
        // 编译器将varargs形式参数T ...元素转换为形式参数T[]元素，即数组。
        // 但是，由于类型擦除，编译器将varargs形式参数转换为Object[]元素。
        // 因此，存在堆污染的可能性
        Object[] objectArray = l;     // Valid
        //该语句可能会导致堆污染。可以将与varargs形式参数l的参数化类型匹配的值分配给变量objectArray，
        // 从而可以将其分配给l。但是，编译器不会在此语句上生成未经检查的警告。
        // 当编译器将varargs形式参数List<String>... l转换为形式参数List[] l时，已经生成了警告。
        // 此声明有效；变量l具有类型List[]，它是Object[]的子类型。
        //因此，如果将任何类型的List对象分配给objectArray数组的任何数组组件，
        // 则编译器不会发出警告或错误，如以下语句所示
        objectArray[0] = Arrays.asList(42);
        //存储在变量l的第一个数组组件中的对象的类型为List<Integer>，
        // 但是此语句期望使用类型为List<String>的对象
        String s = l[0].get(0);       // ClassCastException thrown here
    }

}

