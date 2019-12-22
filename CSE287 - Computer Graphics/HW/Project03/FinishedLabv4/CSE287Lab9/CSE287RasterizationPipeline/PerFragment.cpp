#include "PerFragment.h"

bool FragmentOperations::perPixelLightingEnabled = false;

fogType FragmentOperations::fogSetting = NO_FOG;

void FragmentOperations::applyLighting(Fragment & fragment)
{
		color totalColor = dvec4(0.0, 0.0, 0.0, 1.0);
		double alpha = fragment.shadedColor.a;
		for (auto* light : lights) {
			totalColor += light->getLocalIllumination(glm::normalize(PerVertex::eyePositionInWorldCoords - fragment.worldPosition), fragment.worldPosition, fragment.worldNormal, fragment.material, fragment.uv);
		}
		fragment.shadedColor = totalColor;
		fragment.shadedColor.a = alpha;
} // end applyLighting



color FragmentOperations::fragmentShader( Fragment& fragment)
{
	// Perform per fragment lighting calculations 
	if (perPixelLightingEnabled == true) {
			
		applyLighting(fragment);
	}

	double end = 20;
	double start = 1;
	double depth = glm::distance(fragment.worldPosition, PerVertex::eyePositionInWorldCoords);
	double fogFactor = (end - depth) / (end - start);
	// Apply fog
	if (fogSetting == NO_FOG) {
		fogFactor = 1.0;
	}
	else if (fogSetting == LINEAR_FOG) {
		//  We're good
	}
	else if (fogSetting == EXPONENTIAL_FOG) {
		fogFactor = glm::exp(-(0.15 * depth));
	}
	else if (fogSetting == EXPONENTIAL_2_FOG) {
		fogFactor = pow(exp(-(0.15 * depth)), 2);
	}

	return (fogFactor)  * fragment.shadedColor +  (1.0 - fogFactor) * frameBuffer.getClearColor();

} // end fragmentShader


void FragmentOperations::processFragment(Fragment & fragment)
{
	// Perform depth test
	if (fragment.windowPosition.z < frameBuffer.getDepth((float)fragment.windowPosition.x, (float)fragment.windowPosition.y)) {



		// Call fragment shader
		color shadedColor = fragmentShader(fragment);
		// Perform alpha blending
		color destination = frameBuffer.getPixel(fragment.windowPosition.x, fragment.windowPosition.y);
		color source = shadedColor;
		double a = source.a;
		

		color blendedColor = (1.0 - a) * destination + a * source;
		if (source.a < 1.0) {
			shadedColor = blendedColor;
		}
		else {
			shadedColor = source;
		}
		// Set the color for the pixel 
		frameBuffer.setPixel((int)fragment.windowPosition.x, (int)fragment.windowPosition.y, shadedColor);

		frameBuffer.setDepth((int)fragment.windowPosition.x, (int)fragment.windowPosition.y, fragment.windowPosition.z);
	}

} // end processFragment