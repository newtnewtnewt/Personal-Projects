#include "RightwardTriangle.h"
RightwardTriangle::RightwardTriangle(int x, int y, color c, double w) : x(x), y(y), c(c), w(w), DownwardTriangle(x, y, c, w) {
}
void RightwardTriangle::draw(FrameBuffer& fb) {

	for(int i = y; i < y + (2 * w - 1); i++){
		for(int j = x; j < x + w  - abs(i - w - y); j++){
			fb.setPixel(j, i, c);
		}
		
	}
}