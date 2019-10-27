//
// Created by Biter on 2019/10/24.
//
#include <iostream>
//
// Created by Biter on 2019/10/24.
//
using namespace std;

class Person {
public:

    /**
     * 用来创建 多个 Person对象
     *
     * @param length
     */
    explicit Person(int length );

    /**
     * 列表初始化
     *
     * @param age
     * @param name
     */
    Person(int age, const string name);

    /**
     * 初始化一个对象
     *
     * @param person
     */
    Person(Person &person);

    Person();
    ~Person();

public:
    /**
     * 重载 索引运算符
     * @param i 索引
     * @return 此类对象
     */
    Person &operator[](int i);

    /**
     * 重载赋值运算符
     *
     * @param person
     */
    Person &operator=(const Person &person);

    /**
     * 重载赋值运算符
     *
     * @param age 年龄
     */
    Person &operator=(int age);

    /**
     * 重载赋值运算符,设置名字
     *
     * @param name 名字
     */
    Person &operator=(const string name);

    /**
     * 重载等于判断运算符，赋值对象
     *
     * @param person
     */
    bool operator==(const Person &person);

    /**
     * 重载不等于运算符
     * @param person
     */
    bool operator!=(const Person &person);

    /**
     * 输出对象的属性值
     */
    void show() const;

    friend ostream &operator<<(ostream &os, const Person &person);

private:
    int m_age;
    string m_name{};
    int m_length{0};
    Person *m_p{};
};