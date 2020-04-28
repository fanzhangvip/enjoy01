package com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use;

import java.util.ArrayList;
import java.util.List;

//Guidelines for Wildcard Use（通配符使用准则）
public class Test {
    /**
     * 在学习使用泛型编程时，更令人困惑的方面之一是确定何时使用上限的通配符以及何时使用下限的通配符。
     * 此页面提供了一些在设计代码时要遵循的准则。
     * 为了便于讨论，将变量视为提供以下两个功能之一将很有帮助：
     *
     * “输入”变量：输入变量将数据提供给代码。想象一个具有两个参数的复制方法：copy(src, dest)。
     * src参数提供要复制的数据，因此它是输入参数。
     *
     * “输出”变量：输出变量保存要在其它地方使用的数据。在复制示例copy(src, dest)中，
     * dest参数接受数据，因此它是输出参数。
     *
     * 当然，某些变量既用于“输入”又用于“输出”目的（准则中也解决了这种情况）。
     * 在决定是否使用通配符以及哪种类型的通配符时，可以使用“输入”和“输出”原理。
     * 以下列表提供了要遵循的准则：
     *
     * 通配符准则：
     * 使用上限通配符定义输入变量，使用extends关键字。
     * 使用下限通配符定义输出变量，使用super关键字。
     * 如果可以使用Object类中定义的方法访问输入变量，请使用无界通配符（?）。
     * 如果代码需要同时使用输入和输出变量来访问变量，则不要使用通配符。
     *
     * 这些准则不适用于方法的返回类型。应该避免使用通配符作为返回类型，
     * 因为这会迫使程序员使用代码来处理通配符。
     *
     * @param args
     */

    public static void main(String[] args) {
        List<EvenNumber> le = new ArrayList<>();
        List<? extends NaturalNumber> ln = le;
        //因为List<EvenNumber>是List<? extends NaturalNumber>的一个子类型，
        // 所以可以将le赋给ln。但不能用ln将自然数添加到偶数列表中
       // ln.add(new NaturalNumber(35));  // compile-time error

        //可以对该列表进行以下操作
//        可以添加null。
        ln.add(null);
//        ln.set(0,le.get(0));
//        可以调用clear。
        ln.clear();
//        可以获取迭代器（iterator）和调用remove。
        ln.removeAll(le);
//        可以对通配符进行匹配，并写入已经从list读出的元素
        NaturalNumber naturalNumber = ln.get(0);
        naturalNumber = new NaturalNumber(3);

    }
}

//由List<? extends ...>定义的列表可以被非正式地认为是只读的，但这并不是一个严格的保证。
// 假设你有以下两个类
class NaturalNumber {

    private int i;

    public NaturalNumber(int i) { this.i = i; }

}

class EvenNumber extends NaturalNumber {

    public EvenNumber(int i) { super(i); }
    // ...
}

