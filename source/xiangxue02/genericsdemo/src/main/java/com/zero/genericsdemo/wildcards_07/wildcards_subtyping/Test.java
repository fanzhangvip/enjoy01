package com.zero.genericsdemo.wildcards_07.wildcards_subtyping;

import java.util.ArrayList;
import java.util.List;

//Wildcards and Subtyping（通配符和子类型）
class A {}
class B extends A{}
public class Test {

    public static void main(String[] args) {
        //第一组
        B b= new B();
        A a = b;

        //第二组
        //此示例显示常规类的继承遵循此子类型规则：如果B扩展了A，则类B是类A的子类型。
        // 此规则不适用于通用类型：
        List<B> lb = new ArrayList<>();
//        List<A> la = lb;//报错

        //第三组
        //尽管Integer是Number的子类型，但List<Integer>不是List<Number>的子类型，
        // 实际上，这两种类型无关。List<Number>和List<Integer>的公共父级是List<?>
        List<? extends Integer> intList = new ArrayList<>();
        List<? extends Number>  numList = intList;  // OK. List<? extends Integer> is a subtype of List<? extends Number>

    }
}
