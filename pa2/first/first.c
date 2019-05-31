#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>
#include <string.h>
unsigned short set(unsigned short x, unsigned short k, unsigned short v)
{
	x = (x &(~(1 << v))) | (k << v);
	printf("%u\n", x);
	return x;
}
unsigned short get(unsigned short x, unsigned short k)
{
	unsigned short bit;
	unsigned short mask = x;
	bit = (mask >> k) & 1;
	printf("%d\n", bit);
	return x;
}
unsigned short comp(unsigned short x, unsigned short k)
{
	unsigned short bit;
	unsigned short mask = x; 
	unsigned short y;
	bit = (mask >> k) & 1;
	if (bit == 0)
	{
		y = 1;
	}
	else if (bit == 1)
	{
		y = 0;
	}
	x = (x &(~(1 << k))) | (y << k);

	printf("%u\n", x);
	return x;
}
void choose(unsigned short x, FILE * file, char action[])
{
	unsigned short k, v;
	while (fscanf(file, "%s%u%u", action, &v, &k) == 3)
	{
		if (strcmp("set", action) == 0)
		{
			x = set(x, k, v);
			continue;
		}
		else if (strcmp("get", action) == 0)
		{
			x = get(x, v);
			continue;
		}
		else if (strcmp("comp", action) == 0)
		{
			x = comp(x, v);
			continue;
		}
		else
		{
			printf("Invalid Input\n");
			return;
		}
	}
	return;
}
int main(int argc, char * argv[])
{
	FILE * file = fopen(argv[1], "r");
	if (file == NULL)
	{
		printf("error");
		return 0;
	}
	unsigned short x;
	char action[10];
	fscanf(file, "%u", &x);
	fclose(file);
	FILE *file2 = fopen(argv[1], "r");
	char buffer[100];
	fgets(buffer, 100, file2);
	choose(x, file2, action);
	return 0;
}