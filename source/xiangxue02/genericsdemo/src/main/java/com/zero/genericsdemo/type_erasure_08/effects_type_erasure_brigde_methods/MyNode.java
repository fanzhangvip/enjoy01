package com.zero.genericsdemo.type_erasure_08.effects_type_erasure_brigde_methods;

public class MyNode extends Node<Integer> {
    public MyNode(Integer data) { super(data); }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }



//    Bridge Methods（桥接方法）
//    在编译扩展参数化类或实现参数化接口的类或接口时，作为类型擦除过程的一部分，
//    编译器可能需要创建一个称为桥接方法的综合方法。你通常不必担心桥接方法，
//    但是如果其中一个出现在堆栈跟踪中，你可能会感到困惑
}
