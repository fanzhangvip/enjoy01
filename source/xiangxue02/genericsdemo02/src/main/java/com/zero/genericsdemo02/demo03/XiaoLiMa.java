package com.zero.genericsdemo02.demo03;

import java.lang.reflect.Method;

public class XiaoLiMa extends Person {


    public  Plate<Apple> getApple(){
        //拿了个盘子去装水果了
        Plate<Apple> fruitPlate = new AIPlate<>();
        fruitPlate.set(new Apple());
        return fruitPlate;
    }

    public  Plate<? extends Fruit> getSnack(Plate<Apple> applePlate){
        Plate<? extends Fruit> fruitPlate =  applePlate;
        //不能存放任何元素
        try{
            Method set = fruitPlate
                    .getClass()
                    .getMethod("set",Object.class);
            set.invoke(fruitPlate,new Banana());
            //set.invoke(fruitPlate,new Beef());//什么都能放了 安全没法保证
        }catch(Exception e){}

//        fruitPlate.set(new Apple());
//        fruitPlate.set(new Banana());
        //放null还是可以
        fruitPlate.set(null);
        return fruitPlate;
    }
}
