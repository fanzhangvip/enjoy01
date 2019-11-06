//
// Created by ngh on 2019/10/29.
//
#include <iostream>

using namespace std;
class Base {

public:
    Base() {
        cout << "构造 函数 我是父类" << endl;
    }

    Base(const Base &base) {
        cout << "copy函数 我是父类" << endl;
    }

    ~Base() {
        cout << "析构函数 我是父类" << endl;
    }

    virtual void print() {
        cout << "我是父类" << endl;
    }

private:
    int a;

};

class chileB : public Base {
public:
    void print() {
        cout << "我是子类----" << endl;
    }

    void print1() {
        cout << "我是子类+++++" << endl;
    }

private:
    int c;
};


int main() {
    //TODO $STR
    cout << "兼容性问题 " << endl;

    Base b;
    b.print();
cout<<"========"<<endl;
    chileB c;
    Base *b1;
    b1 = &c;
    b1->print();  //调用的是子类
    c.print1(); //调用的子类
    return 0;
}