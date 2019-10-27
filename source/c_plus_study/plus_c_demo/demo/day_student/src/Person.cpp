//
// Created by Biter on 2019/10/24.
//
#include "Person.h"

Person::Person(int length) : m_length(length) {
    if (length == 0) {
        m_p = nullptr;
    } else {
        std::cout << "创建对象数组" << std::endl;
        m_p = new Person[length];
    }
    cout <<"地址 = " << this << std::endl;
}

Person::Person() {
    m_age = 0;
    m_name = "default";
    std::cout << "默认构造函数 m_age = " << m_age << "; m_name = " << m_name << "； 地址 = " << this << std::endl;
}

Person::Person(Person &person) {
    m_age = person.m_age;
    m_name = person.m_name;
    std::cout << "拷贝构造函数 m_age = " << m_age << "; m_name = " << m_name << "； 地址 = " << this << std::endl;
}

Person::Person(int age, const string name) {
    m_age = age;
    m_name = name;
    std::cout << "列表初始化 m_age = " << m_age << "; m_name = " << m_name << "； 地址 = " << this << std::endl;
}

Person::~Person() {
    std::cout << "析构函数 m_age = " << m_age << "; m_name = " << m_name << "； 地址 = " << this << std::endl;
//    m_p = nullptr;
   delete[] m_p;
   m_p = nullptr;
}

void Person::show() const {
    for (int i = 0; i < m_length; i++) {
        std::cout << "Person[" << i << "].m_age = " << m_p[i].m_age << std::endl;
        std::cout << "Person[" << i << "].m_name = " << m_p[i].m_name << std::endl;
    }
}

Person &Person::operator=(const Person &person) {
    this->m_age = person.m_age;
    m_name = person.m_name;
    std::cout << "赋值运算符 = this->m_age = " << this->m_age << "; this->m_name = " << this->m_name << "  ";
    std::cout << "赋值运算符 = person.m_age = " << person.m_age << "; person.m_name = " << person.m_name << "  ";
    return *this;
}

bool Person::operator==(const Person &person) {
    std::cout << "this->m_age = " << this->m_age << "; this->m_name = " << this->m_name << "  ";
    std::cout << "person.m_age = " << person.m_age << "; person.m_name = " << person.m_name << "  ";
    if ((m_age == person.m_age) && (m_name == person.m_name)) {
        return true;
    } else {
        return false;
    }
}

bool Person::operator!=(const Person &person) {
    std::cout << "this->m_age = " << m_age << "; this->m_name = " << this->m_name << "  ";
    std::cout << "person.m_age = " << person.m_age << "; person.m_name = " << person.m_name << "  ";
    if ((this->m_age != person.m_age) || (this->m_name != person.m_name)) {
        return true;
    } else {
        return false;
    }
}

Person &Person::operator[](int i) {
//    m_p[i].m_age = i;
    std::cout << "数组索引运算符" << std::endl;
    return m_p[i];
}

Person &Person::operator=(int age) {
    m_age = age;
    return *this;
}


Person &Person::operator=(string name) {
    m_name = name;
    return *this;
}

ostream &operator<<(ostream &os, const Person &person) {
    os << "m_age = " << person.m_age << "; m_name = " << person.m_name << endl;
    return os;
}

