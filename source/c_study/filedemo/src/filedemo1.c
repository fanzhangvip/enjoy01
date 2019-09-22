#include <stdio.h>
#include <string.h>

#include <stdlib.h>
#include <time.h>

char a[] = "/Users/wanghuajun/zero/project/enjoy01/source/c_study/cmake-build-debug/filedemo/a.txt";

int main1() {

    //TODO: 文件打开关闭演示
    printf("文件打开关闭演示\n");


//    FILE *p = fopen(a,"r");
    FILE *p = fopen(a, "w");

    if (p) {
        printf("success\n");
        goto CLOSE;
    } else {
        printf("fail\n");

    }

    CLOSE:
    {
        fclose(p);
        p = NULL;
        printf("p = %p\n", p);

    };

    return 0;
}

int main2() {
    //TODO: 文件读取演示
    printf("文件读取演示\n");

    FILE *p = fopen(a, "r");
    if (p) {
        char c = getc(p);
        while (c != EOF) {
            printf("%c", c);
            c = getc(p);

        }
        goto CLOSE;
    } else {
        printf("fail\n");

    }

    CLOSE:
    {
        fclose(p);
        p = NULL;
    };
    printf("\n");

    return 0;
}

int main3() {
    //TODO: 文件写入演示
    printf("文件写入演示\n");

    FILE *p = fopen(a, "w");
    if (p) {
        putc('a',p);
        putc('\n',p);
        putc('b',p);

        goto CLOSE;
    } else {
        printf("fail\n");

    }

    CLOSE:
    {
        fclose(p);
        p = NULL;
    };
    printf("\n");

    return 0;
}

int main4(int argc, char** argv){
    //TODO: 命令行参数指定文件名写文件
    printf("命令行参数指定文件名写文件\n");

    if(argc < 2)
        return 0;
    FILE *p = fopen(argv[1],"w");
    if(p){
        char c = getchar();
        while(c !='0'){
            putc(c,p);
            c = getchar();
        }
        fclose(p);
    }

    return 0;
}


int main5(int argc, char** argv){
    //TODO: 命令行参数指定文件名读文件
    printf("命令行参数指定文件名读文件\n");

    if(argc < 2)
        return 0;
    FILE *p = fopen(argv[1],"r");
    if(p){
        char c = getc(p);
        while(c != EOF){
            printf("%c",c);
            c = getc(p);
        }
        fclose(p);
    }

    return 0;
}

int main6(int argc, char** argv){
    //TODO: 带参数的文件copy
    printf("带参数的文件copy\n");

    if(argc <3) return 0;
    FILE *pw = fopen(argv[2],"w");
    if(pw == NULL) return 0;
    FILE *pr = fopen(argv[1],"r");
    if(pr){
        char c = getc(pr);
        while(c !=EOF){
            putc(c,pw);//从pr里面每读一个char,就往pw里面写一个char
            c = getc(pr);
        }
        fclose(pw);
        fclose(pr);
    }

    return 0;
}


int main7(int argc, char** argv){
    //第三个参数 0代表加密，1代表解密
    //TODO: 加密解密的文件copy
    printf("加密解密的文件copy\n");

    if(argc <4) return 0;
    FILE *pw = fopen(argv[2],"w");
    if(pw == NULL) return 0;
    FILE *pr = fopen(argv[1],"r");
    if(pr){
        char key = argv[3][0];
        printf("key=%c\n",key);

        char c = getc(pr);
        while(c !=EOF){
            if(key == '0')
                c++;
            else
                c--;
            putc(c,pw);//从pr里面每读一个char,就往pw里面写一个char
            c = getc(pr);
        }
        fclose(pw);
        fclose(pr);
    }

    return 0;
}

int main8(int argc,char **argv){
    //TODO: fputs
    printf("fputs\n");

    if(argc < 2)return 0;

    FILE *p = fopen(argv[1],"w");
    if(p){
//        fputs("hello world\n",p);
//        fclose(p);

        while (1){
            char buf[1024] = {0};
            fgets(buf, sizeof(buf),stdin);
            if(strncmp(buf,"exit",4) == 0){
                break;
            }
            fputs(buf,p);
        }
    }

    return 0;
}

int main9(){
    FILE *p = fopen("a.txt","r");
    if(p == NULL)return 0;
    char buf[1024] = {0};

    fgets(buf, sizeof(buf),p);
    printf("%s",buf);
    fclose(p);
}

int main10(){
    //TODO: fgets读取文件
    printf("fgets读取文件\n");

    FILE *p = fopen("a.txt","r");
    FILE *p1 = fopen("b.txt","w");
    if(p == NULL) return 0;
    if(p1 == NULL) return 0;
    while(!feof(p)){//只要没有到文件的结尾，那么循环就继续
        char buf[1024] = {0};

        fgets(buf, sizeof(buf),p);
        printf("%s",buf);
        fputs(buf,p1);


    }
    fclose(p1);
    fclose(p);

    return 0;
}

void decode(char *s){
    int len = 0;
    while(s[len]){
        s[len]++;
        len++;
    }
}

void encode(char *s){
    int len = 0;
    while(s[len]){
        s[len]--;
        len++;
    }
}

int main11(int argc, char** argv){
    //TODO: fputs,fgets加密解密，第三个参数 0表示加密，1表示解密
    printf("fputs,fgets加密解密，第三个参数 0表示加密，1表示解密\n");

    if(argc < 4)return 0;

    FILE *pr = fopen(argv[1],"r");
    if(pr == NULL)return 0;
    FILE *pw = fopen(argv[2],"w");
    if(pw == NULL)return 0;

    char key = argv[3][0];
    while(!feof(pr)){
        char buf[1024] = {0};
        fgets(buf, sizeof(buf),pr);
        if(key == '1')
            decode(buf);
        else
            encode(buf);

        fputs(buf,pw);
    }

    return 0;
}

int main12(){
    //TODO: sort
    printf("sort\n");

    FILE *p = fopen("a.txt","w");
    if(p){
        srand((unsigned int)time(NULL));
        int i;
        for (i = 0; i < 100000; ++i) {
            int seq = rand() % 256;
            char buf[100] = {0};
            sprintf(buf,"%d\n",seq);
            fputs(buf,p);
        }
        fclose(p);
    }

    return 0;
}

void swap(int *a,int *b){
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

void bubbleSort(int *array,size_t size){
    int i,j;
    for(i = 0; i < size;i++){
        for (j = 1; j < size -i; j++) {
            if(array[j -1] > array[j]){
                swap(&array[j-1],&array[j]);
            }
        }
    }
}

int main13(){
    //TODO: 排序
    printf("排序\n");

    FILE *p = fopen("a.txt","r");
    if(p == NULL)return 0;
    int array[100] = {0};
    int index = 0;
    while(!feof(p)){
        char buf[1024] = {0};
        fgets(buf, sizeof(buf),p);
        array[index++] = atoi(buf);//把读出来的字符串转换为整数
    }
    fclose(p);
    //给数组排序
    bubbleSort(array,100);
    p = fopen("a.txt","w");
    if(p == NULL) return 0;
    for (int i = 0; i < 100; ++i) {
        char buf[1024] = {0};
        sprintf(buf,"%d\n",array[i]);//把数组转换为字符串
        fputs(buf,p);
    }
    fclose(p);

    return 0;
}

int main14(){//大文件排序
    FILE *p = fopen("a.txt","r");
    if(p == NULL)return 0;
    int array[256] = {0};//这里是自然有序的
    while(!feof(p)){
        char buf[1024] = {0};
        fgets(buf, sizeof(buf),p);
        int a = atoi(buf);
        array[a]++;//这里因为数组是在256范围内，把数组读取转为统计对应数组的次数
        //然后只有按照顺序把对应的数字重复写array[a]遍就排好序了
    }
    fclose(p);
    p = fopen("a.txt","w");
    if(p == NULL)return 0;
    int i,j;
    for(i = 0; i < 256; i++){
        for(j = 0; j < array[i];j++){//把每个数写array[i]遍
            char buf[100] = {0};
            sprintf(buf,"%d\n",i);//把数字转为为字符串
            fputs(buf,p);
        }
    }
    fclose(p);
    return 0;
}

int func1(int a,char b,int c){
    switch(b){
        case '+':
            return a + c;
        case '-':
            return a - c;
        case '*':
            return a * c;
        case '/':
            if(c != 0)
                return a / c;
    }
    return 0;
}

//第三步
#define NUM 100

int main15(){
    //TODO: 文件追加 计算结果
    printf("文件追加\n");
    FILE *p = fopen("a.txt","r");
    if(p == NULL)return 0;
    //第二步
//    char array[100][100] = {0};
    //第三步
    char *array = calloc(NUM, sizeof(char));
    char *tmp = array;//代表当前要写入字符的位置
    int index = 0;
    //
    while(1){
        char buf[100] = {0};
        fgets(buf, sizeof(buf),p);//假设读到最后一行了，feof不会返回true
        //已经到了最后一行，再次调用fgets,feof才返回true
        if(feof(p))
            break;
        int a = 0;
        char b = 0;
        int c = 0;
        sscanf(buf,"%d%c%d=",&a,&b,&c);
        printf("%d%c%d=%d\n",a,b,c,func1(a,b,c));

        //第二步
//        sprintf(array[index],"%d%c%d=%d\n",a,b,c,func1(a,b,c));
        //
        //第三步
        sprintf(tmp,"%d%c%d=%d\n",a,b,c,func1(a,b,c));
        array = realloc(array,NUM *(index +2));
        tmp = array + (NUM * (index+1));//array永远指向堆内存的首地址，tmp每次往后移动100个字节
        index++;


    }
    fclose(p);
    //第二步
    p = fopen("a.txt","w");
    if(p == NULL)return 0;
    int i;
    //第三步
    tmp = array;//tmp回到起始位置
    for (i = 0; i < index; ++i) {
        fputs(tmp,p);
        tmp += NUM;
    }
    fclose(p);
    free(tmp);
    //



    return 0;
}

int main16(){
    //TODO: fscanf与fprintf
    printf("fscanf与fprintf\n");

    FILE *p = fopen("a.txt","r");
    FILE *p1 = fopen("b.txt","w");

    while(1){
        int a = 0;
        char b = 0;
        int c = 0;
        fscanf(p,"%d%c%d=",&a,&b,&c);//fscanf是从一个文件中读取字符串，并转义
        if(feof(p))break;
        fprintf(p1,"%d%c%d=%d\n",a,b,c,func1(a,b,c));//fprintf和sprintf功能类似，只是目标是文件，而不是字符串
    }
    fclose(p);
    fclose(p1);
    return 0;
}

int main17(){
    //TODO: 姓名=张三,年龄=21
    printf("姓名=张三,年龄=21\n");

    FILE *p = fopen("a.txt","r");

    if(p == NULL) return 0;

    while(1){
//        char name[100] = {0};
        int age = 0;
        char buf[100] = {0};
//        fscanf(p,"姓名=%s,年龄=%d",name,&age);//这句有问题
        fgets(buf, sizeof(buf),p);

        if(feof(p))break;
        sscanf(buf,"年龄=%d",&age);
        printf("%d\n",age);
    }
    fclose(p);

    return 0;
}

int main18(){
    //TODO: 文本解析训练
    printf("文本解析训练\n");

    FILE *p = fopen("a.txt","r");
    if(p == NULL) return 0;
    while(1){
        char buf[1024] = {0};
        fgets(buf, sizeof(buf),p);
        if(feof(p)) break;
        char *s = strtok(buf,",");
        char *name = strchr(s,'=');
        printf("%s\n",&name[1]);
        s  = strtok(NULL,",");
        printf("%s\n",atoi(&s[7]));
    }


    return 0;
}

int main19(){
    //TODO: 数组中找第二大的
    printf("数组中找第二大的\n");

    int a[10] = {32,54,2,435,23,57,321,86,7,11};
    int max = 0;
    int smax = 0;//第二大
    int i;
    for (i = 0; i < 10; i++) {
        if(a[i] > max){
            smax = max;
            max = a[i];
        }else if (a[i] < max && a[i] > smax){
            smax = a[i];
        }
    }
    printf("max = %d, smax = %d\n",max,smax);

    return 0;
}


int main(){
    //TODO: 文本解析训练答案
    printf("文本解析训练答案\n");

    FILE *p = fopen("a.txt","r");
    if(p == NULL) return 0;
    int max = 0;
    int smax = 0;
    char max_name[100] = {0};
    char smax_name[100] = {0};

    while(1){
        char buf[1024] = {0};
        fgets(buf, sizeof(buf),p);
        if(feof(p)) break;
        char *s = strtok(buf,",");
        char *name = strchr(s,'=');
        printf("%s\n",&name[1]);
        s  = strtok(NULL,",");
        printf("%s\n",atoi(&s[7]));
        if(atoi(&s[7] > max)){
            smax = max;
            max = atoi(&s[7]);
            strcpy(smax_name,max_name);
            strcpy(max_name,&name[1]);
        }else if(atoi(&s[7] < max && atoi(&s[7] > smax))){
            smax = atoi(&s[7]);
            strcpy(smax_name,&name[1]);
        }
    }

    printf("%s\n",smax_name);

    return 0;
}

