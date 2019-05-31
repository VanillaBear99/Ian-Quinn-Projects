#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char *argv[])
{
	FILE *file = fopen(argv[1], "r");
	if (file == NULL)
	{
		printf("empty file");
		return 0;
	}
	unsigned short x;
	fscanf(file, "%d", &x);
	printf("%d", x);
	unsigned short n, v;
	char action[5];
	while (fscanf(file, "%s%d%d", action, n, v) == 3)
	{
		if (strcmp(action, "set") == 0)
		{
			set(x, n, v);
		}
		if (strcmp(action, "comp") == 0)
		{
			comp(x, v);
		}
		if (strcmp(action, "get") == 0)
		{
			get(x, v);
		}
		else
		{
			printf("invalid file");
			return 0;
		}
	}
}
void set(unsigned short x, unsigned short n, unsigned short v)
{

}
void comp(unsigned short x, unsigned short v)
{

}
void get(unsigned short x, unsigned short v)
{

}
