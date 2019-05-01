package com.zero.lambdademo.lambda;

public class PersonUtil {

    public static PersonDisplay getPerson(){
        return (name1, age1, score1) -> "{ name: " + name1 + " age: " + age1 + " score: " + score1 + "}";
    }

}
