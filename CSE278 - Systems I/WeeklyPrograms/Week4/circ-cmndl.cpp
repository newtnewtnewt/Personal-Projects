// This program demonstrates command line arguments, and the 
// use  of  command line arguments 
//
#include <iostream>
#include <cmath>
#include <cstdlib>    // needd to convert strings to numbers
using namespace std;

// Circle class declaration
class Circle
{  private:
	float radius;
		
   public:
	void setRadius(float r)
		{   radius = r; }

        float findArea()
		{   return 3.14 * pow(radius, 2);  }
};

int main(int argc, char* argv[] )
{
   // argv is an array of C strings
   // argc counts the number of arguments


   // For input, enter the radii of circles as argument on the command line
   

  Circle cir[10];          // Define and array of up to 10 objects
	               
      for (int i=1; i < argc; ++i)  
           cir[i-1].setRadius(atol(argv[i]));
           


    for (int i=1; i < argc; ++i)  
     cout << "The area of circle " << i << " is " << cir[i-1].findArea() << endl;


	return 0;
}
