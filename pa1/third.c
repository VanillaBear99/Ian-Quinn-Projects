#include <stdio.h>
#include <conio.h>
#include <stdlib.h>
#include<string.h>
//hashnode structure
typedef struct hashNode
{
    int hashkey;
    int data;
    struct hashNode *next;
}hashNode;
//Global Variables
hashNode* hashTable[10000];
int collisions = 0;
int searches = 0;
//Hashing the Data into an index
int gethashcode(int data)
{
    int hashCode = data %10000;
    if(hashCode < 0)
    {
        hashCode = hashCode + 10000;
    }

    return hashCode;
}
//Create new HashNode
hashNode *newHashNode(int data, int index)
{
    hashNode* tempNode = (hashNode*)malloc(sizeof(hashNode));
    tempNode->data = data;
    tempNode->hashkey = index;
    tempNode->next = NULL;
    return tempNode;
}
void hashInsert(int data)
{
	//Create new Node
    int index = gethashcode(data);
    hashNode* newNode = newHashNode(data, index);
	//If bucket is empty
    if(hashTable[index] == NULL)
    {
        hashTable[index] = newNode;
    }
    else
    {
		//New value is smaller than rest of values
        if(hashTable[index]->data > newNode->data)
        {

            collisions++;
            newNode->next = hashTable[index];
            hashTable[index] = newNode;
            return;
        //If they are the same
        if(hashTable[index]->data == newNode->data)
        {
			collisions++;
            return;
        }
        hashNode* temp = hashTable[index];
		//Find place to insert node
        while(temp->next != NULL && temp->next->data < newNode->data)
        {
            temp = temp->next;
        }
        if(temp->next == NULL)
        {
            temp->next = newNode;
            collisions++;
            return;
        }
        if(temp->next->data == newNode->data)
		{
			collisions++;
            return;
        }
		//Insert
        newNode->next = temp->next;
        temp->next = newNode;
        collisions++;
    }
    return;

}
void hashSearch(int data)
{
	//Find the index
    int index = gethashcode(data);
	//If list doesn't exist
    if(hashTable[index] == NULL)
    {
        return;
    }

    else
    {
		//Search for entry in list
        hashNode* temp = hashTable[index];
        while(temp != NULL)
        {
            if(temp->data == data)
            {
                searches++;
                return;
            }
            temp = temp->next;
        }
		//If entry doesn't exist
        if(temp == NULL)
        {
            return;
        }
    }
    return;
}
int main(int argc, char* argv[])
{
	//Open the files
    FILE * file = fopen(argv[1], "r");
	//Populate hashtable with null buckets
    for(int i = 0; i < 10000; i++)
    {
        hashTable[i] = NULL;
    }
	//If file is empty
    if(file == NULL)
    {
        printf("error");
        exit(1);
    }
    char operation[5];
    int number;
	//Scan the file
    while(fscanf(file, "%s%d", operation, &number) == 2)
    {
        //Insert
        if(strcmp(operation, "i") == 0)
        {
            hashInsert(number);
        }
        //Search
        else if(strcmp(operation, "s")== 0)
        {
            hashSearch(number);
        }
		//Return error
        else
        {
            printf("error");
            exit(2);
        }
    }
    fclose(file);
	//Print Collisions and Searches
    printf("%d\n", collisions);
    printf("%d", searches);
    return 0;
}
