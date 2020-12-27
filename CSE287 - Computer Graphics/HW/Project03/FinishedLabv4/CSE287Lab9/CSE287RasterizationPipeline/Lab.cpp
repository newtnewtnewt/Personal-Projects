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
Sphere blueSphere(Material("earth.ppm"),  1.0, 15, 15);
Pyramid greenPyramid(color(0.0, 1.0, 0.0, 1.0), 1.0, 1.0);
Pyramid blackPyramid(color(0.3, 0.0, 0.0, 1.0), 1.0, 1.0);
Box greenBox(color(0, 1.0f, 0.0f, 1.0f), 2.0f, 2.0f, 2.0f);
Box blackBox(color(0, 0, 0, 1.0f), 1.0f, 1.0f, 1.0f);
Pyramid whitePyramid(color(1, 1, 1, 1.0), 1.0, 1.0);

Box yellowBox(color(1.0f, 1.0f, 0.0f, 1.0f), 1.0f, 1.0f, 1.0f);
Box redBox(color(1.0f, 0.0f, 0.0f, 1.0f), 1.0f, 1.0f, 1.0f);
Box blueBox(color(0, 0.0f, 1.0f, 1.0f), 1.0f, 1.0f, 1.0f);
Box whiteBox(color(1, 1.0f, 1.0f, 1.0f), 1.0f, 1.0f, 1.0f);
Box otherGreenBox(color(0, 1.0f, 0.0f, 0.05f), 2.0f, 2.0f, 2.0f);

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

	referencePlane.setPosition(dvec3(0.0, -3.0, 0.0));
	referencePlane.draw();


	greenBox.setPosition(dvec3(-3.0, -2.0, 3.0));
	greenBox.draw();


	blackBox.setPosition(dvec3(3.5, -2.5, -3.75));
	blackBox.draw();

	blackPyramid.setPosition(dvec3(3.5, -1.5, -3.75));
	blackPyramid.draw();

	redBox.setPosition(dvec3(0, -2.5, 0));
	redBox.draw();

	yellowBox.setPosition(dvec3(-1, -2.5, -1));
	yellowBox.draw();

	blueBox.setPosition(dvec3(1, -2.5, -1));
	blueBox.draw();

	whiteBox.setPosition(dvec3(0, -1.5, 0));
	whiteBox.setOrientation(45.0, dvec3(0, 1, 0));
	whiteBox.draw();

	redPyramid.setOrientation(angle, dvec3(1, 0, 0));
	redPyramid.draw();

	blueSphere.setPosition(dvec3(3.0, 0, 0.0));
	blueSphere.setOrientation(angle, dvec3(1, 0, 0));
	blueSphere.draw();

	otherGreenBox.setPosition(dvec3(3.0, 0, 0.0));
	otherGreenBox.draw();

	greenPyramid.setPosition(dvec3(-3.0, 0.0, 0.0));
	greenPyramid.setScale(2.0);
	greenPyramid.setOrientation(angle, dvec3(0, 0, 1));
	greenPyramid.draw();

	whitePyramid.setOrientation(-angle, dvec3(0, 1, 0));
	whitePyramid.setPosition(dvec3(10.0, 0.0, 0.0));	
	whitePyramid.setOrientation(angle, dvec3(1, 0, 1));
	whitePyramid.draw();

} // end renderObjects


static glm::dmat4 myPerspective(double fovy, double aspect, double nearby, double faraway) {
	return glm::perspective(fovy, aspect, nearby, faraway);
	/*glm::dmat4 perspectiveM;
	perspectiveM[0][0] = 1.0 / (aspect * glm::tan(fovy / 2));
	perspectiveM[1][1] = 1.0 / glm::tan(fovy / 2);
	perspectiveM[2][2] = (nearby + faraway) / (faraway - nearby);
	perspectiveM[2][3] = -1;
	perspectiveM[3][2] = (2 * faraway * nearby) / (nearby - faraway);
	return perspectiveM;*/
	// return myPerspective(fovy, aspect, nearby, faraway);
}
static dmat4 myOrtho(double left, double right, double bottom, double top, double zClose, double zFar) {
	return glm::ortho(left, right, bottom, top, zClose, zFar);
	/*dmat4 orthoM;
	orthoM[0][0] =  (2.0 / (right - left));
	orthoM[1][1] =  (2.0) / (top - bottom);
	orthoM[2][2] =  (-2) / (zFar - zClose);
	orthoM[3][2] = -((zFar + zClose) / (zFar - zClose));
	orthoM[3][3] = 1;
	orthoM[3][0] = -((right + left) / (right - left));
	orthoM[3][1] = -((top + bottom) / (top - bottom));*/
	//return orthoM;

}



// Draws two views of the scene. One on the right and one on the left.
void twoViewsSplitVertically()
{
	// Render left side view
	double viewportWidth = (PerVertex::xViewportMax - PerVertex::xViewportMin) / 2;
	double viewportHeight = (PerVertex::yViewportMax - PerVertex::yViewportMin);

	PerVertex::projectionTransformation = myPerspective(45.0,
		((double)viewportWidth) / ((double)viewportHeight), 0.1f, 100.0);

	// Set viewport transformation for left view
	PerVertex::viewportTransformation =
		glm::translate(dvec3(0.0, 0.0, 0.0)) *
		glm::scale(dvec3((double)(viewportWidth) / (PerVertex::xNdcMax -
			PerVertex::xNdcMin),
			(double)(viewportHeight) / (PerVertex::yNdcMax - PerVertex::yNdcMin), 1.0)) *
		glm::translate(dvec3(-PerVertex::xNdcMin, -PerVertex::yNdcMin, 0.0));

	renderObjects();
	
	PerVertex::projectionTransformation = myOrtho(-8.0, 8.0, -7.0, 7.0, 0, 100.0);

	PerVertex::viewportTransformation =
		//glm::translate(dvec3((PerVertex::xViewportMax - PerVertex::xViewportMin) / 2.0, 0.1, 0.0)) *
		glm::translate(dvec3(viewportWidth, 0.0, 0.0)) *
		glm::scale(dvec3((double)(viewportWidth) / (PerVertex::xNdcMax -
			PerVertex::xNdcMin),
			(double)(viewportHeight) / (PerVertex::yNdcMax - PerVertex::yNdcMin), 1.0)) *
		glm::translate(dvec3(-PerVertex::xNdcMin, -PerVertex::yNdcMin, 0.0));

	renderObjects();

} // end twoViewsSplitVertically

// Draws two views of the scene. One on the top and one on the bottom.
void twoViewsSplitHorizontally()
{
	// Render left side view
	double viewportWidth = (PerVertex::xViewportMax - PerVertex::xViewportMin);
	double viewportHeight = (PerVertex::yViewportMax - PerVertex::yViewportMin)/2.0;

	PerVertex::projectionTransformation = myPerspective(45.0,
		((double)viewportWidth) / ((double)viewportHeight), 0.1f, 100.0);

	// Set viewport transformation for left view
	PerVertex::viewportTransformation =
		glm::translate(dvec3(0.0, 0.0, 0.0)) *
		glm::scale(dvec3((double)(viewportWidth) / (PerVertex::xNdcMax -
			PerVertex::xNdcMin),
			(double)(viewportHeight) / (PerVertex::yNdcMax - PerVertex::yNdcMin), 1.0)) *
		glm::translate(dvec3(-PerVertex::xNdcMin, -PerVertex::yNdcMin, 0.0));

	renderObjects();

	PerVertex::projectionTransformation = myOrtho(-20.0, 20.0, -5.0, 5.0, 0, 40.0);

	PerVertex::viewportTransformation =
		//glm::translate(dvec3(viewportWidth/2, viewportHeight + 0.1, 0.0)) *
		glm::translate(dvec3(0.0, viewportHeight, 0.0)) *
		glm::scale(dvec3((double)(viewportWidth) / (PerVertex::xNdcMax -
			PerVertex::xNdcMin),
			(double)(viewportHeight) / (PerVertex::yNdcMax - PerVertex::yNdcMin), 1.0)) *
		glm::translate(dvec3(-PerVertex::xNdcMin, -PerVertex::yNdcMin, 0.0));

	renderObjects();

} // end twoViewsSplitVertically



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

		// Draw the objects in the scene
		switch (view) {

		case VERTICAL_SPLIT:

			twoViewsSplitVertically();
			break;

		case HORIZONTAL_SPLIT:

			twoViewsSplitHorizontally();
			break;

		default:
			// Push the vertices for all objects in the scene down the pipeline
			renderObjects();
		}

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

	// Create a perspective projection matrix. myPerspective vertical field of view is specifed in degrees.
	PerVertex::projectionTransformation = 
		myPerspective(45.0, ((double)PerVertex::xViewportMax - PerVertex::xViewportMin) / 
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
	case('w') : case ('W') :
		zTrans += 1;
		break;
	case('s'): case ('S'):
		zTrans -= 1;
		break;

	default:
		std::cout << key << " key pressed." << std::endl;
		break; 
	}
	// Set the viewing transformation based on the values held in the global
	// varaiables zTrans, rotationX, and rotationY.
	glm::dmat4 transView = glm::translate(glm::vec3(0.0f, 0.0f, zTrans));
	glm::dmat4 rotateViewX = glm::rotate(glm::radians(rotationX),
		glm::dvec3(1.0f, 0.0f, 0.0f));
	glm::dmat4 rotateViewY = glm::rotate(glm::radians(rotationY),
		glm::dvec3(0.0f, 1.0f, 0.0f));

	PerVertex::viewingTransformation = transView * rotateViewX * rotateViewY;

	

	glutPostRedisplay();

} // end KeyboardCB


// Responds to presses of the arrow keys
static void SpecialKeysCB(int key, int x, int y)
{
	static const double rotateInc = glm::radians(20.0);

	switch (key) {
		// Up arrow
		case(100): 
			rotationY += 1.0;
			break;
		// Down Arrow
		case(102): 
			rotationY -= 1.0;
			break;
		// Left Arrow
		case(101): 
			rotationX += 1.0;
			break;
		// Right Arrow
		case(103): 
			rotationX -= 1.0;
			break;
	default:
		std::cout << key << " key pressed." << std::endl;
	}
	// Set the viewing transformation based on the values held in the global
		// varaiables zTrans, rotationX, and rotationY.
	glm::dmat4 transView = glm::translate(glm::vec3(0.0f, 0.0f, zTrans));
	glm::dmat4 rotateViewX = glm::rotate(glm::radians(rotationX),
		glm::dvec3(1.0f, 0.0f, 0.0f));
	glm::dmat4 rotateViewY = glm::rotate(glm::radians(rotationY),
		glm::dvec3(0.0f, 1.0f, 0.0f));

	PerVertex::viewingTransformation = transView * rotateViewX * rotateViewY;

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
		case(0):

			FragmentOperations::fogSetting = NO_FOG;
			break;
		case(1):

			FragmentOperations::fogSetting = LINEAR_FOG;
			break;
		case(2):
			FragmentOperations::fogSetting = EXPONENTIAL_FOG;
			break;

		case(3):
			FragmentOperations::fogSetting = EXPONENTIAL_2_FOG;
			break;
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

void cameraMenu(int value) {
	Camera c1;
	switch (value) {
		case (0):
			c1 = Camera(dvec3(0, 1, 12), dvec3(0, 1, -1), dvec3(0, 1, 0));
			PerVertex::viewingTransformation = c1.getViewingTransformation();
			cout << "Camera 1" << endl;
			break;
		case (1):
			c1 = Camera(dvec3(0, 1, -24), dvec3(0, 1, -1), dvec3(0, 1, 0));
			PerVertex::viewingTransformation = c1.getViewingTransformation();
			cout << "Camera 2" << endl;
			break;
		case (2):
			c1 = Camera(dvec3(0, 30, -42), dvec3(0, 1, -1), dvec3(0, 1, 0));
			PerVertex::viewingTransformation = c1.getViewingTransformation();
			cout << "Camera 3" << endl;
			break;
		case(3):
			c1 = Camera(dvec3(0, 15, 0), dvec3(0, 0, 0), dvec3(1, 0, -1));
			PerVertex::viewingTransformation = c1.getViewingTransformation();
			cout << "Camera 4" << endl;
			break;
	}
	glutPostRedisplay();
}

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

	case(2):

		view = VERTICAL_SPLIT;
		std::cout << "vertical" << std::endl;
		break;

	case(3):

		view = HORIZONTAL_SPLIT;

		std::cout << "horizontal" << std::endl;

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

			case(1):
				PerVertex::viewingTransformation =
					glm::translate(dvec3(0.0, 0.0, zTrans - 2.0));
				break;
			case(2):
				PerVertex::viewingTransformation =
					 glm::translate(dvec3(0.0, 0.0, zTrans - 2.0)) * glm::rotate(glm::radians(45.0), dvec3(1, 0, 0));
				break;

			case(3):
				PerVertex::viewingTransformation =
					 glm::translate(dvec3(0.0, 0.0, zTrans - 2.0)) * glm::rotate(glm::radians(90.0), dvec3(1, 0, 0)) * glm::rotate(glm::radians(90.0), dvec3(0, 1, 0));
				break;


			case(4):
				//glm::lookAt(dvec3(0, 0.0, -zTrans + 2.0), dvec3(0, 1.0, 0.0), glm::dvec3(0, 1, 0));
				PerVertex::viewingTransformation =
					glm::lookAt(dvec3(0, 0.0, -zTrans + 2.0), dvec3(0, 0.0, 0), glm::dvec3(0, 1, 0));
				break;

			case(5):
				PerVertex::viewingTransformation =
					glm::lookAt(dvec3(0, (-zTrans + 2.0) * glm::sin(glm::radians(45.0)), 
					(-zTrans + 2.0) * glm::cos(glm::radians(45.0))),
						dvec3(0, 0.0, 0), glm::normalize(dvec3(0, 1, -1)));

				break;

			case(6):
				PerVertex::viewingTransformation =
					glm::lookAt(dvec3(0.0, -zTrans + 2.0, 0.0), dvec3(0.0, 0, 0.0), glm::dvec3(1, 0, 0));
				break;


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
	GLuint world_Window = glutCreateWindow("CSE 287 Project Two - Noah Dunn");

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

	int cameraMenuId = glutCreateMenu(cameraMenu);
	//  Specify menu items and integer identifiers
	glutAddMenuEntry("Camera View 1", 0);
	glutAddMenuEntry("Camera View 2", 1);
	glutAddMenuEntry("Camera View 3", 2);
	glutAddMenuEntry("Camera View 4", 3);

	// Create main submenu
	int menu1id = glutCreateMenu(mainMenu);
	glutAddSubMenu( "Fog", fogMenuid );
	glutAddSubMenu( "Render", polyMenuid );
	glutAddSubMenu("View", viewMenuid);
	glutAddSubMenu("Viewport", viewportMenuid);
	glutAddSubMenu("Lighting", lightingMenuId);
	glutAddSubMenu("Cameras", cameraMenuId);
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

