package com.zero.genericsdemo.generic_methods_03;

import com.zero.genericsdemo.generic_types_02.OrderedPair;
import com.zero.genericsdemo.generic_types_02.Pair;

public class Util {

    /**
     * 方法泛型
     * @param p1
     * @param p2
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) &&
                p1.getValue().equals(p2.getValue());
    }

    public static void main(String[] args) {
        Pair<Integer, String> p1 = new OrderedPair<>(1, "apple");
        Pair<Integer, String> p2 = new OrderedPair<>(2, "pear");
        //完整语法
        boolean same = Util.<Integer, String>compare(p1, p2);
        //类型推断
        Util.compare(p1, p2);
        System.out.println("结果same: " + same);
    }
}