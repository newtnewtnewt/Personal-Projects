#pragma once

#include "Defines.h"
#include "VertexData.h"
struct Camera
{
	Camera(glm::dvec3 position = glm::dvec3(0.0, 0.0, 0.0),
		glm::dvec3 direction = glm::dvec3(0.0, 0.0, -1.0),
		glm::dvec3 up = glm::dvec3(0.0, 1.0, 0.0));

	~Camera();

	glm::dvec3 getWorldCoordinateViewPosition();

	void setCamera(glm::dvec3 position = dvec3(0.0, 0.0, 0.0), glm::dvec3 direction = dvec3(0.0, 0.0, 0.0), glm::dvec3 up = dvec3(0.0, 0.0, 0.0));

	glm::dmat4 getViewingTransformation();

	glm::dmat4 viewTrans = dmat4();
};

struct ExplicitShape 
{
public:

	virtual void draw();

	virtual void setPosition(const dvec3& positionVector);

	virtual void setOrientation(const double& angle, const dvec3& axis);

	virtual void setScale(const double& scaleValue);

protected:

	dmat4 orientation;
	dmat4 position;
	dmat4 scale;

	std::vector<VertexData> triangleVertices;

}; // end Shape



struct ReferencePlane : ExplicitShape
{
	ReferencePlane( double planeWidth = 8.0, 
					color c1 = color(0.0, 0.0, 0.0, 1.0), 
					color c2 = color(1.0, 1.0, 1.0, 1.0));

	color color1;
	color color2;


};


struct Pyramid : ExplicitShape
{
	Pyramid(color pyColor, double width = 1.0, double height = 1.0);

};
struct Box : ExplicitShape
{
	Box(color cubeColor = color(1.0f, 1.0f, 0.0f, 1.0f), float width = 1.0f, float height = 1.0f, float depth = 1.0f);

};

struct Sphere : ExplicitShape
{
	Sphere(color col, double radius = 0.5f, int slices = 16, int stacks = 16);

	Sphere(Material mat, double radius = 0.5f, int slices = 16, int stacks = 16);

	Material material;


	protected:

	void initializeAll();
	void initializeTop( );
	void initializeBody( );
	void initializeBottom( );

	int stacks;
	int slices;
	double radius;

	std::vector<double> stackAngles;
	std::vector<double> sliceAngles;
	
};


