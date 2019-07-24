package com.enjoy.zero.libzhujie01.repeatable;


import java.lang.annotation.Repeatable;

@interface Persons {
    Person[] value();
}


@Repeatable(Persons.class)
@interface Person {
    String role() default "";
}

@Person(role = "程序员")
@Person(role = "老师")
@Person(role = "超人")
public class Zero2 {
}
