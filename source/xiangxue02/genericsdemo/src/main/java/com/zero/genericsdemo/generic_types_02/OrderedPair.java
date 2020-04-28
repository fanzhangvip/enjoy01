package com.zero.genericsdemo.generic_types_02;

public class OrderedPair<K,V> implements Pair<K,V> {

    private K key;
    private V value;

    public OrderedPair(K key,V value){
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    public static void main(String[] args) {
        Pair<String, Integer> p1 = new OrderedPair<String, Integer>("Even", 8);
        Pair<String, String>  p2 = new OrderedPair<String, String>("hello", "world");

        //类型推断
        Pair<String, Integer> p11 = new OrderedPair<>("Even", 8);
        Pair<String, String>  p21 = new OrderedPair<>("hello", "world");

        //Parameterized Types（参数化类型）
        Pair<String, Box<Integer>> p = new OrderedPair<>("primes", new Box<Integer>());

    }
}
