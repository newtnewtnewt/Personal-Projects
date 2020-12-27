#pragma once

#include "Defines.h"
#include "Material.h"

struct VertexData
{
	dvec4 objectPos;		// Original position of the vertex in Object coordinates
	dvec3 objectNormal;		// Original normal vector relative to Object coordinates
	dvec2 uv;		// Texture coordinates for the vertex

	dvec3 worldPosition;	// Vertex position relative to World coordinates
	dvec3 worldNormal;		// Normal vector relative to World coordinates
	dvec4 transPos;			// Transformed position of vertex World->Eye->Projection->Window
	Material material;		// Matrial properties for the vertex
	color shadedColor;		// Result of per vertex lighting calculations;

	VertexData(  )
	{

	}

	VertexData( const dvec4 & pos, 
				const color & col = LIGHT_GRAY, 
				const dvec3 & norm = dvec3( 0.0, 0.0, 1.0 ),
				const dvec2& uv = dvec2(0.5, 0.5))
		: objectPos( pos ), material( Material( col ) ), 
		  objectNormal( glm::normalize( norm ) ),
		  uv(uv)
	{
		shadedColor = material.getDiffuse(uv);
	}

	VertexData( const dvec4 & pos, 
				const Material & material = Material( LIGHT_GRAY ), 
				const dvec3 & norm = dvec3( 0.0, 0.0, 1.0 ),
				const dvec2 & uv = dvec2(0.5, 0.5) )
		: objectPos( pos ), material( Material( material ) ), 
		  objectNormal( glm::normalize( norm ) ),
		  uv( uv)
	{
		shadedColor = material.getDiffuse(uv);
	}

	VertexData& operator+=(const VertexData& rhs)
	{
		this->objectPos += rhs.objectPos;
		this->objectNormal += rhs.objectNormal;
		this->uv += rhs.uv;
		this->transPos += rhs.transPos;
		this->worldPosition += rhs.worldPosition;
		this->worldNormal += rhs.worldNormal;
		this->material += rhs.material;
		this->shadedColor += rhs.shadedColor;

		return *this;
	}

	friend VertexData operator+(VertexData lhs, const VertexData& rhs);
	friend VertexData operator-(VertexData lhs, const VertexData& rhs);
	friend VertexData operator*(double scalar, const VertexData& rhs);
};

static VertexData operator+(VertexData lhs, const VertexData & rhs)
{
	return lhs += rhs;
}


static VertexData operator-(VertexData lhs, const VertexData& rhs)
{
	return lhs += -1.0 * rhs;
}

static VertexData operator*(double scalar, const VertexData& rhs)
{
	VertexData temp;

	temp.objectPos = scalar * rhs.objectPos;
	temp.transPos = scalar * rhs.transPos;
	temp.uv = scalar * rhs.uv;
	temp.objectNormal = scalar * rhs.objectNormal;
	temp.worldPosition = scalar * rhs.worldPosition;
	temp.worldNormal = scalar * rhs.worldNormal;
	temp.material = scalar * rhs.material;
	temp.shadedColor = scalar * rhs.shadedColor;

	return temp;
}