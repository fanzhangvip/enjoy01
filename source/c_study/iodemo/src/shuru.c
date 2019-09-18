#include <stdio.h>

int main01(){
    char a[10] = {0};
    scanf("%s",a);//存在缓冲区溢出的问题，scanf认为回车 空格 都是结束输入
    printf("%s\n",a);
    return 0;
}

int main02(){
    char a[10] = {0};
    gets(a);//存在缓冲区溢出的问题，gets认为回车是结束输入
    printf("%s\n",a);
    return 0;
}

int main(){
    char a[10] = {0};
    fgets(a,sizeof(a),stdin);//认为回车也是用户要输入的
    printf("%s",a);
    return 0;
}
