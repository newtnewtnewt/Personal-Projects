#include "MySquare.h"
	
MySquare::MySquare(double sideLength, color c, int xVal, int yVal) : 
	sideLength(sideLength), c(c), xVal(xVal), yVal(yVal), MyRectangle(xVal, yVal, c, sideLength, sideLength){ }
MySquare::~MySquare() { }
