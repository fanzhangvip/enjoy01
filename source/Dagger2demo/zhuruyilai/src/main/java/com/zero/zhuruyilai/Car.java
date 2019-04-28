package com.zero.zhuruyilai;

/**
 * 而 Car实现了接口，它们被称为具体。
 */
public class Car implements Driveable {
    @Override
    public void drive() {
        System.out.println("Car drive.");
    }
}
