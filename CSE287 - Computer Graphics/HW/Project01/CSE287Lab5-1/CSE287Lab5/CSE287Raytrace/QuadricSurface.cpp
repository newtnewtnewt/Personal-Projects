#include "QuadricSurface.h"


QuadricSurface::QuadricSurface( const dvec3 & position, const color & mat )
	: ImplicitSurface( mat ), center( position )
{
	// Sphere
	//A = 1;
	//B = 1;
	//C = 1;
	//D = 0;
	//E = 0;
	//F = 0;
	//G = 0;
	//H = 0;
	//I = 0;
	//J = -9;

	// Cylinder
	A = 0;
	B = 1/9.0;
	C = 1/9.0;
	D = 0;
	E = 0;
	F = 0;
	G = 0;
	H = 0;
	I = 0;
	J = -1;

	// Paraboloid
	//A = 0;
	//B = 1;
	//C = 1;
	//D = 0;
	//E = 0;
	//F = 0;
	//G = -1;
	//H = 0;
	//I = 0;
	//J = 0;

}


HitRecord QuadricSurface::findIntersect( const Ray & ray )
{
	HitRecord hitRecord; 

	dvec3 Ro = ray.origin - center;
	dvec3 Rd = ray.direct;

	// After substituting the parametric form of the ray, Ro + t* Rd, into the 
	// generalized form of the quadratic equation for a quadric surface the equation
	// reduces to Aq(tt) + Bq(t) + Cq where

	double Aq = A * (Rd.x*Rd.x) + B * (Rd.y*Rd.y) + C * (Rd.z*Rd.z) + 
			   D * (Rd.x * Rd.y) + E * (Rd.x * Rd.z) + F * (Rd.y * Rd.z);

	double Bq = (2 * A * Ro.x*Rd.x) + (2 * B * Ro.y*Rd.y) + (2 * C * Ro.z*Rd.z) +
			   D * (Ro.x * Rd.y + Ro.y * Rd.x) + 
			   E * (Ro.x * Rd.z + Ro.z * Rd.x) + 
			   F * (Ro.y * Rd.z + Ro.z * Rd.y) +
			   G * Rd.x + H * Rd.y + I * Rd.z;

	double Cq = A * (Ro.x * Ro.x) + B * (Ro.y * Ro.y) + C * (Ro.z * Ro.z) +
			   D * (Ro.x * Ro.y) + E * (Ro.x * Ro.z) + F * (Ro.y * Ro.z) +
			   G * Ro.x + H * Ro.y + I * Ro.z + J; 
	
	// The quadratic equation in the form (-Bq +/- sqrt(Bq*Bq-4 * Aq * Cq))/(2*Aq) is 
	// used to solve for the parameter t..

	//  Part of the quadratic equation under the square root sign
	double discriminant = Bq * Bq - 4 * Aq * Cq;
	 
	// Check if there are any real (non-imaginary) roots to the equation
	if (discriminant >= 0) {

		// Initialize parameter for the point of intersection to largest float possible
		double t = INFINITY; 

		// Does the ray just graze the surface intersecting at only one point?
		if (Aq == 0) {

			t = -Cq / Bq; // Set parameter, t, for the point of intersection

		} 
		else {

			// Use quadratic equation to solve for the closest of the two roots.
			double t0 = (-Bq - sqrt(discriminant)) / (2 * Aq);

			// Is closest point of intersection on the ray or on the negative side of 
			// Ro on a geometric line described by Ro + t* Rd?
			if (t0 > 0) {

				t = t0;
			}
			else {

				// Use quadratic equation to solve for the second closest of the two roots.
				t = (-Bq + sqrt(discriminant)) / (2 * Aq);
			}
		}

		if (t < 0) {
			// Set parameter, t, in the hit record to indicate "no intersection."
			hitRecord.t = INFINITY;
			return hitRecord;
		}

		// Calculate the point of intersection using the parameter t
		dvec3 Ri = Ro + t * Rd;
		
		// Find the normal vector of the surface at the point of intersection
		// using partial derivativex with respect to x, y, and z
		dvec3 Rn;
		Rn.x = 2 * A * Ri.x + D * Ri.y + E * Ri.z + G;
		Rn.y = 2 * B * Ri.y + D * Ri.x + F * Ri.z + H;
		Rn.z = 2 * C * Ri.z + E * Ri.x + F * Ri.y + I;

		// Check if the intersection with the inside or back of the surface
		if (glm::dot(Rn, Rd) > 0) { Rn = -Rn; }

		// Set hit record information about the intersetion.
		hitRecord.t = t;
		hitRecord.interceptPoint = Ri + center;
		hitRecord.surfaceNormal = normalize( Rn );
		hitRecord.material = material;

		// Note:
		// Calculation of UV coordinates should be done in the sub-class
		// using a method that is appropriate to the shape.


	}
	else {
		// Set parameter, t, in the hit record to indicate "no intersection."
		hitRecord.t = INFINITY;
	}

	return hitRecord;

} // end findClosestIntersection

