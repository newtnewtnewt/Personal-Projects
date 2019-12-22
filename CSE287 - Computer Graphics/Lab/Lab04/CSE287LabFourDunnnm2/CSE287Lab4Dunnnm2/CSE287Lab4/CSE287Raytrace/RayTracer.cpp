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
				colorBuffer.setPixel(x, y, traceRay(getPerspectiveViewRay(x, y)));
			}
			else {
				colorBuffer.setPixel(x, y, traceRay(getOrthoViewRay(x, y)));
			}
			
		}
	}


} // end raytraceScene


color RayTracer::traceRay(const Ray& ray, int recursionLevel)
{
	// Find surface intersection that is closest to the origin of the viewRay
	HitRecord record = findClosestIntersection(ray);
	if(record.t == INFINITY) {
		return defaultColor;
	}
	else {
		return record.material.getDiffuse();
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





