// File rectangle.cpp -- Function implementation file
// Contains function definitions for Rectangle class functions

#include "rectangle.h"

//*******************************************************************
//                    Rectangle::setLength                          *
// If the argument passed to the setLength function is zero or      *
// greater, it is copied into the member variable length and true   *
// is returned. If the argument is negative, the value of length    *
// remains unchanged and false is returned.                         *
//*******************************************************************

bool Rectangle::setLength(float len)
{
	bool validData = true;

   if (len >= 0)
      length = len;            // Copy argument to length
   else
      validData = false;       // Leave length unchanged
   
   return validData;
}

//*******************************************************************
//                    Rectangle::setWidth                           *
// If the argument passed to the setWidth function is zero or       *
// greater, it is copied into the member variable width and true    *
// is returned. If the argument is negative, the value of width     *
// remains unchanged and false is returned.                         *
//*******************************************************************

bool Rectangle::setWidth(float w)
{
	bool validData = true;

   if (w >= 0)
      width = w;               // Copy argument to width
   else
      validData = false;       // Leave width unchanged
   
   return validData;
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


