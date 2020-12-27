#pragma once
#include "DownwardTriangle.h"
class RightwardTriangle : public DownwardTriangle
{
protected:
	int x; int y; color c; double w;
public:
	RightwardTriangle(int x, int y, color c, double w);
	void draw(FrameBuffer& fb);

};

