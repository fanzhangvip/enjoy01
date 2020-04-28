package com.zero.genericsdemo.type_inference_06.type_inference_instantiation_generic_classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Type Inference and Instantiation of Generic Classes（泛型类的类型推断和实例化）
public class Test {

    public static void main(String[] args) {
        //完整写法
        Map<String, List<String>> myMap = new HashMap<String, List<String>>();

        //类型推断
        Map<String, List<String>> myMap1 = new HashMap<>();

        //请注意，要在泛型类实例化过程中利用类型推断的优势，
        // 必须使用菱形。在以下示例中，编译器生成未经检查的转换警告，
        // 因为HashMap()构造函数引用的是HashMap原始类型，而不是Map<String，List<String >>类型
        Map<String, List<String>> myMap2 = new HashMap(); // unchecked conversion warning
    }
}
