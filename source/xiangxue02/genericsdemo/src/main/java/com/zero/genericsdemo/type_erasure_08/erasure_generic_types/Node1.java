package com.zero.genericsdemo.type_erasure_08.erasure_generic_types;

class Node11<T> {

    private T data;
    private Node11<T> next;

    public Node11(T data, Node11<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() { return data; }
}
