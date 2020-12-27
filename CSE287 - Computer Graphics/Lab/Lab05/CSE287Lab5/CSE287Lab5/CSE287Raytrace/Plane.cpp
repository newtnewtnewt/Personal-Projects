#include "Plane.h"


Plane::Plane(const dvec3 & point, const dvec3 & normal, const color & material)
	: ImplicitSurface(material), a(point), n(normalize(normal))
{
}


Plane::Plane(std::vector<dvec3> vertices, const color & material)
	: ImplicitSurface(material)
{
	a = vertices[0];

	n = glm::normalize(glm::cross(vertices[2] - vertices[1], vertices[0] - vertices[1]));
}


HitRecord Plane::findIntersect(const Ray& ray){
	HitRecord hitRecord;
	if (abs(glm::dot(ray.direct, n)) <0.001) {
		hitRecord.t = INFINITY;
		return hitRecord;
	}
	else {
		double t = glm::dot((a - ray.origin), n) / (glm::dot(ray.direct, n));
		if (t < 0) {
			hitRecord.t = INFINITY;
		}
		else {
			hitRecord.t = t;
			hitRecord.interceptPoint = ray.origin + t * ray.direct;
			hitRecord.material = material;
			hitRecord.surfaceNormal = n;
		}
		return hitRecord;
	}
} // end findClosestIntersection

