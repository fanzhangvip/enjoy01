//
// Created by Zero on 2019/10/10.
//

#include <iostream>
#include <string.h>
#include <vector>

using namespace std;//使用命名空间std标准的命名空间


/*
 * 类型转化
 * C风格类型转换
 * C++风格类型转换(static, dynamic, const, reinterpret)
 * C++的类型转换有3种方式，C风格，函数风格和C++风格
 * (T)expression  //C风格
 * T(expression) //函数风格
 * C++风格
 * static_cast<T>(expression)
 * dynamic_cast<T>(expression) //T必须是指向多态类型的指针或引用
 * const_cast<T>(expression) //T必须是指针或引用
 * reinterpret_cast<T>(expression) //T必须是指针或引用
 */
int main01() {

    //static_cast,其实就相当于C++风格的显式转换
    //static_cast是最常用最好理解的C++风格类型转换，
    // 其实就相当于C++风格的显式转换，例如把int转为double，
    // 把无类型(void)指针转换为有类型指针，继承层次中的转换，
    // 把non-const对象转为const，但是不可以把const对象转为non-const(这是const_cast的功能)，
    // 也不能转换两个完全不兼容的类型
    //double转为int:
    double d = 3.14;
    int a = (int) d; //C风格转换
    int b = static_cast<int>(d); //C++风格转换

    //non-const转为const:
    int a1 = 3;
    const int b1 = (const int) a1; //C风格转换
    const int c1 = static_cast<const int>(a1); //C++风格转换

    return 0;
}

class Base {
public:
    void doSomething() {};

    virtual void doSomething1() {};//作为多态基类
};

class D : public Base {//继承类

};

int main02() {
    //dynamic_cast 名字里带个"dynamic"，就说明它是动态的，发生在运行时，
    // 而上面静态的static_cast则发生在编译时。像动态绑定(dynamic binding)一样，
    // dynamic_cast需要RTTI(runtime type identification)信息做出运行时类型检测，
    // 所以它是最吃性能的cast。功能上不同于static_cast的是，它只能用在继承层级中，
    // 而且只支持指向多态类型的指针或引用，即继承层次的子类或有虚函数的基类

    D *d = new D();

    Base *base = new Base();

    //向上转型，转为父类
    Base *base1 = dynamic_cast<Base *>(d); //编译通过
    cout << "base1: " << base1 << endl;

    //但要注意如果基类没有虚函数，目标类型就不是多态类型，就不能通过编译
    //向下转型，转为子类
    D *d1 = dynamic_cast<D *>(base); //编译通过，但是返回空指针
    cout << "d1: " << d1 << endl;

//    dynamic_cast的一大用途就是检测当前指针指向的是父类还是子类。
//    如果返回了空指针，当前指针就指向父类，如果返回了有效的指针，指向的就是子类
    return 0;
}


void doSomething(int *i) {};

int main03() {
//    const_cast用来抹除对象的常量性(constness)，即去掉const限定符，它是唯一能去掉变量限定符的cast，
//    也可以用来去掉volatile限定符。不过既然一个变量被定义为了const，我们当然不希望改变它，
//    此这种cast最有用的地方体现在函数传值里:
    const int a = 10;
//    doSomething(&a); //报错，不能把const int转为int
    doSomething(const_cast<int *>(&a));// OK

    return 0;
}

int main04() {
//    reinterpret_cast用来把指针转换为任何类型的指针。既然叫做"reinterpret"，
//    它的用途就是把相同的内存数据以不同的方式表示出来，因此不生成任何额外的CPU代码，
//    常见于底层的应用。同时它也是一个十分危险的cast，再加上比较特殊的用途，所以比较少用

    int a = 0x76; //字母v的ASCII
    printf("a=%d,addr=%p\n", a, &a);
    int *ap = &a; //获取指针
    char *c = reinterpret_cast<char *>(ap); //输出v
    printf("c=%c,addr=%p\n", *c, c);

    float a1 = 2.76f; //尝试用reinterpret_cast把float转为int
    printf("a1=%f,addr=%p\n", a1, &a1);

    float *ap1 = &a1;
    printf("ap1=%f,addr=%p\n", *ap1, ap1);
    int *b = reinterpret_cast<int *>(ap1); //只是按照比特位输出0xffffcbec
    printf("b=%f,addr=%p\n", *b, b);
    return 0;
}

class Base1 {
};

class Derived : public Base1 {
};

int main05() {

    Derived d;
    printf("d:addr=%p\n", &d);
    Base1 *pb = &d; //derived*隐式转换为base*
    printf("pb:addr=%p\n", pb);
    return 0;
}

class Test {
public:
    ~Test() {
        cout << "析构函数 starts" << endl;

        throw 1;
        cout << "析构函数 end" << endl;
    }
};

void f1() throw(int) {           //函数f1会抛出一个整型的异常代码
    cout << "f1 starts" << endl;
    int i;                       //这个变量会在栈展开的过程中被释放资源
    throw 100;                   //抛出异常，程序开始在栈中搜索对应的异常处理器，即开始栈展开
    cout << "f1 ends" << endl;       //这行代码不会被执行
}

void f2() throw(int) {            //函数f2调用了f1，所以抛出异常的类型也是整型
    cout << "f2 starts" <<
         endl;
    int j;                      //这个变量也会在栈展开的过程中被释放资源
//    Test t;//析构函数不要抛出异常
    f1();                       //f1没有搜索到对应的异常处理，因此返回到f2搜索
    cout << "f2 ends" <<
         endl;      //这行代码也不会被执行
}

void f3() {
    cout << "f3 starts" << endl;
    try {                        //函数f3在try里调用f2，并可能会catch一个整型的异常
        f2();
    } catch (int i) {              //f2也没有找到异常处理，最后返回了f3并找到了异常处理
        cout << "exception " << i << endl;
    }
    cout << "f3 ends" << endl;
}

int main06() {
    f3();
    return 0;
}


template<typename T>
void swap1(T &a, T &b) {
    T temp(a);
    a = b;
    b = temp;
}

class Num {
public:
    Num() {
        cout << "构造" << endl;
    }

    ~Num() {
        cout << "析构" << endl;
    }

    vector<int> *v;
};

void printV(vector<int> *v) {
    vector<int>::iterator it;
    for (it = v->begin(); it != v->end(); ++it) {
        cout << *it << " ";
    }
    cout << endl;
}

template<>
void swap1(Num &left, Num &right) {
    cout << "特殊化" << endl;
    swap1(left.v, right.v);
}

void swap2(int &left, int &right) {
    int tmp = left;
    left = right;
    right = tmp;
}

int main07() {
    //TODO: 模板使用
    cout << "模板使用" << endl;
    int l = 1;
    int r = 2;

    swap<int>(l, r);

    cout << "l: " << l << " r: " << r << endl;

    double dl = 1.2;
    double dr = 3.4;
    swap<double>(dl, dr);

    cout << "dl: " << dl << " dr: " << dr << endl;

    return 0;
}

class Printer {
public:
//    这是因为c++ compiler在parse一个类的时候就要确定vtable的大小，
//    如果允许一个虚函数是模板函数，那么compiler就需要在parse这个类之前扫描所有的代码，
//    找出这个模板成员函数的调用（实例化），然后才能确定vtable的大小，而显然这是不可行的，
//    除非改变当前compiler的工作机制

    template<typename T>
    void print(const T &t) {//为什么成员函数模板不能是虚函数(virtual)
        cout << t << endl;
    }
};

//当返回值类型也是参数时
template<typename T1, typename T2, typename T3>
T1 sum(T2 v2, T3 v3) {//返回值类型与参数类型完全无关，那么就需要显示的指定返回值类型，
    // 其他的类型交给实参推断
    return static_cast<T1>(v2 + v3);
}

template<typename T1, typename T2, typename T3>
T3 sum1(T1 v1, T2 v2) {
    return static_cast<T1>(v1 + v2);
}

template<typename It>
auto sum2(It beg, It end) -> decltype(*beg) {
    decltype(*beg) ret = *beg;
    for (It it = beg + 1; it != end; it++) {
        ret = ret + *it;
    }
    return ret;
}

template<typename T>
void func(T &t) { //通用模板函数
    cout << "In generic version template " << t << endl;
}

template<typename T>
void func(T *t) { //指针版本
    cout << "In pointer version template " << *t << endl;
}

void func(string *s) { //普通函数
    cout << "In normal function " << *s << endl;
}


int main08() {
    //TODO: 成员函数模板
    cout << "成员函数模板" << endl;

//    Printer p;
//    p.print<const char *>("abc");
//
//    //实参推断
//    int i = 1, j = 2;
//    swap(i, j);
//    double n = 1.0, m = 2.0;
//    swap(n, m);
//    p.print(123);
////可以通过把函数模板赋值给一个指定类型的函数指针，让编译器根据这个指针的类型，对模板实参进行推断
//    void (*pf)(int &, int &) = swap;
//    pf(i, j);
//
//    auto result = sum<long>(1L, 2);//指定T1, T2和T3交由编译器来推断
//
////    auto result1 = sum1<long>(1L, 23); //error，只能从左向右逐一指定
//    auto result2 = sum1<long, int, long>(1L, 23); //ok, 谁叫你把最后一个T3作为返回类型的呢？
//
//
//    vector<int> v = {1, 2, 3, 4};
//    auto s = sum2(v.begin(), v.end()); //s = 10
//
//
//    int i1 = 10;
//    func(i1); //调用通用版本，其他函数或者无法实例化或者不匹配
//    func(&i1); //调用指针版本，通用版本虽然也可以用，但是编译器选择最特殊的版本
//    string s1 = "abc";
//    func(&s1); //调用普通函数，通用版本和特殊版本虽然也都可以用，但是编译器选择最特化的版本
//    func<>(&s1); //调用指针版本，通过<>告诉编译器我们需要用template而不是普通函数

    cout << "=========" << endl;
    vector<int> v1{1, 2, 3, 4, 5};

    vector<int> v2{6, 7, 8};

    Num num1;
    num1.v = &v1;
    cout << " num1.v: ";
    printV(num1.v);
    Num num2;
    num2.v = &v2;
    cout << " num2.v: ";
    printV(num2.v);
    swap1(num1, num2);
    cout << "============swap==========" << endl;
    cout << " num1.v: ";
    printV(num1.v);
    cout << " num2.v: ";
    printV(num2.v);
    cout << "=========1======" << endl;
    return 0;
}

class B {
public:
    int b;
protected:
private:
};

class B1 : virtual public B {
public:
    int b1;
protected:
private:
};

class B2 : virtual public B {
public:
    int b2;
protected:
private:
};

class C : public B1, public B2 {
public:
    int c;
protected:
private:
};

int main() {
    C myc;
    myc.c = 10;
    myc.b = 100;//二义性  error C2385: 对“b”的访问不明确

    return 0;
}


