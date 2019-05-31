#include <stdio.h>
#include <stdlib.h>
#include <string.h>
typedef struct llNode
{
    int data;
    struct llNode * next;
}llNode;

llNode *newLLNode(int data)
{
    llNode * tempNode = (llNode*)malloc(sizeof(llNode));
    tempNode->data = data;
    tempNode->next = NULL;
    return tempNode;
}
int listSize = 0;
int main(int argc, char* argv[])
{
    //Getting file name from user
   FILE * file = fopen(argv[1], "r");
   //Print error if file is empty or does not exist
   if(file == NULL)
    {
        printf("error");
        exit(1);
    }
    //Scanning the File
    char action[5];
    int data;
    llNode* head = NULL;
    while(fscanf(file, "%s%d", action , &data) == 2)
    {
        //Insert a Node
        if(strcmp(action, "i") == 0)
        {
            llNode * tempNode = newLLNode(data);
            //If list is empty
            if(head == NULL)
            {
                head = tempNode;
                listSize++;
            }
            //If new node is smallest value on list
            else if(tempNode->data <= head->data)
            {
                tempNode->next = head;
                head = tempNode;
                listSize++;
            }
            else
            {
                llNode * currentNode = head;
                llNode * previousNode = NULL;
                //Traverse list to find place for new node
                while(currentNode != NULL && tempNode->data > currentNode->data)
                {
                    previousNode = currentNode;
                    currentNode = currentNode->next;
                }
                //If at the end of the list
                if(currentNode == NULL)
                {
                    previousNode->next = tempNode;
                    listSize++;
                }
                //Regular Insertion
                else
                {
                    previousNode->next = tempNode;
                    tempNode->next = currentNode;
                    listSize++;
                }
            }
        }
        //Delete a Node
        else if(strcmp(action, "d")== 0)
        {
            //If list is empty
            if(head == NULL)
            {
                continue;
            }
            if(head->data == data)
            {
                head = head->next;
                listSize--;
                continue;
            }
            llNode * currentNode = head;
            llNode * previousNode = NULL;
            //Find entry to be deleted
            while(currentNode != NULL && currentNode->data != data)
            {
                previousNode = currentNode;
                currentNode = currentNode->next;
            }
            //If entry if not found
            if(currentNode == NULL)
            {
                continue;
            }
            //Regular Deletion
            else if(currentNode->data == data)
            {
                previousNode->next = currentNode->next;
                free(currentNode);
                listSize--;
            }
        }
        //Return Error
        else
        {
            printf("error");
            exit(2);
        }
    }
    fclose(file);
    //If the list is empty
    if(head == NULL)
    {
        printf("%d\n", 0);
        return 0;
    }

    llNode *currentNode = head;
    //Print size
    printf("%d\n", listSize);
    //Print the rest of the list
    while(currentNode != NULL)
    {
        //If at the end of the list;
        if(currentNode->next == NULL)
        {
            printf("%d\t", currentNode->data);
            break;
        }
        //Make sure no duplicate values are printed
        if(currentNode->data == currentNode->next->data)
        {
            currentNode = currentNode->next;
            continue;
        }
        printf("%d\t", currentNode->data);
        currentNode = currentNode->next;
    }
    return 0;
}

