package com.zero.reflectdemo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Client {

    public static void main(String ... args){
            //TODO: 静态代理
        IShop zero = new Zero();
//        zero.buy();//没办法直接这么调用
        //创建代理
        IShop av = new AV(zero);
//        av.buy();

        //动态代理
        IShop proxyShop = (IShop) Proxy.newProxyInstance(
                zero.getClass().getClassLoader(),
                zero.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                        if("buy".equals(method.getName())){
                            System.out.println("动态代理");
                            Object result = method.invoke(zero,args);
                            return result;
                        }
                        return null;
                    }
                }
        );//返回一个动态代理对象

        proxyShop.buy();

    }
}
