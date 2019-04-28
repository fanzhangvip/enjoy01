package com.zero.zhuruyilai;

public class Main {

    public static void main(String ... args){
            //TODO:

        Person person = new Person();
        person.buy(new House());
        person.buy(new Car());

    }

    static class Person{

        //表示依赖House
        public void buy(House house){}
        //表示依赖Car
        public void buy(Car car){}
    }

    static class House{

    }

    static class Car{

    }
}
