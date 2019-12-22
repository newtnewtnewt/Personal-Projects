#include "Lab.h"
#include "Defines.h"

/**
* LAB2 INSTRUCTIONS:
* Below are description of problems in vector arithmetic. Each problem has
* at least one or two functions associated with it (functions - not methods.
* there is not a class). There is a function call in the main for each one
* of the problems. For each problem, write the necessary code to solve it.
* All console output should be labeled. Do NOT simply print a number. Make
* sure there is text that indicates what the number is and possibly its
* significance.
*
* You will find it helpful to have the chapter 2 lecture notes handy as you
* work through the problems. As you are moving from problem to problem, you
* may want to comment out some of the function calls in the main so that you
* easily see the output for the current problem. Uncomment all function calls
* before turning in the lab. Defines.h includes definitions of
* simple functions for printing dvec2, dvec3, and dvec4 objects to the console.
*
* When you have completed all the problems, complete the following turnin
* instructions:
*
*	1.	Copy the folder containing your solution to the desktop.
*	2.	Change the name of the folder to CSE287Lab2 followed by your unique
*		identifier. For instance “CSE287Lab2Bachmaer.”
*	3.	Open the solution. Make sure it still runs.
*	4.	Clean the solution by selecting Build->Clean Solution. (The will delete
*		all the intermediate temporary files that are part of your project and
*		reduce the size of your submission.)
*   6.  Zip up the solution folder using the standard windows compression tool.
*		(No 7zips, rars, etc.)
*	7.	Submit the zip archive of your solution through canvas.

*/


// Suppose x equals 45 degrees. Write a statement (without glm) that does the necessary 
// calculation to convert this value to radians. Print out the resulting 
// value. Write another statement that uses the correct glm function
// to convert x to radians. Print out the resulting value.
void problem1()
{
	double x = 45.0;
	cout << "Problem 1: 45 degrees to radians non-glm way: " << (x * (PI/180))  << endl;
	cout << "Problem 1: 45 degrees to radians glm way: " << glm::radians(x) << endl;

} // end Problem1


// Suppose x equals 2.0943951 radians. Write a statement (without glm) that does the 
// necessary calculation to convert this value to degrees. Print out the
// resulting value. Write another statement that uses the correct glm 
// function to convert x to degrees. Print out the resulting value.
// This website can be used to check calculations for this and the next few
// problems: http://www.rapidtables.com/calc/math/index.htm
void problem2()
{
	double x = 2.0943951;
	cout << "Problem 2: 2.0943951 radians to degrees the non-glm way " << (x * (180/PI)) << endl;
	cout << "Problem 2: 2.0943951 radians to degrees the glm way " << glm::degrees(x) << endl;
	

} // end Problem2


// Suppose the cosine of an angle theta is 0.34202014332566873304409961468226.
// Use a trigometric function to determine theta. Print out the angle in 
// both degrees and radians.
void problem3()
{		
	double cosTheta = 0.34202014332566873304409961468226;
	cout << "Problem 3 " << "Cosine of theta in radians: " << glm::acos(cosTheta) << " and in degrees: " << glm::degrees(acos(cosTheta)) << endl;

	

} // end Problem3


// Suppose the vector v = [4 -6 7]. Create three vectors: 
// 1. p, which is twice as long as v and points in the same direction as v
// 2. q, which has the same length as v and points in the opposite direction of v 
// 3. r, which is three quarters the length of v and points in the same direction as v
// Print out the results of each vector calculation.
void problem4()
{
	cout << "Problem 4: " << endl;

	dvec3 v(4, -6, 7);
	cout << "1. Double the first : " << 2.0 * v << endl;
	cout << "2. Inverse the first: " << -1.0 * v << endl;
	cout << "3. 3/4 The first : " << 0.75 * v << endl; 
} // end Problem4


// Write a function that compares two vec3s for equality by comparing 
// individual components. It should return true if the vectors are equal 
// and false otherwise. Check for correctness by using equal and unequal
// vec3s. Print out the results. Vertify that the same results are obtained
// when using the overloaded '==' operator. Print out the results.
bool vectorEquality(const dvec3& v, const dvec3& w)
{
	if (v[0] == w[0] && v[1] == w[1] && v[2] == w[2]) {
		return true;
	}
	return false;

} // end vectorEquality

void problem5()
{
	dvec3 v1(1, 1, 1);
	dvec3 v2(2, 2, 2);
	dvec3 v3(1, 1, 1); 
	cout << "Problem 5" << endl;
	cout << "Comparing two equal vectors: My Method " << vectorEquality(v1, v3) << " Their way " << (v1 == v3) << endl;
	cout << "Comparing two unequal vectors: My Method " << vectorEquality(v1, v2) << " Their way " << (v1 == v2) << endl;


} // end Problem5


// Write a function that calculates and returns the length of a vector. 
// Call the function on the vector v = [2 3 4]. Perform this same calculation
// using the glm length function. Print out the results of both calculations.
double myLength(const dvec3& v)
{
	return sqrt(pow(v[0], 2) + pow(v[1],2) + pow(v[2],2));

} // end myLength


void problem6()
{
	cout << "Problem 6" << endl;

	dvec3 v(2, 3, 4);
	cout << "Length of v " << v << " is " << myLength(v) << endl;
		 
} // end Problem6


// Write a function that calculates and returns the unit length version of a vec3
// that is received as an argument. Call the function on the vector v = [2 3 4]. 
// Perform this same calculation using the glm normalize function. Print out
// the results of both calculations.
dvec3 myUnitVector(const dvec3& v)
{
	dvec3 unitVector = (v * (1 / myLength(v)));
	return unitVector;

} // end myUnitVector


void problem7()
{
	cout << "Problem 7" << endl;

	dvec3 v(2, 3, 4);

	cout << "The unit vector for " << v << " is " << myUnitVector(v) << endl;

} // end Problem7


// Suppose the vector v = [-4 2 -3]. Create three vectors:
// 1. p, which points in the same direction, but has a length of one
// 2. q, which points in the same direction, but has a length of three
// 3. r, which points in the opposite direction and has a length of five
// Print out the results of each vector calculation.
// (Feel free to use the glm normalize function for this problem.)
void problem8()
{
	cout << "Problem 8" << endl;

	dvec3 v(-4, 2, -3);
	cout << "The vector p with a length of 1: " << glm::normalize(v) << endl;
	cout << "The vector q with a length of 3: " << glm::normalize(v) * 3.0 << endl; 
	cout << "The vector r with a length of 5 in the opposite direction: " << glm::normalize(v) * -5.0 << endl;

} // end Problem8


// Suppose the vector v = [2 -6 3] and the vector w = [-4 3 10]
// Add the two vectors together using the overloaded glm addition operator. 
// Print the resulting sum.
void problem9()
{
	cout << "Problem 9" << endl;

	dvec3 v(2, -6, 3);
	dvec3 w(-4, 3, 10);
	cout << "Resultant summation vector " << v + w << endl; 

} // end Problem9


// Suppose someone walks three miles north and then seven miles west. 
// Assume they started at position (0,0). Assume the X axis points north.
// Use vector arithmetic to determine the coordinates of their finishing 
// point. Print the result. Also print a result that shows that it does 
// not matter whether they walk north or west first. You will want to use separate
// vectors to represent each walking direction.
void problem10()
{
	cout << "Problem 10" << endl;

	glm::vec2 position(0, 0);
	glm::vec2 walkingNorth(7, 0);
	glm::vec2 walkingWest(0, 3);
	
	cout << "End coordinates walking north first: " << position + walkingNorth + walkingWest << endl;
	cout << "End coordinates walking west first: " <<  position + walkingWest + walkingNorth << endl;



} // end Problem10


// Suppose the person in the previous quesion starts at (3,4). What would
// their finishing point be? Print the result.
void problem11()
{
	cout << "Problem 11" << endl;

	glm::vec2 position(3, 4);
	glm::vec2 walkingNorth(7, 0);
	glm::vec2 walkingWest(0, 3);
	cout << "End coordinates starting at (3, 4) : " << position + walkingNorth + walkingWest << endl;

} // end Problem11


// Use two vectors to show that vector subtraction is not communitive.
// Print the vectors and the values that indicate the fact.
void problem12()
{	
	glm::vec2 v1(2, 2);
	glm::vec2 v2(4, 4);
	cout << "Problem 12" << endl;
	cout << "Going forwards " << v1 - v2 << " going backwards " << v2 - v1 << endl;
	


} // end Problem12


// Write a function that calculates the dot product of two vec3s and returns
// this scalar value as a double. Use the vectors v = [2 6 3] and w = [-4 3 10] 
// to show that your function produces the same value as the glm dot function.
// Print the results.
double myDot(const dvec3 a, const dvec3 b)
{
	return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];

} // end myDot


void problem13()
{
	cout << "Problem 13" << endl;

	dvec3 v(2, 6, 3);
	dvec3 w(-4, 3, 10);

	cout << "Dot Product of v and w " << myDot(v, w) << endl;
	cout << "Dot Product of v and w in glm " << glm::dot(v, w) << endl; 

} // end Problem13


// Use the dot product to find the cosines of the angles that the vector 
// v = [-2 1 3] makes with each of the following vectors. Print out the
// values. 
//   x = [2 6 3] (result should be 0.419982)
//   w = [-4 3 10]
//   q = [3 0 0]
void problem14()
{
	cout << "Problem 14" << endl;

	dvec3 v(-2, 1, 3);
	dvec3 x(2, 6, 3);
	dvec3 w(-4, 3, 10);
	dvec3 q(3, 0, 0);
	cout << "The cosine of the angle between v and x is: " << glm::dot(v, x) / (glm::length(v) * glm::length(x)) << endl;
	cout << "The cosine of the angle between v and w is: " << glm::dot(v, w) / (glm::length(v) * glm::length(w)) << endl;
	cout << "The cosine of the angle between v and q is: " << glm::dot(v, w) / (glm::length(v) * glm::length(q)) << endl;


} // end Problem14


// Write a function that compares two vec3s. If they are: 
//   within 90 degrees of each other, return 1
//   perpendicular, return 0
//   more than 90 degrees apart, return -1 
// Do not use any trigometric functions.
// Use the following vectors to demonstrate that your
// function works:
//   v = [0 10 0]
//   w = [10 0 0]
//   x = [-1 9 0]
// v and w are perpendicular, v and x are within 90 degrees, and 
// w and x are more than 90 degrees apart.
// Print your results.
int vectorCompare(dvec3 a, dvec3 b)
{
	double dotProductResult = glm::dot(a, b);
	if (dotProductResult == 0) {
		return 0;
	}
	else if(dotProductResult < 0){
		return -1;
	}
	else {
		return 1;
	}
	return 0;
} // end vectorCompare

void problem15()
{
	cout << "Problem 15" << endl;

	dvec3 v(0, 10, 0);
	dvec3 w(10, 0, 0);
	dvec3 x(-1, 9, 0);
	cout << "v and w: " << vectorCompare(v, w) << endl;
	cout << "v and x: " << vectorCompare(v, x) << endl;
	cout << "w and x: " << vectorCompare(w, x) << endl;

} // end Problem15


// Write a function that finds the projection of one vector onto another and 
// returns it as a double. Use it to find the projection of v = [2 6 3] onto 
// w = [-4 3 10]. Print the result.
// (To check your method, call project for the values [3 4 5] onto [2 -2 3].
// The result should be 3.152963.)
double project(dvec3 from, dvec3 onto)
{

	return 0.0;

} // end project


void problem16()
{
	cout << "Problem 16" << endl;

	dvec3 v(2, 6, 3);
	dvec3 w(-4, 3, 10);

} // end Problem16


// Take the cross product of the vectors v = [2 6 3] and w = [-4 3 10]. Use the glm cross
// product function. Print the result. Use the dot product to verify that the resulting
// product is perpendicular to each of the multiplicands. Print the result.
void problem17()
{
	cout << "Problem 17" << endl;

	dvec3 v(2, 6, 3);
	dvec3 w(-4, 3, 10);

} // end Problem17


// Calculate the cross product v x w and then w x v. Use the vectors v = [2 6 3] 
// and w = [-4 3 10]. Print each of the products. Verify that cross product is not
// commutitative by checking for equality or by adding the two products together 
// and checking the sum. Print the result.
void problem18()
{
	cout << "Problem 18" << endl;

	dvec3 v(2, 6, 3);
	dvec3 w(-4, 3, 10);

} // end Problem18


// Write a function that has three vec3 parameters. The parameters should give 
// three corners of a parallelogram in counter-clockwise order. In the function
// use the cross product to calculate the area of the parallelogram. Test the
// function with the following vertices: x = [0 2 0], y = [0 0 0], and z = [4 0 0]. Print the 
// result. Then test the function with another three vertices of your choosing.
// Print out this area as well. 
// (To check your method, call areaOfParallelogram with the values [0 3 0], [0 0 0], 
// and [3 0 0]. The result should be 9.)
double areaOfParallelogram(dvec3 a, dvec3 b, dvec3 c)
{

	return 0.0f;

} // end areaOfParallelogram


void problem19()
{
	cout << "Problem 19" << endl;

	dvec3 x(0, 2, 0);
	dvec3 y(0, 0, 0);
	dvec3 z(4, 0, 0);

} // end Problem19


// Write a function that has three vec3 parameters. The parameters should give 
// three corners of a triangle in counter-clockwise order. In the function
// use the cross product to either calculate the area of the triangle, or call the 
// function you wrote for the previous quesion and multiply the returned area
// by one half. Test the function with the following vertices: x = [0 2 0],
// y = [0 0 0], and z = [4 0 0]. Print the result. Then test the function with another
// three vertices of your choosing. Print out this area as well. 
double triangleArea(dvec3 a, dvec3 b, dvec3 c)
{

	return 0.0;

} // end triangleArea


void problem20()
{
	cout << "Problem 20" << endl;

	dvec3 x(0, 2, 0);
	dvec3 y(0, 0, 0);
	dvec3 z(4, 0, 0);

} // end Problem20


// Write a function that has two vec3 parameters. The function should find and 
// return a unit length vector that points from the position described by 
// the first vec3 to the position described by the second vec3. Test the 
// function with the following vectors: x = [0 4 0] and y = [4 0 0]. Print the 
// result.
dvec3 findPointingVector(dvec3 first, dvec3 second)
{

	return dvec3(0, 0, 0);

} // end findPointingVector

void problem21()
{
	cout << "Problem 21" << endl;

	dvec3 x(0, 4, 0);
	dvec3 y(4, 0, 0);

} // end Problem21


// Write a function that has two vec3 parameters. The function should find find
// the angle between the two vectors by computing the dot product and then 
// using the arccos function (acos). The agled returned should be in degrees.
// result. Test it using two vectors that you determine the angel between 
// by "inspection."
double findAngle(dvec3 first, dvec3 second)
{

	return 0.0;

} // end findAngle


void problem22()
{
	cout << "Problem 22" << endl;

	dvec3 x(0, 4, 0);
	dvec3 y(4, 0, 0);

} // end Problem22

// Write a function that maps a target number from a range between from a 
// "from" range to a "to" range. Test it out with with ranges were both 
// range are negative, positive, and one of each.
double rangeMap(double fromLow, double fromHigh, double toLow, double toHigh, double target)
{

	return 0.0;

} // end findAngle


void problem23()
{
	cout << "Problem 23" << endl;


} // end Problem23


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
	problem9();
	problem10();
	problem11();
	problem12();
	problem13();
	problem14();
	problem15();
	problem16();
	problem17();
	problem18();
	problem19();
	problem20();
	problem21();
	problem22();
	problem23();
 
	return 0;

} // end main