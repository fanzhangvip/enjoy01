//
// Created by Zero on 2019/10/10.
//

#include <iostream>
#include <string.h>

using namespace std;//使用命名空间std标准的命名空间

// 运算符重载
// 可以使得用户自定义的数据，使用起来更简单
/*
 * java  String  "abc" + "def"
 * c/c++ strcat
 */

class Complex{
public:

    Complex(int a=0,int i=0){
        this->a = a;
        this->i = i;
    }

    void print(){
        cout <<"Complex: ("<<a<<"+"<<i<<"i)"<<endl;
    }
private:
    friend Complex myAdd(Complex c1, Complex c2);
    friend Complex operator+(Complex c1,Complex c2);
    int a;
    int i;
};

Complex myAdd(Complex c1, Complex c2){
    Complex tmp(c1.a+c2.a,c1.i +c2.i);
    return tmp;
}

//void operator-(Complex i){
//
//}


Complex operator+(Complex c1,Complex c2){//看成是一个函数
    Complex tmp(c1.a+c2.a,c1.i +c2.i);
    return tmp;
}

int main(){
    //TODO: 运算符重载
    cout<<"运算符重载"<<endl;

    int a = 0;
    int b = 0;
    int c;
    c = a + b;//基础数据类型 如何运算  C++编译器是不是已经定义好了的

    //"abc " + "def"
    //复数 实数 + 虚数 a + i  加法 (a1+i1) +  (a2+i2) = (a1+a2) + (i1+i2)
    Complex c1(1,2);
    c1.print();
    Complex c2(3,4);

    Complex sum;// sum c1 + c2;C++编译器如何支持操作符重载的？
//   sum = myAdd(c1,c2);
//   sum.print();
    sum = c1 + c2;// 函数调用
    sum.print();

    //是不是所有的运算符都可以重载
    //不是
    /*不可以重载的运算符
     *  1.  . 成员访问运算符
     *  2.  .*, ->* 成员指针访问运算符
     *  3.  ::  域运算符
     *  4.  sizeof 长度运算符
     *  5.  ?: 三目运算符
     *  6.  # 预处理符
     */
     // new/delet new[] delete[] 可以的
    return 0;
}



