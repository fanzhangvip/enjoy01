package com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext;

public class PESC1 {
    public static void main(String[] args) {

        Plate<? extends Fruit> p = new Plate<Apple>(new Apple());
//        p.set(new Fruit());    //Error
//        p.set(new Apple());    //Error
        Fruit newFruit1 = p.get();
        Object newFruit2 = p.get();
//        Apple newFruit3 = p.get();    //Error
        Plate<? super Fruit> p1 = new Plate<Fruit>(new Fruit());
        p1.set(new Fruit());
        p1.set(new Apple());
//        Apple newFruit3 = p1.get();    //Error
//        Fruit newFruit1 = p1.get();    //Error
//        Object newFruit2 = p1.get();

    }
}

class Plate<T>{
    private T item;
    public Plate(T t){item = t;}
    public void set(T t){item = t;}
    public T get(){return item;}
}

class Food{}

class Fruit extends Food{}

class Meat extends Food{}

class Apple extends Fruit{}

class Banana extends Fruit{}

class Pork extends Meat{}

class Beef extends Meat{}

class RedApple extends Apple{}

class GreenApple extends Apple{}
