package com.enjoy.zero.libzhujie01.type.genericArrayType;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * GenericArrayType
 * 范型数组,组成数组的元素中有范型则实现了该接口; 它的组成元素是ParameterizedType或TypeVariable类型,它只有一个方法:
 *
 * Type getGenericComponentType(): 返回数组的组成对象, 即被JVM编译后实际的对象
 * @param <T>
 */
public class TestType <T> {
    public static void main(String[] args) throws Exception {
        Method method = Test.class.getDeclaredMethods()[0];
        // public void com.test.Test.show(java.util.List[],java.lang.Object[],java.util.List,java.lang.String[],int[])
        System.out.println(method);
        Type[] types = method.getGenericParameterTypes();  // 这是 Method 中的方法
        for (Type type : types) {
            System.out.println(type instanceof GenericArrayType);
        }
    }
}
class Test<T> {
    /**
     * 第一个参数List<String>[]的组成元素List<String>是ParameterizedType类型, 打印结果为true
     * 第二个参数T[]的组成元素T是TypeVariable类型, 打印结果为true
     * 第三个参数List<String>不是数组, 打印结果为false
     * 第四个参数String[]的组成元素String是普通对象, 没有范型, 打印结果为false
     * 第五个参数int[] pTypeArray的组成元素int是原生类型, 也没有范型, 打印结果为false
     * @param pTypeArray
     * @param vTypeArray
     * @param list
     * @param strings
     * @param ints
     */
    public void show(List<String>[] pTypeArray, T[] vTypeArray, List<String> list, String[] strings, int[] ints) {
    }
}