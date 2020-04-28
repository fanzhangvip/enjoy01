package com.zero.genericsdemo.type_erasure_08.effects_type_erasure_brigde_methods;

public class Node<T> {

    public T data;

    public Node(T data) { this.data = data; }

    public void setData(T data) {
        System.out.println("Node.setData");
        this.data = data;
    }

    //冲突了
//    public boolean equals(T value){
//        Object o;
//        return (this.equals(value));
//    }


    //泛型约束和局限性—— 类型擦除所带来的麻烦
//    (1)  继承泛型类型的多态麻烦。（—— 子类没有覆盖住父类的方法 ）
//    (2) 泛型类型中的方法冲突
//    (3) 没有泛型数组一说
//    泛型代码与JVM
//    ① 虚拟机中没有泛型，只有普通类和方法。
//    ② 在编译阶段，所有泛型类的类型参数都会被Object或者它们的限定边界来替换。(类型擦除)
//    ③ 在继承泛型类型的时候，桥方法的合成是为了避免类型变量擦除所带来的多态灾难。
}
