package com.zero.genericsdemo.type_inference_06.generic_non_genric_classes;


public class MyClass<X> {

    <T> MyClass(T t){

    }

    public static void main(String[] args) {

        //该语句创建参数化类型MyClass<Integer>的实例；
        // 该语句为泛型类MyClass<X>的形式类型参数X明确指定类型Integer。
        // 请注意，此泛型类的构造函数包含一个形式类型参数T。
        // 编译器会为该泛型类的构造函数的形式类型参数T推断String类型（因为该构造函数的实际参数是String对象）
        new MyClass<Integer>("");//推断类型为MyClass<Integer>

        //Java SE 7之前的发行版中的编译器能够推断泛型构造函数的实际类型参数，
        // 类似于泛型方法。但是，如果使用菱形（<>），
        // 则Java SE 7和更高版本中的编译器可以推断要实例化的泛型类的实际类型参数。考虑以下示例

        //在此示例中，编译器为通用类MyClass<X>的形式类型参数X推断类型Integer。
        // 它为该泛型类的构造函数的形式类型参数T推断类型String
        MyClass<Integer> myObject = new MyClass<>("");

        //注意：推断算法仅使用调用参数，目标类型，并且可能使用明显的预期返回类型。
        // 推断算法不使用程序后续的结果
    }
}
