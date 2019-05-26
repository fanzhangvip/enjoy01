package com.zero.reflectdemo.proxy;

public class Zero implements IShop {
    @Override
    public void buy() {
        System.out.println("购买..." );
    }
}
