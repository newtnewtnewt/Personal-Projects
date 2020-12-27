#include "PerVertex.h"

// static Pipeline transformation matrices
dmat4 PerVertex::modelingTransformation;
dmat4 PerVertex::viewingTransformation;
dmat4 PerVertex::projectionTransformation;
dmat4 PerVertex::viewportTransformation;
dvec3 PerVertex::eyePositionInWorldCoords;
dmat3 PerVertex::modelingTransfomationForNormals;

bool PerVertex::perVertexLightingEnabled = false;
Render_Mode PerVertex::polygonRenderMode = FILL;

// static Nnrmalized device coordinate horizontal and vertical limits
const int PerVertex::xNdcMin = -1, PerVertex::yNdcMin = -1, PerVertex::xNdcMax = 1, PerVertex::yNdcMax = 1;

// static view port limits
double PerVertex::xViewportMin, PerVertex::yViewportMin, PerVertex::xViewportMax, PerVertex::yViewportMax;

// static planes describing the normalized device coordinates view volume
std::vector<ClippingPlane> PerVertex::ndcPlanes{ ClippingPlane(dvec3(0, 1, 0), dvec3(0, -1, 0)),
											   ClippingPlane(dvec3(1, 0, 0), dvec3(-1, 0, 0)),
											   ClippingPlane(dvec3(0, 0, 1), dvec3(0, 0, -1)),
											   ClippingPlane(dvec3(-1, 0, 0), dvec3(1, 0, 0)),
											   ClippingPlane(dvec3(0, -1, 0), dvec3(0, 1, 0)),
											   ClippingPlane(dvec3(0, 0, -1), dvec3(0, 0, 1)) };


//********************************** Clipping *********************************

// Clips a convex polygon against a plane.
std::vector<VertexData> PerVertex::clipAgainstPlane(std::vector<VertexData>& verts, ClippingPlane& plane)
{
	std::vector<VertexData> output;

	if (verts.size() > 2) {

		verts.push_back(verts[0]);

		for (unsigned int i = 1; i < verts.size(); i++) {

			bool v0In = plane.insidePlane(verts[i - 1]);
			bool v1In = plane.insidePlane(verts[i]);

			if (v0In && v1In) {

				output.push_back(verts[i]);
			}
			else if (v0In || v1In) {

				VertexData I = plane.findIntersection(verts[i - 1], verts[i]);
				output.push_back(I);

				if (!v0In && v1In) {

					output.push_back(verts[i]);
				}
			}
		}
	}

	return output;

} // end clipAgainstPlane


// Break general convex polygons into triangles.
std::vector<VertexData> PerVertex::triangulate(const std::vector<VertexData>& poly)
{
	std::vector<VertexData> triangles;
	triangles.push_back(poly[0]);
	triangles.push_back(poly[1]);
	triangles.push_back(poly[2]);

	for (unsigned int i = 2; i < poly.size() - 1; i++) {

		triangles.push_back(poly[0]);
		triangles.push_back(poly[i]);
		triangles.push_back(poly[i + 1]);
	}

	return triangles;

} // end triangulate


// Clip polygons against a a normalized view volume. Vertices should be in 
// clip coordinates.
std::vector<VertexData> PerVertex::clipPolygon(const std::vector<VertexData>& clipCoords)
{
	std::vector<VertexData> ndcCoords;

	if (clipCoords.size() > 2) {

		std::vector<VertexData> polygon;

		for (unsigned int i = 0; i < clipCoords.size() - 2; i += 3) {

			polygon.push_back(clipCoords[i]);
			polygon.push_back(clipCoords[i + 1]);
			polygon.push_back(clipCoords[i + 2]);

			for (ClippingPlane plane : ndcPlanes) {
				polygon = clipAgainstPlane(polygon, plane);
			}

			if (polygon.size() > 3) {
				polygon = triangulate(polygon);
			}

			for (VertexData v : polygon) {
				ndcCoords.push_back(v);
			}
			polygon.clear();
		}
	}

	return ndcCoords;

} // end clip


  // Clip line segments against a a normalized view volume. Vertices should be in 
  // clip coordinates.
std::vector<VertexData> PerVertex::clipLineSegments(const std::vector<VertexData>& clipCoords)
{
	std::vector<VertexData> ndcCoords;

	if (clipCoords.size() > 1) {

		for (unsigned int i = 0; i < clipCoords.size() - 1; i += 2) {

			VertexData v0 = clipCoords[i];
			VertexData v1 = clipCoords[i + 1];

			bool outsideViewVolume = false;

			for (unsigned int i = 0; i < ndcPlanes.size(); i++) {
				//for (Plane plane : ndcPlanes) {

				ClippingPlane plane = ndcPlanes[i];

				bool v0In = ndcPlanes[i].insidePlane(v0);
				bool v1In = ndcPlanes[i].insidePlane(v1);

				if (v0In == false && v1In == false) {

					outsideViewVolume = true;
					break; // Line segment is entirely clipped

				}
				else if (v0In == true && v1In == false) {

					v1 = ndcPlanes[i].findIntersection(v0, v1);
				}
				else if (v0In == false && v1In == true) {

					v0 = ndcPlanes[i].findIntersection(v0, v1);
				}
			}

			if (outsideViewVolume == false) {
				ndcCoords.push_back(v0);
				ndcCoords.push_back(v1);
			}
		}
	}

	return ndcCoords;

} // end clip


//********************************** Backface Culling *********************************

  // Remove all triangles that are not facing the view point.
  // Should be performed in orthographic coordinates such as normalized device coordinates.
std::vector<VertexData> PerVertex::removeBackwardFacingTriangles(const std::vector<VertexData> & triangles)
{
	std::vector<VertexData> forwardFacingTriangles;

	const dvec3 v(0.0, 0.0, -1.0);
	for (unsigned int i = 0; i < triangles.size(); i += 3) 
	{
		dvec3 v0 = triangles[i].transPos.xyz;
		dvec3 v1 = triangles[i + 1].transPos.xyz;
		dvec3 v2 = triangles[i + 2].transPos.xyz;

		dvec3 n = findUnitNormal(v0, v1, v2);

		if(glm::dot(n, v) <= 0.0){ 
			forwardFacingTriangles.push_back(triangles[i]);
			forwardFacingTriangles.push_back(triangles[i + 1]);
			forwardFacingTriangles.push_back(triangles[i + 2]);
		}
	}
	//return triangles;

	return forwardFacingTriangles;

} // end removeBackwardFacingTriangles


//********************************** Vertex Shading *********************************


void PerVertex::applyLocalIllumination(VertexData& vertData)
{
	if (PerVertex::perVertexLightingEnabled) {	
		color totalColor = dvec4(0.0, 0.0, 0.0, 1.0);
		for (auto* light : lights) {
			totalColor += light->getLocalIllumination(vertData.objectNormal, vertData.objectPos, vertData.objectNormal, vertData.material, vertData.uv);
		}
		vertData.shadedColor = totalColor;
	}

} // end applyLighting


void PerVertex::vertexShader(VertexData& vertData)
{
	modelingTransfomationForNormals = dmat3(modelingTransformation);
	modelingTransfomationForNormals = glm::transpose(glm::inverse(modelingTransfomationForNormals));

	// Transform the position and surface normal to world coordinates
	vertData.worldPosition = (modelingTransformation * vertData.objectPos).xyz;
	vertData.worldNormal = modelingTransfomationForNormals * vertData.objectNormal;

	// Transform vertex to clip coordinates
	vertData.transPos = projectionTransformation * viewingTransformation * modelingTransformation * vertData.objectPos;

		// Perform lighting calculations in World coordinates
		applyLocalIllumination(vertData);


}

//********************************** Triangle Pipeline *********************************

// Tranforms triangle vertices from world to view port coordinate via eye, clip, and normalized device coordinates. 
// Vertices are clipped and backfaces are culled. Lighting calculations are performed in World coordinates.
void PerVertex::fetchTriangleVertices(std::vector<VertexData> & objectCoords)
{
 
  	for (VertexData & vertData : objectCoords) {
	
		// Apply modeling, viewing, and projection transformation matrices
		vertexShader(vertData);
  
		// Apply perspective projection to complete projection transformation
		vertData.transPos = vertData.transPos / vertData.transPos.w;
  	}

	// Backface Cullling
	std::vector<VertexData> modifiedVertexList = removeBackwardFacingTriangles(objectCoords);

	// Clipping
	modifiedVertexList = clipPolygon(modifiedVertexList);
	//modifiedVertexList = modifiedVertexList;

	// Apply viewport transformation to the vertices
	for (VertexData& vertData : modifiedVertexList) {

		vertData.transPos = viewportTransformation * vertData.transPos;
	}
  
	// Rasterize the triangle vertices according to the current render mode
	if (polygonRenderMode == FILL) {
	
		rasterizeFilledTriangles(modifiedVertexList);
	}
	else { // LINE

		rasterizeWireFrameTriangles(modifiedVertexList);
	}
  
} // end processTriangleVertices
  
//********************************** Line Segment Pipeline *********************************

// Tranforms line segments from world to view port coordinate via eye, clip, and normalized device coordinates. 
// Vertices are clipped . Lighting calculations are performed in World coordinates.
void PerVertex::fetchLineSegmentVertices(std::vector<VertexData> & objectCoords)
{
	for (VertexData& vertData : objectCoords) {

		// Apply modeling, viewing, and projection transformation matrices
		vertexShader(vertData);

		// Apply perspective projection to complete projection transformation
		vertData.transPos = vertData.transPos / vertData.transPos.w;
	}
  
  	// Clipping
  	std::vector<VertexData> modifiedVertexList = clipLineSegments(objectCoords);

	// Apply viewport transformation to vertex
	for (VertexData& vertData : modifiedVertexList) {

		vertData.transPos = viewportTransformation * vertData.transPos;
	}
  
	// Rasterize the vertices that describe line segment endpoints.
	rasterizeLines(modifiedVertexList);
  
} // end linePipeline