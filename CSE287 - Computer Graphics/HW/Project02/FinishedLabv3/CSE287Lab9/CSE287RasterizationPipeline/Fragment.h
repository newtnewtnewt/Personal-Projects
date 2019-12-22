#pragma once

#include "Defines.h"

struct Fragment {

	color shadedColor; // Result of lighting calculations
	dvec3 windowPosition;
	Material material;
	dvec3 worldNormal;
	dvec3 worldPosition;
	dvec2 uv;		// Texture coordinates for the fragment
};
