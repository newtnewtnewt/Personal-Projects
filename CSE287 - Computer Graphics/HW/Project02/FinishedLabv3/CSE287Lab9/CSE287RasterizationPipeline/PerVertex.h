#pragma once

#include "Defines.h"
#include "LightSource.h"
#include "Rasterization.h"
#include "VertexData.h"
#include "ClippingPlane.h"

extern std::vector<LightSource*> lights;

enum Render_Mode { FILL, LINE };


class PerVertex {

public:

	// Pipeline transformation matrices
	static dmat4 modelingTransformation;
	static  dmat4 viewingTransformation;
	static dmat4 projectionTransformation;
	static dmat4 viewportTransformation;
	static  dvec3 eyePositionInWorldCoords;
	static dmat3  modelingTransfomationForNormals;

	// View port limits
	static double xViewportMin, yViewportMin, xViewportMax, yViewportMax;

	// Normalized device coordinate horizontal and vertical limits
	static const int xNdcMin, yNdcMin, xNdcMax, yNdcMax;

	// Planes describing the normalized device coordinates view volume
	static std::vector<ClippingPlane> ndcPlanes;

	static bool perVertexLightingEnabled;

	static Render_Mode polygonRenderMode;

	static void fetchTriangleVertices(std::vector<VertexData> & objectCoords);

	static void fetchLineSegmentVertices(std::vector<VertexData> & objectCoords);

protected:

	static void vertexShader(VertexData& vertData);

	static std::vector<VertexData> clipAgainstPlane(std::vector<VertexData> & verts, ClippingPlane & plane);

	static std::vector<VertexData> triangulate(const std::vector<VertexData> & poly);

	static std::vector<VertexData> clipPolygon(const std::vector<VertexData> & clipCoords);

	static std::vector<VertexData> clipLineSegments(const std::vector<VertexData> & clipCoords);

	static std::vector<VertexData> removeBackwardFacingTriangles(const std::vector<VertexData> & triangleVerts);

	static void applyLocalIllumination(VertexData& vertData);

}; // end PerVertex class



