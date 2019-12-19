#ifndef RECTANGLE_H
#define RECTANGLE_H

// Rectangle class declaration
class Rectangle
{
	private:
		float length;
		float width;
	public:
		bool  setLength(float);
                bool  setWidth(float);
		float getLength();
		float getWidth();
		float getArea();
};
#endif

