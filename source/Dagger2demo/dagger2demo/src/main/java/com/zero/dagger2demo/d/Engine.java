package com.zero.dagger2demo.d;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

public class Engine {


    /**
     * 用于自定义注解，我能可以通过@Scope自定义的注解来限定注解作用域，实现局部的单例
     * 1. @Scope定义一个CarScope注解
     */
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CarScope {
    }

    private String name;

    Engine(String name) {
        this.name = name;
        System.out.println("Engine create: " + name);
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                '}';
    }

    public void run() {
        System.out.println("引擎转起来了~~~");
    }
}
