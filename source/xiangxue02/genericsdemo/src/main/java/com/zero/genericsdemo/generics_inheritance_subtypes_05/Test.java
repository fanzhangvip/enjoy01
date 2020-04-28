package com.zero.genericsdemo.generics_inheritance_subtypes_05;

public class Test {

    public static void main(String[] args) {
        //第一组
        Object someObject = new Object();
        Integer someInteger = new Integer(10);
        someObject = someInteger;   // OK

        //第二组
        //面向对象的术语中，这称为“is a”关系。由于Integer是一种Object，
        // 因此允许分配。但是Integer也是Number的一种，因此以下代码也有效
        //Integer extends Number
        someMethod(new Integer(10));   // OK
        someMethod(new Double(10.1));   // OK

        //第三组
//        boxTest(new Box<Integer>(10));//报错
        //给定两种具体的类型A和B（例如Number和Integer），
        // 无论A和B是否相关，MyClass<A>与MyClass<B>没有关系。
        // MyClass<A>和MyClass<B>的公共父对象是Object
    }

    public static void someMethod(Number n) { /* ... */ }

    //Box<Integer> Box<Number>没有半毛钱关系
    public static void boxTest(Box<Number> n) { /* ... */ }
}
