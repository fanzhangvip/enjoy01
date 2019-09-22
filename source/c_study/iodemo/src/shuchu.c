
#include <stdio.h>

int main11(){
    char a[] = "hello world";
    puts(a);//自动带了\n换行
    printf("%s\n",a);
    fputs(a,stdout);
    return 0;
}