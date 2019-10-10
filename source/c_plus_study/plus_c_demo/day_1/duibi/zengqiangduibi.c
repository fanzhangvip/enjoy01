
#include <stdio.h>
int main1(){
    //TODO: 增强对比
    printf("增强对比\n");

    int i;
    printf("hello...\n");

    int k;
    i = 10;
    k = 11;

    printf("i:%d,k:%d \n",i,k);

    return 0;
}

int main2(){
    //TODO: register增强
    printf("register增强\n");

    register int a = 0;
//    printf("&a: %d \n",&a);//c语言里面 对register的变量不能取地址

    return 0;
}

int g_a= 100;
int g_a;//这里会报错，但是在c语言里面可以

int main3(){
    //TODO: 变量检查增强
    printf("hello... g_a: %d \n",g_a);
    return 0;
}


struct Teacher{
    char name[32];
    int age;
};
int main4(){
    //TODO: struct增强
    printf("struct增强\n");

//    Teacher t1;//会报错,不会认为是一种新的类型
    struct Teacher t1;//必须这么定义
    t1.age = 23;
    return 0;
}

/*
 * c++中所有的变量和函数都必须有类型
 * c语言中的默认类型在c++中是不合法的
 *
 * 函数f的返回值是什么类型，参数又是什么类型？
 * 函数g可以接收多少个参数
 */
f(i){
    printf("i = %d \n",i);
}

g(){
    return 5;
}

int main5(){
    //TODO: 变量类型增强
    printf("变量类型增强\n");

    f(10);
    printf("g() = %d \n",g(1,2,3,4,5));

    return 0;
}

int main6(){
    //TODO: 三目运算符
    printf("三目运算符\n");

    int a = 10;
    int b = 20;
    //返回一个最小数，并且给最小数赋值成3
    //三目运算符是一个表达式，表达式不可能做左值
    //在c语言中 表达式的元素结果 放在什么地方，寄存器
    //表达式的返回值是一个数，一个值
    //但是在c++中返回的是变量的本身
    // 2 如何做到 返回地址？
    // 让表达式返回一个内存空间，内存首地址 指针
    //在c语言中 如何 实现c++的三目运算符的效果
    //(a < b ? 1 : b )= 30是不行的
    //使用指针
    //3 本质
    // c++编译器帮我们完成了刚才我们的工作

//    (a < b ? a :b ) = 30; //在c语言中编译不过
    *(a < b ? &a : &b ) = 30;
    printf("a = %d ,b = %d \n",a,b);

    return 0;
}

int main(){
    //TODO: c语言中的const是一个冒牌货
    printf("c语言中的const是一个冒牌货\n");

    const int a = 10;//好像a是一个常量
//    a = 11;
    int *p = NULL;
    p = &a;
    *p = 20; //间接赋值

    printf("a:%d \n",a);

    return 0;
}
