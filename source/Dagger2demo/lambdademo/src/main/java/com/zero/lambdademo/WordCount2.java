package com.zero.lambdademo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class WordCount2 {

    public static void main(String[] args) throws  Exception {
        InputStream inputStream = WordCount2.class.getResourceAsStream("work.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));


        br.lines().flatMap(x->Stream.of(x.split(" "))).
                collect(Collectors.groupingBy(String::toString)).forEach((a,b)-> System.out.println(a+":"+b.size()));


    }


}
