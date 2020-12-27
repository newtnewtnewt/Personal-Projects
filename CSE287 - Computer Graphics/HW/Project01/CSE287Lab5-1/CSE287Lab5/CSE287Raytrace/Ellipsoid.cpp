#pragma once
#include "QuadricSurface.h"
class Ellipsoid : public QuadricSurface {
public:
	Ellipsoid(const dvec3& position, const color& mat, const double a, const double b, const double c) :
		QuadricSurface(position, mat) {
		A = 1 / pow(a, 2);
		B = 1 / pow(b, 2);
		C = 1 / pow(c, 2);
		D = 0; 
		E = 0;
		F = 0; 
		G = 0;
		H = 0;
		I = 0;
		J = -1;
	}



};
