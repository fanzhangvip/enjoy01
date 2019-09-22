#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#pragma pack(8)    //设置4字节对齐
//#pragma pack()     //取消4字节对齐

int main1(){
    //TODO:char *a 与 char a[]的区别
    printf("Hello world!\n");

    char a = 'a';
    printf("sizeof(char)=%d\n",sizeof(char));
    printf("sizeof(a)=%d\n",sizeof(a));
    printf("sizeof('a')=%d\n",sizeof('a'));//整型字符常量
    printf("sizeof(\"a\")=%d\n",sizeof("a"));

    char* s1= "123456789";
    char* s2 = "abcdef";
    strncpy(s1,s2,6);//编译没问题，但是运行会报错，原因是企图修改s1的内容，但由于s1,s2指向的是常量字符串
                     //其内容不可修改
    printf("s1=%s,s2=%s\n",s1,s2);

    return 0;
}

int main2(){
    //TODO:可读可写
    printf("Hello world!\n");
    char s1[10] = "123456789";
    char s2[10] = "abcdef";
    strncpy(s1,s2,6);
    printf("s1=%s,s2=%s\n",s1,s2);
    return 0;
}

int main3(){
    //TODO:
    printf("Hello world!\n");
//    char str[30];
//    char *str = (char*)calloc(30,sizeof(char));
    char *str = "hello";
    gets(str);
    printf("%s\n", str);
    return 0;
}

typedef struct node1
{
    int a;
    char b;
    short c;
}S1;

typedef struct node2
{
    char a;
    int b;
    short c;
}S2;

typedef struct node3
{
    int a;
    short b;
}S3;

typedef struct node4
{
    bool a;
    S1 s1;
    short b;
}S4;

typedef struct node5
{
    bool a;
    S1 s1;
    double b;
    int c;
}S5;

int main(){
    //TODO:
    printf("Hello world!\n");

    S1 s1;
    S2 s2;
    S3 s3;
    S4 s4;
    S5 s5;
    printf("sizeof(s1)=%d\n", sizeof(s1));
    printf("sizeof(s2)=%d\n", sizeof(s2));
    printf("sizeof(s3)=%d\n", sizeof(s3));
    printf("sizeof(s4)=%d\n", sizeof(s4));
    printf("sizeof(s5)=%d\n", sizeof(s5));


    return 0;
}