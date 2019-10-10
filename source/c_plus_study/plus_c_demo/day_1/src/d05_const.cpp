//
// Created by Zero on 2019/10/4.
//
#include <iostream>

using namespace std;

struct Teacher {
    char name[64];
    int age;
};

int operatorTeacher(const Teacher *pT) {//常量指针，修饰的是变量，表示指针所执行的内存空间，不能被修改
//    pT->age = 23;//是输入参数，只读，不能修改
    pT = NULL;
    return 0;
}

int operatorTeacher1(Teacher *const pT) {//指针常量，修饰的是指针，表示指针的指向不能被修改
    pT->age = 2;//输出参数
//    pT = NULL;//指针的指向不能被修改
    return 0;
}


/*
 * const好处
 * 1. 指针函数参数，可以有效的提高代码可读性，减少bug
 * 2. 清楚的分清楚参数的输入和输出特性
 *结论：
*C语言中的const变量
*C语言中const变量是只读变量，有自己的存储空间
*C++中的const常量
*可能分配存储空间,也可能不分配存储空间
*当const常量为全局，并且需要在其它文件中使用
*当使用&操作符取const常量的地址
 */
int main51() {
    //TODO: const基础用法
    cout << "const基础用法" << endl;
    const int a = 10;
    int const b = 20;//一样的

    const int *c;//常量指针，const修饰的是指针所指向的变量，表示这个变量是一个常量，不能被修改，指针所指向的内存空间，不能被修改
    c = &a;
//     *c = 30;报错
    int a1 = 1;
    int *const d = &a1;//指针常量，const修饰的是指针本身，指针的指向不能修改

    const int *const e = &a1;//常量指针常量

    Teacher t1;
    t1.age = 33;
    operatorTeacher1(&t1);
    cout << "age: " << t1.age << endl;
    operatorTeacher(&t1);
    cout << "age: " << t1.age << endl;

    //在c语言中const是一个冒牌货
    //在c++中是一个真正的常量
    //原因分析
    //
    const int a2 = 10;//在c语言中好像a是一个常量,在c语言中是会给a2单独分配内存的
    //在c++中会把a2放到符号表(key -value｛a2 : 10｝)
//    a2 = 11;
    int *p = NULL;
    p = (int *) &a2;//在c++中当你对a2取地址的时候会为a2单独开辟一块空间，并且返回地址
    //3. const变量分配内存的时机，是编译期
    *p = 20; //间接赋值，这里的赋值不能改变a2的值

    printf("a:%d，*p:%d \n", a, *p);

    int c1;
    const int c2 = 35;
    int c3;
    printf("&c1:%p,&c2:%p,&c3:%p \n", &c1, &c2, &c3);//c2的地址在c1，c3之间证明是在编译期分配的，栈上是连续分配的

    return 0;
}

//4. const 和#define的相同之处
// #define 是在编译预处理阶段处理，是简单的文本替换
// const 是由编译器处理的，提供类型检查和作用域检查
#define d1 20

int main52() {
    //TODO: //4. const用途
    cout << "//4. const用途" << endl;
    int a = 10;
    int b = 20;

    int array[a + b];//linux内核里面是成立的，原因编译linux内核的gcc编译器支持
    //c和c++编译器不支持这种语法现象
    const int c = 10;
    const int d = 20;
    int array2[c + d];
    int array3[c + d1];
    return 0;
}

void fun1() {
#define a 10//在fun1定义
    const int b = 20;
//#undef a //卸载
// #undef
}

void fun2() {
    printf("a = %d \n", a);//在fun2里面也可以使用
//    printf("b = %d \n",b);
}

int main53() {
    //TODO: const #define的不同支持
    cout << "const #define的不同支持" << endl;

    fun1();
    fun2();
    return 0;
}