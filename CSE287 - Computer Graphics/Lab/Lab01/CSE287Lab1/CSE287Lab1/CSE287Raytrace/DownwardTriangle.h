#pragma once
#include "Defines.h"
#include "FrameBuffer.h"

class DownwardTriangle{
protected:
	int x; int y; color c; double w;
public:
	DownwardTriangle(int x, int y, color c, double w);
	int getPointX();
	int getPointY();
	color getColor();
	void setPoint(int x, int y);
	void setColor(color c);
	virtual void draw(FrameBuffer& fb);
};

