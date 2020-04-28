package com.zero.genericsdemo.generic_types_02;

import java.util.ArrayList;
import java.util.List;

/**
 * Why Use Generics? 为什么使用泛型
 */
public class Generics02 {

    public static void main(String[] args) {

    }

    static class Box {
        private Object object;

        public void set(Object object) { this.object = object; }
        public Object get() { return object; }
    }

}
