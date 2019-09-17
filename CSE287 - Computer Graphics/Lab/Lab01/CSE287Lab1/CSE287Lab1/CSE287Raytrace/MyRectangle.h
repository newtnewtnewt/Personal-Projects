#include "Defines.h"
#include "FrameBuffer.h"

class MyRectangle {
protected:
	int x; int y; color c; double w; double h;
public:
	MyRectangle(int x, int y, color c, double w, double h);
	int getPointX();
	int getPointY();
	color getColor();
	double getWidth();
	double getHeight();
	void setPoint(int x, int y);
	void setColor(color c);
	void setWidth(double w);
	void setHeight(double h);
	virtual void draw(FrameBuffer& fb);

};



