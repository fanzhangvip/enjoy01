package com.enjoy.zero.libzhujie01.annotationattr;


import org.xml.sax.ext.Attributes2;

@interface AttrTest1{
    int id();
    String name();
}

@interface AttrTest2{
    int id() default -1;
    String name() default "";
}

@interface AttrSingle{
    String value();
    AttrTest2 test();
//    Test test();
}

@interface AttrVoid{

}


@interface AttrSupports{

}


@AttrVoid //没有任何属性,括号都可以省略
@AttrSingle("Single") //只有 value 属性
//@AttrSingle(value = "single")
@AttrTest1(id=1,name="testattr")//多个属性在注解的括号内以 value="" 形式，多个属性之前用 ，隔开
@AttrTest2
public class Main {
    public static void main(String ... args){
            //TODO:
    }
}
