package com.zero.lambdademo.lamdba;


import com.zero.lambdademo.lamdba.lamdba3.Person;

@FunctionalInterface
public interface Lambda6Interface {
     Person createPerson(String name, Integer age, Integer score);
}
