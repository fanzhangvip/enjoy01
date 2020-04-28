package com.zero.genericsdemo.type_erasure_08.effects_type_erasure_brigde_methods;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SetDemo {
    public static void main(String[] args) {

        HashSet<Integer> s1 = new HashSet<Integer>(Arrays.asList(1,2,3));
        printSet(s1);

    }

    public static void printSet(Set s){
        s.add(10);
        for(Object o: s){
            System.out.println(o);
        }
    }

    public static void printSet1(Set<?> s){
//        s.add(10);
        for(Object o: s){
            System.out.println(o);
        }
    }
}
