#include "Rasterization.h"

// ColorBuffer object that is global to all files
extern FrameBuffer frameBuffer;

// Draws a line segment if there are at least two vertices
static void rasterizeLine(VertexData v0, VertexData v1);

// Rasterizes a vertical line
static void rasterizeVerticalLine( VertexData v0, VertexData v1 );

// Rasterizes a horizontal line
static void rasterizeHorizontalLine(VertexData v0, VertexData v1);

// Uses the midpoint algorithm to render a sloped line
static void midPointLine(VertexData v0, VertexData v1);

// Draws a wire frame if there are at least three vertices
static void rasterizeWireFrameTriangle(VertexData v0, VertexData v1, VertexData v2);

// Draws a filled triangle if there are at least three vertices
static void rasterizeFilledTriangle(const VertexData& v0, const VertexData& v1, const VertexData& v2);


static Fragment biLinearInterpVertToFrag( const double & alpha, const VertexData & v0, 
										  const double & beta, const VertexData & v1 );

static Fragment gouraudInterpVertToFrag( const double & alpha, const VertexData & v0,
										   const double & beta, const VertexData & v1,
										   const double & gama, const VertexData & v2 );

void rasterizeLine(VertexData v0, VertexData v1)
{
	if (v0.transPos.x == v1.transPos.x) {
		rasterizeVerticalLine(v0, v1);
	}
	else if (v0.transPos.y == v1.transPos.y) {
		rasterizeHorizontalLine(v0, v1);
	}
	else {
		midPointLine(v0, v1);
	}

} // end drawLine


void rasterizeLines(const std::vector<VertexData>& vertices)
{
	for (unsigned int i = 0; (i + 1) < vertices.size(); i += 2) {

		rasterizeLine(vertices[i], vertices[i + 1]);
	}

} // end drawManyLines


void rasterizeWireFrameTriangle(VertexData v0, VertexData v1, VertexData v2)
{
	rasterizeLine(v0, v1);
	rasterizeLine(v1, v2);
	rasterizeLine(v2, v0);

} // end drawWireFrameTriangle


void rasterizeWireFrameTriangles(const std::vector<VertexData>& vertices)
{
	for (unsigned int i = 0; (i + 2) < vertices.size(); i += 3) {

		rasterizeWireFrameTriangle(vertices[i], vertices[i + 1], vertices[i + 2]);
	}

} // end drawManyWireFrameTriangles


// ************** Line Rasterization **************************

double cheapNonPerspectiveCorrectInterpolationForLines(const dvec2 start, const dvec2 end, const dvec2 & online)
{
	return glm::length(online - start) / glm::length(end-start);
}


void rasterizeVerticalLine( VertexData v0, VertexData v1 )
{
	if (v1.transPos.y < v0.transPos.y) {

		std::swap(v0, v1);
	}

	double zDifference = v1.transPos.z - v0.transPos.z;

	for (double y = v0.transPos.y; y < v1.transPos.y; y++) {

		// Interpolate vertex attributes
		double weight = cheapNonPerspectiveCorrectInterpolationForLines(v0.transPos.xy, v1.transPos.xy, dvec2(v0.transPos.x, y));

		Fragment fragment = biLinearInterpVertToFrag((1.0 - weight), v0, weight, v1);

		double z = (1 - weight) * v0.transPos.z + weight * v1.transPos.z;

		fragment.windowPosition = dvec3(v0.transPos.x, y, z);

		FragmentOperations::processFragment(fragment);
	}

} // end drawVerticalLine


void rasterizeHorizontalLine( VertexData v0, VertexData v1)
{
	if (v1.transPos.x < v0.transPos.x) {

		std::swap(v0, v1);
	}

	for (double x = v0.transPos.x; x < v1.transPos.x; x++) {

		// Interpolate vertex attributes
		double weight = cheapNonPerspectiveCorrectInterpolationForLines(v0.transPos.xy, v1.transPos.xy, dvec2(x, v0.transPos.y));

		Fragment fragment = biLinearInterpVertToFrag((1.0 - weight), v0, weight, v1);

		double z = (1 - weight) * v0.transPos.z + weight * v1.transPos.z;

		fragment.windowPosition = dvec3(x, v1.transPos.y, z);

		FragmentOperations::processFragment(fragment);
	}

} // end drawVerticalLine


void midPointLine( VertexData v0, VertexData v1)
{
	// Check if v0 is left of v1
	if (v1.transPos.x < v0.transPos.x) {

		// Make v0 left of v1
		std::swap(v0, v1);
	}

	// Calculate slope of the line
	double m = (v1.transPos.y - v0.transPos.y) / (v1.transPos.x - v0.transPos.x);

	if (m > 0 && m < 1.0) { // For slope in (0,1] More "run" than "rise"

		double y = v0.transPos.y;

		for (double x = v0.transPos.x; x < v1.transPos.x; x += 1.0) {

			// Interpolate vertex attributes
			double weight = cheapNonPerspectiveCorrectInterpolationForLines(v0.transPos.xy, v1.transPos.xy, dvec2(x, y));

			Fragment fragment = biLinearInterpVertToFrag((1.0 - weight), v0, weight, v1);

			double z = (1 - weight) * v0.transPos.z + weight * v1.transPos.z;
			fragment.windowPosition = dvec3(x, y, z);

			FragmentOperations::processFragment(fragment);

			// Evaluate the implicit equation for the line to determine if
			// the line will be above the midpoint between the pixel centers.
			double fXY = (v0.transPos.y - v1.transPos.y) * (x + 1.0) +
				(v1.transPos.x - v0.transPos.x) * (y + 0.5f) +
				v0.transPos.x * v1.transPos.y - v1.transPos.x * v0.transPos.y;

			if (fXY < 0) {
				y += 1.0;
			}
		}
	}
	else if (m > 1) { // For slope in (1,infinity] More "run" than "rise"

		double x = v0.transPos.x;

		for (double y = v0.transPos.y; y < v1.transPos.y; y += 1.0) {

			// Interpolate vertex attributes
			double weight = cheapNonPerspectiveCorrectInterpolationForLines(v0.transPos.xy, v1.transPos.xy, dvec2(x, y));

			Fragment fragment = biLinearInterpVertToFrag((1.0 - weight), v0, weight, v1);

			double z = (1 - weight) * v0.transPos.z + weight * v1.transPos.z;
			fragment.windowPosition = dvec3(x, y, z);

			FragmentOperations::processFragment(fragment);
			
			// Evaluate the implicit equation for the line to determine if
			// the line will be left or right the midpoint between the pixel centers.
			double fXY = (v0.transPos.y - v1.transPos.y) * (x + 0.5f) +
				(v1.transPos.x - v0.transPos.x) * (y + 1.0) +
				v0.transPos.x * v1.transPos.y - v1.transPos.x * v0.transPos.y;

			if (fXY > 0) {
				x += 1.0;
			}
		}
	}
	else if (m >= -1.0 && m < 0) { // For slope in [-1,0) More "run" than "rise"

		double y = v0.transPos.y;
		double x = v0.transPos.x;

		for (double x = v0.transPos.x; x < v1.transPos.x; x += 1.0) {

			// Interpolate vertex attributes
			double weight = cheapNonPerspectiveCorrectInterpolationForLines(v0.transPos.xy, v1.transPos.xy, dvec2(x, y));

			Fragment fragment = biLinearInterpVertToFrag((1.0 - weight), v0, weight, v1);

			double z = (1 - weight) * v0.transPos.z + weight * v1.transPos.z;
			fragment.windowPosition = dvec3(x, y, z);

			FragmentOperations::processFragment(fragment);

			// Evaluate the implicit equation for the line to determine if
			// the line will be below the midpoint between the pixel centers.
			double fXY = (v0.transPos.y - v1.transPos.y) * (x + 1.0) +
				(v1.transPos.x - v0.transPos.x) * (y - 0.5f) +
				v0.transPos.x * v1.transPos.y - v1.transPos.x * v0.transPos.y;

			if (fXY > 0) {

				y -= 1.0;
			}
		}
	}
	else if (m < -1) { // For slope in [-infinity,-1) More "run" than "rise"

		double x = v0.transPos.x;

		for (double y = v0.transPos.y; y > v1.transPos.y; y -= 1.0) {

			// Interpolate vertex attributes
			double weight = cheapNonPerspectiveCorrectInterpolationForLines(v0.transPos.xy, v1.transPos.xy, dvec2(x, y));

			// Interpolate vertex attributes using weights
			Fragment fragment = biLinearInterpVertToFrag((1.0 - weight), v0 , weight, v1);

			// Calculate vertex depth
			double z = (1 - weight) * v0.transPos.z + weight * v1.transPos.z;
			fragment.windowPosition = dvec3(x, y, z);

			FragmentOperations::processFragment(fragment);

			// Evaluate the implicit equation for the line to determine if
			// the line will be left or right the midpoint between the pixel centers.
			double fXY = (v0.transPos.y - v1.transPos.y) * (x + 0.5f) +
				(v1.transPos.x - v0.transPos.x) * (y - 1.0) +
				v0.transPos.x * v1.transPos.y - v1.transPos.x * v0.transPos.y;

			if (fXY < 0) {
				x += 1.0;
			}
		}
	}

} // end midPointLine


Fragment biLinearInterpVertToFrag(const double& alpha, const VertexData& v0,
	const double& beta, const VertexData& v1)
{
	Fragment fragment;

	// Interpolate vertex attributes using weights
	fragment.shadedColor = alpha * v0.shadedColor + beta * v1.shadedColor;
	fragment.material = alpha * v0.material + beta * v1.material;
	fragment.worldNormal = alpha * v0.worldNormal + beta * v1.worldNormal;
	fragment.worldPosition = alpha * v0.worldPosition + beta * v1.worldPosition;
	fragment.uv = alpha * v0.uv + beta * v1.uv;

	return fragment;

} // end biLinearInterpVertToFrag

// ************** Triangle Rasterization **************************


// Implicit equation for the line between v0 and v1
inline double f01(const VertexData & v0, const VertexData & v1, const VertexData & v2, double x, double y)
{
	return (v0.transPos.y - v1.transPos.y) * x + (v1.transPos.x - v0.transPos.x) * y + 
		   (v0.transPos.x * v1.transPos.y) - (v1.transPos.x * v0.transPos.y);

} // end f01


// Implicit equation for the line between v1 and v2
inline double f12(const VertexData & v0, const VertexData & v1, const VertexData & v2, double x, double y)
{
	return (v1.transPos.y - v2.transPos.y) * x + (v2.transPos.x - v1.transPos.x) * y + 
		   (v1.transPos.x * v2.transPos.y) - (v2.transPos.x * v1.transPos.y);

} // end f12


 // Implicit equation for the line between v2 and v0
inline double f20(const VertexData & v0, const VertexData & v1, const VertexData & v2, double x, double y)
{
	return (v2.transPos.y - v0.transPos.y) * x + (v0.transPos.x - v2.transPos.x) * y + 
		   (v2.transPos.x * v0.transPos.y) - (v0.transPos.x * v2.transPos.y);

} // end f20


void rasterizeFilledTriangle(const VertexData & v0, const VertexData & v1, const VertexData & v2)
{
	// Find minimimum and maximum x and y limits for the triangle
	double xMin = glm::floor(glm::min(glm::min(v0.transPos.x, v1.transPos.x), v2.transPos.x));
	double xMax = glm::ceil(glm::max(glm::max(v0.transPos.x, v1.transPos.x), v2.transPos.x));
	double yMin = glm::floor(glm::min(glm::min(v0.transPos.y, v1.transPos.y), v2.transPos.y));
	double yMax = glm::ceil(glm::max(glm::max(v0.transPos.y, v1.transPos.y), v2.transPos.y));

	double fAlpha = f12(v0, v1, v2, v0.transPos.x, v0.transPos.y);
	double fBeta = f20(v0, v1, v2, v1.transPos.x, v1.transPos.y);
	double fGama = f01(v0, v1, v2, v2.transPos.x, v2.transPos.y);

	// Check all the pixels in the rows between the minimum and maximum y
	for (double y = yMin; y <= yMax; y++) {

		// Check all the pixels in the columns between the minimum and maximum x
		for (double x = xMin; x <= xMax; x++) {

			// Calculate the weights for Gouraud inperpolation
			// If any weight is negative, the fragment is not in the triangle
			double alpha = f12(v0, v1, v2, x, y) / fAlpha;
			double beta = f20(v0, v1, v2, x, y) / fBeta;
			double gama = f01(v0, v1, v2, x, y) / fGama;

			// Determine if the pixel position is inside the triangle
			if (alpha >= 0 && beta >= 0 && gama >= 0) {

				if ((alpha > 0 || fAlpha * f12(v0, v1, v2, -1, -1) > 0) &&
					(beta > 0 || fBeta * f20(v0, v1, v2, -1, -1) > 0) &&
					(gama > 0 || fGama * f01(v0, v1, v2, -1, -1) > 0)) {

					// Interpolate vertex attributes using alpha, beta, and gama weights
					Fragment fragment = gouraudInterpVertToFrag(alpha, v0, beta, v1, gama, v2);

					// Interpolate z values to determine fragment depth
					double z = alpha * v0.transPos.z + beta * v1.transPos.z + gama * v2.transPos.z;

					// Set fragment window position
					fragment.windowPosition = dvec3(x, y, z);

					// Perform per fragment operations on the fragment
					FragmentOperations::processFragment(fragment);
				}
			}
		}
	}
} // end drawFilledTriangle


void rasterizeFilledTriangles(const std::vector<VertexData> & vertices)
{
	for (unsigned int i = 0; (i + 2) < vertices.size(); i += 3) {

		rasterizeFilledTriangle(vertices[i], vertices[i + 1], vertices[i + 2]);
	}

} // end drawManyFilledTriangles


Fragment gouraudInterpVertToFrag( const double& alpha, const VertexData& v0,
									const double& beta, const VertexData& v1,
									const double& gama, const VertexData& v2)
{
	Fragment fragment;

	// Interpolate vertex attributes using alpha, beta, and gama weights
	fragment.shadedColor = alpha * v0.shadedColor + beta * v1.shadedColor + gama * v2.shadedColor;
	fragment.material = alpha * v0.material + beta * v1.material + gama * v2.material;
	fragment.worldNormal = alpha * v0.worldNormal + beta * v1.worldNormal + gama * v2.worldNormal;
	fragment.worldPosition = alpha * v0.worldPosition + beta * v1.worldPosition + gama * v2.worldPosition;
	fragment.uv = alpha * v0.uv + beta * v1.uv + gama * v2.uv;

	return fragment;
}