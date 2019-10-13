//
// Created by Zero on 2019/10/12.
//

#include <iostream>
using namespace std;

//inline void printA();

//inline int myfunc(int a, int b)和函数体的实现，写在一块

inline void printA()
{
    int a = 10;
    cout<<"a"<<a<<endl;
}

int main21()
{
    printA();
    //2 C++编译器直接将函数体插入在函数调用的地方
    //3 内联函数省去了普通函数调用时压栈，跳转和返回的开销
// 	{
// 		int a = 10;
// 		cout<<"a"<<a<<endl;
// 	}

    cout<<"hello..."<<endl;
    return 0;
}

//带参数的宏
#define MYFUNC(a, b) ((a) < (b) ? (a) : (b))

inline int myfunc(int a, int b)
{
    return a < b ? a : b;
}

int main22(){
    //TODO: 带参数的宏
    cout<<"带参数的宏"<<endl;
    int a = 1;
    int b = 3;
//    int c = myfunc(++a, b);  // a=2 b=3 c=2
    int c1 = MYFUNC(++a, b); //==>宏替换并展开 ((++a) < (b) ? (++a) : (b))  //a=3 b=3 c=3


    printf("a = %d\n", a);
    printf("b = %d\n", b);
//    printf("c = %d\n", c);
    printf("c1 = %d\n", c1);

    return 0;
}