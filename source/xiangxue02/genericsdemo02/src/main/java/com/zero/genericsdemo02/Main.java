package com.zero.genericsdemo02;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    // ? super T 的用法
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
//        sort(students);
        sort1(students);

        List<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("bbb");
        String[] arrays = new String[strings.size()];
        System.out.println(Arrays.toString(arrays));

        ArrayList<A>  lista =  new ArrayList<A>();
        ArrayList<? super B> list = lista;

//        list.add(new A());
        list.add(new B());
        list.add(new C());

    }
    static class A{

    }
    static class B extends A{

    }
    static class C extends B{

    }

    //没办法给Student排序
    public static <T extends Comparable<T>> void sort(List<T> list){
        Collections.sort(list);
    }

    public static <T extends Comparable<? super T>> void sort1(List<T> list){
        Collections.sort(list);
    }
}
class Person implements Comparable<Person>{
    @Override
    public int compareTo(Person o) {
        return 0;
    }
}

class Student extends Person {

}