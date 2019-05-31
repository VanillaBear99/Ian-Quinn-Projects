#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

int main(int argc, char *argv[])
{
	unsigned short number = (unsigned short)atoi(argv[1]);
	unsigned short prevBit;
	unsigned short bit;
	unsigned short pairs = 0;
	unsigned short even;
	bool prevPair = false;
	bool even = true;
	for (int i = 0; i < 16; i++)
	{
		unsigned short mask = number;
		bit = (mask >> i) & 1;
		if (bit == 0)
		{
			prevBit = bit;
			prevPair = false;
			continue;
		}
		else if(bit == 1)
		{
			if(prevBit == 0)
			{
				prevBit = bit;
				prevPair = false;
				continue;
			}
			else if (prevBit == 1)
			{
				if (prevPair == false)
				{
					pairs++;
					if (even == true)
					{
						even = false;
					}
					else
					{
						even = true;
					}
					prevPair = true; 
					prevBit = bit;
					continue;
				}
				else
				{
					prevBit = bit;
					prevPair = false;
					continue;
				}
			}
		}
	}
	if ((pairs % 2) == 0)
	{
		printf("Even-Parity   %u", pairs);
	}
	else
	{
		printf("Odd-Parity   %u", pairs);
	}
	return 0;
}