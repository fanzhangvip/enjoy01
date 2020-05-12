//
// Created by Zero on 2019/10/10.
//

#include <iostream>
using namespace std;


struct Teacher{
    char name[20];
    int age;
};
int main11(){
    //TODO: 引用的基本用法
    cout<<"引用的基本用法"<<endl;

//    Teacher b;
    int a = 10;
    // type & name = var;
    int &b = a;//b就是一个引用,请不要用C的语法是思考
    //就是给a变量取了一个别名
    printf("b:%d\n",b);
    printf("a:%d\n",a);

    b =100;
    printf("b:%d\n",b);
    printf("a:%d\n",a);
    // type * const 指针常量
//    int &c;//会报错的，普通的引用必须要依附某个变量，必须初始化


    return 0;
}

int myswap1(int *a,int *b){
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

int myswap(int a,int b){//值传递
    int tmp = a;
    a = b;
    b = tmp;
}//完成不了我们的功能

int myswap2(int &a,int &b){
    int tmp = a;
    a = b;
    b = tmp;
}


int main12(){
    //TODO: 基本类型的引用
    cout<<"基本类型的引用"<<endl;

    int x,y;
    x = 10;
    y = 20;
    myswap(x,y);
    printf("x:%d, y:%d \n",x,y);

    myswap1(&x,&y);
    printf("x:%d, y:%d \n",x,y);

    int num1,num2;
    num1 = 1;
    num2 = 2;

    myswap2(num1,num2);//a 就是num1的别名，b就是num2的别名
    printf("num1:%d, num2:%d \n",num1,num2);

    return 0;
}

void printT1(const Teacher *pT){
    cout << pT->age<<endl;
}

void printT2(Teacher &pT){
    cout << pT.age<<endl;
    //pT是一个别名
    pT.age = 33;
}

void printT3(Teacher pT){//值传递
    cout << pT.age<<endl;
    //pT是一个别名
    pT.age = 36;
}

int main13(){
    //TODO: 复杂类型引用的使用
    cout<<"复杂类型引用的使用"<<endl;
    Teacher t1;
    t1.age = 35;
    printT1(&t1);
    printT2(t1);//pT 是t1的别名
    printT3(t1);//pT是形参， t1 copy一份数据给pT //pT = t1;
    printf("t1.age:%d\n",t1.age);

    return 0;
}

void  modifyA(int &a){
    //引用
    a  = 11;
}

void modifyA1(int * const a){//指针常量
    *a = 12;
}

struct Teacher1{
    char name[64];
    int age;// 8
    int &a;//8
    int &b;//8
};



int main14(){
    //TODO: 引用的本质思考？
    cout<<"引用的本质思考？"<<endl;


    int a = 10;
    int &b = a;// b像一个常量
    modifyA(a);//函数调用的时候，我们程序员不需要取a的地址
    //请思考：对同一内存空间 可以取好几个名字吗？
    //引用就是取别名
    //从使用的角度，引用会让人误会其只是一个别名，没有自己的存储空间
    printf("&a:%p, &b:%p \n", &a,&b);

    modifyA1(&a);//指针，我们需手动取地址
    //引用在C++的内部实现 就是一个指针常量


    printf("sizeof(Teacher1): %d \n", sizeof(Teacher1));

    return 0;
}

void modifyA3(int *p){
    *p = 300;
}

int main15(){
    //TODO: 间接赋值
    cout<<"间接赋值"<<endl;

    int a = 10;
    int *p = NULL;
    p = &a;
    *p = 100;
    {
        *p = 200;
    }

    modifyA3(&a);
    printf("a: %d \n",a);
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
//812LFWMRSH-eyJsaWNlbnNlSWQiOiI4MTJMRldNUlNIIiwibGljZW5zZWVOYW1lIjoi5q2j54mIIOaOiOadgyIsImFzc2lnbmVlTmFtZSI6IiIsImFzc2lnbmVlRW1haWwiOiIiLCJsaWNlbnNlUmVzdHJpY3Rpb24iOiIiLCJjaGVja0NvbmN1cnJlbnRVc2UiOmZhbHNlLCJwcm9kdWN0cyI6W3siY29kZSI6IklJIiwiZmFsbGJhY2tEYXRlIjoiMjAxOS0wNC0yMSIsInBhaWRVcFRvIjoiMjAyMC0wNC0yMCJ9LHsiY29kZSI6IkFDIiwiZmFsbGJhY2tEYXRlIjoiMjAxOS0wNC0yMSIsInBhaWRVcFRvIjoiMjAyMC0wNC0yMCJ9LHsiY29kZSI6IkRQTiIsImZhbGxiYWNrRGF0ZSI6IjIwMTktMDQtMjEiLCJwYWlkVXBUbyI6IjIwMjAtMDQtMjAifSx7ImNvZGUiOiJQUyIsImZhbGxiYWNrRGF0ZSI6IjIwMTktMDQtMjEiLCJwYWlkVXBUbyI6IjIwMjAtMDQtMjAifSx7ImNvZGUiOiJHTyIsImZhbGxiYWNrRGF0ZSI6IjIwMTktMDQtMjEiLCJwYWlkVXBUbyI6IjIwMjAtMDQtMjAifSx7ImNvZGUiOiJETSIsImZhbGxiYWNrRGF0ZSI6IjIwMTktMDQtMjEiLCJwYWlkVXBUbyI6IjIwMjAtMDQtMjAifSx7ImNvZGUiOiJDTCIsImZhbGxiYWNrRGF0ZSI6IjIwMTktMDQtMjEiLCJwYWlkVXBUbyI6IjIwMjAtMDQtMjAifSx7ImNvZGUiOiJSUzAiLCJmYWxsYmFja0RhdGUiOiIyMDE5LTA0LTIxIiwicGFpZFVwVG8iOiIyMDIwLTA0LTIwIn0seyJjb2RlIjoiUkMiLCJmYWxsYmFja0RhdGUiOiIyMDE5LTA0LTIxIiwicGFpZFVwVG8iOiIyMDIwLTA0LTIwIn0seyJjb2RlIjoiUkQiLCJmYWxsYmFja0RhdGUiOiIyMDE5LTA0LTIxIiwicGFpZFVwVG8iOiIyMDIwLTA0LTIwIn0seyJjb2RlIjoiUEMiLCJmYWxsYmFja0RhdGUiOiIyMDE5LTA0LTIxIiwicGFpZFVwVG8iOiIyMDIwLTA0LTIwIn0seyJjb2RlIjoiUk0iLCJmYWxsYmFja0RhdGUiOiIyMDE5LTA0LTIxIiwicGFpZFVwVG8iOiIyMDIwLTA0LTIwIn0seyJjb2RlIjoiV1MiLCJmYWxsYmFja0RhdGUiOiIyMDE5LTA0LTIxIiwicGFpZFVwVG8iOiIyMDIwLTA0LTIwIn0seyJjb2RlIjoiREIiLCJmYWxsYmFja0RhdGUiOiIyMDE5LTA0LTIxIiwicGFpZFVwVG8iOiIyMDIwLTA0LTIwIn0seyJjb2RlIjoiREMiLCJmYWxsYmFja0RhdGUiOiIyMDE5LTA0LTIxIiwicGFpZFVwVG8iOiIyMDIwLTA0LTIwIn0seyJjb2RlIjoiUlNVIiwiZmFsbGJhY2tEYXRlIjoiMjAxOS0wNC0yMSIsInBhaWRVcFRvIjoiMjAyMC0wNC0yMCJ9XSwiaGFzaCI6IjEyNzk2ODc3LzAiLCJncmFjZVBlcmlvZERheXMiOjcsImF1dG9Qcm9sb25nYXRlZCI6ZmFsc2UsImlzQXV0b1Byb2xvbmdhdGVkIjpmYWxzZX0=-ti4tUsQISyJF/zfWxSHCr+IcYrX2w24JO5bUZCPIGKSi+IrgQ0RT2uum9n96o+Eob9Z1iQ9nUZ6FJdpEW5g0Exe6sw8fLrWMoLFhtCIvVgQxEEt+M7Z2xD0esmjP1kPKXZyc/i+NCxA2EO2Sec9uifqklBGP1L3xoENAw2QsIWBfttIe6EPWhbS8TIMMr2vF/S3HrN8To5Hj5lwD/t1GHgFK1uWrhsuifAiKcVzqogybzGiR1h2+yNYTMbKxP7uPCcdYMsIyrBNVRGA3IuEJgyGQTQlFbnVQoVUTGPW2tQxprmC464wMjKi40JHh27WzjOHPwgzxDaigwn4Z0EbSpA==-MIIElTCCAn2gAwIBAgIBCTANBgkqhkiG9w0BAQsFADAYMRYwFAYDVQQDDA1KZXRQcm9maWxlIENBMB4XDTE4MTEwMTEyMjk0NloXDTIwMTEwMjEyMjk0NlowaDELMAkGA1UEBhMCQ1oxDjAMBgNVBAgMBU51c2xlMQ8wDQYDVQQHDAZQcmFndWUxGTAXBgNVBAoMEEpldEJyYWlucyBzLnIuby4xHTAbBgNVBAMMFHByb2QzeS1mcm9tLTIwMTgxMTAxMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxcQkq+zdxlR2mmRYBPzGbUNdMN6OaXiXzxIWtMEkrJMO/5oUfQJbLLuMSMK0QHFmaI37WShyxZcfRCidwXjot4zmNBKnlyHodDij/78TmVqFl8nOeD5+07B8VEaIu7c3E1N+e1doC6wht4I4+IEmtsPAdoaj5WCQVQbrI8KeT8M9VcBIWX7fD0fhexfg3ZRt0xqwMcXGNp3DdJHiO0rCdU+Itv7EmtnSVq9jBG1usMSFvMowR25mju2JcPFp1+I4ZI+FqgR8gyG8oiNDyNEoAbsR3lOpI7grUYSvkB/xVy/VoklPCK2h0f0GJxFjnye8NT1PAywoyl7RmiAVRE/EKwIDAQABo4GZMIGWMAkGA1UdEwQCMAAwHQYDVR0OBBYEFGEpG9oZGcfLMGNBkY7SgHiMGgTcMEgGA1UdIwRBMD+AFKOetkhnQhI2Qb1t4Lm0oFKLl/GzoRykGjAYMRYwFAYDVQQDDA1KZXRQcm9maWxlIENBggkA0myxg7KDeeEwEwYDVR0lBAwwCgYIKwYBBQUHAwEwCwYDVR0PBAQDAgWgMA0GCSqGSIb3DQEBCwUAA4ICAQAF8uc+YJOHHwOFcPzmbjcxNDuGoOUIP+2h1R75Lecswb7ru2LWWSUMtXVKQzChLNPn/72W0k+oI056tgiwuG7M49LXp4zQVlQnFmWU1wwGvVhq5R63Rpjx1zjGUhcXgayu7+9zMUW596Lbomsg8qVve6euqsrFicYkIIuUu4zYPndJwfe0YkS5nY72SHnNdbPhEnN8wcB2Kz+OIG0lih3yz5EqFhld03bGp222ZQCIghCTVL6QBNadGsiN/lWLl4JdR3lJkZzlpFdiHijoVRdWeSWqM4y0t23c92HXKrgppoSV18XMxrWVdoSM3nuMHwxGhFyde05OdDtLpCv+jlWf5REAHHA201pAU6bJSZINyHDUTB+Beo28rRXSwSh3OUIvYwKNVeoBY+KwOJ7WnuTCUq1meE6GkKc4D/cXmgpOyW/1SmBz3XjVIi/zprZ0zf3qH5mkphtg6ksjKgKjmx1cXfZAAX6wcDBNaCL+Ortep1Dh8xDUbqbBVNBL4jbiL3i3xsfNiyJgaZ5sX7i8tmStEpLbPwvHcByuf59qJhV/bZOl8KqJBETCDJcY6O2aqhTUy+9x93ThKs1GKrRPePrWPluud7ttlgtRveit/pcBrnQcXOl1rHq7ByB8CFAxNotRUYL9IF5n3wJOgkPojMy6jetQA5Ogc8Sm7RG6vg1yow==

int main(){
    //TODO: 引用的难点
    cout<<"引用的难点"<<endl;

    int j_  = j();
    cout << "j_: " << j_ <<endl;

    int j1_  = j1();
    cout << "j1_: " << j1_ <<endl;

    int &j1_1  = j1();
    cout << "j1_1: " << j1_1 <<endl;

    int a1  = getA1();
    cout << "a1: " << a1 <<endl;

    int a2  = getA2();
    cout << "a2: " << a2 <<endl;
    int &a21  = getA2();
    cout << "a21: " << a21 <<endl;

    int* a3  = getA3();
    cout << "a3: " << *a3 <<endl;

    int a = 10;
    int &b = a;
    int c = 20;
//    &b = c;
    b = 30;
    const int &b1 = a;
//    b1 = 40;


    return 0;
}








