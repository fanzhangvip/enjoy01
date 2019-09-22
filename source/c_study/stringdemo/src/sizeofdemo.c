#include <stdio.h>
#include <stdlib.h>

void func(int array[]){
    printf("func sizeof(array)= %d\n", sizeof(array));
}

int main00(){
    //TODO: 指针数组与数组指针的理解
//    二者有本质的区别，前者是存储指针的数组，后者是指向数组的指针！
//    int* a[4] 是指针数组，[]的优先级比*高，所以int* a[4]可以看作int*(a[4]), 表示数组a中的元素都为int型指针。元素可以表示为*a[i]或*(a[i])
//
//    int (*a)[4] 是数组指针，表示指向数组a的指针, 元素可以表示为(*a)[i]

    printf("Hello world!\n");
    int array[4] = {1, 2, 3, 4};
    int* a[4];//定义指针数组
    int (*b)[4];//定义数组指针
    b = &array;//将数组array的数组地址赋值给另一个数组的指针b，这时b就指向了array
    for (int k = 0; k < 4; ++k) {
        printf("%d ", (*b)[k]);
    }
    printf("\n");
    //将array赋值给a，就得一个个来了
    for (int j = 0; j < 4; ++j) {
        a[j] = &array[j];//将array里的元素地址赋值给a数组的各个元素
    }
    printf("b=%p, array=%p, a[0]=%p \n", b, array, a[0]);
    for (int n = 0; n < 4; ++n) {
        printf("%d ", *a[n]);
    }

    return 0;
}

int main01(){
    //TODO:
    printf("Hello world!\n");

    int arr[10] = {1,2,3,4};
    int *ptrArr = arr;
    //sizeof可以计算出数组的容量，却无法计算指针所指向的内存的容量
    printf("sizeof(arr) = %d, sizeof(ptrArr) = %d\n", sizeof(arr), sizeof(ptrArr));
    printf("arr           =%p\n",arr);
    printf("&arr[0]       =%p\n",&arr[0]);
    printf("ptrArr        =%p\n", ptrArr);
    printf("&ptrArr       =%p\n",&ptrArr);
    printf("(arr+1)       =%p\n",(arr+1));
    printf("(&arr +1)     =%p\n",(&arr+1));
    printf("(ptrArr +1)   =%p\n",(ptrArr + 1));
    printf("(&ptrArr +1)  =%p\n",(&ptrArr + 1));
    //ptrArr+1, arr+1, &ptrArr[1], &arr[1] 都是表示数组arr[1]的地址
    printf("&arr[1]=%p, &ptrArr[1]=%p, *(arr+1)=%p, *(ptrArr+1)=%p\n",&arr[1],&ptrArr[1],(arr+1),(ptrArr+1));
    //*(ptrArr+1), *(arr+1), ptrArr[1], arr[1]都是获取数组arr[1]的值
    printf("arr[1]=%d, ptrArr[1]=%d, *(arr+1)=%d, *(ptrArr+1)=%d\n",arr[1],ptrArr[1],*(arr+1),*(ptrArr+1));

    func(arr);

    return 0;
}

int main02(){
    //TODO:
    printf("Hello world!\n");
    int a[4][3] = {
            {100,  2,  3},
            {4,  5,  6},
            {7,  8,  9},
            {10, 11, 12}
    };
    int (*p)[3] = a;//表示是一个数组类型的指针，它可以看成是一个具有4个元素的一维指针
    //每个元素又是一个包含3个元素的一维数组，即a[0],a[1],a[2],a[3]表示第i行元素的首地址(i={0,1,2,3})
    //a[i], *(a+i)，p+i都是表示第i行第0列元素的地址
    //a[i]+j, *(a+i)+j, &a[0][0]+3*i+j, (p+i)+j都是第i行第j列元素的地址
    //首地址与它的值
    printf("a           = %p, %d\n",a,**a);
    printf("&a          = %p, %d\n",&a,a[0][0]);
    printf("a[0]        = %p, %d\n",a[0],*a[0]);
    printf("&a[0][0]    = %p, %d\n",&a[0][0],a[0][0]);
    printf("p           = %p, %d\n",p,**p);
    printf("p[0]        = %p, %d\n",p[0],*p[0]);


    putchar('\n');
    //第二行的首地址
    printf("a[2]   = %p, %d\n",a[2],*a[2]);
    printf("&a[2]  = %p, %d\n",&a[2],a[2][0]);
    printf("*(a+2) = %p, %d\n",*(a+2),**(a+2));
    printf("p+2    = %p, %d\n", p+2,**(p+2));
    putchar('\n');
    //第2行第1列的地址与第2行第1列的值
    printf("&a[2][1]     = %p, %d\n",&a[2][1],a[2][1]);//下标法
    printf("p[2][1]      = %p, %d\n",&p[2][1],p[2][1]);//下标法
    printf("a[1][4]      = %p, %d\n",&a[1][4],a[1][4]);//下标溢出,对于一维数组，下标溢出的值是不确定的，二维数组则不同

    printf("*(a+2)+1     = %p, %d\n",*(a+2)+1,*(*(a+2)+1));//指针法
    printf("*(p+2)+1     = %p, %d\n",*(p+2)+1,*(*(p+2)+1));//指针法
    printf("a[2]+1       = %p, %d\n",a[2]+1,*(a[2]+1));//下标指针混合法
    printf("p[2]+1       = %p, %d\n",p[2]+1,*(p[2]+1));//下标指针混合法
    printf("(&a[0][0]+ 2*3 +1)    = %p, %d\n",(&a[0][0]+ 2*3 +1) ,*(&a[0][0]+ 2*3 +1 ));//下标指针混合法
    printf("(p[0]+ 2*3 +1)        = %p, %d\n",(p[0]+ 2*3 +1) ,*(p[0]+ 2*3 +1 ));//下标指针混合法

//    int *p1[3] = a;//编译不过，这是表示的是一个数组，指针数组，是一个里面放三个int*指针的数组
    putchar('\n');
    return 0;
}