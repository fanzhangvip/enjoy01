package com.zero.reflectdemo.proxy;

public class AV implements IShop {
    private IShop mShop;

    public AV(IShop shop){
        mShop = shop;
    }

    @Override
    public void buy() {
        //帮Zero老师买东西
        System.out.println("AV老师代购");
        mShop.buy();
    }
}
