#include "ExplicitShapes.h"
#include "Lab.h"

#include "TextureCoordinateFunctions.h"
Camera::Camera(glm::dvec3 position, glm::dvec3 direction, glm::dvec3 up)
{
	viewTrans = glm::lookAt(position, direction, up);
};

glm::dvec3 Camera::getWorldCoordinateViewPosition()
{
	return glm::inverse(viewTrans)[3].xyz;
}
void Camera::setCamera(glm::dvec3 position, glm::dvec3 direction, glm::dvec3 up) {
	viewTrans = glm::lookAt(position, direction, up);
}
glm::dmat4 Camera::getViewingTransformation() {
	return viewTrans;
}
Camera::~Camera() {}

	void ExplicitShape::draw() {
		// Send the reference plane vertices down the pipeline
		PerVertex::fetchTriangleVertices(triangleVertices);
		PerVertex::modelingTransformation = glm::translate(dvec3(0, 0, 0));
		
	}

	void ExplicitShape::setPosition(const dvec3& positionVector) {
		position = glm::translate(positionVector);
		PerVertex::modelingTransformation = PerVertex::modelingTransformation * position;
	}

    void ExplicitShape::setOrientation(const double& angle, const dvec3& axis) {
		orientation = glm::rotate(angle , axis);
		PerVertex::modelingTransformation = PerVertex::modelingTransformation * orientation;
	}

	void ExplicitShape::setScale(const double& scaleValue) {
		scale = glm::scale(scaleValue * dvec3(1, 1, 1));
		PerVertex::modelingTransformation = PerVertex::modelingTransformation * scale;
	}



//********************************** Pyramid *********************************
Box::Box(color cubeColor, float width, float height, float depth) {
	color c = cubeColor;
	
	
	/*dvec4 ftl(-width / 2.0, -height / 2.0, width / 2.0, 1.0);
	dvec4 ftr(width / 2.0, -height / 2.0, width / 2.0, 1.0);
	dvec4 fbl(width / 2.0, -height / 2.0, -width / 2.0, 1.0);
	dvec4 fbr(-width / 2.0, -height / 2.0, -width / 2.0, 1.0);*/

	dvec4 ftl(-width / 2.0, height / 2.0,  depth / 2.0, 1.0);
	dvec4 ftr(width / 2.0,  height / 2.0,  depth / 2.0, 1.0);
	dvec4 fbl(-width / 2.0, -height / 2.0, depth / 2.0, 1.0);
	dvec4 fbr(width / 2.0, -height / 2.0,  depth / 2.0, 1.0);
	
	dvec4 btl(-width / 2.0, height / 2.0,  -depth / 2.0, 1.0);
	dvec4 btr(width / 2.0, height / 2.0,   -depth / 2.0, 1.0);
	dvec4 bbl(-width / 2.0, -height / 2.0, -depth / 2.0, 1.0);
	dvec4 bbr(width / 2.0, -height / 2.0,  -depth / 2.0, 1.0);
	


	// Set vertex locations for one triangle

	//  Front face triangles
	dvec4 n(findUnitNormal(ftr.xyz, ftl.xyz, fbl.xyz), 0.0);

	triangleVertices.push_back(VertexData(ftr, c, n));
	triangleVertices.push_back(VertexData(ftl, c, n));
	triangleVertices.push_back(VertexData(fbl, c, n));
	
	n = dvec4(findUnitNormal(fbl.xyz, fbr.xyz, ftr.xyz), 0.0);

	triangleVertices.push_back(VertexData(fbl, c, n));
	triangleVertices.push_back(VertexData(fbr, c, n));
	triangleVertices.push_back(VertexData(ftr, c, n));

	//  Left face triangles

	n = dvec4(findUnitNormal(ftl.xyz, btl.xyz, bbl.xyz), 0.0);

	triangleVertices.push_back(VertexData(ftl, c, n));
	triangleVertices.push_back(VertexData(btl, c, n));
	triangleVertices.push_back(VertexData(bbl, c, n));

	n = dvec4(findUnitNormal(bbl.xyz, fbl.xyz, ftl .xyz), 0.0);

	triangleVertices.push_back(VertexData(bbl, c, n));
	triangleVertices.push_back(VertexData(fbl, c, n));
	triangleVertices.push_back(VertexData(ftl , c, n));

	// Right face triangles

	n = dvec4(findUnitNormal(btr.xyz, ftr.xyz, fbr.xyz), 0.0);

	triangleVertices.push_back(VertexData(btr, c, n));
	triangleVertices.push_back(VertexData(ftr, c, n));
	triangleVertices.push_back(VertexData(fbr, c, n));

	n = dvec4(findUnitNormal(fbr.xyz, bbr.xyz, btr.xyz), 0.0);

	triangleVertices.push_back(VertexData(fbr, c, n));
	triangleVertices.push_back(VertexData(bbr, c, n));
	triangleVertices.push_back(VertexData(btr, c, n));

	// Back face triangles
	n = dvec4(findUnitNormal(bbl.xyz, btl.xyz, btr.xyz), 0.0);
	triangleVertices.push_back(VertexData(bbl, c, n));
	triangleVertices.push_back(VertexData(btl, c, n));
	triangleVertices.push_back(VertexData(btr, c, n));

	n = dvec4(findUnitNormal(btr.xyz, bbr.xyz, bbl.xyz), 0.0);

	triangleVertices.push_back(VertexData(btr, c, n));
	triangleVertices.push_back(VertexData(bbr, c, n));
	triangleVertices.push_back(VertexData(bbl, c, n));

	//  Top face triangles

	n = dvec4(findUnitNormal(btr.xyz, btl.xyz, ftl.xyz), 0.0);

	triangleVertices.push_back(VertexData(btr, c, n));
	triangleVertices.push_back(VertexData(btl, c, n));
	triangleVertices.push_back(VertexData(ftl, c, n));
	
	n = dvec4(findUnitNormal(ftl.xyz, ftr.xyz, btr.xyz), 0.0);

	triangleVertices.push_back(VertexData(ftl, c, n));
	triangleVertices.push_back(VertexData(ftr, c, n));
	triangleVertices.push_back(VertexData(btr, c, n));

	//  Bottom face triangles

	n = dvec4(findUnitNormal(fbl.xyz, bbl.xyz, bbr.xyz), 0.0);

	triangleVertices.push_back(VertexData(fbl, c, n));
	triangleVertices.push_back(VertexData(bbl, c, n));
	triangleVertices.push_back(VertexData(bbr, c, n));

	n = dvec4(findUnitNormal(bbr.xyz, fbr.xyz, fbl.xyz), 0.0);

	triangleVertices.push_back(VertexData(bbr, c, n));
	triangleVertices.push_back(VertexData(fbr, c, n));
	triangleVertices.push_back(VertexData(fbl, c, n));

}


Pyramid::Pyramid( color pyColor, double width, double height)
{
	color c = pyColor;

	dvec4 v0(0.0, height / 2.0, 0.0, 1.0);
	dvec4 v1(-width / 2.0, -height / 2.0, width / 2.0, 1.0);
	dvec4 v2(width / 2.0, -height / 2.0, width / 2.0, 1.0);
	dvec4 v3(width / 2.0, -height / 2.0, -width / 2.0, 1.0);
	dvec4 v4(-width / 2.0, -height / 2.0, -width / 2.0, 1.0);


	// Set vertex locations for one triangle
	dvec4 n(findUnitNormal(v0.xyz, v1.xyz, v2.xyz), 0.0);

	triangleVertices.push_back(VertexData(v0, c, n));
	triangleVertices.push_back(VertexData(v1, c, n));
	triangleVertices.push_back(VertexData(v2, c, n));
	

	dvec4 n2(findUnitNormal(v0.xyz, v4.xyz, v1.xyz), 0.0);

	triangleVertices.push_back(VertexData(v0, c, n2));
	triangleVertices.push_back(VertexData(v4, c, n2));
	triangleVertices.push_back(VertexData(v1, c, n2));

	dvec4 n3(findUnitNormal(v0.xyz, v3.xyz, v4.xyz), 0.0);

	triangleVertices.push_back(VertexData(v0, c, n3));
	triangleVertices.push_back(VertexData(v3, c, n3));
	triangleVertices.push_back(VertexData(v4, c, n3));

	dvec4 n5(findUnitNormal(v0.xyz, v2.xyz, v3.xyz), 0.0);

	triangleVertices.push_back(VertexData(v0, c, n5));
	triangleVertices.push_back(VertexData(v2, c, n5));
	triangleVertices.push_back(VertexData(v3, c, n5));

	dvec4 n6(findUnitNormal(v1.xyz, v4.xyz, v2.xyz), 0.0);

	triangleVertices.push_back(VertexData(v1, c, n6));
	triangleVertices.push_back(VertexData(v4, c, n6));
	triangleVertices.push_back(VertexData(v2, c, n6));

	dvec4 n7(findUnitNormal(v1.xyz, v4.xyz, v2.xyz), 0.0);

	triangleVertices.push_back(VertexData(v3, c, n7));
	triangleVertices.push_back(VertexData(v2, c, n7));
	triangleVertices.push_back(VertexData(v4, c, n7));


} // end Pyramid Constructor

//********************************** Reference Plane *********************************

ReferencePlane::ReferencePlane( double planeWidth, color c1, color c2 )
	: color1(c1), color2(c2)
{
	dvec4 n(0.0, 1.0, 0.0, 0.0);

	triangleVertices.push_back(VertexData(dvec4(0.0, 0.0, 0.0, 1.0), color1, n));
	triangleVertices.push_back(VertexData(dvec4(-planeWidth / 2.0, 0.0, -planeWidth / 2.0, 1.0), color1, n));
	triangleVertices.push_back(VertexData(dvec4(-planeWidth / 2.0, 0.0, planeWidth / 2.0, 1.0), color1, n));
	triangleVertices.push_back(VertexData(dvec4(0.0, 0.0, 0.0, 1.0), color1, n));
	triangleVertices.push_back(VertexData(dvec4(planeWidth / 2.0, 0.0, planeWidth / 2.0, 1.0), color1, n));
	triangleVertices.push_back(VertexData(dvec4(planeWidth / 2.0, 0.0, -planeWidth / 2.0, 1.0), color1, n));

	triangleVertices.push_back(VertexData(dvec4(0.0, 0.0, 0.0, 1.0), color2, n));
	triangleVertices.push_back(VertexData(dvec4(planeWidth / 2.0, 0.0, -planeWidth / 2.0, 1.0), color2, n));
	triangleVertices.push_back(VertexData(dvec4(-planeWidth / 2.0, 0.0, -planeWidth / 2.0, 1.0), color2, n));
	triangleVertices.push_back(VertexData(dvec4(0.0, 0.0, 0.0, 1.0), color2, n));
	triangleVertices.push_back(VertexData(dvec4(-planeWidth / 2.0, 0.0, planeWidth / 2.0, 1.0), color2, n));
	triangleVertices.push_back(VertexData(dvec4(planeWidth / 2.0, 0.0, planeWidth / 2.0, 1.0), color2, n));

} // end Reference Plane Constructor

//********************************** Sphere *********************************


/**
* Generates Cartesion xyz coordinates on the surface of a sphere of a specified
* radius from polar coordinates. Assumes the "poles" of the sphere on on the Y
* axis. Stack angles are relative the Y axis. Slice angles
* are around the Y axis in plane that is perpendicular to it.
* @param radius of the sphere on which the Cartesion coordinates are being generated
* @param sliceAngle - angle in the XZ plane
* @param stackAngle - angle relative to the Y axis.
* @returns homogenous 4 dimensional vector containing the Cartesion coordinates
* for the specified slice and stack angles.
*/
dvec4 sphericalToCartesion( double sliceAngle, double stackAngle, double radius )
{
	dvec4 v( glm::cos( stackAngle ) * glm::sin( sliceAngle ) * radius,
		glm::sin( stackAngle ) * radius,
		glm::cos( stackAngle ) * cos( sliceAngle ) * radius,
		1.0);

	return v;

} // end sphericalToCartesion

dvec2 getSphericalTextCoords( double sliceAngle, double stackAngle )
{
	double s = sliceAngle / ( TWO_PI );
	double t = ( stackAngle - PI/2 ) / -PI;

	return dvec2( s, t );
}


Sphere::Sphere(Material mat, double radius, int slices, int stacks)
	: radius( radius ), stacks( stacks ), slices( slices ), material( mat )
{
	initializeAll();
}


Sphere::Sphere( color col, double radius, int slices, int stacks )
	: radius( radius ), stacks( stacks ), slices( slices ), material( col )
{
	initializeAll();
}

void Sphere::initializeAll()
{
	double stackInc = ( PI ) / ( stacks );
	double sliceInc = ( 2 * PI ) / ( slices );

	double angle = -PI / 2 + stackInc;
	stackAngles.push_back( angle );

	for( int i = 1; i < stacks - 1; i++ ) {

		angle += stackInc;
		stackAngles.push_back( angle );
	}

	angle = 0;
	sliceAngles.push_back( angle );

	for( int i = 0; i < slices; i++ ) {

		angle += sliceInc;
		sliceAngles.push_back( angle );
	}

	initializeTop( );
	initializeBody( );
	initializeBottom( );

	stackAngles.clear();
	sliceAngles.clear();
}


void Sphere::initializeTop( )
{
	dvec4 vertTop = dvec4( 0, radius, 0, 1 );
	dvec4 normTop = dvec4( glm::normalize( vertTop ) );

	dvec4 vert = sphericalToCartesion( sliceAngles[0], stackAngles[stackAngles.size( ) - 1], radius );
	dvec4 norm = dvec4( glm::normalize( vert ) );
	dvec2 text = getSphericalTextCoords(sliceAngles[0], stackAngles[stackAngles.size( ) - 1]);
	VertexData vertSideDat = VertexData( vert, material, norm, text);

	for( int sliceIndex = 0; sliceIndex < slices; sliceIndex++ ) {

		dvec2 texBottom = getSphericalTextCoords( ( sliceAngles[sliceIndex] + sliceAngles[sliceIndex + 1] ) / 2, PI / 2 );
		VertexData vertTopDat = VertexData( vertTop, material, normTop, texBottom );

		triangleVertices.push_back( vertTopDat );

		triangleVertices.push_back( vertSideDat );

		vert = sphericalToCartesion( sliceAngles[sliceIndex + 1], stackAngles[stackAngles.size( ) - 1], radius );
		norm = dvec4( glm::normalize( vert ) );
		text = getSphericalTextCoords(sliceAngles[sliceIndex + 1], stackAngles[stackAngles.size( ) - 1]);

		vertSideDat = VertexData( vert, material, norm, text );

		triangleVertices.push_back( vertSideDat );

	}

} // end initializeTop

void Sphere::initializeBody( )
{

	for( unsigned int stackIndex = 0; stackIndex < stackAngles.size( ) - 1; stackIndex++ ) {

			dvec4 vert0 = sphericalToCartesion( sliceAngles[0], stackAngles[stackIndex + 1], radius );
			dvec2 text0 = getSphericalTextCoords( sliceAngles[0], stackAngles[stackIndex + 1] );

			dvec4 vert1 = sphericalToCartesion( sliceAngles[0], stackAngles[stackIndex], radius );
			dvec2 text1 = getSphericalTextCoords( sliceAngles[0], stackAngles[stackIndex] );

			VertexData vertDat0 = VertexData( vert0, material, dvec4( glm::normalize( vert0 ) ), text0 );
			VertexData vertDat1 = VertexData( vert1, material, dvec4( glm::normalize( vert1 ) ), text1 );

		for( unsigned int sliceIndex = 0; sliceIndex < sliceAngles.size( ) - 1; sliceIndex++ ) {

			dvec4 vert2 = sphericalToCartesion( sliceAngles[sliceIndex + 1], stackAngles[stackIndex], radius );
			dvec2 text2 = getSphericalTextCoords( sliceAngles[sliceIndex + 1], stackAngles[stackIndex] );

			dvec4 vert3 = sphericalToCartesion( sliceAngles[sliceIndex + 1], stackAngles[stackIndex + 1], radius );
			dvec2 text3 = getSphericalTextCoords( sliceAngles[sliceIndex + 1], stackAngles[stackIndex + 1] );

			VertexData vertDat2 = VertexData( vert2, material, dvec4( glm::normalize( vert2 ) ), text2 );
			VertexData vertDat3 = VertexData( vert3, material, dvec4( glm::normalize( vert3 ) ), text3 );

			triangleVertices.push_back( vertDat0 );
			triangleVertices.push_back( vertDat1 );
			triangleVertices.push_back( vertDat2 );

			triangleVertices.push_back( vertDat0 );
			triangleVertices.push_back( vertDat2 );
			triangleVertices.push_back( vertDat3 );

			vertDat0 = vertDat3;
			vertDat1 = vertDat2;
		}
	}

} // end initializeBody


void Sphere::initializeBottom( )
{
	dvec4 vertBottom = dvec4( 0, -radius, 0, 1 );
	dvec4 normBottom = dvec4( glm::normalize( vertBottom ) );

	dvec4 vert = sphericalToCartesion( sliceAngles[slices], stackAngles[0], radius );
	dvec4 norm = dvec4( glm::normalize( vert ) );
	dvec2 text = getSphericalTextCoords( sliceAngles[slices], stackAngles[0] );
	VertexData vertSideDat = VertexData( vert, material, norm, text );

	for( int sliceIndex = slices; sliceIndex > 0; sliceIndex-- ) {

		dvec2 texBottom = getSphericalTextCoords( ( sliceAngles[sliceIndex] + sliceAngles[sliceIndex - 1] ) / 2, -PI / 2 );
		VertexData vertBottomDat = VertexData( vertBottom, material, normBottom, texBottom );
		triangleVertices.push_back( vertBottomDat );

		triangleVertices.push_back( vertSideDat);

		vert = sphericalToCartesion( sliceAngles[sliceIndex - 1], stackAngles[0], radius );
		norm = dvec4( glm::normalize( vert ) );
		text = getSphericalTextCoords( sliceAngles[sliceIndex - 1], stackAngles[0] );
		vertSideDat = VertexData( vert, material, norm, text );
		
		triangleVertices.push_back( vertSideDat );
	}

} // end initializeBottom


