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

	// Apply fog
	// TODO

	return fragment.shadedColor;

} // end fragmentShader


void FragmentOperations::processFragment(Fragment & fragment)
{
	// Perform depth test
	// TODO

	// Call fragment shader
	color shadedColor = fragmentShader( fragment );

	// Perform alpha blending

	// Set the color for the pixel 
	frameBuffer.setPixel((int)fragment.windowPosition.x, (int)fragment.windowPosition.y, shadedColor);

} // end processFragment