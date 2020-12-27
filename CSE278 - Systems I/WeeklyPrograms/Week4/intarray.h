#ifndef INTARRAY_H
#define INTARRAY_H
#include <iostream>
using namespace std;

class IntArray
{
private:
	int *aptr;
	int arraySize;
	void subError();          // Handles subscripts out of range
public:
	IntArray(int);  				// Constructor
	IntArray(const IntArray &); 	      // Copy constructor
	~IntArray(); 			      // Destructor
	int size()
		{ return arraySize; }
	int &operator[](int);               // Overloaded [] operator
};

#endif
