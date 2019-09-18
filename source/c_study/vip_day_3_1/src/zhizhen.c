#include <stdio.h>
#include <stdlib.h>//引入头文件 java import

int main01() {

    printf("Hello world!\n");

    //空指针
    int *ptr_i =NULL;//给初始化指针赋值为NULL,free之后 置为NULL 
    //*ptr_i //这种操作是不允许

    //通用指针 void*
    int n;
    char c;
    short s;
    //指针类型 分别是int char short
    int *ptr_n = &n;
    char *ptr_c = &c;
    short *prt_s = &s;

    printf("ptr_n=%p\n",ptr_n);
    void *ptr_void =ptr_n;//把一个整型指针转化成了一个通用指针，地址不会改变，但是类型信息丢失
    printf("ptr_void=%p\n",ptr_void);

    int *ptr_n_1 = (int*)ptr_void;

    double *ptr_double = (double*)ptr_n;//语法正确，存在语义错误，编译器无法识别

    return 0;
}

int main02(){

    //指针的算术运算
    //1, 对于变量，不同的类型，占用的空间大小是不一样
    //2, 对于不同类型的指针变量，它们占用的空间都是相同的
    //为了指针算术运算

    int i = 100;
    int *prt_i = &i;
    printf("prt_i=%p\n",prt_i);
    printf("prt_i+1=%p\n",prt_i+1);
    printf("prt_i+2=%p\n",prt_i+2);

    short c = 100;
    short *prt_c = &c;
    printf("prt_c=%p\n",prt_c);
    printf("prt_c+1=%p\n",prt_c+1);
    printf("prt_c+2=%p\n",prt_c+2);

    printf("sizeof(void)=%d\n",sizeof(void) );
    //指针占4位，步进长度与其所指类型有关
    //只能进行加法和减法：+，-， ++，--，+=，-=

    return 0;
}

int main03(){

    //指针与一维数值  
    //数组名就是数值的起始地址，也是第一个元素的地址，数组名就是一个常量指针

    int array[] = {1,2,3,4,5};

    int *ptr_array = array;

    // ptr_array = ptr_array +1;

    printf("ptr_array=%p\n", ptr_array);
    printf("&array=%p\n", &array);
    printf("&array[0]=%p\n", &array[0]);

    ptr_array[2] = 100;
    *(ptr_array +3) = 200;
    printf("array[2]=%d\n",array[2]);

    //*(array+3), *&array[3],*(ptr_array+3),*&ptr_array[3] 代表的是array[3]
    printf("*(array+3)=%d\n",*(array+3));
    printf("*&array[3]=%d\n",*&array[3]);
    printf("\n");
    printf("*(ptr_array+3)=%d\n",*(ptr_array+3));
    printf("\n");
    printf("ptr_array[3]=%d\n",ptr_array[3]);
    printf("&(ptr_array[3])=%p\n",&(ptr_array[3]));
    printf("*&(ptr_array[3])=%d\n",*&(ptr_array[3]));
    printf("\n");
    //array+3,&array[3], ptr_array+3,&ptr_array[3] 代表是同一个地址
    printf("array+3=%p\n",array+3);
    printf("&array[3]=%p\n",&array[3]);
    printf("ptr_array+3=%p\n",ptr_array+3);
    printf("&ptr_array[3]=%p\n",&ptr_array[3]);

    return 0;
}

int main(){


    //数组指针与指针数组
    //* [] ()
    // int (*ptr_array)[5] 数组指针 
    // int *ptr_arr[5]  指针数组

    int array[] = {1,2,3,4,5};
    int *ptr_array = array;
    int (*ptr_arr)[5] = &array;

    printf("array=%p\n",array);
    printf("&array=%p\n",&array);

    printf("\n");

    printf("array+1=%p, *(array+1)=%d\n",array+1,*(array+1));
    printf("&array+1=%p,*(&array+1)=%d\n",&array+1,*(&array+1));//偏移的array[5]类型的 20字节
    printf("ptr_arr+1=%p,*(ptr_arr+1)=%d\n",ptr_arr+1,*(ptr_arr+1));


    return 0;
}

int main05(){


   
    int array[] = {1,2,3,4,5};
    //int *ptr_array = &array;//错误的 把它当成了一个int类型的指针

    //int (*ptr_array)[4] = &array;//这里会编译报错，(*)[4] = &array-> (*)[5] 类型不同
    int (*ptr_array)[5] = &array;//数组指针

    int *p=NULL;

    int i = 0;
    for(i = 0; i< 5; i++){
        printf("array[%d]=%d, *(array +%d)=%d\n",i,array[i],i,*(array+i));
        printf("&array[%d]=%p, array+%d=%p\n",i,&array[i],i,array+i);
    }

    printf("\n");
    //以指针的方式
    for(p = array; p < array +5;p++){
        printf("p=%p, *p=%d\n",p,*p);
    }
    
    return 0;
}





