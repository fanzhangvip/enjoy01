package com.zero.genericsdemo.generics_inheritance_subtypes_05;

public class Box<T> {
    private T t;

    public Box(T t){
        this.t = t;
    }

    public void set(T t){
        this.t = t;
    }

    public T get(){return t;}
}
