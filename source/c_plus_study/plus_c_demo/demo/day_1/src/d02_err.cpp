//
// Created by Zero on 2019/9/28.
//
#include <iostream>

using namespace std;

class circle{
public:
    double r;
    double pi = 3.1415926;
    double area = pi * r *r;
};

int main21(){
    //TODO: 常犯的错误
    cout<<"常犯的错误"<<endl;
    circle c1;
    cout << "请输入r: "<<endl;
    cin >> c1.r;
    cout << "c1.area: " << c1.area <<endl;


    return 0;
}
