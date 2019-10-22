//
// Created by Zero on 2019/10/10.
//

#include <iostream>
#include <string.h>
#include <vector>

using namespace std;//使用命名空间std标准的命名空间
class Test
{
public:
    Test(){}
    Test(int i) {}

    void* operator new (size_t size)
    {
        cout<<"new "<<endl;
        void *p = malloc(size);
        return p;
    }
    //void* operator new (size_t size, Test *p)
    //{
    // return p;
    //}
};

class A{
    friend std::ostream& operator<<(std::ostream &os,const A&);
public:
    int operator*(){return b;}
    A* operator->(){return this;}
    int geta(){return a;}
    int getb(){return b;}
private:
    int a=0;
    int b=1;
};
std::ostream& operator<<(std::ostream &os,const A& a)
{
    os<<a.a<<' '<<a.b;
    return os;
}
int main01()
{
    A a;
    A *pa=&a;
    std::cout<<*a<<','<<*a<<std::endl;
    std::cout<<*pa<<','<<*pa<<std::endl;
    std::cout<<a->geta()<<','<<a->getb()<<std::endl;
    return 0;
}

int main02(){


    Test *p = new Test;
    int i = 10;
//    new(p) Test(i);

    return 0;
}

// 假设一个实际的类
class Obj {
    static int i, j;
public:
    void f() const { cout << i++ << endl; }
    void g() const { cout << j++ << endl; }
};

// 静态成员定义
int Obj::i = 10;
int Obj::j = 12;

// 为上面的类实现一个容器
class ObjContainer {
     vector<Obj*> a;
public:
    void add(Obj* obj)
    {
        a.push_back(obj);  // 调用向量的标准方法
    }
    friend class SmartPointer;
};

// 实现智能指针，用于访问类 Obj 的成员
class SmartPointer {
    ObjContainer oc;
    int index;
public:
    SmartPointer(ObjContainer& objc)
    {
        oc = objc;
        index = 0;
    }
    // 返回值表示列表结束
    bool operator++() // 前缀版本
    {
        if(index >= oc.a.size() - 1) return false;
        if(oc.a[++index] == 0) return false;
        return true;
    }
    bool operator++(int) // 后缀版本
    {
        return operator++();
    }
    // 重载运算符 ->
    Obj* operator->() const
    {
        if(!oc.a[index])
        {
            cout << "Zero value";
            return (Obj*)0;
        }
        return oc.a[index];
    }
};

int main() {
    const int sz = 10;
    Obj o[sz];
    ObjContainer oc;
    for(int i = 0; i < sz; i++)
    {
        oc.add(&o[i]);
    }
    SmartPointer sp(oc); // 创建一个迭代器
    do {
        sp->f(); // 智能指针调用
        sp->g();
    } while(sp++);
    return 0;
}


