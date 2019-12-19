//This program demonstrates the bounds-checking capabilities
//of the IntArray class
#include <iostream>
#include "intarray.h"
using namespace std;

int main()
{
	IntArray table(10);
        int x; // Loop index

	// Store values in the array
	for (x = 0; x < 10; x++)
		table[x] = x;
	// Display the values in the array
	for (x = 0; x < 10; x++)
		cout << table[x] << " ";
	cout << endl;

	cout << "Now attempting to store a value in table[11].\n";
    	table[11] = 0;
	return 0;
}
