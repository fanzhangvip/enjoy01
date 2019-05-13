package com.zero.serializabledemo.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Course implements Serializable {

    private static final long serialVersionUID = 667279791530738499L;
    private String name;
    private float score;

    public Course() {
        System.out.println("Course: empty");
    }

    public Course(String name, float score) {
        System.out.println("Course: " + name + " " + score);
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

    public static void main(String... args) throws Exception {
        //TODO:
        //TODO:
        Course course = new Course("英语", 12f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(course);
        course.setScore(78f);
        oos.reset();
//        oos.writeUnshared(course);
        oos.writeObject(course);
        byte[] bs = out.toByteArray();
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));
        Course course1 = (Course) ois.readObject();
        Course course2 = (Course) ois.readObject();
        System.out.println("course1: " + course1);
        System.out.println("course2: " + course2);

    }
}
