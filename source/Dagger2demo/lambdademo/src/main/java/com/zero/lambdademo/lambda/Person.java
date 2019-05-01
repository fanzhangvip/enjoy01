package com.zero.lambdademo.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Person {

    private String name;
    private Integer age;

    private Integer score;

    public Person(){};

    public Person(String name, Integer age, Integer score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }

    public String setDisplay(PersonDisplay display){
        return display.getPerson(name,age,score);
    }

    public static void main(String... args) {
        //TODO:


        // ::  方法引用
        Consumer<String> c =  new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        Consumer<String> c1 = s -> System.out.println(s);

        //对象的方法引用
        Consumer<String> c2 = System.out::print;

        c2.accept("hello ");






        Person p1 = new Person("Av",18,90);
        Person p2 = new Person("King",20,0);
        Person p3 = new Person("Lance",17,100);
        List<Person> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        //这里我们需要比较list里面的person,按照年龄排序
        //那么我们最常见的做法是
        //sort(List<T> list, Comparator<? super T> c)
        //1. 因为我们的sort方法的第二个参数是一个接口，所以我们需要实现一个匿名内部类
        Collections.sort(list, new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                return person1.getAge().compareTo(person2.getAge());
            }
        });
        //2. 因为第二个参数是一个@FunctionalInterface的函数式接口，所以我们可以用lambda写法
        Collections.sort(list, (person1,person2) -> p1.getScore().compareTo(p2.getAge()));
        //3. 因为第二个参数我们可以用lambda的方式去实现，
        // 但是刚好又有代码(Comparator.comparing)已经实现了这个功能
        // 这个时候我们就可以采用方法引用了
        /**
         * 重点：
         * 当我们想要实现一个函数式接口的那个抽象方法，但是已经有类实现了我们想要的功能，
         * 这个时候我们就可以用方法引用来直接使用现有类的功能去实现。
         */
        Collections.sort(list, Comparator.comparing(Person::getAge));

        System.out.println(list);

        Predicate<Person> personPredicate = person -> person.getAge() > 18;
        System.out.println("Av老师是否大于18岁： " + personPredicate.test(p1));
        System.out.println("King老师是否大于18岁： " +personPredicate.test(p2));
        System.out.println("Lance老师是否大于18岁： " +personPredicate.test(p3));



    }

}
