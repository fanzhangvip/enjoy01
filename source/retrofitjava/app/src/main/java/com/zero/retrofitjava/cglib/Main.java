package com.zero.retrofitjava.cglib;

import net.sf.cglib.proxy.Enhancer;

public class Main {

    public static void main(String[] args) {
        cglibProxy();
    }

    public static void cglibProxy() {
        //；CGLIB通过继承的方式进行代理，无论目标对象有没有实现接口都可以代理，但是无法处理final的情况
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(MycglibTest.class);
        enhancer.setCallback(new CglibProxy(new MycglibTest() {
            @Override
            public int method2(int count) {
                System.out.println("count: " +count);
                return count*2;
            }
        }));
        MycglibTest targetObject2=(MycglibTest)enhancer.create();
        System.out.println("result: " + targetObject2.method2(2));;
    }


}
