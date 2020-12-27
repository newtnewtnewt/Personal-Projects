#include "PerFragment.h"

bool FragmentOperations::perPixelLightingEnabled = false;

fogType FragmentOperations::fogSetting = NO_FOG;

void FragmentOperations::applyLighting(Fragment & fragment)
{

	// TODO

} // end applyLighting



color FragmentOperations::fragmentShader( Fragment& fragment)
{
	// Perform per fragment lighting calculations 
	if (perPixelLightingEnabled == true) {
			
		applyLighting(fragment);
	}

	double end = 30;
	double start = 1.0;

	// Apply fog
	double depth = glm::distance(fragment.worldPosition, PerVertex::eyePositionInWorldCoords);
	double fogFactor = (end - depth)/(end - start);

	return fogFactor * fragment.shadedColor + (1.0 - fogFactor) * frameBuffer.getClearColor();

} // end fragmentShader


void FragmentOperations::processFragment(Fragment & fragment)
{
	// Perform depth test
	if (fragment.windowPosition.z < frameBuffer.getDepth((float)fragment.windowPosition.x, (float)fragment.windowPosition.y)) {



		// Call fragment shader
		color shadedColor = fragmentShader(fragment);

		// Perform alpha blending
		color destination = frameBuffer.getPixel(fragment.windowPosition.x, fragment.windowPosition.y);
		color source = fragment.shadedColor;
		double a = fragment.shadedColor.a;

		color blendedColor = (1.0 - a) * destination + a * source;
		// Set the color for the pixel 
		frameBuffer.setPixel((int)fragment.windowPosition.x, (int)fragment.windowPosition.y, blendedColor);

		frameBuffer.setDepth((int)fragment.windowPosition.x, (int)fragment.windowPosition.y, fragment.windowPosition.z);
	}

} // end processFragment