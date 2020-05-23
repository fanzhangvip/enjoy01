package com.zero.xuliehuademo.serializable;

import java.io.Serializable;

class A{
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

public class SerializableTest {

    public static void main(String[] args) throws Exception {

        B b = new B("Zero",18,new A("test"));

        byte[] bs = SerializeableUtils.serialize(b);

        //反序列化
        B b1 = SerializeableUtils.deserialize(bs);
        System.out.println(b1);

    }
}
