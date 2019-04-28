package com.zero.zhuruyilai;

/**
 * 而 Bike 实现了接口，它们被称为具体。
 */
public class Bike implements Driveable {
    @Override
    public void drive() {
        System.out.println("Bike drive");
    }
}
