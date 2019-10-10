//
// Created by Zero on 2019/10/3.
//
#include <iostream>
using namespace std;

int main41(){
    //TODO: 实用性增强
    cout<<"实用性增强"<<endl;


    int i;
    cout << "hello ..."<<endl;
    i = 10;
    int k;
    k = 11;
    cout << "i: " << i <<endl;
    cout << "k: " << k <<endl;


    return 0;
}

//1.在c语言中 请求编译器将局部变量存储在寄存器中
//c语言中无法取得register变量地址
//2.在c++中有了自己的优化方式，不使用register也可能做优化
//c++中可以获取register变量的地址，当c++编译器发现程序中需要获取register变量的地址时，register对变量的声明变得无效
//3.早期c语言编译器不会对代码进行优化，因此register变量是一个很好的补充
int main42(){
    //TODO: register增强
    cout<<"register增强"<<endl;
    //请求编译器让变量直接放到寄存器

    register int a = 0;
    printf("&a: %d \n",&a);

    for (int i = 0; i < 1000; ++i) {//不使用register也可能做优化
        cout << "i: " << i <<endl;

    }
    return 0;
}

/*
 * 在c语言中，重复定义多个同名的全局变量是合法的
 * 在c++中，不允许订阅多个同名的全局变量
 * c语言中多个同名的全局变量最终会被链接到全局数据区的同一个地址空间上
 * int g_var;
 * int g_var =1;
 * c++直接拒绝这种二义性的做法
 */
int g_a= 100;
//int g_a;//这里会报错，但是在c语言里面可以
int main43(){
    //TODO: 变量检查增强
    cout<<"变量检查增强"<<endl;
    printf("hello... g_a: %d \n",g_a);
    return 0;
}

//struct 关键字，class关键字 完成的功能是一样的，不过还是有区别的，这个以后再说
struct Teacher{
    char name[32];
    int age;
};
struct Teacher1{
public:
    char name[32];
private:
    int a;
};
class Teacher2{
public:
    char name[32];
private:
    int a;
};
int main44(){
    //TODO: struct增强
    printf("struct增强\n");

    Teacher t1;//会认为是一种新的类型
//    struct Teacher t1;//不需要这么定义
    t1.age = 23;
    return 0;
}

/*
 * c++中新增bool类型
 * c++中的bool可能的值 只要true和false
 * 理论上bool只占用一个字节
 * 如果多个bool变量定义在一起，可能会各占一个bit，这取决于编译器的实现
 * true代表真值 编译器内部用1表示
 * false代表假值 编译器内部用0表示
 * c++编译器会在赋值时将非0转换为true,0值转换为false
 */
int main45(){
    //TODO: 新增bool类型
    cout<<"新增bool类型"<<endl;

    bool b1 = true;//告诉c++编译器给我分配1个字节的内存

    bool b2,b3,b4,b5;
    cout<<"sizeof(bool) " << sizeof(bool) <<endl;

    b1 = 10;//bool变量要么是0 要么是1
    cout << "b1: " << b1 <<endl;
    b1 = -10;
    cout << "b1: " << b1 <<endl;
    b1 = 0;
    cout << "b1: " << b1 <<endl;

    return 0;
}

int main46(){
    //TODO: 三目运算符
    printf("三目运算符\n");

    int a = 10;
    int b = 20;
    //返回一个最小数，并且给最小数赋值成3
    //三目运算符是一个表达式，表达式不可能做左值
    //在c语言中 表达式的元素结果 放在什么地方，寄存器
    //表达式的返回值是一个数，一个值
    //但是在c++中返回的是变量的本身
    (a < b ? a :b ) = 30; //在c语言中编译不过
//    (a < b ? 1 : b ) = 30;//也是不行的
    //在这里a会被赋值给30
    printf("a = %d ,b = %d \n",a,b);

    return 0;
}



