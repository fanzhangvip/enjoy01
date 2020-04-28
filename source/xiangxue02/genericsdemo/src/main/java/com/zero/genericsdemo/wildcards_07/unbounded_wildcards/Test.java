package com.zero.genericsdemo.wildcards_07.unbounded_wildcards;

import java.util.Arrays;
import java.util.List;

/**
 * 无界通配符类型使用通配符（?）来指定，例如List<?>。
 * 这就是所谓的未知类型的列表。有两种情况下，无界通配符是一种有用的方法
 */
//Unbounded Wildcards（无限通配符）
public class Test {

    public static void main(String[] args) {

        List<String> stringList = Arrays.asList("Zero","Lance","av");
        //不能调用
        //printList的目标是打印任何类型的列表，但未能实现该目标（它仅打印Object实例的列表）；
        // 它不能打印List<Integer>、List<String>、List<Double>等，
        // 因为它们不是List<Object>的子类型
//        printList(stringList);// 报错

        //通用类型实现
        printList1(stringList);//ok

    }

    public static void printList(List<Object> list) {
        for (Object elem : list)
            System.out.println(elem + " ");
        System.out.println();
    }

    /**
     * 1. 如果你正在编写一个可以使用Object类中提供的功能实现的方法。
     * 2. 当代码使用通用类中不依赖于类型参数的方法时。例如，List.size或List.clear。
     * 事实上，Class<?>之所以这么经常使用，是因为Class<T>中的大部分方法都不依赖于T。
     * @param list
     */
    //? 退化了，不能使用List中任何依赖类型参数【T】的方法
    public static void printList1(List<?> list) {
        //副作用
//        list.add("sss")
        list.size();
        list.add(null);
        list.get(0);
        list.contains(2);
//        list.add(2);
        for (Object elem: list)
            System.out.print(elem + " ");
        System.out.println();
    }

}
