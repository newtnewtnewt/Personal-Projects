#include "RayTracer.h"


RayTracer::RayTracer(FrameBuffer& cBuffer, color defaultColor)
	:colorBuffer(cBuffer), defaultColor(defaultColor), recursionDepth(2)
{

}


void RayTracer::setCameraFrame(const dvec3& viewPosition, const dvec3& viewingDirection, dvec3 up)
{
	eye = viewPosition;
	w = glm::normalize(-viewingDirection); // backwards
	u = glm::normalize(glm::cross(up, w));
	v = glm::normalize(glm::cross(w, u));
} // end setCameraFrame


void RayTracer::calculatePerspectiveViewingParameters(const double& verticalFieldOfViewDegrees)
{
	
	// Calculate the distance between pixels in the horizontal and vertical directions
	nx = (double)colorBuffer.getWindowWidth();
	ny = (double)colorBuffer.getWindowHeight();

	topLimit = 1.0;
	bottomLimit = -topLimit;

	distToPlane = topLimit / (glm::tan(glm::radians(verticalFieldOfViewDegrees) / 2.0));

	rightLimit = topLimit * (nx / ny);
	leftLimit = -rightLimit;

	renderPerspectiveView = true; // generate perspective view rays

} // end calculatePerspectiveViewingParameters


void RayTracer::calculateOrthographicViewingParameters(const double& viewPlaneHeight)
{
	topLimit = fabs(viewPlaneHeight) / 2.0;

	rightLimit = topLimit * ((double)colorBuffer.getWindowWidth() / colorBuffer.getWindowHeight()); // Set r based on aspect ratio and height of plane

	// Make view plane symetrical about the viewing direction
	leftLimit = -rightLimit;
	bottomLimit = -topLimit;

	// Calculate the distance between pixels in the horizontal and vertical directions
	nx = (double)colorBuffer.getWindowWidth();
	ny = (double)colorBuffer.getWindowHeight();

	topLimit = 1.0;


	distToPlane = 0.0; // Rays start on the view plane

	renderPerspectiveView = false; // generate orthographic view rays

} // end calculateOrthographicViewingParameters


void RayTracer::raytraceScene()
{
	// Iterate through each and every pixel in the rendering window
	for (int y = 0; y < colorBuffer.getWindowHeight(); y++) {
		for (int x = 0; x < colorBuffer.getWindowWidth(); x++) {
			if (renderPerspectiveView == true) {
				colorBuffer.setPixel(x, y, traceRay(getPerspectiveViewRay(x, y), recursionDepth));
			}
			else {
				colorBuffer.setPixel(x, y, traceRay(getOrthoViewRay(x, y), recursionDepth));
			}
			
		}
	}


} // end raytraceScene

/**
 * @fn	static double fresnel(const dvec3& i, const dvec3& n, const double& etai, const double& etat)
 *
 * @brief	Compute Fresnel equation
 *
 * @param	i   	is the incident view direction.
 * @param	n   	is the normal at the intersection point.
 * @param	etai	is the refractive index of the material the light is leaving.
 * @param	etat	is the refractive index of the material the light is entering.
 *
 * @returns	kr is the percentage of light reflected As a consequence of the conservation of
 * 			energy, transmittance is given by: kt = 1 - kr
 *
 */
static double fresnel(const dvec3& i, const dvec3& n, const double& etai, const double& etat)
{
	// Percentage of light that is reflected
	// Percentage of light that is refracted is equal to 1-kr
	double kr;

	// Calculate the dot product of the incidence vector and the normal 
	// vector of the surface the the light is entering
	double cosi = glm::clamp(-1.0, 1.0, glm::dot(i, n));

	// Compute the sine of the angle of refraction using Snell's law
	double sint = etai / etat * sqrt(glm::max(0.0, 1.0 - cosi * cosi));

	// Check if angle of incidence exceeds critical angle 
	if (sint >= 1.0) {
		kr = 1.0; // Total internal reflection
	}
	else {
		// Calculate the percentage of light that will be reflected
		double cost = sqrt(glm::max(0.0, 1.0 - sint * sint));
		cosi = fabs(cosi);

		// S polarized light (parallel)
		double Rs = ((etat * cosi) - (etai * cost)) / ((etai * cost) + (etat * cosi));
		// P polarized light (perpendicular)
		double Rp = ((etai * cosi) - (etat * cost)) / ((etat * cost) + (etai * cosi));

		kr = (Rs * Rs + Rp * Rp) / 2.0;
	}

	return kr;

} // end fresnel


color RayTracer::traceRay(const Ray& ray, int recursionLevel)
{
	// Find surface intersection that is closest to the origin of the viewRay
	HitRecord closestHit = findClosestIntersection(ray);
	double etai = 2.3, etat = 0.8;
	if(closestHit.t == INFINITY){
		return defaultColor;
	}
	else {
		color totalLight = BLACK;

		for (auto& light : lights) {
			//  Our directional vector to our light source
			dvec3 shadowRay = light->getLightVector(closestHit.interceptPoint + EPSILON * closestHit.surfaceNormal);
			double distToLight = light->getLightDistance(shadowRay);
			//  Combining position and direction to see if we actually hit something before the light source
			double distOfShadow = findClosestIntersection(Ray(closestHit.interceptPoint + EPSILON * closestHit.surfaceNormal, shadowRay)).t;
			//  If we hit something before we hit the light, there must be a shadow
			if(distOfShadow > distToLight){
				totalLight += light->getLocalIllumination(
					-ray.direct, closestHit.interceptPoint,
					closestHit.surfaceNormal,
					closestHit.material, closestHit.uv);
			}
			
			
		}
		/* Code provided by Dr. Bachmann */
		totalLight += closestHit.material.getEmisive() * 0.2;
		if (recursionLevel > 0) {
			if (closestHit.rayStatus == ENTERING) { etai = 1.0; etat = closestHit.material.dielecticRefractionIndex; }
			else { etai = closestHit.material.dielecticRefractionIndex; etat = 1.0; }

			double kr = fresnel(ray.direct, closestHit.surfaceNormal, etai, etat);
			dvec3 reflectDirection = glm::reflect(ray.direct, closestHit.surfaceNormal);

			Ray reflectRay(closestHit.interceptPoint + EPSILON * closestHit.surfaceNormal, reflectDirection);
	
			dvec3 refractDirection = glm::refract(ray.direct, closestHit.surfaceNormal, /*Out of*/ etat / /*Into*/ etai);

			Ray refractRay(closestHit.interceptPoint - EPSILON * closestHit.surfaceNormal, refractDirection);

			Material bounceColor = closestHit.material;
			totalLight += kr * traceRay(reflectRay, recursionLevel - 1);
			
			if (bounceColor.getEmisive()[3] != 1) {

				totalLight += (1.0 - kr) * traceRay(refractRay, recursionLevel - 1);
			}
		}

		return totalLight;
	}


} // end traceRay

HitRecord RayTracer::findClosestIntersection(const Ray& ray)
{
	HitRecord closestHit;
	closestHit.t = INFINITY;

	// Check if the ray intersects any surfaces in the scene
	for (int i = 0; i < this->surfaces.size(); i++) {
		if (surfaces[i]->findIntersect(ray).t < closestHit.t) {
			closestHit = surfaces[i]->findIntersect(ray);
		}
	}

	return closestHit;

} // end findIntersection


Ray RayTracer::getOrthoViewRay(const int& x, const int& y)
{
	Ray orthoViewRay;

	dvec2 uv = getImagePlaneCoordinates(x, y);

	orthoViewRay.origin = eye + uv.x * u + uv.y * v;
	orthoViewRay.direct = glm::normalize(-w);

	return orthoViewRay;

} // end getOrthoViewRay


Ray RayTracer::getPerspectiveViewRay(const int& x, const int& y)
{
	Ray perspectiveViewRay;
	dvec2 uv = getImagePlaneCoordinates(x, y);

	perspectiveViewRay.origin = eye;
	perspectiveViewRay.direct = glm::normalize(distToPlane * -w + uv.x * u + uv.y * v);

	return perspectiveViewRay;

} // end getPerspectiveViewRay


dvec2 RayTracer::getImagePlaneCoordinates(const int& x, const int& y)
{
	dvec2 s;
	s[0] = leftLimit + (rightLimit - leftLimit) * ((x + 0.5) / nx);
	s[1] = bottomLimit + (topLimit - bottomLimit) * ((y + 0.5) / ny);
	return s;

} // end getImagePlaneCoordinates





