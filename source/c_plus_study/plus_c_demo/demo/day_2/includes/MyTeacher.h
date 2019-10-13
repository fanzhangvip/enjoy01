//
// Created by Zero on 2019/10/13.
//
#pragma once // //只包含一次

#ifndef C_PLUS_STUDY_MYTEACHER_H
#define C_PLUS_STUDY_MYTEACHER_H


class MyTeacher {
private:
    int m_age;
    char m_name[32];

public:

    void setAge(int age);
    int  getAge();
};


#endif //C_PLUS_STUDY_MYTEACHER_H
