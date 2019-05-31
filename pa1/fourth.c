#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
//Matrix Structure
typedef struct Matrix
{
	int **matrix;
    int rows;
    int columns;
    
}Matrix;
//Create a new Matrix
Matrix* newMatrix(int rows, int columns)
{

    Matrix * tempMatrix = malloc(sizeof(Matrix));
    tempMatrix->rows = rows;
    tempMatrix->columns = columns;
	//Allocate the space for the matrix
    tempMatrix->matrix = (int**)malloc(sizeof(int*) * rows);
    int x =0;
	for (int x = 0; x < rows; x++)
	{
		tempMatrix->matrix[x] = (int*)calloc(columns, sizeof(double));
	}
    return tempMatrix;
}
int main(int argc, char* argv[])
{
    //Get the input file locations
    FILE * file = fopen(argv[1], "r");
    //Create the First Matrix
    int row1;
    int col1;
    fscanf(file, "%d%d", &row1, &col1);
    Matrix * matrix1 = newMatrix(row1, col1);
	//Fill out first matrix
    for(int x = 0; x < row1; x++)
    {
        for(int y = 0; y < col1; y++)
        {
            fscanf(file, "%d", &matrix1->matrix[x][y]);
        }
    }
	//Create Second Matrix
    int row2;
    int col2;
    fscanf(file, "%d%d", &row2, &col2);
    Matrix * matrix2 = newMatrix(row2, col2);
	//Fill out second matrix
    for(int x = 0; x < row2; x++)
    {
        for(int y = 0; y < col2; y++)
        {
            fscanf(file, "%d", &matrix2->matrix[x][y]);
        }
    }
	//Determine if the matrices are compatible
    if(matrix2->rows != matrix1->columns)
    {
        printf("bad-matrices");
        exit(3);
    }
	//Create Result Matrix
    Matrix * matrix3 = newMatrix(row1, col2);
    int sum = 0;
	//Fill out result matrix
    for(int x = 0; x < row1; x++)
    {

        for(int y = 0; y< col2; y++)
        {
            sum = 0;
            for(int z = 0; z < row2; z++)
            {
                sum = sum + (matrix1->matrix[x][z] * matrix2->matrix[z][y]);
            }
            matrix3->matrix[x][y] = sum;
        }
    }
    //Print resulting Matrix
    for(int x = 0; x < row1; x++)
    {
        for(int y = 0; y < col2; y++)
        {
            printf("%d\t", matrix3->matrix[x][y]);
        }
        printf("\n");
    }
    return 0;
}
