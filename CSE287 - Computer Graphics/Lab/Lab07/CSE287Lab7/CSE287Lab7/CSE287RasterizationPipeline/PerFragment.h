#pragma once

#include "Defines.h"
#include "FrameBuffer.h"
#include "LightSource.h"
#include "PerVertex.h"
#include "Fragment.h"

extern FrameBuffer frameBuffer;

extern std::vector<LightSource*> lights;

enum fogType { NO_FOG, LINEAR_FOG, EXPONENTIAL_FOG, EXPONENTIAL_2_FOG };

class FragmentOperations
{
	public:

		static fogType fogSetting;

		static bool perPixelLightingEnabled;

		static color fragmentShader( Fragment& fragment);
	
		static void processFragment(Fragment & fragment);

	protected:

		static void applyLighting(Fragment & fragment);

		static void applyFog(Fragment & fragment);


};// end FragmentOperations



