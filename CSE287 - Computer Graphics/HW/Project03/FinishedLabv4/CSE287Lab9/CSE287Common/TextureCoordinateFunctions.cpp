#include "TextureCoordinateFunctions.h"

dvec2 calcSphericalTextCoord(const dvec3& objectCoord, const double& width, const double& height, AXIS axis)
{
	double s, t;

	dvec3 normObjCoord = glm::normalize(objectCoord);

	if (axis == X_AXIS) {
		s = 0.5 - atan2(normObjCoord.z, normObjCoord.y) / TWO_PI;
		t = 0.5 - (asin(normObjCoord.x) / PI);
	}
	else if (axis == Y_AXIS) {
		s = 0.5 - atan2(normObjCoord.z, normObjCoord.x) / TWO_PI;
		t = 0.5 - (asin(normObjCoord.y) / PI);
	}
	else { // Z_AXIS
		s = 0.5 - atan2(normObjCoord.y, normObjCoord.x) / TWO_PI;
		t = 0.5 - (asin(normObjCoord.z) / PI);
	}

	return glm::dvec2(s, t);

}

dvec2 calcPlanarTextCoord(const dvec3& objectCoord, const double& width, const double& height, AXIS axis)
{
	double s, t;
	if (axis == X_AXIS) {
		s = objectCoord.z / width + width / 2;
		t = objectCoord.y / height + height / 2;
	}
	else if (axis == Y_AXIS) {
		s = objectCoord.x / width + width / 2;
		t = objectCoord.z / height + height / 2;
	}
	else { // Z_AXIS	
		s = objectCoord.x / width + width / 2;
		t = objectCoord.y / height + height / 2;
	}

	return dvec2(s, t);
}

dvec2 calcCylindricalTextCoord(const dvec3& localCoordinates, const double& height, AXIS axis)
{
	double s, t;

	if (axis == X_AXIS) {
		s = 0.5 - atan2(localCoordinates.y, localCoordinates.z) / TWO_PI;
		t = 0.5 - localCoordinates.x / height;
	}
	else if (axis == Y_AXIS) {
		s = 0.5 - atan2(localCoordinates.x, localCoordinates.z) / TWO_PI;
		t = 0.5 - localCoordinates.y / height;
	}
	else { // Z_AXIS
		s = 0.5 - atan2(localCoordinates.x, localCoordinates.y) / TWO_PI;
		t = 0.5 - localCoordinates.z / height;
	}

	return glm::dvec2(s, t);
}



