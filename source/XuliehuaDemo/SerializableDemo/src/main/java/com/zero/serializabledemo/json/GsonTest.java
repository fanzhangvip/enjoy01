package com.zero.serializabledemo.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GsonTest {

    static String CurPath = System.getProperty("user.dir");

    public static void main(String... args) throws Exception {
        //TODO:

        test1();
    }

    static class GsonBean{
        private String name;
        private int age;

        public GsonBean(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "GsonBean{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void test1() throws Exception{
        //TODO: 没有数据头的纯数组的JSON
        Gson gson = new Gson();
        //1. 生成json文件
        File file = new File(CurPath + "/gsonjsontest.json");
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String line;
        StringBuffer sb = new StringBuffer();

        while (null != (line = br.readLine())) {
            sb.append(line);
        }
        fis.close();
        isr.close();
        br.close();

        System.out.println(sb.toString());

        //Json的解析类对象
        JsonParser jsonParser = new JsonParser();
        //转化成一个JsonArray对象
        JsonArray jsonArray = jsonParser.parse(sb.toString()).getAsJsonArray();

        ArrayList<GsonBean> gsonBeans = new ArrayList<>();
        for(JsonElement jsonElement :jsonArray){
            //使用Gson,直接转化成bean对象
            GsonBean gsonBean = gson.fromJson(jsonElement,GsonBean.class);
            gsonBeans.add(gsonBean);
        }

        System.out.println(gsonBeans);

    }
}
