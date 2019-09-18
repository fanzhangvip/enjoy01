#include <stdio.h>

int main() {
    printf("Hello, World!\n");
    printf("享学C基础教学\n");

    printf("%d\n",sizeof(int));
    printf("%d\n",sizeof(long));
    printf("%d\n",sizeof(long long));
    printf("%d\n",sizeof(char));
    printf("%d\n",sizeof(short));
    printf("%d\n",sizeof(float));
    printf("%d\n",sizeof(double));
    printf("%d\n",sizeof(long double));
    printf("===========\n");
    printf("%d\n",-1);
    printf("%u\n",-1);

    char c = 128;
    printf("%d\n",c);
    printf("%hhd\n",c);
    printf("%hd\n",c);
    printf("%hu\n",c);

    return 0;
}

