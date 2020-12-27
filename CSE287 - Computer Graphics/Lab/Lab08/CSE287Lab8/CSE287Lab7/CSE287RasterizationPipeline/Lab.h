#pragma once

#include <time.h> 
#include "Defines.h"
#include "FrameBuffer.h"
#include "PerVertex.h"
#include "LightSource.h"
#include "ExplicitShapes.h"

/**
* Declarations for functions that acte as "call backs" for various
* events. FreeGLUT commands are used to register these functions
* as event handlers.
*/

/**
 * @fn	static void RenderSceneCB();
 *
 * @brief	Acts as the display function for the window. It is called every the window needs to
 * 			be repainted.
 */
static void RenderSceneCB();

/**
 * @fn	static void ResizeCB(int width, int height);
 *
 * @brief	Resets the graphics viewport limits for full window rendering each time the window is
 * 			resized.
 *
 * @param	width 	of the window in pixels.
 * @param	height	of the window in pixels.
 */
static void ResizeCB(int width, int height);

/**
 * @fn	static void animate();
 *
 * @brief	If registered as the "idle" function, this method will continously as the OS to
 * 			repaint the window.
 */
static void animate();

static dmat4 myOrthographic(double const left, double const right, double const bottom, double const top, double const n, double const f);
static dmat4 myPerspective(double fov, double aspect, double nearPlane, double farPlane);

