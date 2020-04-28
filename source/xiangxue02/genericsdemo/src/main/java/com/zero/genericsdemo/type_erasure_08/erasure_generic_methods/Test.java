package com.zero.genericsdemo.type_erasure_08.erasure_generic_methods;


//Erasure of Generic Methods（通用方法的擦除）
public class Test {

    public static void main(String[] args) {

    }

    //由于T是无界的，因此Java编译器将其替换为Object
    public static <T> int count(T[] anArray, T elem) {
        int cnt = 0;
        for (T e : anArray)
            if (e.equals(elem))
                ++cnt;
        return cnt;
    }

    //Java编译器用Shape替换T：
    public static <T extends Shape> void draw(T shape) { /* ... */ }
}

class Shape { /* ... */ }
class Circle extends Shape { /* ... */ }
class Rectangle extends Shape { /* ... */ }
