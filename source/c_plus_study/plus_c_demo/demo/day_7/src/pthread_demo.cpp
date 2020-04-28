//
// Created by Zero on 2019/10/10.
//

#include <iostream>
#include <string.h>

#include <pthread.h>
#include <unistd.h>

#include "myTest.h"

int main(){

    int[5] *arr;
    int (*arr1) [5];

    char c[65];
    int i = snprintf(c,4,"%s","avcdfg");
    printf("c=%s\n",c);
    printf("i=%d\n",i);

    test(1,2);
    return 0;
}

