package com.zero.genericsdemo02.demo02;

import java.util.ArrayList;
import java.util.List;


public class ConditionalPlate<T> implements Plate<T> {

    private List<T> items = new ArrayList<T>(10);

    public ConditionalPlate(){

    }

    @Override
    public void set(T t) {
        items.add(t);
    }

    @Override
    public T get(){
        int index = items.size() -1;
        if(index>= 0){
            return items.get(index);
        }else{
            return null;
        }
    }

    @Override
    public String toString() {
        return "Plate{" +
                "items=" + items +
                '}';
    }

//    @Override
//    public boolean equals(T t) {
//        return super.equals(t);
//    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
//擦除后
//public class ConditionalPlate implements Plate {
//
//    private List<Object> items = new ArrayList<Object>(10);
//
//    public ConditionalPlate(){
//
//    }
//
//    @Override
//    public void set(Object t) {
//        items.add(t);
//    }
//
//    @Override
//    public Object get(){
//        int index = items.size() -1;
//        if(index>= 0){
//            return items.get(index);
//        }else{
//            return null;
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "Plate{" +
//                "items=" + items +
//                '}';
//    }
//}