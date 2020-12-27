#include "Sphere.h"

color sphereColor;
double shineValue;
Sphere::Sphere(const dvec3 & position, double radius, const color & material, const double shinyIndex)
	: ImplicitSurface(material), center(position), radius(radius)
{
	sphereColor = material;
	shineValue = shinyIndex;
}


HitRecord Sphere::findIntersect( const Ray & ray )
{
	HitRecord hitRecord;

	double discriminant = pow(glm::dot(ray.direct, ray.origin - center), 2) - 
						  dot(ray.direct, ray.direct) *
						 (glm::dot(ray.origin - center, ray.origin - center) - radius * radius);

	if( discriminant >= 0 ) {

		double t = INFINITY;

		if( discriminant > 0 ) {

			// Two intercepts. Find and return the closest one.
			double t1 = (glm::dot(-ray.direct, ray.origin - center) - sqrt(discriminant)) / 
						dot(ray.direct, ray.direct);
			double t2 = (glm::dot(-ray.direct, ray.origin - center) + sqrt(discriminant)) / 
						dot(ray.direct, ray.direct);
	
			if (t1 < 0) {
				t1 = INFINITY;
			}
			if (t2 < 0) {
				t2 = INFINITY;
			}

			if (t1 < t2) {

				t = t1;
			}
			else {

				t = t2;
			}
		}
		else {
			// One Intercept. Find and return the t for the single point of intersection.
			t = glm::dot(-ray.direct, ray.origin - center) / dot(ray.direct, ray.direct);
			if (t < 0) {
				t = INFINITY;
			}
		}

		// Set hit record information about the intersection.
		hitRecord.t = t;
		hitRecord.interceptPoint = ray.origin + t * ray.direct;

		// New Stuff
		dvec3 objectCoord = hitRecord.interceptPoint - this->center;
		hitRecord.uv = calcSphericalTextCoord(objectCoord, 1, 1, Y_AXIS);

		// Assign surface material properties to the hit record
		hitRecord.material.emissiveColor = sphereColor;
		hitRecord.material = material;
		hitRecord.material.dielecticRefractionIndex = shineValue;

		// Calculate the normal vector for the point of intersection
		dvec3 n = glm::normalize(hitRecord.interceptPoint - center);

		
		// Check for back face intersection
		if (glm::dot(n, ray.direct) > 0) {

			n = -n; // reverse the normal
			hitRecord.rayStatus = LEAVING;
		}
		else {

			hitRecord.rayStatus = ENTERING;
		}

		// Assign normal vector to the hit record
		hitRecord.surfaceNormal = n;

	}
	else {
		// Set parameter, t, in the hit record to indicate "no intersection."
		hitRecord.t = INFINITY;
	}

	return hitRecord;

} // end checkIntercept