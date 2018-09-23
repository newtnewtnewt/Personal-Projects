// This program uses a function to double the value of
// each element of an array.

#include <iostream>
using namespace std;

// Declare arrayType to be an alias for an array of int, 
typedef int arrayType[];    
    
// Function prototypes
void doubleArray(arrayType, int);	
void showValues(int[], int);	  //  the first parameter here could be
                                  //  int[] 
                                  //  or arrayType
int main()
{
	const int arraySize = 12;
   arrayType set = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

	// Display the original values
   cout << "The arrays values are:\n";
	showValues(set, arraySize);

	doubleArray(set, arraySize);

	// Display the new values
   cout << "\nAfter calling doubleArray, the values are:\n";
	showValues(set, arraySize);
	cout << endl;
	return 0;
}

//**************************************************
// Definition of function doubleArray              *
// This function doubles the value of each element *
// in the array passed into nums.                  *
//**************************************************
void doubleArray(arrayType nums, int size)
{
	for (int index = 0; index < size; index++)
		nums[index] *= 2;
}

//************************************************************
// Definition of function showValues                         *
// This function accepts an array of integers and its size   *
// as arguments. The contents of the array are displayed.    *
//************************************************************
void showValues (arrayType nums, int size)
{
	for (int index = 0; index < size; index++)
		cout << nums[index] << " ";
	cout << endl;
}
