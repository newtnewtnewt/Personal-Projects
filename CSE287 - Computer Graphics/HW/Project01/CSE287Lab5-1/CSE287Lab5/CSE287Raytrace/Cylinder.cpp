#pragma once
#include "QuadricSurface.h"
class Cylinder : public QuadricSurface {
public:
	double r;
	double h;
	int axis;
	dvec3 position;
	Cylinder(const dvec3& position, const color& mat, const double r, const double h, int axis) :
		QuadricSurface(position, mat), position(position), r(r), h(h), axis(axis) {
		//  X-Axis facing Cylinder ripped from Chapter 3 slides
		if (axis == X_AXIS) {
			A = 0;
			B = 1 / pow(r, 2);
			C = 1 / pow(r, 2);
			D = 0;
			E = 0;
			F = 0;
			G = 0;
			H = 0;
			I = 0;
			J = -1;
		}
		//  Y-Axis oriented cylinder ripped from Chapter 3 Slides
		else if (axis == Y_AXIS) {
			A = 1 / pow(r, 2);
			B = 0;
			C = 1 / pow(r, 2);
			D = 0;
			E = 0;
			F = 0;
			G = 0;
			H = 0;
			I = 0;
			J = -1;
		}
		//  Z-Axis facing cylinder 
		else {
			A = 0;
			B = 1 / pow(r, 2);
			C = 1 / pow(r, 2);
			D = 0;
			E = 0;
			F = 0;
			G = 0;
			H = 0;
			I = 0;
			J = -1;
		}
	}
	HitRecord findIntersect(const Ray& ray) {
		HitRecord heavyLift = QuadricSurface::findIntersect(ray);
		if (axis == Y_AXIS) {
			if (heavyLift.interceptPoint.y >= (position.y - h) && heavyLift.interceptPoint.y <= (position.y + h)) {
					return heavyLift;
			}
			else {
				heavyLift = QuadricSurface::findIntersect(Ray(heavyLift.interceptPoint - EPSILON * heavyLift.surfaceNormal, ray.direct));
				if (heavyLift.interceptPoint[1] >= (position[1] - h) && heavyLift.interceptPoint[1] <= (position[1] + h)) {
					return heavyLift;
				}
				else {
					heavyLift.t = INFINITY;
					return heavyLift;
				}
			}
		}
		else if (axis == X_AXIS) {
				if (heavyLift.interceptPoint.x >= (position.x - h) && heavyLift.interceptPoint.x <= (position.x + h)) {
					return heavyLift;
				}
				else {
					heavyLift = QuadricSurface::findIntersect(Ray(heavyLift.interceptPoint - EPSILON * heavyLift.surfaceNormal, ray.direct));
					if (heavyLift.interceptPoint.x >= (position.x - h) && heavyLift.interceptPoint.x <= (position.x + h)) {
						return heavyLift;
					}
					else {
						heavyLift.t = INFINITY;
						return heavyLift;
					}
				}
			}
		else {
				if (heavyLift.interceptPoint.z >= (position.z - h) && heavyLift.interceptPoint.z <= (position.z + h)) {
					return heavyLift;
				}
				else {
					heavyLift = QuadricSurface::findIntersect(Ray(heavyLift.interceptPoint + EPSILON * heavyLift.surfaceNormal, ray.direct));
					if (heavyLift.interceptPoint.z >= (position.z - h) && heavyLift.interceptPoint.z <= (position.z + h)) {
						return heavyLift;
					}
					else {
						heavyLift.t = INFINITY;
						return heavyLift;
					}
				}
			}

	}
	
};
	

