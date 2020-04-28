package com.zero.genericsdemo02.demo02;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AIPlate<T extends Comparable<T>> implements Plate<T> {

    private List<T> items = new ArrayList<T>(10);

    public AIPlate(){

    }

    @Override
    public void set(T t) {
        items.add(t);
        Collections.sort(items);
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

//    @Override
//    public boolean equals(T obj) {
//        return super.equals(obj);
//    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Plate{" +
                "items=" + items +
                '}';
    }
}
//擦除后
//public class AIPlate implements Plate {
//
//    private List<Comparable> items = new ArrayList<Comparable>(10);
//
//    public AIPlate(){
//
//    }
//
//    @Override
//    public void set(Comparable t) {
//        items.add(t);
//        Collections.sort(items);
//    }
//
//    @Override
//    public Comparable get(){
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
