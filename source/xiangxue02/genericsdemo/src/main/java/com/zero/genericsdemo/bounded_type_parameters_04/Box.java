package com.zero.genericsdemo.bounded_type_parameters_04;

//        E - Element (Java Collections Framework广泛使用)
//        K - Key
//        N - Number
//        T - Type
//        V - Value
//        S,U,V etc. - 2nd, 3rd, 4th types
/**
 * 泛型类(泛型接口也是这样定义)
 * class name<T1, T2, ..., Tn> { /* ... * / }
 * @param <T>
 */
public class Box<T> {
    private T t;

    public void set(T t){
        this.t = t;
    }

    public T get(){return t;}

    /**
     * 限定类型参数  extends 上限
     * @param u
     * @param <U>
     */
    public <U extends Number> void inspect(U u){
        System.out.println("T: " + t.getClass().getName());
        System.out.println("U: " + u.getClass().getName());
    }

    public <U> void inspect1(U u){
        System.out.println("T: " + t.getClass().getName());
        System.out.println("U: " + u.getClass().getName());
    }






    public static void main(String[] args) {

        Box<Integer> integerBox2 = new Box<Integer>();
        integerBox2.set(new Integer(10));
        integerBox2.inspect(10L);
        integerBox2.inspect1("string");
//        integerBox2.inspect("some text"); // error: this is still String!

    }
}
