//
// Created by Zero on 2019/10/4.
//
#include <iostream>

using namespace std;

//1 引用的概念
//2 请不用c的语法去思考问题
int main61() {
    //TODO: 引用的基本用法
    cout << "引用的基本用法" << endl;

    int a = 10;
    //引用的语法，type& name = var;
    int &b = a;// int * const b = &a;

    printf("b:%d\n", b);
    printf("a:%d\n", a);

    b = 100;//相当于把a改成100；/ *b = 100;
    printf("b:%d\n", b);
    printf("a:%d\n", a);
    //请不用c的语法考虑b = 100

//    int &c;//会报错，普通引用必须要依附某个变量，必须初始化，因为相当于type * const 指针常量

    return 0;
}

int myswap(int a, int b) {
    int tmp = a;
    a = b;
    b = tmp;
}//完成不了功能

int myswap1(int *a, int *b) {
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

int myswap2(int &a, int &b) {
    int tmp = a;
    a = b;
    b = tmp;
}

int main62() {
    //TODO: 基础类型的引用
    cout << "基础类型的引用" << endl;

    int x, y;
    x = 10;
    y = 20;
    myswap(x, y);
    printf("x:%d, y:%d \n", x, y);
    myswap1(&x, &y);
    printf("x:%d, y:%d \n", x, y);

    myswap2(x, y);//a 就是x的别名，b就是y的别名
    printf("x:%d, y:%d \n", x, y);

    return 0;
}


struct Teacher {
    char name[64];
    int age;
};

void printT(const Teacher *pT) {
    cout << pT->age << endl;
}

void printT1(Teacher &pT) {
    cout << pT.age << endl;
    //这里是别名
    pT.age = 44;//就真修改啦
}

void printT2(Teacher pT) {//pT 和t1 是两个不同的变量，修改pT不会影响t1
    cout << pT.age << endl;
    pT.age = 45;
}

int main63() {
    //TODO: 复杂数据类型的引用
    cout << "复杂数据类型的引用" << endl;
    Teacher t1;
    t1.age = 35;

    printT(&t1);
    printT1(t1);//pT是t1的别名
    printT2(t1);//pT是形参，t1 copy一份数据给pT // pT = t1;
    printf("t1.age:%d \n", t1.age);

    return 0;
}


void modifyA(int &a) {//c++引用
    a = 11;
}

void modifyA1(int *const a) {//c++内部实现
    *a = 12;
}

struct Teahcer1 {
    char name[64];//64
    int age; //8
    //思考2：普通引用有自己的空间吗 引用是一个有地址，引用是常量
    int &a; //8
    int &b; //8 占内存空间,在c++内部就是一个常量指针 const type*
};

int main64() {
    //TODO: 引用的本质思考
    cout << "引用的本质思考" << endl;
//const int a;//必须初始化
    int a = 10;
    //b是a的别名，请问c++编译器后面做了什么工作？
    int &b = a;//b很像一个常量
    int *c = NULL;
    modifyA(a);//指向这个函数调用的时候，我们程序员不需要取a的地址，编译器帮我们做了
    //请思考：对同一内存空间可以取好几个名字吗
    printf("&a:%p, &b:%p \n", &a, &b);// a和b就是同一块内存空间的门牌号
    modifyA1(&a);//如果是指针，需要手动取地址
    printf("sizeof(int&): %d\n", sizeof(&b));
    printf("sizeof(int*): %d\n", sizeof(int *));
    printf("sizeof(Teahcer1): %d\n", sizeof(Teahcer1));
//    1）引用在C++中的内部实现是一个常量指针
//    Type& name vs Type* const name
//    2）C++编译器在编译过程中使用常指针作为引用的内部实现，因此引用所占用的空间大小与指针相同。
//    3）从使用的角度，引用会让人误会其只是一个别名，没有自己的存储空间。这是C++为了实用性而做出的细节隐藏


    return 0;
}

void modifyA3(int *p){
    *p = 200;// *p 3
}

int main65(){
    //TODO: 间接赋值
    cout<<"间接赋值"<<endl;
    int a = 10;
    int *p = NULL;//间接赋值成立的三个条件，1. 定义两个变量
    p = &a;
    *p = 100;
    {
        *p = 200;
    }
    modifyA3(&a);//2 建立关联
    return 0;
}

int getA1(){
    int a;
    a = 10;
    return a;
}

int& getA2(){//返回a的本身
    int a;//如果返回栈上的引用，是返回不了的，有问题
    //若返回栈变量，不能成为其他引用的初始值
    //不能作为左值使用
    a = 10;
    return a;
}

int* getA3(){
    int a;
    a = 10;
    return &a;
}

int j(){
    //返回变量是静态变量
    static int a = 10;
    a++;
    return a;
}

int& j1(){
    //返回变量是静态变量
    static int a = 10;
    a++;
    return a;
}

int main66(){
    //TODO: 引用的难点
    cout<<"引用的难点"<<endl;

    int j0  = j();
    cout << "j0: " << j0 <<endl;

    int j2  = j1();
    cout << "j2: " << j2 <<endl;

    int &j21  = j1();
    cout << "j21: " << j21 <<endl;

    int a1  = getA1();
    cout << "a1: " << a1 <<endl;

    int a2  = getA2();
    cout << "a2: " << a2 <<endl;
    int &a21  = getA2();
    cout << "a21: " << a21 <<endl;

    int* a3  = getA3();
    cout << "a3: " << *a3 <<endl;



    return 0;
}

// 全局变量声明
int g = 99;

// 函数声明
int func();

int fung1(int a,int b){
    g=a+b;
    cout<<"被改变的全局变量为："<<g<<endl;
    return 0;
}

int fung2(){
    cout<<"此时的全局变量为："<<g<<endl;
    return 0;
}

int main(){
    //TODO: 全局变量
    cout<<"全局变量"<<endl;
//    // 局部变量声明
//    int g = 10;
//    cout << g <<endl;
//    int kk = func();
//    cout << kk;

    fung2();
    fung1(10,20);
    fung2();

    extern int a;
    cout<<"a= "<<a<<endl; //合法，输出10
    return 0;
    return 0;
}

int a=10; //全局变量从此处定义

// 函数定义
int func()
{
    return g;
}
