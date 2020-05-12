package com.zero.genericsdemo02.demo04;

public class TestClass<T extends ClassTwo & ClassOne & ClassThree> {

    private T mT;
    public void setT(T t){
        mT = t;
    }
}
