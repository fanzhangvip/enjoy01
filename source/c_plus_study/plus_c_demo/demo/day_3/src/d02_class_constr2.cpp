//
// Created by Zero on 2019/10/13.
//
#include <iostream>
#include <string.h>

using namespace std;


class Test2 {
public:
    Test2()  //无参数构造函数
    {
        m_a = 0;
        m_b = 0;
        cout << "无参数构造函数" << endl;
    }

    Test2(int a) {
        cout << a << endl;
        m_a = a;
        m_b = 0;
    }

    Test2(int a, int b) //有参数构造函数   //3种方法
    {
        cout << a << ", " << b << endl;
        m_a = a;
        m_b = b;
        cout << "有参数构造函数" << endl;
    }

    //赋值构造函数 (copy构造函数) //
    Test2(const Test2 &obj) {
        cout << "我也是构造函数 " << endl;
    }

public:
    void printT() {
        cout << "普通成员函数" << endl;
    }

private:
    int m_a;
    int m_b;
};

int main21() {
    //TODO: 构造函数分类
    cout << "构造函数分类" << endl;
    Test2 t1;  //调用无参数构造函数
    cout << "hello..." << endl;
    return 0;
}


int main22() {
    //TODO: 构造函数分类
    cout << "构造函数分类" << endl;
    //1括号法
    Test2 t1(1, 2);  //调用参数构造函数  c++编译器自动的调用构造函数
    t1.printT();

    // 2 =号法
    Test2 t2 = (3, 4, 5, 6, 7); // = c+对等号符 功能增强  c++编译器自动的调用构造函数

    Test2 t3 = 5;

    //3 直接调用构造函数  手动的调用构造函数
    Test2 t4 = Test2(1, 2);   //匿名对象 (匿名对象的去和留) 抛砖 ....//t4对象的初始化
    //

    t1 = t4;  //把t4 copy给 t1  //赋值操作
    //对象的初始化 和 对象的赋值 是两个不同的概念

    cout << "hello..." << endl;
    return 0;
}

class Test3 {
public:
    void init(int _a, int _b) {
        a = _a;
        b = _b;
    }

    void print() {
        cout << a << ", " << b << endl;
    }

protected:
private:
    int a;
    int b;
};

int main23() {
    //TODO: 初始化方案
    cout << "初始化方案" << endl;
    //类没有提供构造函数,c++编译器会自动给你提供一个默认的构造函数
    //类没有提供构造函数 copy构造构造函数, c++编译器会自动给程序员提供一个 默认的copy构造函数  =
    Test3 t1;
    int a = 10;
    int b = 20;
    t1.init(a, b);
//    Test3 t2 = Test3(t1);//默认copy构造函数；
//    t2.print();

    Test3 tArray[3];
    tArray[0].init(1, 2);
    tArray[1].init(1, 2);
    tArray[2].init(1, 2);

    //
    Test3 t21;
    t21.init(1, 2);
    Test3 t22;
    t22.init(1, 2);
    Test3 t23;
    t23.init(1, 2);

    //在这种场景之下 显示的初始化方案 显得很蹩脚
    Test3 tArray2[3] = {t21, t22, t23};

    //在这种场景之下,满足不了,编程需要
    Test3 tArray3[1999] = {t21, t22, t23};


    cout << "hello..." << endl;
    return 0;
}

class Test4 {
public:
    Test4()  //无参数构造函数
    {
        m_a = 0;
        m_b = 0;
        cout << "无参数构造函数" << endl;
    }

    Test4(int a) {
        m_a = a;
        m_b = 0;
    }

    Test4(int a, int b) //有参数构造函数   //3种方法
    {
        m_a = a;
        m_b = b;
        cout << "有参数构造函数" << endl;
    }

    //赋值构造函数 (copy构造函数) //
    Test4(const Test4 &obj) {
        cout << "我也是构造函数 " << endl;
        m_b = obj.m_b + 100;
        m_a = obj.m_a + 100;
    }

public:
    void printT() {
        cout << "普通成员函数" << endl;
        cout << "m_a" << m_a << " m_a" << m_b << endl;
    }

private:
    int m_a;
    int m_b;
};

//1  赋值构造函数 用1个对象去初始化另外一个对象
int main24() {
    //TODO: copy构造函数应用场景1
    cout << "copy构造函数应用场景1" << endl;

    Test4 t1(1, 2);
    Test4 t0(1, 2);

    //赋值=操作 会不会调用构造函数
    //operator=()//抛砖
    t0 = t1; //用t1 给 t0赋值  到操作 和 初始化是两个不同的概念
    //第1种调用方法
    Test4 t2 = t1; //用t1来初始化 t2
    t2.printT();

    cout << "hello..." << endl;
    return 0;
}

//第二种调用时机
int main25() {
    //TODO: 第二种
    cout << "第二种" << endl;

    Test4 t1(1, 2);
    Test4 t0(1, 2);

    Test4 t2(t1);  //用t1对象 初始化 t2对象
    t2.printT();

    cout << "hello..." << endl;

    return 0;
}


//第三种应用时机
class Location {
public:
    Location(int xx = 0, int yy = 0) {
        X = xx;
        Y = yy;
        cout << "Constructor Object.\n";
    }

    //copy构造函数  完成对象的初始化
    Location(const Location &obj) //copy构造函数
    {
        X = obj.X;
        Y = obj.Y;
    }

    ~Location() {
        cout << X << "," << Y << " Object destroyed." << endl;
    }

    int GetX() { return X; }

    int GetY() { return Y; }

private :
    int X, Y;
};


//业务函数  形参是一个元素
void f(Location p) {
    cout << "f:copy= "<<p.GetX() << endl;
}

void playobj() {
    Location a(1, 2);
    Location b = a;
    cout << "b对象已经初始化完毕" << endl;

    f(b); //b实参取初始化形参p,会调用copy构造函数
}

int main26() {
    //TODO: 第三种 难点
    cout << "第三种 难点" << endl;

    playobj();
    cout<<"hello..."<<endl;

    return 0;
}
//第四种 难点
//g函数 返回一个元素
//结论1 : 函数的返回值是一个元素 (复杂类型的), 返回的是一个新的匿名对象(所以会调用匿名对象类的copy构造函数)

//
//结论2: 有关 匿名对象的去和留
//如果用匿名对象  初始化 另外一个同类型的对象, 匿名对象 转成有名对象
//如果用匿名对象  赋值给 另外一个同类型的对象, 匿名对象 被析构

//
//你这么写代码,设计编译器的大牛们:
//我就给你返回一个新对象(没有名字 匿名对象)
Location g()
{
    Location A(1, 2);//A是一个局部变量
    return A;
}

//
void objplay2()
{
    g();
}

//
void objplay3()
{
    //用匿名对象初始化m 此时c++编译器 直接把匿名对转成m;(扶正) 从匿名转成有名字了m
    Location m = g();
    printf("匿名对象,被扶正,不会析构掉\n");
    cout<<m.GetX()<<endl;;
}

void objplay4()
{
    //用匿名对象 赋值给 m2后, 匿名对象被析构
    Location m2(1, 2);
    m2 = g();
    printf("因为用匿名对象=给m2, 匿名对象,被析构\n");
    cout<<m2.GetX()<<endl;;
}

struct Person{
    int age;
    int &a;
};
int main(){
    //TODO: 第四种 难点
    cout<<"第四种 难点"<<endl;
    int a = 10;
    Person person={3,a};

    //objplay2();
    //objplay3();
    objplay4();
    cout<<"hello..."<<endl;
    return 0;
}

