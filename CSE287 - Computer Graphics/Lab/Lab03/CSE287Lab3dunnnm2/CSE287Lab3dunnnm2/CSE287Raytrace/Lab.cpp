#include "Lab.h"


/**
* LAB3 INSTRUCTIONS:
* Below are descriptions of problems in vector arithmetic and three dimensional
* geometry. Most require you to implement a struct that can be used to
* represent two or three dimensional curves or surfaces.
*
* Coding up these problems gives you a chance to verify your work is correct.
* Test by using values or quantities for which you know the answer.
*
* Please note that Defines includes overloaded stream operators for printing
* dvec2, dvec3, and dvec4 objects to the console. These functions
* can be used to print vectors and matrices to the console.
*
* Uncomment all function calls before turning in the lab.
* When you have completed all the problems, complete the following turning in
* instructions:
*
*	1.	Copy the folder containing your solution to the desktop.
*	2.	Change the name of the folder to CSE287Lab3 followed by your unique
*		identifier. For instance “CSE287Lab3Bachmaer.”
*	3.	Open the solution. Make sure it still runs.
*	4.	Clean the solution by selecting Build->Clean Solution. (The will delete
*		all the intermediate temporary files that are part of your project and
*		reduce the size of your submission.)
*   6.  Zip up the solution folder using the standard windows compression tool.
*		(No 7zips, rars, etc.)
*	7.	Submit the zip archive of your solution through canvas.
*
*/

// Complete the constructor and the checkPoint methods for the Circle struct.
// The constructor should assign the input arguments to the data members using an
// initializer list.
// The checkPoint method should return a negative value for points that are inside the
// circle, zero for points that are on the circle, and positive values for points
// that are outside the circle.
// Test the checkPoint function using using the following values:
//   Circle c with center at (0, 0) and radius = 1
//   vec2 p = [0.5 0] (inside circle)
//   vec2 q = [1 0] (on circle)
//   vec2 r = [2 0] (outside circle)
// Then test your function for points of your choosing on a Circle that is NOT
// centered on the origin.
// Display all the results to the console.
struct Circle {

	dvec2 center;
	double radius;

	Circle(double r = 1.0, dvec2 ctr = dvec2(0.0, 0.0))
	{
		this->center = ctr;
		this->radius = r;
	} // end Circle constructor

	double checkPoint(dvec2 pt)
	{
		return pow(length(pt - center), 2) - pow(radius, 2);

	} // end checkPoint

}; // end Circle struct


void problem1()
{
	cout << "Problem 1" << endl;
	Circle c1 = Circle();
	cout << "Testing on the origin: " << dvec2(0.5, 0) << " " << c1.checkPoint(dvec2(0.5, 0)) << " " << dvec2(1, 0) << " " << c1.checkPoint(dvec2(1, 0)) << " " << dvec2(2, 0) << " " << c1.checkPoint(dvec2(2, 0)) << endl;
	Circle c2 = Circle(1.0, dvec2(2.0, 2.0));
	cout << "Testing on (2, 2): " << dvec2(2.5, 2) << " " << c2.checkPoint(dvec2(2.5, 2)) << " " << dvec2(3, 2) << " " << c2.checkPoint(dvec2(3, 2)) << " " << dvec2(4, 2) << " " << c2.checkPoint(dvec2(4, 2)) << endl;



} // end Problem1

// Create a Sphere struct that is similar to the Circle struct. Again, it should 
// have a constructor with default arguments and an initializer list, and a 
// checkPoint method that returns negative values, zero, and positive values in a 
// manner that is similar to the Circle struct. 
// Test using the following values:
//   Sphere s with center at (0, 0, 0) and radius = 1
//   dvec3 p = [0 0.5 0] (inside sphere)
//   dvec3 q = [1 0 0] (on sphere)
//   dvec3 r = [0 0 4] (outside sphere)
// Then test with points of your choosing that are inside, on, and outside a Sphere 
// that is NOT centered on the origin. Display the results to the console.
struct Sphere {

	dvec3 center;
	double radius;

	Sphere(double r = 1.0, dvec3 ctr = dvec3(0.0, 0.0, 0.0))
	{
		this->center = ctr;
		this->radius = r;
	} // end Sphere constructor

	double checkPoint(dvec3 pt)
	{
		return pow(length(pt - center), 2) - pow(radius, 2);

	} // end checkPoint
	

}; // end Sphere struct

void problem2()
{
	cout << "Problem 2" << endl;
	Sphere s1 = Sphere(1.0, dvec3(0.0, 0.0, 0.0));
	cout << "Testing on the origin: " << dvec3(0, 0.5, 0) << " " << s1.checkPoint(dvec3(0, 0.5, 0)) << " " << dvec3(1, 0, 0) << " " << s1.checkPoint(dvec3(1, 0, 0)) << " " << dvec3(0, 0, 4) << " " << s1.checkPoint(dvec3(0, 0, 4)) << endl;
	Sphere s2 = Sphere(1.0, dvec3(2.0, 2.0, 2.0));
	cout << "Testing on (2.0, 2.0, 2.0): " << dvec3(2, 2.5, 2) << " " << s2.checkPoint(dvec3(2, 2.5, 2)) << " " << dvec3(3, 2, 2) << " " << s2.checkPoint(dvec3(3, 2, 2)) << " " << dvec3(2, 2, 6) << " " << s2.checkPoint(dvec3(2, 2, 6)) << endl;
	



} // end Problem2

// Write a struct for a Quadric surface of your choice. It should include a constructor
// and checkPoint method as in the previous problems. Test the method in a similar 
// manner and display the results.

struct Cylinder {

	dvec2 center;
	double aVal;
	double bVal;

	Cylinder(double a = 1.0, double b = 1.0, dvec2 center = dvec2(2.0, 2.0))
	{
		this->center = center;
		this->aVal = a;
		this->bVal = b;
	} // end Cylinder constructor

	double checkPoint(dvec2 pt)
	{
		return (pow(pt[0] - center[0], 2) / pow(aVal, 2) + pow(pt[1] - center[1], 2) / pow(bVal, 2) - 1);

	} // end checkPoint


}; // end Sphere struct

void problem3()
{
	cout << "Problem 3" << endl;
	Cylinder c1 = Cylinder(1.0, 2, dvec2(0, 0));
	cout << "Cylinder centered at 0, 0 with a and b values of 1 and 1: " << endl;
	cout << "Testing on the origin: " << dvec2(0.5, 0) << " " << c1.checkPoint(dvec2(0.5, 0)) << " " << dvec2(1, 0) << " " << c1.checkPoint(dvec2(1, 0)) << " " << dvec2(2, 0) << " " << c1.checkPoint(dvec2(2, 0)) << endl;
	Cylinder c2 = Cylinder(1, 2, dvec2(2, 2));
	cout << "Testing on (2, 2): " << dvec2(2.5, 2) << " " << c2.checkPoint(dvec2(2.5, 2)) << " " << dvec2(3, 2) << " " << c2.checkPoint(dvec2(3, 2)) << " " << dvec2(4, 2) << " " << c2.checkPoint(dvec2(4, 2)) << endl;
} // end Problem3

// Write a Plane struct. The constructor should have three vec3s as parameters
// that are assumed to be in counter-clockwise order. The data members of the 
// struct should include a normal vector and a point on the Plane. The normal
// vector should point out of the side of the Plane on which the vertices are
// in counter-clockwise order and should be unit length. 
//
// As long as the normal vector is a unit vector, the signed distance of a 
// point from a plane is given by the equation on page 57 of the notes. Write
// a signedDistance function that has a vec3 as a parameter. 
// Test it using the following values:
//   Plane p with points (0, 4, 0), (0, 0, 0), (4, 0, 0)
//   dvec3 x = [0 0 1] (in front of plane)
//   dvec3 y = [0 0 -1] (behind plane)
//   dvec3 z = [0 3 0] (on plane)
// Then test it for using points and a new Plane of your choosing. Display  
// all of your results on the console.
//
// Can your checkPoint methods in the previous questions be modified to 
// return a signed distance? If you believe the answer is "yes," go ahead
// and modify the functions accordingly in each of the previous questions.
struct Plane {
	dvec3 vecNormal;
	dvec3 pointOnPlane;
	dvec3 vec1;
	dvec3 vec2;
	dvec3 vec3;
	Plane(dvec3 vec1 = dvec3(0, 4, 0), dvec3 vec2 = dvec3(0, 0, 0), dvec3 vec3 = dvec3(4, 0, 0)) {
		this->vec1 = vec1;
		this->vec2 = vec2;
		this->vec3 = vec3;
		vecNormal = glm::normalize(glm::cross(vec1 - vec2, vec3 - vec2));
		pointOnPlane = vec1;
	}
	double signedDistance(dvec3 vecOther = dvec3(0, 0, 0)) {
		return glm::dot(pointOnPlane - vecOther, vecNormal);
	}



}; // end Plane struct

void problem4()
{
	cout << "Problem 4" << endl;
	Plane p2 = Plane();
	cout << "Point 0, 0, 1 should be on: " << p2.signedDistance(dvec3(0, 0, 1)) << " This should > 0  " << endl;
	cout << "Point 0, 0, -1 should be behind: " << p2.signedDistance(dvec3(0, 0, -1)) << " This should < 0 " << endl;
	cout << "Point 0, 3, 0 should be on: " << p2.signedDistance(dvec3(0, 3, 0)) << " This should = 0 " << endl;
	cout << endl;
	Plane p1 = Plane(dvec3(0, 0, 0), dvec3(0, -2, 0), dvec3(2, -2, 0));
	cout << "Point 1, 1, 0 should be on: " << p1.signedDistance(dvec3(1, 1, 0)) << " This should = 0 " << endl;
	cout << "Point 1, 1, -1 should be behind: " << p1.signedDistance(dvec3(1, 1, -1)) << " This should < 0 " << endl;
	cout << "Point 1, 1, 1 should be on: " << p1.signedDistance(dvec3(1, 1, 1)) << " This should > 0 " << endl;
	


} // end Problem4

// Write a struct that supports a parametric representation of a line 
// in three dimensional space. The constructor should take two points on
// the line as parameters. 
//
// It should have a method called checkPoint that returns true if the 
// input argument is on the line and false otherwise. If the point is on 
// the line it should also return the parameter of the point "by reference." 
// Test this method for the following values:
//   ParametricLine with points (0, 0, 0) and (1, 0, 0)
//   Point u = (2, 0, 0) (on line,  returned parameter = 2)
//   Point v = (0, 3, 0) (off line)
// Then test the method with your own values for the line and points.
// Verify that the returned parameter is correct for points on the line. 
// Display the results. 
//
// Write a method called getPoint. Given a value for the parameter, t, it
// should return a vec3 that contains the location that corresponds to the
// supplied parameter. 
//
// Test this method with the same ParametricLine from the checkPoint test and 
// parameter values of 3, -3, and 0.5 (the results should be [3 0 0], 
// [-3 0 0], and [0.5 0 0], respectively).

// Then test the method using your own values for the line and points. Include
// parameters for positive and negative points, as well as a point in between
// the two used to define the line.
// Display the results.
struct ParametricLine {
	dvec3 p0;
	dvec3 p1;

	ParametricLine(dvec3 p0 = dvec3(0, 0, 0), dvec3 p1 = dvec3(2, 0, 0)) {
		this->p0 = p0;
		this->p1 = p1; 
	}

	dvec3 getPoint(double t) {
		double x = p0[0] + t * (p1[0] - p0[0]);
		double y = p0[1] + t * (p1[1] - p0[1]);
		double z = p0[2] + t * (p1[2] - p0[2]);
		return dvec3(x, y, z);
	}

	bool checkPoint(const dvec3& p, double& t)
	{
		t = (p[0] - p0[0]) / (p1[0] - p0[0]);
		double y = p0[1] + t * (p1[1] - p0[1]);
		double z = p0[2] + t * (p1[2] - p0[2]);
		if (abs(y - p[1]) < 0.01 && abs(z - p[2]) < 0.01) {
			return true;
		}
		//t = 0.0;
		return false;
	}


}; // end ParametricLine struct

void problem5()
{
	cout << "Problem 5" << endl;
	double t = 0.0;
	ParametricLine pl = ParametricLine(dvec3(0, 0, 0), dvec3(1, 0, 0));
	cout << "First point " << dvec3(2, 0, 0) << " should be 1 " << pl.checkPoint(dvec3(2, 0, 0), t) << " t val should be 2 " << t << endl;
	cout << "Second point " << dvec3(0, 3, 0) << " should be 0 " << pl.checkPoint(dvec3(0, 3, 0), t) << endl;
	// Test this method with the same ParametricLine from the checkPoint test and 
	// parameter values of 3, -3, and 0.5 (the results should be [3 0 0], 
	// [-3 0 0], and [0.5 0 0], respectively).
	cout << pl.getPoint(3) << "should be 3 0 0" << endl;
	cout << pl.getPoint(-3) << "should be -3 0 0" << endl;
	cout << pl.getPoint(0.5) << "should be 0.5 0 0" << endl;

	ParametricLine p2 = ParametricLine(dvec3(-1, -1, -1), dvec3(1, 1, 1));
	cout << "First point " << dvec3(0, 0, 0) << " should be 1 " << p2.checkPoint(dvec3(0, 0, 0), t) << " t val should be 0.5 " << t << endl;
	cout << "Second point " << dvec3(0, 3, 0) << " should be 0 " << p2.checkPoint(dvec3(0, 3, 0), t) << endl;
	cout << "Checking t = 0.5 should be 0 0 0 " << p2.getPoint(0.5) << endl;
	cout << "Checking t = 0 should be -1 -1 -1 " << p2.getPoint(0) << endl;
	cout << "Checking t = -1 should be -3 -3 -3 " << p2.getPoint(-1) << endl;
	

} // end Problem5

// Write a function that supports linear interpolation between
// two scalar (doubles) values. It should take two doubles as 
// arguments. These represent the initial and ending values. It
// should also take a value for the interpolation parameter as 
// an argument. If the parameter is zero or negative, the function
// should return the initial value. If the parameter is one or
// greater, the function should return the final value. Otherwise,
// it should return an interpolated value between the begining 
// and ending values. Use the function to interpolate between 5 and 15.
// Test it with parameter values of -1, 0, 0.6, 1, and 20. Display
// the results to the console.
double linearInterpolateScalars(const double& initial, const double& final, const double& t)
{
	double interpolatedVal = initial * (1 - t) + final * t;
	if (t <= 0){
		return initial;
	}
	else if (t >= 1) {
		return final;
	}
	else {
		return interpolatedVal;
	}
} // end linearInterpolateScalars

void problem6()
{
	cout << "Problem 6" << endl;
	cout << "Interpolation: -1 -> " << linearInterpolateScalars(5, 15, -1) << "| 0 -> " << linearInterpolateScalars(5, 15, 0) << "| 0.6 -> " << linearInterpolateScalars(5, 15, 0.6) << "| 1 -> " << linearInterpolateScalars(5, 15, 1) << "| 20 -> " << linearInterpolateScalars(5, 15, 20) << endl;

} // end Problem6

// Write a function that linearly interpolates between two three
// dimension vector values. Functionality should be similar to 
// the previous question. Test it with points at (0,0,0) and (4, 4, 0).
// Use the same parameter values as the previous question. Display the 
// results.
dvec3 linearInterpolateVectors(const dvec3& initial, const dvec3& final, const double& t)
{
	dvec3 interpolatedVal = initial * (1 - t) + final * t;
	if (t <= 0) {
		return initial;
	}
	else if (t >= 1) {
		return final;
	}
	else {
		return interpolatedVal;
	}
}

void problem7()
{
	cout << "Problem 7" << endl;
	dvec3 init = dvec3(0, 0, 0);
	dvec3 fin = dvec3(4, 0, 0);

	cout << "Interpolation: -1 -> " << linearInterpolateVectors(init, fin, -1) << "| 0 -> " << linearInterpolateVectors(init, fin, 0) << "| 0.6 -> " << linearInterpolateVectors(init, fin, 0.6) << "| 1 -> " << linearInterpolateVectors(init, fin, 1) << "| 20 -> " << linearInterpolateVectors(init, fin, 20) << endl;


} // end Problem7


// Refer to the updated version of slide 37 in the chapter two notes
// for this problem. 
//
// Suppose the vector v = [2 -6 3] and the vector w = [-4 3 10].
// Find the vector that represents the projection of v onto w. Find the
// vector that represents the component of v that is perpendicular to w. Print
// out each of these vectors. Verify correctness by adding together the
// two calculated vectors and checking for equality with v. Also verify the 
// reuslts by taking the dot product of the two vectors. Display the results.
void problem8()
{
	cout << "Problem 8" << endl;

	dvec3 v(2, -6, 3);
	dvec3 w(-4, 3, 10);
	dvec3 proj = (glm::dot(v, w) / pow(glm::length(w), 2)) * w;
	dvec3 perp = v - (glm::dot(v, w) / pow(glm::length(w), 2)) * w;
	cout << "Projection of " << v << " onto " << w << " is " << proj <<  endl;
	cout << "Perpendicular Vector: " << perp << endl;
	cout << "Checking if valid, should be 1 " << (v == (proj + perp)) << endl;
	cout << "Checking if perpendicular, should be 1 " << (glm::dot(perp, w) == 0) << endl;
} // end Problem8


/**
 * @fn	int main(int argc, char** argv)
 *
 * @brief	Main entry-point for this application. Calls functions that
 * 			solve vector arithmetic problems.
 *
 * @param	argc	The number of command-line arguments provided.
 * @param	argv	An array of command-line argument strings.
 *
 * @returns	Exit-code for the process - 0 for success, else an error code.
 */

int main(int argc, char** argv)
{

	problem1();
	problem2();
	problem3();
	problem4();
	problem5();
	problem6();
	problem7();
	problem8();
	 
	return 0;

} // end main