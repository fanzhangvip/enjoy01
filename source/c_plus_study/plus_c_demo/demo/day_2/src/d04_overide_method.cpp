//
// Created by Zero on 2019/10/12.
//

#include <iostream>
using namespace std;

void myPrint4(int a)
{
    printf("a:%d \n", a);
}

void myPrint4(char* p)
{
    printf("%s \n", p);
}

void myPrint4(int a, int b)
{
    printf("a:%d ", a);
    printf("b:%d \n", b);
}


/*
//返回值 不是 判断函数重载的标准
int myPrint4(int a, int b)
{
	printf("a:%d ", a);
	printf("b:%d \n", b);
}
*/

//1 当函数名和不同的参数搭配时函数的含义不同
//2 函数重载的判断标准
//名称 参数 返回值
//名称相同 参数不一样(个数/类型/)

//3 返回值 不是 判断函数重载的标准 ///

//4 重载函数的调用标准
//
int main41()
{

    myPrint4(1);
    myPrint4("sdafadsfwer");
    myPrint4(1, 2);
    cout<<"hello..."<<endl;
    return 0 ;
}



// 函数重载  和  函数默认参数 在一起

void myfunc4(int a, int b, int c = 0)
{
    printf("a:%d b:%d c:%d \n", a, b, c);
}

void myfunc4(int a, int b)
{
    printf("a:%d b:%d\n", a, b);
}

void myfunc4(int a)
{
    printf("a:%d\n", a);
}
int main42()
{
    //myfunc4(1, 2); //函数调用时,会产生二义性
    myfunc4(1);


    cout<<"hello..."<<endl;
    return 0;
}