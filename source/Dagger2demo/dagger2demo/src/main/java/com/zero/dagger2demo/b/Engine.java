package com.zero.dagger2demo.b;

import javax.inject.Inject;

public class Engine {

    private String name;

    @Inject
    Engine(){}

    Engine(String name) {
        this.name = name;
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
