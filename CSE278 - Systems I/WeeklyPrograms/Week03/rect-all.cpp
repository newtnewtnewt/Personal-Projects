// This program implements a Rectangle class.

#include <iostream>
using namespace std;

// Rectangle class declaration
class Rectangle
{
	private:
		float length;
		float width;
	public:
		void  setLength(float);
      void  setWidth(float);
		float getLength();
		float getWidth();
		float getArea();
};

// Member function implementation section

//*******************************************************************
//                     Rectangle::setLength                         *
// This function sets the value of the member variable length.      *
// If the argument passed to the function is zero or greater, it is *
// copied into length. If it is negative, 1.0 is assigned to length.*
//*******************************************************************
void Rectangle::setLength(float len)
{
	if (len >= 0)
      length = len;
   else
   {   length = 1.0;
       cout << "Invalid length. Using a default value of 1.\n";
   }
}

//*******************************************************************
//                      Rectangle::setWidth                         *
// This function sets the value of the member variable width.       *
// If the argument passed to the function is zero or greater, it is *
// copied into width. If it is negative, 1.0 is assigned to width.  *
//*******************************************************************
void Rectangle::setWidth(float w)
{
	if (w >= 0)
      width = w;
   else
   {   width = 1.0;
       cout << "Invalid width. Using a default value of 1.\n";
   }
}

//*******************************************************************
//                      Rectangle::getLength                        *
// This function returns the value in the private member length.    *
//*******************************************************************
float Rectangle::getLength()
{
	return length;
}

//*******************************************************************
//                       Rectangle::getWidth                        *
// This function returns the value in the private member width.     *
//*******************************************************************
float Rectangle::getWidth()
{
	return width;
}

//*******************************************************************
//                        Rectangle::getArea                        *
// This function calculates and returns the area of the rectangle.  *
//*******************************************************************
float Rectangle::getArea()
{
	return length * width;
}

//*******************************************************************
//                             main                                 *
//*******************************************************************
int main()
{
	Rectangle box;         // Declare a Rectangle object
	float boxLength, boxWidth;

	//Get box length and width
	cout << "This program will calculate the area of a rectangle.\n";
	cout << "What is the length? ";
	cin  >> boxLength;
	cout << "What is the width? ";
	cin  >> boxWidth;

	// Call member functions to set box dimensions 
	box.setLength(boxLength);
   box.setWidth(boxWidth);

	// Call member functions to get box information to display
	cout << "\nHere is the rectangle's data:\n";
	cout << "Length: " << box.getLength() << endl;
	cout << "Width : " << box.getWidth()  << endl;
	cout << "Area  : " << box.getArea()   << endl;
	return 0;
}
