package com.zero.genericsdemo.type_erasure_08.erasure_generic_types;

public class Test {

    public static void main(String[] args) {

    }
}

/**
 * 类型擦除过程中，Java编译器将擦除所有类型参数，
 * 如果类型参数是有界的，则将每个参数替换为其第一个边界；如果类型参数是无界的，则将其替换为Object
 * @param <T>
 */
class Node<T> {

    private T data;
    private Node<T> next;

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() { return data; }
}

/**
 * Java编译器将绑定类型参数T替换为第一个绑定类Comparable
 * @param <T>
 */
class Node1<T extends Comparable<T>> {

    private T data;
    private Node1<T> next;

    public Node1(T data, Node1<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() { return data; }
    // ...
}

