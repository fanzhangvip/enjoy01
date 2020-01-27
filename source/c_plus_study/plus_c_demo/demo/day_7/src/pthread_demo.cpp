//
// Created by Zero on 2019/10/10.
//

#include <iostream>
#include <string.h>

#include <pthread.h>
#include <unistd.h>

int main(){


//    char c[65];
//    int i = snprintf(c,4,"%s","avcdfg");
//    printf("c=%s\n",c);
//    printf("i=%d\n",i);
    char strArray[][10] = {"dfasdkf","df"};
    printf("strArray[%d]=%s\n",0,strArray[0]);

    char *strArrPoint[10] = strArray;

    printf("strArrPoint=%s\n",strArrPoint+1);

    return 0;
}

