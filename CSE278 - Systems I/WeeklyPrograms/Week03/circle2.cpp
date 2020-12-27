// This program demonstrates a simple class.
#include <iostream>
#include <cmath>
using namespace std;

// Circle class declaration
class Circle
{	private:
		float radius;
		
	public:
		void setRadius(float r)
		{   radius = r; }

		float findArea()
		{   return 3.14 * pow(radius, 2);  }
};

int main()
{
	Circle sphere1,          // Define 2 Circle objects
	       sphere2;        

	sphere1.setRadius(1);    // This sets sphere1's radius to 1
	sphere2.setRadius(2.5);  // This sets sphere2's radius to 2.5

	cout << "The area of sphere1 is " << sphere1.findArea() << endl;
	cout << "The area of sphere2 is " << sphere2.findArea() << endl;
	return 0;
}
