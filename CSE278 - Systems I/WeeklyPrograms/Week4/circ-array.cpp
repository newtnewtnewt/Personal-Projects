// This program demonstrates how an overloaded constructor 
// that accepts an argument can be invoked for multiple objects 
// when an array of objects is created.

#include <iostream>
#include <iomanip>
#include <cmath>
using namespace std;

// Circle class declaration
class Circle
{	private:
		float radius;
		
	public:
		Circle() { radius = 1.0;}             // Default constructor 
		Circle(float r) {radius = r;}         // Overloaded constructor
		void setRadius(float r) {radius = r;}
		float findArea() {return 3.14 * pow(radius, 2);}
};

const int numCircles = 4;

int main()
{
	int index;

    // Define an array that holds 4 Circle objects. Use an initialization
    // list to call the constructor using one float argument for the first
   // 3 objects. The default constructor will be called for the final object.
    
	Circle sphere[numCircles] = {0.0, 2.0, 2.5};       
	              
	// Display area of each object
	cout << fixed << showpoint << setprecision(2);
	cout << "\nHere are the areas of the " << numCircles 
        << " spheres.\n";
	for (index = 0; index < numCircles; index++)
	{  cout << "sphere " << (index+1) << setw(8)
           << sphere[index].findArea() << endl;
	}
	return 0;
}

