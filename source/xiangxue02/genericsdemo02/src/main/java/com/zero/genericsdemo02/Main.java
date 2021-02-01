package com.zero.genericsdemo02;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class MyCourse implements Serializable {

    private String name;
    private float score;

    public MyCourse() {
        System.out.println("_Course: empty");
    }

    public MyCourse(String name, float score) {
        System.out.println("_Course: " + name + " " + score);
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
public class Main {

    // ? super T 的用法
    public static void main(String[] args) throws Exception{
//        List<Student> students = new ArrayList<>();
////        sort(students);
//        sort1(students);
//
//        List<String> strings = new ArrayList<>();
//        strings.add("aaa");
//        strings.add("bbb");
//        String[] arrays = new String[strings.size()];
//        System.out.println(Arrays.toString(arrays));
//
//        ArrayList<A>  lista =  new ArrayList<A>();
//        ArrayList<? super B> list = lista;
//
////        list.add(new A());
//        list.add(new B());
//        list.add(new C());

        MyCourse course = new MyCourse("english",12);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);

        course.setScore(34f);
        oos.writeObject(course);
        byte[] bs = out.toByteArray();
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));
        MyCourse course1 = (MyCourse)ois.readObject();
//        MyCourse course2 = (MyCourse)ois.readObject();
        System.out.println("course1: " + course1);
//        System.out.println("course2: " + course2);
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