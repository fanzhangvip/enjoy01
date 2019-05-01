package com.zero.lambdademo.lamdba;



import com.zero.lambdademo.lamdba.lamdba3.Person;

import java.util.function.*;

//构造器引用 And 数组引用
public class Lambda6 {

    //构造器引用  ClassName :: new
   public  void test1() {

        Supplier<Person> x0 = new Supplier<Person>() {
            @Override
            public Person get() {
                return new Person();
            }
        };

        Supplier<Person> x1 = ()->new Person();

        Supplier<Person> x2 = Person::new;
   }

    //构造器引用  ClassName :: new
    public  void test2() {

        Lambda6Interface lambda6Interface = new Lambda6Interface() {
            @Override
            public Person createPerson(String name, Integer age, Integer score) {
                return new Person(name,age,score);
            }
        };

        Lambda6Interface lambda6Interface1 = (name,age,score) ->new Person(name,age,score);

        Lambda6Interface lambda6Interface2 = Person::new;

    }



    //type[] :: new
    public  void test3() {
        Function<Integer,Person[]> f0  = new Function<Integer, Person[]>() {
            @Override
            public Person[] apply(Integer x) {
                return new Person[x];
            }
        };

        Function<Integer,Person[]> f1  = x->new Person[x];

        Function<Integer,Person[]>  f2 = Person[]::new;
    }


}
