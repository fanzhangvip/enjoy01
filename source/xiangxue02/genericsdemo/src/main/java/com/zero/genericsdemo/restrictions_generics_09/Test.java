package com.zero.genericsdemo.restrictions_generics_09;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Test {

//    为了高效使用Java泛型，你必须考虑一下下面的约束条件：
//
//    无法利用原始类型来创建泛型
//            无法创建类型参数的实例
//    无法创建参数化类型的静态变量
//            无法对参数化类型使用转换或者instanceof关键字
//    无法创建参数化类型的数组
//    无法创建、捕获或是抛出参数化类型对象
//    当一个方法的所有重载方法的形参类型擦除后，如果它们具有了相同的原始类型，那么此方法不可重载
}

//1. Cannot Instantiate Generic Types with Primitive Types 无法利用原始类型来创建泛型
class Pair<K, V> {

    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static void main(String[] args) {
//        Pair<int, char> p = new Pair<>(8, 'a');  // compile-time error

        //使用其包装类
        Pair<Integer, Character> p = new Pair<>(Integer.valueOf(8), new Character('a'));
    }

}

//2. Cannot Create Instances of Type Parameters 无法创建类型参数的实例
class Test02 {
    //你无法创建一个类型参数的实例。例如，下面代码就会引起编译时错误：
    public static <E> void append(List<E> list) {
//        E elem = new E();  // compile-time error
//        list.add(elem);
    }

    //通过反射创建一个参数化类型的实例
    public static <E> void append(List<E> list, Class<E> cls) throws Exception {
        E elem = cls.newInstance();   // OK
        list.add(elem);
    }
}

//3 Cannot Declare Static Fields Whose Types are Type Parameters 无法创建参数化类型的静态变量
class MobileDevice<T> {
//    private static T os;

    public void test(T t){}

//    public static void test1(T t){}

    public static <T> void test1(T t){}

    //如果允许类型参数的静态变量存在，那么下面代码就会混乱：
    public static void main(String[] args) {
        //由于该静态变量由phone、pager和pc三者所共享，那么os的类型究竟是什么呢？它无法同时兼具三者。
        // 因此，你不可以创建类型参数的静态变量。
        MobileDevice<Smartphone> phone = new MobileDevice<>();
        MobileDevice<Pager> pager = new MobileDevice<>();
        MobileDevice<TabletPC> pc = new MobileDevice<>();
    }

}

class Smartphone {
}

class Pager {
}

class TabletPC {
}

//4 Cannot Use Casts or instanceof with Parameterized Types
// 无法对参数化类型使用转换或者instanceof关键字
class Test04 {
    public static <E> void rtti(List<E> list) {
        //由于Java编译器会将泛型代码中所有的类型参数擦除，
        // 因此你也就无法确定一个泛型的运行时参数化类型究竟是什么了
        //传入rtti方法的参数化类型集合为：
        //S = { ArrayList<Integer>, ArrayList<String> LinkedList<Character>, ... }


//        if (list instanceof ArrayList<Integer>) {  // compile-time error
// ...
    }
    //运行时并不记录类型参数，因此它无法辨别ArrayList<Integer>和ArrayList<String>两者的不同。
    // 你能做的就是利用非受限通配符去确认这个list是一个ArrayList

    public static void rtti1(List<?> list) {
        if (list instanceof ArrayList<?>) {  // OK; instanceof requires a reifiable type
// ...
        }
    }

    public static void main(String[] args) {

        //第一组
        //一般的，除非是一个类型的参数为非受限通配符，否则你就不能将此类型转化为参数化类型。例如
        List<Integer> li = new ArrayList<>();
//        List<Number>  ln = (List<Number>) li;  // compile-time error

        //第二组
        //然而在某些情况下，编译器知道某个类型参数总是有效的，因而也就允许了类型转换。例如
        List<String> l1 = new ArrayList<>();
        List<String> l2 = (ArrayList<String>)l1;  // OK
    }

}

//5 Cannot Create Arrays of Parameterized Types 无法创建参数化类型的数组
class Test05{
    public static void main(String[] args) {

        //你无法创建参数化类型数组。例如下面的代码就无法通过编译：
//        List<Integer>[] arrayOfLists = new List<Integer>[2];  // compile-time error

        //如果你对一个泛型list做同样的事，那也会出问题：
        //如果参数化List的数组被允许，之前的代码就无法抛出预期的ArrayStoreException异常了
//        Object[] stringLists = new List<String>[]{};  // compiler error, but pretend it's allowed
//        stringLists[0] = new ArrayList<String>();   // OK
//        stringLists[1] = new ArrayList<Integer>();  // An ArrayStoreException should be thrown,
// but the runtime can't detect it.

    }
}

//6 Cannot Create, Catch, or Throw Objects of Parameterized Types 无法创建、捕获或是抛出参数化类型的对象
// Extends Throwable indirectly
//一个泛型类型无法直接或间接继承Throwable类。例如，下面的代码就无法通过编译
//class MathException<T> extends Exception { /* ... */ }    // compile-time error

// Extends Throwable directly
//class QueueFullException<T> extends Throwable { /* ... */ // compile-time error
class Test06{
    public static <T extends Exception, J> void execute(List<J> jobs) {
//        try {
//            for (J job : jobs){}
//            // ...
//            //一个方法无法捕获一个类型参数的实例：
//        } catch (T e) {   // compile-time error
// ...
        }

}

class Parser<T extends Exception> {
    public void parse(File file) throws T {     // OK
// ...
    }
}

//7 Cannot Overload a Method Where the Formal Parameter Types of Each Overload Erase to the Same Raw Type
// 当一个方法的所有重载方法的形参类型擦除后，如果它们具有了相同的原始类型，那么此方法是不可重载的
 class Example {
    //一个类中是无法存在类型擦除后具有相同签名的两个重载方法
    //这两个重载方法在classfile中具有相同的表达，因此会生成一个编译时错误
//    public void print(Set<String> strSet) { }
    public void print(Set<Integer> intSet) { }
//    public void print(Set<Object> intSet) { }
}
