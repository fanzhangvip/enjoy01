package com.zero.genericsdemo.generic_types_02;

//        E - Element (Java Collections Framework广泛使用)
//        K - Key
//        N - Number
//        T - Type
//        V - Value
//        S,U,V etc. - 2nd, 3rd, 4th types

import java.util.List;

/**
 * 泛型类(泛型接口也是这样定义)
 * class name<T1, T2, ..., Tn> { /* ... * / }
 * @param <T>
 */
//泛型接口
interface Generics<T>{

}

interface GenericsN<T,S,U,V>{

}

//1. 泛型类/接口的 继承/实现
class  Generics2<T> implements Generics<T> {

    public static void main(String[] args) {
        Generics2<Integer> generics2 = new Generics2<>();
    }
}
//2. 泛型类/接口的 继承/实现
class  Generics3 implements Generics<String> {

    public static void main(String[] args) {
        Generics3 generics2 = new Generics3();
    }

}

class GeneriscMethod{

    public void test(Object o){

    }
    //泛型方法
    public <T> T test1(T t){
        return t;
    }

    public void test2(List<String> list){

    }

    public void test3(List<?> list){

    }

//    public <T> void test4(Box<E>: box){
//
//    }

}

//泛型类
public class Box<T> {
    private T t;

    //这个是泛型方法吗？  不是
    public void set(T t){
        this.t = t;
    }

    public T get(){return t;}

    /**
     * 限定类型参数
     * @param u
     * @param <U>
     */
    public <U extends Number> void inspect(U u){
        System.out.println("T: " + t.getClass().getName());
        System.out.println("U: " + u.getClass().getName());
    }


    public static void main(String[] args) {
        Box<Integer> integerBox = new Box<Integer>();
        Box<Integer> integerBox1 = new Box<>();// The Diamond(菱形) 类型推断

        //参数化类型，请为形式类型参数T提供一个实际的类型参数
        Box<Integer> intBox = new Box<>();
        //则创建Box<T>的原始类型
        Box rawBox = new Box();

        Box<String> stringBox = new Box<>();
        //使用原始类型时，你实际上会获得泛型行为（Box为你提供对象）。
        // 为了向后兼容，允许将参数化类型分配给其原始类型
        Box rawBox1 = stringBox;
        rawBox1.set(9);

        Box rawBox2 = new Box();           // rawBox is a raw type of Box<T>
        Box<Integer> intBox1 = rawBox2;     // warning: unchecked conversion

        Box bi;
        bi = createBox();

        Box<Integer> integerBox2 = new Box<Integer>();
        integerBox.set(new Integer(10));
        //integerBox.inspect("some text"); // error: this is still String!

    }

    static Box createBox(){
        return new Box();
    }
}
