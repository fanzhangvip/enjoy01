package com.zero.genericsdemo.wildcards_07.upper_bounded_wildcards;

import com.zero.genericsdemo.wildcards_07.Foo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//Upper Bounded Wildcards（上限通配符）
public class Test {

    public static void main(String[] args) {

        try {
            reflectionTest();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Integer> li = Arrays.asList(1, 2, 3);
        //匹配Number类型的列表或其任何子类
        System.out.println("sum = " + sumOfList(li));
        //只匹配Number类型的列表
//        System.out.println("sum = " + sumofList1(li));
        List<Double> ld = Arrays.asList(1.2, 2.3, 3.5);
        System.out.println("sum = " + sumOfList(ld));


    }

    public static void reflectionTest() throws Exception{
        //声明Integer的泛型ArrayList对象，并放入Integer实例
        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(new Integer(5));
        intList.add(7);
        //定义需要被加入list对象的字符串
        String str = "abc";
        //通过反射获取list对象运行时的add方法，此时该方法已经被擦除泛型
        Method m = intList.getClass().getMethod("add", java.lang.Object.class);
        //调用泛型方法加入String对象
        m.invoke(intList, str);
        //打印结果：[5, 7, abc]
        System.out.println(intList);
        for(Object obj:intList)
            System.out.println(obj.getClass());
    }

    public static void process(List<? extends Foo> list){
        for(Foo f: list){
            f.f1();
            System.out.println(f.getFooSize());
        }
    }

    //要编写在Number类型的列表和Number的子类型（如Integer、Double和Float）上工作的方法，
    // 你会指定List<? extends Number>。术语List<Number>比List<? extends Number>更有限制性，
    // 因为前者只匹配Number类型的列表，而后者匹配Number类型的列表或其任何子类

    public static double sumofList1(List<Number> list){
        double s = 0.0;
        for (Number n : list)
            s += n.doubleValue();
        return s;
    }

    //更灵活 PE list Producer生产者 可以从它里面拿数据，但是你没办法往里面添加数据
    public static double sumOfList(List<? extends Number> list) {
        //副作用
//        list.add(1); //上限 in  只读，但这不是严格限制
        //反射调用 最新的不能调用
        // Caused by: java.lang.UnsupportedOperationException
        Class<?> clazz = list.getClass();
        System.out.println(clazz);
        try {
//            方法原型 boolean add(E e)
//            list = new ArrayList<Number>();
//            clazz = list.getClass();
            Method addMethod = clazz.getMethod("add", java.lang.Object.class);
            addMethod.setAccessible(true);
            addMethod.invoke(list,100);
            System.out.println(list.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        double s = 0.0;
        for (Number n : list)
            s += n.doubleValue();
        return s;
    }
}
