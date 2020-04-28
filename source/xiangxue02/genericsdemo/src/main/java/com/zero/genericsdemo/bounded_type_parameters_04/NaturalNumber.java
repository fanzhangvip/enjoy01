package com.zero.genericsdemo.bounded_type_parameters_04;

public class NaturalNumber<T extends Integer> {

    private T n;

    public NaturalNumber(T n)  { this.n = n; }

    public boolean isEven() {
        //除了限制可用于实例化泛型类型的类型之外，限定类型参数还允许你调用在范围中定义的方法
        return n.intValue() % 2 == 0;
    }

}

