#include "MyRectangle.h"
class MySquare : public MyRectangle  {
protected:
	double sideLength;
	color c;
	int xVal;
	int yVal;


public:
	MySquare(double sideLength, color c, int xVal, int yVal);
	~MySquare();
};

