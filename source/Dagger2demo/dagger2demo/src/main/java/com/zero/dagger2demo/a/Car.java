package com.zero.dagger2demo.a;

import javax.inject.Inject;

/**
 * Car类是需求依赖方，依赖了Engine类；因此我们需要在类变量Engine上添加@Inject来告诉Dagger2来为自己提供依赖。
 */
public class Car {
    /**
     * @Inject：@Inject有两个作用，一是用来标记需要依赖的变量，以此告诉Dagger2为它提供依赖
     */
    @Inject
    Engine engine;

    public Car() {
        DaggerCarComponent.builder().build().inject(this);
    }

    public Engine getEngine() {
        return this.engine;
    }

    public static void main(String ... args){
        //TODO:
        Car car = new Car();
        System.out.println(car.getEngine());
    }
}
