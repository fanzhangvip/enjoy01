package com.zero.genericsdemo.type_erasure_08;

import java.util.ArrayList;

/**
 * Java语言引入了泛型，以在编译时提供更严格的类型检查并支持泛型编程。
 * 为了实现泛型，Java编译器将类型擦除应用于：
 *
 * 如果类型参数不受限制，则将通用类型中的所有类型参数替换为其边界（上下限）或Object。
 * 因此，产生的字节码仅包含普通的类，接口和方法。
 * 必要时插入类型转换，以保持类型安全。
 * 生成桥接方法以在扩展的泛型类型中保留多态。
 *
 * 类型擦除可确保不会为参数化类型创建新的类；因此，泛型不会产生运行时开销
 */
public class TypeErasure {

    static class Pair<T>{
        private T value;
        public T getValue(){
            return value;
        }

        public void setValue(T value){
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Pair<String> pair = new Pair<>();
        pair.setValue("myString");

        System.out.println("pair: " + pair.getValue());

        //原理
        ArrayList<String> arrayString=new ArrayList<String>();
        ArrayList<Integer> arrayInteger=new ArrayList<Integer>();
        System.out.println(arrayString.getClass()==arrayInteger.getClass());

    }
}
