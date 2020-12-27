#include "MyRectangle.h";


MyRectangle::MyRectangle(int x, int y, color c , double w , double h) : x(x), y(y), c(c), w(w), h(h) { }
void MyRectangle::draw(FrameBuffer& fb) {
	cout << "drawing rectangle" << endl;
	
	for (int i = y; i < h + y; i++) {
		for (int j = x; j < w + x; j++){
			color blend = color(
				((1 - this->c[3]) * (fb.getPixel(j, i))[0] + (this->c[3] * c[0])),
				((1 - this->c[3]) * (fb.getPixel(j, i))[1] + (this->c[3] * c[1])),
				((1 - this->c[3]) * (fb.getPixel(j, i))[2] + (this->c[3] * c[2])),
				1);
			fb.setPixel(j, i, blend) ;
		}
	}
}

int MyRectangle::getPointX() {
	return this->x;

}
int MyRectangle::getPointY() {
	return this->y;
}
color MyRectangle::getColor() {
	return this->c;
}
double MyRectangle::getWidth() {
	return this->w;
}
double MyRectangle::getHeight() {
	return this->h;
}
void MyRectangle::setPoint(int x, int y) {
	this->x = x;
	this->y = y;
}
void MyRectangle::setColor(color c) {
	this->c;
}
void MyRectangle::setWidth(double w) {
	this->w = w;
}
void MyRectangle::setHeight(double h) {
	this->h = h;

}
