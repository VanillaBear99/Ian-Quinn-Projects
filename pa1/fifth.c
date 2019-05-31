
int main(int argc, char* argv[])
{
	//Take the command line input as a string
    //char *argv = argv[0];
	//Find the length of the string
    //int a = 0;
    //while((argv[a] = getchar()) != '\n' && a < 100)
    //{
        //a++;
    //}
	//Make the end blank
    //argv[a] = '\0';
    int i = 0;
	//Traverse stringa and print out vowels regardless of case
    while(argv[i] != '\0')
    {
        if(argv[i] == 'a' || argv[i] == 'A')
        {
            printf("%c", argv[i]);
            i++;
            continue;
        }
        if(argv[i] == 'e' || argv[i] == 'E')
        {
            printf("%c", argv[i]);
            i++;
            continue;
        }
        if(argv[i] == 'i' || argv[i] == 'I')
        {
            printf("%c", argv[i]);
            i++;
            continue;
        }
        if(argv[i] == 'u' || argv[i] == 'U')
        {
            printf("%c", argv[i]);
            i++;
            continue;
        }
        if(argv[i] == 'o' || argv[i] == 'O')
        {
            printf("%c", argv[i]);
            i++;
            continue;
        }
        else{
            i++;
            continue;
        }
    }
    return 0;
}
