package com.zero.dagger2demo.c;

import javax.inject.Inject;

/**
 * 那么如果一台汽车有两个引擎（也就是说Car类中有两个Engine变量）怎么办呢？没关系，我们还有@Qulifier
 */
public class Car {
    /**
     * 3. 接下来依赖需求方Car类同样需要修改
     */
    @Engine.QualifierA
    @Inject
    Engine engineA;

    @Engine.QualifierB
    @Inject
    Engine engineB;

    public Car() {
        DaggerCarComponent.builder().markCarModule(new MarkCarModule())
                .build().inject(this);
    }

    public Engine getEngineA() {
        return this.engineA;
    }

    public Engine getEngineB() {
        return this.engineB;
    }

    public static void main(String... args) {
        //TODO:
        Car car = new Car();
        System.out.println(car.getEngineA());
        System.out.println(car.getEngineB());
    }
}
