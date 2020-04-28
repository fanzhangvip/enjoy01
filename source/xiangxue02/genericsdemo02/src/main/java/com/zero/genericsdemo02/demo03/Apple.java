package com.zero.genericsdemo02.demo03;


public class Apple extends Fruit {
    private int id;
    public Apple(){
        id = 0;
    }

    public Apple(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "id=" + id +
                '}';
    }
}
