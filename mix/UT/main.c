#include<stdio.h>
#include<math.h>
#include<malloc.h>
#include<string.h>
#include <stdlib.h>
#define N 13
int isPrime(long number) 
{
    int iRet = 1;
    long l_right_bound = sqrt(number);
    for (int i = 2; i <= l_right_bound; i++)
        if (number % i == 0)
        {
            iRet = 0;
            break;
        }
    return iRet;
}
long less7(int number)
{
    long l_result = 2;
    int iCounter = 1;
    long k = 2;
    while (iCounter < number)
    {
        k++;
        if (isPrime(k)==1)
        {
            iCounter++;
        }
    }
    return k;
}
void main(){
	printf("7 is prime? %d", isPrime(7));
	printf("8 is prime? %d", isPrime(8));
	printf("all is prime less than 8? %d", less7(8));
}
