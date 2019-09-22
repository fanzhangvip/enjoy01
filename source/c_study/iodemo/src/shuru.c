#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main01(){
    char a[10] = {0};
    scanf("%s",a);//存在缓冲区溢出的问题，scanf认为回车 空格 都是结束输入
    printf("%s\n",a);
    return 0;
}

int main011(){

    float a;
//    scanf("%6.2f",&a);//scanf没有精度控制
    scanf("%f",&a);
    printf("a=%f\n",a);
    return 0;
}

int main012(int argc, char** argv){
    //TODO:
    printf("argc=%d\n",argc);
    int i = 0;
    while(argv[i]){
        printf("argv[%d] = %s\n",i,*(argv+i));
        i++;
    }
    return 0;
}

int main013(){
    //TODO:
    char a,b,c;
    scanf("%c%c%c",&a,&b,&c);//在以%c格式输入字符数据时，所有输入的均为有效字符
    printf("a=%c,b=%c,c=%c\n",a,b,c);//d e f
                                     //a=d,b= ,c=e
    return 0;
}

int main014(){
    //TODO:
    char *q = (char*)malloc(sizeof(char*)*10),*p =  (char*)malloc(sizeof(char*)*10);
    scanf("%s%s",q,p);//如果以%s读入字符串，则空格和回车键都可以作为输入结束的标志
    printf("q=%s,p=%s\n",q,p);
    return 0;
}

int main015(){
    //TODO:
    int a,b,c;
//    scanf("%d,%d,%d,",&a,&b,&c);//如果在格式控制字符串中有非格式字符，则输入时要照原样输入
    scanf("%d%d%d",&a,&b,&c);
    printf("a=%d,b=%d,c=%d\n",a,b,c);
    return 0;
}

int main016(){
    //TODO:
    int n;
    while(scanf("%d",&n)==1 && n!=0){
//        getchar();//没加getchar  输入1b n=1,ch=b 加了 输入1 b
        char ch;
        scanf("%c",&ch);
        printf("n=%d,ch=%c\n",n,ch);
    }
    return 0;
}

int main017(){
    //TODO:
    int n;
    while(scanf("%d%*c",&n)==1 && n!=0){//加上%*c即可
        // "*"符用以表示该输入项，读入后不赋予相应的变量，即跳过该输入值
        char ch;
        scanf("%c",&ch);
        printf("n=%d,ch=%c\n",n,ch);
    }
    return 0;
}

int main(){
    //TODO:
    char s1[10] = "12345";
    char *p;
    strcpy(p,s1);//出错，因为在指针没有被赋值的时候，即没有让指针指向特定的内存单元时，不能用指针进行操作
    printf("s1=%s,p=%s\n",s1,p);
    return 0;
}

int main02(){
    char a[10] = {0};
    gets(a);//存在缓冲区溢出的问题，gets认为回车是结束输入
    printf("%s\n",a);
    return 0;
}

int main03(){
    char a[10] = {0};
    fgets(a,sizeof(a),stdin);//认为回车也是用户要输入的
    printf("%s",a);
    return 0;
}
