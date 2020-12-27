#include "Lab.h"

// Viewing parameters
enum View_Type { FULL_SCREEN, VERTICAL_SPLIT, HORIZONTAL_SPLIT };

View_Type view = FULL_SCREEN;

/********************** GLOBALS ******************************/

std::vector<LightSource*> lights;

const GLint FRAMES_PER_SECOND = 60; // Desired maximum number of frames per second
const GLint FRAME_INTERVAL = 1000 / FRAMES_PER_SECOND; // Interval in milliseconds between frames

// Frame buffer that contains both the color and depth buffers
FrameBuffer frameBuffer(WINDOW_WIDTH, WINDOW_HEIGHT);

// Objects in the scene

// Pyramid
Pyramid redPyramid(color(1.0, 0.0, 0.0, 1.0f), 1.0, 1.0);
Pyramid bluePyramid(color(0.0, 0.0, 1.0, 1.0), 1.0, 1.0);
Pyramid greenPyramid(color(0.0, 1.0, 0.0, 1.0), 1.0, 1.0);
Pyramid blackPyramid(color(0.3, 0.0, 0.0, 1.0), 1.0, 1.0);
Pyramid blackPyramidTwo(color(0.3, 0.0, 0.0, 1.0), 1.0, 1.0);
Pyramid whitePyramid(color(1, 1, 1, 1.0), 1.0, 1.0);

static double zTrans = -12.0;
static double rotationX = 0.0;
static double rotationY = 0.0;

// Reference plane
ReferencePlane referencePlane;

// Global to hold the rotation angle of objects in the scene
double angle = glm::radians(45.0);

// Light sources 
LightSource ambientLight(color(0.15, 0.15, 0.15, 1.0));
PositionalLight lightPos(dvec3(-3, 3, -3), dvec4(0.75, 0.75, 0.75, 1.0));
DirectionalLight lightDir(dvec3(1, 1, 1), dvec4(1.0, 1.0, 1.0, 1.0));


/********************** END GLOBALS ******************************/

void renderObjects()
{
	// Set Modeling transformation for the reference plane
	PerVertex::modelingTransformation = glm::translate(dvec3(0.0, -3.0, 0.0));

	// Send the reference plane vertices down the pipeline
	PerVertex::fetchTriangleVertices( referencePlane.triangleVertices);

	// Set Modeling transformation for the pyramid
	PerVertex::modelingTransformation = glm::translate(dvec3(-4.0, -3.0, 2.5));


	// Send the reference plane vertices down the pipeline
	PerVertex::fetchTriangleVertices(blackPyramid.triangleVertices);


	// Set Modeling transformation for the pyramid
	PerVertex::modelingTransformation = glm::translate(dvec3(4.0, -3.0, -5.5));


	// Send the reference plane vertices down the pipeline
	PerVertex::fetchTriangleVertices(blackPyramidTwo.triangleVertices);


	// Set modeling transformation for the center pyramid
	PerVertex::modelingTransformation =  glm::translate(dvec3(0.0, 0.0, 0.0)) * glm::rotate(angle, dvec3(0, 1, 0));

	// Send the pyramid vertices down the pipeline
	PerVertex::fetchTriangleVertices(redPyramid.triangleVertices);

	// Set modeling transformation for the left pyramid
	PerVertex::modelingTransformation = glm::translate(dvec3(3.0, 0.0, 0.0)) * glm::rotate(angle, dvec3(1, 0, 0));

	// Send the pyramid vertices down the pipeline
	PerVertex::fetchTriangleVertices(bluePyramid.triangleVertices);

	//  Apply to green pyramid 
	PerVertex::modelingTransformation =  glm::translate(dvec3(-3.0, 0.0, 0.0)) * glm::scale(dvec3(2.0, 2.0, 1.0)) * glm::rotate(angle, dvec3(0, 0, 1));


	// Send the pyramid vertices down the pipeline
	PerVertex::fetchTriangleVertices(greenPyramid.triangleVertices);

	// White pyramid orbit
	PerVertex::modelingTransformation =  glm::rotate(-angle, dvec3(0, 1, 0)) * glm::translate(dvec3(10.0, 0.0, 0.0)) * glm::rotate(angle, dvec3(1, 0, 1));


	// Send the pyramid vertices down the pipeline
	PerVertex::fetchTriangleVertices(whitePyramid.triangleVertices);




} // end renderObjects



/**
* Acts as the display function for the window.
*/
static void RenderSceneCB()
{
	// time in milliseconds of last frame render
	static GLint lastRenderTime = 0;

	int currentTime = glutGet(GLUT_ELAPSED_TIME); // Get current time
	int elapsedTime = currentTime - lastRenderTime; // Calc time since last frame

	// Check if enough time has elapsed since the last render.
	if (elapsedTime >= FRAME_INTERVAL) {

		// Save time for this frame render
		lastRenderTime = currentTime;

		// Clear the color buffer
		frameBuffer.clearColorAndDepthBuffers();

		// Increment angle so that objects can appear to spin
		angle += glm::radians(1.0);

		// Determine the position of the viewpoint in world coordinates
		PerVertex::eyePositionInWorldCoords = glm::inverse(PerVertex::viewingTransformation)[3].xyz;

		// Push the vertices for all objects in the scene down the pipeline
		renderObjects();

		// Display the color buffer
		frameBuffer.showColorBuffer();
	}

} // end RenderSceneCB

// Reset viewport limits for full window rendering each time the window is resized.
// This function is called when the program starts up and each time the window is 
// resized.
static void ResizeCB(int width, int height)
{
	// Size the color buffer to match the window size.
	frameBuffer.setFrameBufferSize(width, height); 

	// Set rendering window parameters for viewport transfomation
	PerVertex::xViewportMin = 0;
	PerVertex::yViewportMin = 0;
	PerVertex::xViewportMax = (double)width;
	PerVertex::yViewportMax = (double)height;

	// Create a perspective projection matrix. glm::perspective vertical field of view is specifed in degrees.
	PerVertex::projectionTransformation = 
		glm::perspective(45.0, ((double)PerVertex::xViewportMax - PerVertex::xViewportMin) / 
								((double)PerVertex::yViewportMax - PerVertex::yViewportMin), 1.0, 50.0);

	// Set viewport transformation
	PerVertex::viewportTransformation =
		glm::translate(dvec3(PerVertex::xViewportMin, PerVertex::yViewportMin, 0.0)) *
		glm::scale(dvec3((double)(PerVertex::xViewportMax - PerVertex::xViewportMin) / 
			(PerVertex::xNdcMax - PerVertex::xNdcMin), 
			(double)(PerVertex::yViewportMax - PerVertex::yViewportMin) / 
			(PerVertex::yNdcMax - PerVertex::yNdcMin), 1.0)) *
		glm::translate(dvec3(-PerVertex::xNdcMin, -PerVertex::yNdcMin, 0.0));

	// Signal the operating system to re-render the window
	glutPostRedisplay();

} // end ResizeCB


void setViewing( )
{
	// Set the viewing transformation based on the values held in the global
	// variables zTrans, rotationX, and rotationY.
	
	// TODO

}


// Responds to 'f' and escape keys. 'f' key allows 
// toggling full screen viewing. Escape key ends the
// program. Allows lights to be individually turned on and off.
static void KeyboardCB(unsigned char key, int x, int y)
{
	switch (key) {

	case('f') : case('F') : // 'f' key to toggle full screen
		glutFullScreenToggle();
		break;
	case(27) : // Escape key
		glutLeaveMainLoop();
		break;

	default:
		std::cout << key << " key pressed." << std::endl;
	}


	glutPostRedisplay();

} // end KeyboardCB


// Responds to presses of the arrow keys
static void SpecialKeysCB(int key, int x, int y)
{
	static const double rotateInc = glm::radians(20.0);

	switch (key) {



	default:
		std::cout << key << " key pressed." << std::endl;
	}

	glutPostRedisplay();

} // end SpecialKeysCB


// Register as the "idle" function to have the screen continously
// repainted. Due to software rendering, the frame rate will not
// be fast enough to support motion simulation
static void animate()
{
	glutPostRedisplay();

} // end animate


void fogMenu( int value )
{
	switch( value ) {


		default:
			std::cout << "Invalid fog selection " << std::endl;
	}

	std::cout << "fog selection " << std::endl;

	// Signal GLUT to call display callback
	glutPostRedisplay( );

} // end fogMenu


void polygonRenderMenu( int value )
{
	switch( value ) {

		case( 0 ):

			PerVertex::polygonRenderMode = FILL;
			break;
		case( 1 ):

			PerVertex::polygonRenderMode = LINE;
			break;

		default:
			std::cout << "Invalid polygon render selection " << std::endl;
	}

	// Signal GLUT to call display callback
	glutPostRedisplay( );

} // end polygonRenderMenu

void viewPortMenu(int value)
{
	switch (value) {

	case(0):

		glutLeaveMainLoop();
		break;
	case(1):

		view = FULL_SCREEN;

		ResizeCB((int)PerVertex::xViewportMax,
			(int)PerVertex::yViewportMax);

		break;

	default:
		std::cout << "Invalid view selection " << std::endl;
	}

	// Signal GLUT to call display callback
	glutPostRedisplay();

} // end viewPortMenu


void viewMenu(int value)
{
	switch (value) {

	default:
		std::cout << "Invalid view selection " << std::endl;
	}

	// Signal GLUT to call display callback
	glutPostRedisplay();

} // end viewMenu


void mainMenu(int value)
{
	switch (value) {

	case(0):

		// "Quit" selected on the menu
		glutLeaveMainLoop();
		break;

	default:
		std::cout << "Invalid main menu selection " << std::endl;
	}

	// Signal GLUT to call display callback
	glutPostRedisplay();

} // end mainMenu


void lightingMenu(int value)
{
	switch (value) {

	case(0):

		// Turn OFF shading calculations
		PerVertex::perVertexLightingEnabled = false;
		FragmentOperations::perPixelLightingEnabled = false;
		break;
	case(1):

		// Turn ON per vertex shading calculations
		PerVertex::perVertexLightingEnabled = true;
		// Turn OFF per pixel shading calculations
		FragmentOperations::perPixelLightingEnabled = false;
		std::cout << "perVertexLighting" << std::endl;
		break;
	case(2):

		// Turn OFF per vertex shading calculations
		PerVertex::perVertexLightingEnabled = false;
		// Turn ON per pixel shading calculations
		FragmentOperations::perPixelLightingEnabled = true;
		std::cout << "PerPixelLighting" << std::endl;
		break;

	default:
		std::cout << "Invalid light mode selection " << std::endl;
	}

	// Signal GLUT to call display callback
	glutPostRedisplay();

} // end lightingMenu


// To keep the console open on shutdown, start the project with Ctrl+F5 instead of just F5.
int main(int argc, char** argv)
{
	// freeGlut and Window initialization ***********************

	// initialize random seed
	srand((unsigned int)time(NULL));

	// Pass any applicable command line arguments to GLUT. These arguments
	// are platform dependent.
	glutInit(&argc, argv);

	// Set the initial display mode.
	glutInitDisplayMode(GLUT_RGBA | GLUT_SINGLE);

	// Set the initial window size
	glutInitWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT);

	// Create a window using a string and make it the current window.
	GLuint world_Window = glutCreateWindow("Modeling Tranformations");

	// Indicate to GLUT that the flow of control should return to the program after
	// the window is closed and the GLUTmain loop is exited.
	glutSetOption(GLUT_ACTION_ON_WINDOW_CLOSE, GLUT_ACTION_GLUTMAINLOOP_RETURNS);

	// Callback for window redisplay
	glutDisplayFunc(RenderSceneCB);
	glutReshapeFunc(ResizeCB);
	glutKeyboardFunc(KeyboardCB);
	glutSpecialFunc(SpecialKeysCB);
	glutIdleFunc(animate);

	int fogMenuid = glutCreateMenu( fogMenu );
	glutAddMenuEntry( "No Fog", 0 );
	glutAddMenuEntry( "Linear Fog", 1 );
	glutAddMenuEntry( "Exponential Fog", 2 );
	glutAddMenuEntry( "Exponential 2 Fog", 3 );

	// Create polygon render submenu
	int polyMenuid = glutCreateMenu( polygonRenderMenu );
	// Specify menu items and integer identifiers
	glutAddMenuEntry( "Fill", 0 );
	glutAddMenuEntry( "Line", 1 );

	// Create view submenu
	int viewMenuid = glutCreateMenu(viewMenu);
	// Specify menu items and integer identifiers
	glutAddMenuEntry("View 1", 1);
	glutAddMenuEntry("View 2", 2);
	glutAddMenuEntry("View 3", 3);
	glutAddMenuEntry("View 4", 4);
	glutAddMenuEntry("View 5", 5);
	glutAddMenuEntry("View 6", 6);

	// Create viewport submenu
	int viewportMenuid = glutCreateMenu(viewPortMenu);
	// Specify menu items and integer identifiers
	glutAddMenuEntry("Full Screen", 1);
	glutAddMenuEntry("Vertical Split", 2);
	glutAddMenuEntry("Horizontal Split", 3);

	int lightingMenuId = glutCreateMenu(lightingMenu);
	// Specify menu items and integer identifiers
	glutAddMenuEntry("No Lighting", 0);
	glutAddMenuEntry("Per Vertex Lighting", 1);
	glutAddMenuEntry("Per Pixel Lighting", 2);

	// Create main submenu
	int menu1id = glutCreateMenu(mainMenu);
	glutAddSubMenu( "Fog", fogMenuid );
	glutAddSubMenu( "Render", polyMenuid );
	glutAddSubMenu("View", viewMenuid);
	glutAddSubMenu("Viewport", viewportMenuid);
	glutAddSubMenu("Lighting", lightingMenuId);
	glutAddMenuEntry("Quit", 0);

	lights.push_back(&ambientLight);
	lights.push_back(&lightPos);
	lights.push_back(&lightDir);

	// Attach menu to right mouse button
	glutAttachMenu(GLUT_RIGHT_BUTTON);

	// Request that the window be made full screen
	//glutFullScreenToggle();

	color clearColor(0.5, 0.5, 0.5, 1.0);

	// Set red, green, blue, and alpha to which the color buffer is cleared.
	frameBuffer.setClearColor(clearColor);

	// ************* Object vertex coordinate initialization ***********************

	// Set the initial viewing tranformation for the scene
	PerVertex::viewingTransformation = glm::translate(dvec3(0.0, -1.0, zTrans));

	// Enter the GLUT main loop. Control will not return until the window is closed.
	glutMainLoop();

	return 0;

} // end main

