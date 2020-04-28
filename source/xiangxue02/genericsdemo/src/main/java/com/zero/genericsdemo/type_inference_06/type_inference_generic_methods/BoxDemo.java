package com.zero.genericsdemo.type_inference_06.type_inference_generic_methods;

import com.zero.genericsdemo.type_inference_06.Box;

import java.util.ArrayList;
import java.util.List;
//Type Inference and Generic Methods（类型推断和通用方法）
public class BoxDemo {
    public static <U> void addBox(U u, List<Box<U>> boxes) {
        Box<U> box = new Box<>();
        box.set(u);
        boxes.add(box);
    }

    public static <U> void outputBoxes(List<Box<U>> boxes) {
        int counter = 0;
        for (Box<U> box: boxes) {
            U boxContents = box.get();
            System.out.println("Box #" + counter + " contains [" +
                    boxContents.toString() + "]");
            counter++;
        }
    }

    public static void main(String[] args) {
        ArrayList<Box<Integer>> listOfIntegerBoxes = new ArrayList<>();
        //通用方法为你引入了类型推断，使你可以像调用普通方法一样调用通用方法，
        // 而无需在尖括号之间指定类型。考虑下面的示例BoxDemo，它需要Box类
        //完整用法
        BoxDemo.<Integer>addBox(Integer.valueOf(10), listOfIntegerBoxes);

        //类型推断
        BoxDemo.addBox(Integer.valueOf(20), listOfIntegerBoxes);
        BoxDemo.addBox(Integer.valueOf(30), listOfIntegerBoxes);
        BoxDemo.outputBoxes(listOfIntegerBoxes);
    }

}
