#include <stdio.h>

int main07() {

    int a[4][3] = {
            {1,  2,  3},
            {4,  5,  6},
            {7,  8,  9},
            {10, 11, 12}
    };
    int (*p)[3] = a;

    printf("p   = %p\n",p);
    printf("p+1 = %p\n",p+1);
    putchar('\n');
    printf("&a   = %p\n",&a);
    printf("&a+1 = %p\n",&a+1);
    putchar('\n');
    int (*p2)[4][3] = &a;
    printf("p2   = %p\n",p2);
    printf("p2+1 = %p\n",p2+1);
    putchar('\n');

    printf(" sizeof(int)        = %d\n", sizeof(int));
    printf(" sizeof(int*)        = %d\n", sizeof(int*));
    printf(" sizeof(a[0])       = %d\n", sizeof(&a[0]));
    printf(" sizeof(0xffffcc04) = %d\n", sizeof(0xffffcbdc));
    printf(" sizeof(a)          = %d\n", sizeof(a));//数组类型的长度
    printf("sizeof(&a)          = %d\n", sizeof(&a));//计算的是指针的长度
    putchar('\n');
    int i, j;
    //打印第一行的元素
    for (i = 0, j = 0; j < 3; j++) {
        printf("%d\t", *(*p + j));
    }
    putchar('\n');
    //打印第二行的元素
    for (i = 1, j = 0; j < 3; j++) {
        printf("%d\t", *(p[i] + j));
    }
    putchar('\n');
    //打印第三行的元素
    for (i = 2, j = 0; j < 3; j++) {
        printf("%d\t", (*(p + i))[j]);
    }
    putchar('\n');
    //打印第四行的元素
    for (i = 3, j = 0; j < 3; j++) {
        printf("%d\t", *(&p[0][0] + i * 3 + j));
    }
    putchar('\n');
    //打印第四行的元素，变形
    for (i = 3, j = 0; j < 3; j++) {
        printf("%d\t", (&p[0][0])[i * 3 + j]);
    }
    putchar('\n');

    return 0;
}