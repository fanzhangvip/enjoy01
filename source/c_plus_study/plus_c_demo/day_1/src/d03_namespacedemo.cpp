//
// Created by Zero on 2019/10/3.
//
#include <iostream>

using namespace std;
//1. 文件中iostream没有引入标准的std,需要我们程序员手工的写
//2. 如果不写using namespace std; 需要显示的引入std
int main31(){
    //TODO:
    std::cout <<"namespace test" <<std::endl;



    return 0;
}

//3. 定义namespace
namespace namespaceA{
    int a = 10;
}

namespace  namespaceB{
    int a = 20;
    namespace namespaceC{//命名空间 可以嵌套
        struct Teacher{
            char name[20];
            int age;
        };
    }
}
//4. 使用namespace
int main32(){
    //TODO: 定义命名空间namespace
    cout<<"定义命名空间namespace"<<endl;

    using namespace namespaceA;
    using namespace namespaceB;
    cout << "namespaceA: " << namespaceA::a <<endl;
    cout << "namespaceB: " << namespaceA::a <<endl;

    //显示的 写全
    {
        namespaceB::namespaceC::Teacher t1;
        t1.age = 33;
    }
    using namespace namespaceC;
    Teacher t2;
    t2.age = 43;
    return 0;
}
