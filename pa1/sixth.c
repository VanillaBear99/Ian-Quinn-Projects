#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <string.h>
//BTS Node Structure
typedef struct BTSnode {
    int data;
    struct BTSnode *left;
    struct BTSnode *right;
} BTSnode;
//Creating New BTSNode
BTSnode *newNode(int data){
    BTSnode *newBTS = (BTSnode *)malloc(sizeof(BTSnode));
    newBTS->data = data;
    newBTS->left = NULL;
    newBTS->right = NULL;
    return newBTS;
}

BTSnode *insertBTS(BTSnode *root, int data)
{
    //Empty BTS
    if (root==NULL)
    {
        root = newNode(data);
        return root;
    }
    //Left subtree
    else if(data < root->data)
    {
        root->left = insertBTS(root->left, data);
    }
    //Right subtree
    else if(data > root->data)
    {
        root->right = insertBTS(root->right, data);
    }
    //Duplicate
    else
    {
        return root;
    }
    //Make it compile
    return root;
}
void printTree(BTSnode *root)
{
    if(root!=NULL)
    {
		//Go to the left subtree if it exists
        printTree(root->left);
		//Print current entry
        printf("%d\t", root->data);
		//Go to the left subtree if it exists
        printTree(root->right);
    }
}

int main(int argc, char* argv[])
{
	//Open file from command line
   FILE * file = fopen(argv[1], "r");
   //If the file is empty
    if(file == NULL)
    {
        printf("error");
        exit(3);
    }
    BTSnode *root = NULL;
    char operation[5];
    int data;
	//Scan the file
    while(fscanf(file, "%s%d", operation, &data) == 2)
    {
        if(strcmp(operation, "i") == 0)
        {
            root = insertBTS(root, data);
        }
        else
        {
            printf("error");
            exit(1);
        }
    }
    printTree(root);
    return 0;
}
