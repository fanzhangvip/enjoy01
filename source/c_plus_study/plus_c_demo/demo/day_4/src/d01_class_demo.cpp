//
// Created by Zero on 2019/10/10.
//

#include <iostream>
#include <string.h>

using namespace std;//使用命名空间std标准的命名空间

class Animal{

public:
    Animal(int a){
        mA=a;
        cout<< "构造函数 Animal() "<< "mA="<<mA<<endl;
    }

    ~Animal(){
        cout<< "析构函数 ~Animal() "<< "mA="<<mA<<endl;
    }

private:
    int mA;

};

class Dog{
public:
    Dog():mA(2){//构造函数的初始化列表
        mA = Animal(2);//不能这样初始化Animal对象
        mB =1;
    }

    Dog(Animal animal):mA(2){//构造函数的初始化列表
        mB =1;
        mA = animal;
        cout<< "构造函数Dog(Animal animal):mA(2)  "<<endl;
    }

private:
    int mB;
    Animal mA;

};

class A {
public:

    A(int a) {
        m_a = a;
        cout << "构造函数: " << m_a << endl;
    }

    ~A() {
        cout << "析构函数" << endl;
    }

    int getMA() {
        return m_a;
    }

private:
    int m_a;
};

/*
 * 构造函数的初始化列表
 * 语法：ClassName():m1(xxx),m2(xxx),m3(xxx)
 * 1. 为了在B类中组合了一个A类对象(A类有有参数的构造函数)
 * 2. 初始化const type 常变量，必须用初始化列表
 */
class B {
public:
//    B(int b,A a):m_a(2),m_b(12)//构造函数的初始化列表 默认值
//        m_b = b;
//        m_a = a;
//    }

    B() : m_a2(3), m_a1(2), m_a(m_a2), m_b(10),c(3) {//初始化列表一定要注意顺序问题，是一种错误的习惯
    }

    B(int b) :  m_a(2), m_a2(3), m_a1(4),c(5) {//正确的初始化列表顺序应该和声明的变量的顺序是一致的

    }

    B(A a):m_a(1),m_a1(2),m_a2(3),c(10){
    m_a = a;
}

    void print() {
        cout << m_a.getMA() << " " << m_b << endl;
    }

private://初始化列表里面初始化的顺序是按照我们成员变量声明的顺序，不是初始化列表的顺序
    int m_b;//在初始化列表里面 m_a(1),m_b(2) 这个是谁先初始化
    A m_a;
    A m_a1;
    A m_a2;//编译器它不知道要调用哪个构造函数

    const int c;//常变量java final
};


typedef char* const *(*Next)();

char buf[] = "helloworld\n";

char* const *func(){
    return (char *const*)buf;
}


int main(){

    Next n;

    n =func;

    cout<<"next: " <<(char*)n()<<endl;

    return 0;
}


