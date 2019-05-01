package com.zero.lambdademo.lambda;

@FunctionalInterface
public interface PersonDisplay {

    String getPerson(String name,Integer age,Integer score);

}
