#include <iostream>

using namespace std;//使用命名控件std标准的命名空间



int main1() {
    //cout 标准输出 控制台
    // << 左移操作符，在c++里面 功能的改造(增强) === > c++语言操作符重载
    cout << "Hello, World!" << endl;//endl \n
    //打印到屏幕 并且回车换行
//    system("pause");
    return 0;
}

int main2() {
    //TODO: 求圆的面积,面向过程
    // 标准输入cin 和标准输出cout 始终写在操作符的左边
    double r = 0;
    double s = 0;

    cout << "请输入圆的半径: ";
    //cin标准输入，代表键盘
    cin >> r;
    cout << "r的值是: " << r << endl;
    s = 3.14 * r * r;
    cout << "圆的面积是: " << s << endl;

    return 0;
}

struct Circle{
    double m_s;//圆的面积
    double m_r;//圆的半径
};

class MyCicle {
public:
    double m_s;//成员变量
    double m_r;
public:
    void setR(double r){//成员函数
        m_r = r;
    }
    double getR(){
        return m_r;
    }
    double getS(){
        m_s = 3.14 * m_r*m_r;
    }

};
//用面向对象的方法
//1. 类的抽象，成员变量和成员函数
//2. 实例化 类的对象
//3. 求面积
//4. main集成测试
//思考一: 类的调用，执行过程分析==》类代码不是一步一步指向
//类是一个数据类型，(固定大小内存块的别名)；定义一个类，是一个抽象的概念
//不会给你分配内存，用数据类型定义变量的时候，才会分配内存
int main3() {
    //TODO: 求圆的面积，面向对象

    MyCicle c1,c2,c3; // 用类定义变量对象
    double r;
    cout << "请输入c1圆形的半径:"<<endl;
    cin >> r;
    //给c1圆形的属性赋值
    c1.setR(4);
    cout << "c1圆形的面积是: " <<c1.getS() <<endl;


    return 0;
}

int main4() {
    //TODO: 求圆的面积，面向对象

    MyCicle c1,c2,c3; // 用类定义变量对象
    double r1,r2,r3;
    cout << "请输入c1圆形的半径:"<<endl;
    cin >> r1;
    //给c1圆形的属性赋值
    c1.setR(r1);
    cout << "c1圆形的面积是: " <<c1.getS() <<endl;

    cout << "请输入c2圆形的半径:"<<endl;
    cin >> r2;
    //给c1圆形的属性赋值
    c2.setR(r2);
    cout << "c2圆形的面积是: " <<c2.getS() <<endl;

    cout << "请输入c3圆形的半径:"<<endl;
    cin >> r3;
    //给c1圆形的属性赋值
    c3.setR(r3);
    cout << "c3圆形的面积是: " <<c3.getS() <<endl;


    return 0;
}

