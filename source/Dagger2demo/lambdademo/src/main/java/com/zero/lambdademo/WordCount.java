package com.zero.lambdademo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class WordCount {

    public static void main(String[] args) throws  Exception {
        InputStream inputStream = WordCount.class.getResourceAsStream("work.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        Map<String,Integer> map = new HashMap<>();
        while((line=br.readLine()) !=null) {
            String[] split = line.split(" ");
            initResult(map, split[0]);
            initResult(map, split[1]);
        }

        System.out.println(map);
    }

    private static void initResult(Map<String, Integer> result, String key) {
        Integer value = 1;
        if(result.containsKey(key)) {
            value =  result.get(key) +1;
        }
        result.put(key,value);
    }
}
