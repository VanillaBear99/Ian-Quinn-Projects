#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

int main(int argc, char *argv[])
{
	unsigned short number = (unsigned short)atoi(argv[1]);
	if (number < 384)
	{
		printf("Not-Palindrome");
		return 0;
	}
	unsigned short bit1;
	unsigned short bit2;
	unsigned short mask1;
	unsigned short mask2;
	int i = 0;
	int j = 15;
	bool palindrome = true;
	while (i < j)
	{
		mask1 = number;
		mask2 = number;
		bit1 = (mask1 >> i) & 1;
		bit2 = (mask2 >> j) & 1;
		if (bit1 == bit2)
		{
			i++;
			j--;
			continue;
		}
		else
		{
			palindrome = false;
			break;
		}
	}
	if (palindrome == false)
	{
		printf("Not-Palindrome");
	}
	else
	{
		printf("Is-Palindrome");
	}
	return 0;
}