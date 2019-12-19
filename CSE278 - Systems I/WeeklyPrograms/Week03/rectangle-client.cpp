// This program implements the Rectangle class, with separate 
// files for the class declaration, the implementation of the 
// class functions, and the client code that uses the class.

#include <iostream>
#include "rectangle.h"      // Contains Rectangle class declaration
using namespace std;
// Remember to link this program with rectangle.cpp!

int main()
{
	Rectangle box;                     // Declare a Rectangle object
	float boxLength, boxWidth;

	//Get box length and width
	cout << "This program will calculate the area of a rectangle.\n";
	cout << "What is the length? ";
	cin  >> boxLength;
	cout << "What is the width? ";
	cin  >> boxWidth;

	// Call member functions to set box dimensions 
	if (!box.setLength(boxLength))     // Store the length
      cout << "Invalid box length entered.\n";
   else if (!box.setWidth(boxWidth))  // Store the width
      cout << "Invalid box width entered.\n";
   else
   {	// Call member functions to get box information to display
	   cout << "\nHere is the rectangle's data:\n";
	   cout << "Length: " << box.getLength() << endl;
	   cout << "Width : " << box.getWidth()  << endl;
	   cout << "Area  : " << box.getArea()   << endl;
   }
	return 0;
}
