package com.zero.genericsdemo.bounded_type_parameters_04.multiple_bounds;

// 多重限定
public class Test {

    static class A{}
    static class A1{}
    static interface B{}
    static interface C{}

//    static class D<T extends B & A &C>{}//编译报错
    //具有多个限定的类型变量是范围中列出的所有类型的子类型。如果范围之一是类，则必须首先指定它
    static class D1<T extends A & B & C>{}//OK

    //单继承
//    static class D2<T extends  A & A1 & B & C>{}
}
