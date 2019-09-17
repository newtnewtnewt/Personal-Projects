#include "Lab1.h"
#include "MySquare.h"
#include "DownwardTriangle.h"
#include "RightwardTriangle.h"

//******************* GLOBALS **********************//

// The global variables are visible throughout this file.

// Frame buffer holding the color values for each pixel
FrameBuffer frameBuffer(WINDOW_WIDTH, WINDOW_HEIGHT);
MyRectangle r0 = MyRectangle(200, 200, BLUE, 20, 50);
MyRectangle r3 = MyRectangle(300, 300, GREEN, 40, 10);
MySquare s1 = MySquare(100, BLACK, 100, 100);
DownwardTriangle dt = DownwardTriangle(0, 100, RED, 60);
RightwardTriangle rt = RightwardTriangle(0, 200, WHITE, 50);
color lightGreen = color(0.120, 0.240, 0.081, 0.3);
MyRectangle r1 = MyRectangle(400, 100, YELLOW, 70, 30);
MyRectangle r2 = MyRectangle(420, 82.5, lightGreen, 30, 70);


//*********** END OF GLOBAL DECLARATIONS **********//


/**
 * @fn	static void RenderSceneCB()
 *
 * @brief	Acts as the display function for the window.
 */
static void RenderSceneCB()
{
	int frameStartTime = glutGet( GLUT_ELAPSED_TIME ); // Get start time
	
	// Clear the color buffer
	frameBuffer.clearColorAndDepthBuffers( ); 
	
	r0.draw(frameBuffer);
	s1.draw(frameBuffer);
	dt.draw(frameBuffer);
	rt.draw(frameBuffer);
	r1.draw(frameBuffer);
	r2.draw(frameBuffer);
	r3.draw(frameBuffer);

	// Display the color buffer
	frameBuffer.showColorBuffer();

	// Calculate and display time required to render scene.
	int frameEndTime = glutGet( GLUT_ELAPSED_TIME ); // Get end time

	float totalTimeSec = (frameEndTime - frameStartTime) / 1000.0f;

	cout << "Render time: " << totalTimeSec << " sec." << endl;

} // end RenderSceneCB


/**
 * @fn	static void ResizeCB(int width, int height)
 *
 * @brief	Reset viewport limits for full window rendering each time the window 
 * 			is resized. This function is called when the program starts up and 
 * 			each time the window is resized.
 *
 * @param	width 	The window width in pixels.
 * @param	height	The window height in pixels.
 */
static void ResizeCB(int width, int height)
{
	// Size the color buffer to match the window size.
	frameBuffer.setFrameBufferSize( width, height );

	// Signal the operating system to re-render the window
	glutPostRedisplay();

} // end ResizeCB


/**
 * @fn	static void KeyboardCB(unsigned char key, int x, int y)
 *
 * @brief	Responds to 'f' and escape keys. 'f' key allows toggling full 
 * 			screen viewing. Escape key ends the program. Allows lights to
 * 			be individually turned on and off by pressing the 'a,' 'p,' 
 * 			'd,' and 's' keys. and  
 *
 * @param	key	The key that was pressed.
 * @param	x  	The x coordinate of the mouse.
 * @param	y  	The y coordinate of the mouse.
 */
static void KeyboardCB(unsigned char key, int x, int y)
{
	switch(key) {

	case('f'): case('F') : // 'f' key to toggle full screen
		glutFullScreenToggle();
		break;
	case(27): // Escape key
		glutLeaveMainLoop();
		break;
	default:
		std::cout << key << " key pressed." << std::endl;
	}

	glutPostRedisplay();

} // end KeyboardCB


/**
 * @fn	static void SpecialKeysCB(int key, int x, int y)
 *
 * @brief	Responds to presses of the arrow keys
 *
 * @param	key	The key that was pressed
 * @param	x  	The x coordinate of the mouse.
 * @param	y  	The y coordinate of the mouse.
 */
static void SpecialKeysCB(int key, int x, int y)
{
	switch(key) {
	
	case(GLUT_KEY_RIGHT):
		cout << "Right Arrow Key Pressed." << endl;
		break;
	case(GLUT_KEY_LEFT):
		cout << "Left Arrow Key Pressed." << endl;
		break;
	default:
		cout << key << " key pressed." << std::endl;
	}

	glutPostRedisplay();

} // end SpecialKeysCB


/**
 * @fn	static void animate()
 *
 * @brief Registered as the "idle" function to have the screen continuously
 * 		  repainted. Due to software rendering, the frame rate will not
 * 		  be fast enough to support motion simulation.
 */
static void animate()
{
	glutPostRedisplay();

} // end animate




/**
 * @fn	int main(int argc, char** argv)
 *
 * @brief	Main entry-point for this application. Creates a rendering window
 * 			and initializes OpenGL through Glut function calls. Registers
 * 			callback functions (functions that are called by the operating
 * 			system) for specific events. Builds a scene and then enters the 
 * 			rendering loop.
 *
 * @param	argc	The number of command-line arguments provided.
 * @param	argv	An array of command-line argument strings.
 *
 * @returns	Exit-code for the process - 0 for success, else an error code.
 */

int main(int argc, char** argv)
{
	// freeGlut and Window initialization ***********************

    // Pass any applicable command line arguments to GLUT. These arguments
	// are platform dependent.
    glutInit(&argc, argv);

	// Set the initial display mode.
	glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE | GLUT_ALPHA );

	// Set the initial window size
	glutInitWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT);

	// Create a window using a string and make it the current window.
	GLuint world_Window = glutCreateWindow("CSE287 Lab 1 - Simple Shapes");

	// Indicate to GLUT that the flow of control should return to the program after
	// the window is closed and the GLUTmain loop is exited.
	glutSetOption(GLUT_ACTION_ON_WINDOW_CLOSE, GLUT_ACTION_GLUTMAINLOOP_RETURNS);

	// Request that the window be made full screen
	//glutFullScreenToggle();
	
	// Create a color
	color clearColor(0.2, 0.84, 1.0, 1.0);

	// Set red, green, blue, and alpha to which the color buffer is cleared.
	frameBuffer.setClearColor(clearColor);

	// Callbacks for window redisplay and other events
	glutDisplayFunc(RenderSceneCB);		
	glutReshapeFunc(ResizeCB);
	glutKeyboardFunc(KeyboardCB);
	glutSpecialFunc(SpecialKeysCB);
	//glutIdleFunc( animate );

	// Enter the GLUT main loop. Control will not return until the window is closed.
    glutMainLoop();
 
	return 0;

} // end main