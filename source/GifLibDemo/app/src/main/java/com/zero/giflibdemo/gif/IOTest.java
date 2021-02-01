package com.zero.giflibdemo.gif;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;

class Person {
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return "name:" + name + ",age:" + age;
    }
}

public class IOTest {


    public static void reflectTest() {

        try {
            //第一步先找到Class
            Class<?> c = null;
            Object obj = null;
            c = Class.forName("com.zero.giflibdemo.gif.Person");
            //然后通过newinstance实例化
            obj = c.newInstance();

            //第二步找到方法Method
            Method myage = c.getDeclaredMethod("setAge", int.class);

            //第三步通过invoke调用
            myage.invoke(obj, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Test() throws Exception {
        InputStream inputStream = new FileInputStream("aaa.txt");
        byte[] buffer = new byte[1024];

        //java IO流的读取
        inputStream.read(buffer, 0, buffer.length);
    }
}
