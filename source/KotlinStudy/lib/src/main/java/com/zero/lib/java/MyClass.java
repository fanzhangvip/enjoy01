package com.zero.lib.java;

public class MyClass {

    class Base{

    }

    class Child{
        public Child(){

        }
    }

    class User{
        private int id;//字段
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        {
            System.out.println("这是一个初始化块");
        }

        public User(int id,String name){
                this.id = id;
                this.name = name;
        }

    }

    class User2{
        private int age;
        private String name;
        private String addr;
        private String tel;

        public User2() {
        }

        public User2(int age, String addr) {
            this.age = age;
            this.addr = addr;
        }

        public User2(int age, String name, String addr) {
            this.age = age;
            this.name = name;
            this.addr = addr;
        }

        public User2(int age, String name, String addr, String tel) {
            this.age = age;
            this.name = name;
            this.addr = addr;
            this.tel = tel;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }

    public static void testargs(String ... args){
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }

    public static void test(Object[] objects){
        System.out.println(objects.length);
    }

    public static void main(String ... args){
            //TODO:
    }
}
