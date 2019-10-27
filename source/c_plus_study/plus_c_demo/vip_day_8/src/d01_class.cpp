//
// Created by Zero on 2019/10/10.
//

#include <iostream>
#include <string.h>

#include "MyString.h"
using namespace std;//使用命名空间std标准的命名空间



int main(){
    MyString s1;
    MyString s2("s2");
    MyString s2_2 = "";
    MyString s3 = s2;
    MyString s4 = "s4444444444";

    s4 = s2;
    s4 = "s2222";
    s4[1] = '4';
    printf("%c", s4[1]);

    

    cout<<s4 <<endl;
    return 0;
}



