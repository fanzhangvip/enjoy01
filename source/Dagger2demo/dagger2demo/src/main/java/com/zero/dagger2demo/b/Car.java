package com.zero.dagger2demo.b;

import javax.inject.Inject;

/**
 * 如果创建Engine的构造函数是带参数的呢？比如说制造一台引擎是需要齿轮(Gear)的。或者Eggine类是我们无法修改的呢？这时候就需要@Module和@Provide上场了
 */
public class Car {
    /**
     * 我们提到@Inject和@Module都可以提供依赖，那如果我们即在构造函数上通过标记@Inject提供依赖，有通过@Module提供依赖Dagger2会如何选择呢？具体规则如下：
     *
     * 步骤1：首先查找@Module标注的类中是否存在提供依赖的方法。
     * 步骤2：若存在提供依赖的方法，查看该方法是否存在参数。
     * a：若存在参数，则按从步骤1开始依次初始化每个参数；
     * b：若不存在，则直接初始化该类实例，完成一次依赖注入。
     *
     *
     * 步骤3：若不存在提供依赖的方法，则查找@Inject标注的构造函数，看构造函数是否存在参数。
     * a：若存在参数，则从步骤1开始依次初始化每一个参数
     * b：若不存在，则直接初始化该类实例，完成一次依赖注入
     */
    @Inject
    Engine engine;

    public Car() {
        DaggerCarComponent.builder().markCarModule(new MarkCarModule())
                .build().inject(this);
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
