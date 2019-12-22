#include "ClippingPlane.h"


ClippingPlane::ClippingPlane( glm::vec3 point, glm::vec3 normal )
{
	a = point;
	n = glm::normalize( normal );
}

// Returns true is a point is to the "left" of a plane.
bool ClippingPlane::insidePlane( const VertexData & point )
{
	// If dot product is positive the point is on the "positive" side of the plane
	if( glm::dot( ( dvec3( point.transPos.xyz ) - a ), n ) > 0.0 ) {
		return true;
	}
	else {
		return false;
	}

} // end insidePlane

// Finds the point of intersection for a line segment and a plane
VertexData ClippingPlane::findIntersection( const VertexData &  p1, const VertexData & p2 )
{
	// Find the distance of each point from the plane
	double d1 = glm::dot( dvec3( p1.transPos.xyz ) - a, n );
	double d2 = glm::dot( dvec3( p2.transPos.xyz ) - a, n );

	// Find the paramter of the intercept with the plane
	double t = d1 / ( d1 - d2 );

	// Generate the vertex data for the point of intersection using linear interpolation
	VertexData I = p1 + t * (p2 - p1);

	return I;

} // end findIntersection
