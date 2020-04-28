package com.zero.genericsdemo.wildcards_07.lower_bounded_wildcards;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//Lower Bounded Wildcards（下界通配符）
public class Test {

    /**
     * 上限通配符”部分显示，上限通配符将未知类型限制为特定类型或该类型的子类型，
     * 并使用extends关键字表示。以类似的方式，
     *
     * 下限通配符将未知类型限制为特定类型或该类型的超类型
     * 下限通配符使用通配符（?）表示，后跟super关键字，后跟下限, 如：<? super A>
     *
     *  注意：你可以为通配符指定一个上限，也可以指定一个下限，但不能同时指定两者。
     */

    public static void main(String[] args) {

        List<Integer> integers = new ArrayList<>(10);
        addNumbers(integers);
        addNumbers1(integers);

        List<Object> numbers = new ArrayList<>(10);
        //List<? super Integer> 匹配作为Integer的超类型的任何类型的列表
        addNumbers(numbers);
        //List<Integer>只匹配Integer类型的列表
        //addNumbers1(numbers);//报错

    }

    /**
     * 假设你要编写一个将Integer对象放入列表的方法。为了最大程度地提高灵活性，
     * 你希望该方法可用于List<Integer>，List<Number>和List<Object>（可以容纳Integer值的任何内容）。
     * 要编写对Integer的列表和Integer的超类型（如Integer、Number和Object）的方法，
     * 你可以指定List<? super Integer>。术语List<Integer>比List<? super Integer>的限制性更强，
     * 因为前者只匹配Integer类型的列表，而后者则匹配作为Integer的超类型的任何类型的列表。
     */

    //SC Consumer消费者 list理解为消费者 添加数据，从它那获取数据
    public static void addNumbers(List<? super Integer> list) {

        //PESC原则
        //这就是Producer extends原则
        //当只想从集合中获取元素，请把这个集合看成生产者，请使用<? extends T>
        //这就是Consumer super原则
        //当你仅仅想增加元素到集合，把这个集合看成消费者，请使用<? super T>。

        //下限 只写， 读取类型信息丢失
//        Integer tmp = list.get(0);
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
    }

    public static void addNumbers1(List<Integer> list) {
        Integer tmp = list.get(0);
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
    }
}
