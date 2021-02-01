package com.zero.xuliehuademo.gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class A implements Serializable{
    private String str;

    public A(String str){
        this.str = str;
    }

    @Override
    public String toString() {
        return "A{" +
                "str='" + str + '\'' +
                '}';
    }
}

class B implements Serializable {
    private String name;
    private int age;

    private A a;

    public B(String name,int age, A a){
        this.name = name;
        this.age = age;
        this.a = a;
    }

    @Override
    public String toString() {
        return "B{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", a=" + a +
                '}';
    }
}

class MyCourse implements Serializable{

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

public class SerializableTest {

    public static void main(String[] args) throws Exception {

      MyCourse course = new MyCourse("english",12);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);

        course.setScore(34f);
        oos.writeObject(course);
        byte[] bs = out.toByteArray();
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));
        MyCourse course1 = (MyCourse)ois.readObject();
        MyCourse course2 = (MyCourse)ois.readObject();
        System.out.println("course1: " + course1);
        System.out.println("course2: " + course2);

    }
}
