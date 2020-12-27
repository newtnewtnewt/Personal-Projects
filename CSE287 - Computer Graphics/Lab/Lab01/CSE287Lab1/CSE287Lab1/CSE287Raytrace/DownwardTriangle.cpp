#include "DownwardTriangle.h"

DownwardTriangle::DownwardTriangle(int x, int y, color c, double w) : x(x), y(y), c(c), w(w) { }
int DownwardTriangle::getPointX() {
	return x;
}
int DownwardTriangle::getPointY() {
	return y;
}
color DownwardTriangle::getColor() {
	return c;
}
void DownwardTriangle::setPoint(int x, int y) {
	this->x = x; this->y = y;
}
void DownwardTriangle::setColor(color c) {
	this->c = c;	
}
void DownwardTriangle::draw(FrameBuffer& fb) {
	int leftBorder = x; 
	int rightBorder = x + w;
	for (int i = y; leftBorder != rightBorder; i--) {
		for (int j = leftBorder; j < rightBorder; j++) {
			fb.setPixel(j, i, c);
		}
		leftBorder++; 
		if (leftBorder == rightBorder) { break; }
		rightBorder--;
	}
}