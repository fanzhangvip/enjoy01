#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main()
{
    char *str;

    /* 最初的内存分配 */
    str = (char *) malloc(sizeof(char) * 15);

   str = (char *) calloc(15,sizeof(char));
    // memset(str,0,15);
    printf("Memory Allocated at: %x\n",str);
    int i=0;
    for (i=0;i<15;i++)
    {
        printf("%3d",str[i]);
    }
    printf("\n");
    strcpy(str, "c study");
    printf("String = %s,  Address = %p\n", str, str);

    /* 重新分配内存 */
    str = (char *) realloc(str, sizeof(char) * 50);
    strcat(str, ": https://ke.qq.com/course/list/");
    printf("String = %s,  Address = %p\n", str, str);

    free(str);
    printf("String = %s,  Address = %p\n", str, str);
    str = NULL;
    printf("String = %s,  Address = %p\n", str, str);
    return 0;
}