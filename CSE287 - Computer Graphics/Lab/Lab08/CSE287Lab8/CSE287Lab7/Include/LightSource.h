#pragma once

#include "Defines.h"
#include "Material.h"
//#include "HitRecord.h"

/**
 * @struct	LightSource
 *
 * @brief	Base struct for all types of lights. Supports only specification of the color and
 * 			intensity of light sources. Parent of sub-structs that simulate positional,
 * 			directional, and spot lights.
 * 			
 * 			Instances of this struct an be used to simulate only ambient light. Ambient light is
 * 			described as bounced light that has been scattered so much that it is impossible to
 * 			tell the direction to its source. If using a LightSource instantiation to simulate
 * 			ambient light, the overall intensity of the light should be low in order to avoid
 * 			washing out shadows as well as diffuse and specular effects that can be achieved when
 * 			using children of this struct.
 */
struct LightSource
{
	LightSource(const color & lightColor = BLACK) 
	: diffuseLightColor(lightColor)
	{
	}

	//virtual color getLocalIllumination(const dvec3& eyeVector, HitRecord & hitRecord )
	//{
	//	return getLocalIllumination(eyeVector, hitRecord.interceptPoint, hitRecord.surfaceNormal, hitRecord.material, hitRecord.uv);
	//}

	virtual color getLocalIllumination(	const dvec3& eyeVector, const dvec3& position,
										const dvec3& normal, const Material& material,
										const dvec2& uv /*= dvec2(0.5, 0.5)*/ )
	{
	
		if( enabled ) {

			return ambientLightColor * material.getAmbient(uv);
		}
		else {
			return BLACK;
		}
	}		

	virtual dvec3 getLightVector(dvec3 position) { return dvec3(0.0, 0.0, 0.0); }

	virtual double getLightDistance(dvec3 position) { return 0.0; }


	/** @brief	Ambient color and intensity of the light.*/
	color ambientLightColor = BLACK;

	/** @brief	Diffuse color and intensity of the light. */
	color diffuseLightColor; 

	/** @brief	Specular color and intensity of the light. */
	color specularLightColor = WHITE;

	/** @brief	Shading calculations are performed if true. BLACK (no light) is returned otherwise. */
	bool enabled = true;
		
}; // end LightSource super struct


/**
 * @struct	PositionalLight
 *
 * @brief	Struct for simulating light sources that have an explicit position and shine equally
 * 			in all directions. Instantiations of the struct will have a position property and a
 * 			color and intensity of the light given off by the light.
 */
struct PositionalLight : public LightSource
{
	/**
	 * @fn	PositionalLight(glm::dvec3 position, const color & lightColor)
	 *
	 * @brief	Constructor
	 *
	 * @param	position  	The position of the light source relative to 
	 * 						World coordinates.
	 * @param	lightColor	Ambient and diffuse color of the light.
	 */
	PositionalLight(glm::dvec3 position, const color & lightColor)
	: LightSource(lightColor), lightPosition(position)
	{}

	/**
	 * @fn	virtual color getLocalIllumination(const glm::dvec3 & eyeVector, HitRecord & closestHit)
	 *
	 * @brief	Calculates and returns the combined ambient, diffuse, and 
	 * 			specular reflection for a vertex, fragment, or point of intersection.
	 *
	 * @param 		  	eyeVector 	Direction from the fragment (point 
	 * 								of intersection) to the viewpoint.
	 * @param [in,out]	closestHit	Contains position of the fragment (point
	 * 								of intersection), surface normal and material properties.
	 *
	 * @returns	The local illumination.
	 */
	virtual color getLocalIllumination(	const dvec3& eyeVector, const dvec3& position,
										const dvec3& normal, const Material& material,
										const dvec2& uv/* = dvec2(0.5, 0.5)*/) override
	{
		if( enabled ) {

			//cout << "uv = " << uv << " diffuse mat = " << material.getDiffuse(uv) << endl;

			// Find a vector that points at this light source
			dvec3 lightVector = glm::normalize( lightPosition - position );

			// Find distance from the intersection to this light source
			double distanceToLight = glm::distance( position, lightPosition );

			// Calculate the half vector
			//dvec3 halfVector = normalize(lightVector + eyeVector);

			// Calculate the reflection of the light vector for use in the specular reflection calculation
			//dvec3 reflectionVector = glm::normalize(lightVector - 2.0 * glm::dot(lightVector, closestHit.surfaceNormal) * closestHit.surfaceNormal);

			dvec3 reflectionVector = glm::normalize( glm::reflect( -lightVector, normal) );

			// Calculate ambient reflection
			color ambient = ambientLightColor * material.getAmbient(uv);

			// Calculate the diffuse reflection
			color diffuse = diffuseLightColor * material.getDiffuse(uv) * glm::max( glm::dot(normal, lightVector ), 0.0 );

			// Calculate the specular reflection based on the relationship between the half vector and the surface normal vector
			//color specular = pow(glm::max(0.0, dot(halfVector, closestHit.surfaceNormal)), 128.0) * color(1.0, 1.0, 1.0, 1.0);

			// Calculate the specular reflection based on the relationship between the light reflection vector and the eye vector
			color specular = pow( glm::max( 0.0, glm::dot( reflectionVector, eyeVector ) ), material.shininess ) * material.getSpecular(uv) * specularLightColor;

			// Combine the diffuse and specular reflections and apply attenuation
			color totalForThisLight = ( 1 / ( CONSTANT_ATTEN + LINEAR_ATTEN * distanceToLight + QUADRATIC_ATTEN * distanceToLight * distanceToLight ) ) * ( ambient + diffuse + specular );

			// Clamp each of the color components to the range [0,1]
			totalForThisLight = glm::clamp( totalForThisLight, color( 0.0, 0.0, 0.0, 0.0 ), color( 1.0, 1.0, 1.0, 1.0 ) );

			return totalForThisLight;

		}
		else {
			return BLACK;
		}

	}


	virtual dvec3 getLightVector(dvec3 position)
	{

		return glm::normalize( lightPosition - position);
	}

	virtual double getLightDistance(dvec3 position)
	{

		return glm::distance( lightPosition, position);
	}


	/** @brief	x, y, z position of the light source. */
	glm::dvec3 lightPosition; 

}; // end PositionalLight struct


/**
 * @struct	DirectionalLight
 *
 * @brief	Struct for simulating light sources that do not have an explicit position. Such light
 * 			sources have only a direction against which they are shinning. Instantiations of the
 * 			struct will have this direction properties along with a color and intensity of the
 * 			light given off by the light source.
 */
struct DirectionalLight : public LightSource
{
	DirectionalLight(dvec3 direction, const color & lightColor)
	: LightSource(lightColor), lightDirection(glm::normalize(direction))
	{}


	/**
	 * @fn	virtual color getLocalIllumination(const glm::dvec3 & eyeVector, HitRecord & closestHit)
	 *
	 * @brief	Calculates and returns the combined ambient, diffuse, and
	 * 			specular reflection for a vertex, fragment, or point of intersection.
	 *
	 * @param 		  	eyeVector 	Direction from the fragment (point
	 * 								of intersection) to the viewpoint.
	 * @param [in,out]	closestHit	Contains position of the fragment (point
	 * 								of intersection), surface normal and material properties.
	 *
	 * @returns	The local illumination.
	 */
	virtual color getLocalIllumination(	const dvec3& eyeVector, const dvec3& position,
										const dvec3& normal, const Material& material,
										const dvec2& uv /*= dvec2(0.5, 0.5)*/) override
	{
		if (enabled) { 

			// Insure the light vector is unit length
			dvec3 lightVector = glm::normalize(lightDirection);

			// Calculate the reflection of the light vector for use in the specular reflection calculation
			dvec3 reflectionVector = glm::normalize(glm::reflect(-lightVector, normal));
			
			// Calculate ambient reflection
			color ambient = ambientLightColor * material.getAmbient(uv);

			// Calculate the diffuse reflection
			color diffuse = diffuseLightColor * material.getDiffuse(uv) * glm::max(dot(normal, lightVector), 0.0);

			// Calculate the specular reflection based on the relationship between the light reflection vector and the eye vector
			color specular = pow(glm::max(0.0, dot(reflectionVector, eyeVector)), material.shininess ) * material.getSpecular(uv) * specularLightColor;

			// Combine the diffuse and specular reflections. Attenuation can not be applied to directional lights
			color totalForThisLight = ambient + diffuse + specular;

			// Clamp each of the color components to the range [0,1]
			totalForThisLight = glm::clamp(totalForThisLight, color(0.0, 0.0, 0.0, 0.0), color(1.0, 1.0, 1.0, 1.0));

			return totalForThisLight;

		}
		else {
			return BLACK;
		}
	}

	virtual dvec3 getLightVector(dvec3 position)
	{

		return lightDirection;
	}

	virtual double getLightDistance(dvec3 position)
	{

		return INFINITY;
	}

	/**
	 * @brief	Unit vector that points in the direction that is opposite the direction in which the
	 * 			light is shining.
	 */
	glm::dvec3 lightDirection; 

}; // end DirectionalLight struct

/**
 * @struct	SpotLight
 *
 * @brief	Struct for simulating light sources that have an explicit position and shine in a
 * 			specified direction.Width of the associated beam of light is controlled using a spot
 * 			cutoff cosine. Instantiations of the struct will have position and direction
 * 			properties along with a color and intensity of the light given off by the light
 * 			source.
 */
struct SpotLight : public PositionalLight
{
	SpotLight( dvec3 position, dvec3 direction, double cutOffCosineRadians, const color & colorOfLight )
		: PositionalLight( position, colorOfLight ), spotDirection( glm::normalize( direction ) ), cutOffCosineRadians( cutOffCosineRadians )
	{
	}

	/**
	 * @fn	virtual color getLocalIllumination( const dvec3& eyeVector, const dvec3& interceptPoint, const dvec3& surfaceNormal, const dvec2& uv, const Material& material)
	 *
	 * @brief	Calculates and returns the combined ambient, diffuse, and specular reflection for a
	 * 			vertex, fragment, or point of intersection.
	 *
	 * @param	eyeVector	  	Direction from the fragment (point of intersection) to the viewpoint.
	 * @param	interceptPoint	Contains position of the fragment (point of intersection), surface
	 * 							normal and material properties.
	 * @param	surfaceNormal 	The surface normal.
	 * @param	uv			  	The uv.
	 * @param	material	  	The material.
	 *
	 * @returns	The local illumination.
	 */
	virtual color getLocalIllumination(	const dvec3& eyeVector, const dvec3& position,
										const dvec3& normal, const Material& material,
										const dvec2& uv /*= dvec2(0.5, 0.5)*/) override
	{
		dvec3 negLight = glm::normalize( position - lightPosition  );

		double spotCosine = glm::dot( spotDirection, negLight );

		if( enabled == true && spotCosine > cutOffCosineRadians  ) {

			//return glm::pow(spotCosine, 1.0) * PositionalLight::illuminate( eyeVector, closestHit, surfaces );
			return ( 1.0 - ( 1.0 - spotCosine ) / ( 1.0 - cutOffCosineRadians ) ) * PositionalLight::getLocalIllumination( eyeVector, position, normal, material, uv);

		}
		else {

			return BLACK;
		}
	}

	/** @brief	Unit vector that points in the direction in which the light is shining. */
	dvec3 spotDirection;

	/** @brief	Angle in radians of half the spot light beam */
	double cutOffCosineRadians;

}; // end SpotLight struct

