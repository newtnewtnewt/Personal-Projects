/*
*  Noah Dunn
 *  Instructor: Norm Krumpe
 *  Date: 11/10/2019
 *  Class: CSE270M 
 *  Assignments: Julia Sets
 */

// Julia sets are based on a simple iterated function: f(x) = x^2 + c (where c is some constant)
// We ask: given the iterated function f(x) = x^2 + c, which seeds escape, and which don't?
// Technically, the Julia set is the boundary between the points that escape and the points that don't.
// Julia sets are really done with all complex numbers.  We are working with real numbers instead.
// But everything else is the same!

final float ESCAPE_DISTANCE = 2; //  At what point will we say the orbit escaped and won't return 
int BAILOUT = 10;        //  When we are choosing to stop running the checks 
ComplexNumber c = new ComplexNumber(0, 0); //  Our initial C value 
final float LOWER_BOUND = -2.0;      //  The lower limit of our imaginary numbers
final float UPPER_BOUND = 2.0;       //  The upper limit of our imginary numbers
final float INCREMENT = 0.005;       //  The amount we are spacing our imaginary values on either axis
PImage currentImage;                 //  The current image of the julia set we have built




void setup() {
  currentImage = makeJulia2D();  //  Create our initial Julia set image 
  size(800, 800);                //  Set our frame render
  image(currentImage, 0, 0);     //  Draw the created image
}

void draw() {
  image(currentImage, 0, 0);  //  Draw the image currently saved 
  textSize(15);               //  Set the size of our text to 15
  text("c : " + c.toString(), 10, 40);  //  Display our current constant value for the equation f(x) = x^2 + c 
  text("a-range: " + LOWER_BOUND + " to " + UPPER_BOUND, 10, 60);  //  Display our real number range
  text("b-range: " + LOWER_BOUND + " to " + UPPER_BOUND, 10, 80);  //  Display our imaginary number range
  int x = mouseX;  // Grab the current x-coordinate position of our mouse
  int y = mouseY;  // Grab the current y-coordinate position of our mouse
  double real           = map(x, 0, width, LOWER_BOUND, UPPER_BOUND);  //  Grab the current position on the real-axis (x-axis)
  double imaginary      = map(y, 0, height, UPPER_BOUND, LOWER_BOUND); //  Grab the current position on the imaginary-axis(y-axis)
  drawOrbits(new ComplexNumber(real, imaginary));                      //  Create our orbits using the above two values
  text("mouse at(" + real + "," + imaginary + "i )", 10, 100);         //  Print our current positions
  text("Bailout Value: " + BAILOUT, 10, 120);                          //  Print our current bailouts
}

/*
*  This method activates when we press any key, but
 *  we really only care when the key pressed is either the up arrow key
 *  or the down arrow key
 */
void keyPressed() {
  if (key == CODED) {     //  If the key is in processing's default codes
    if (keyCode == UP) {  //  If the keyPress is the up key
      BAILOUT++;          //  Increase bailout and redraw
      currentImage = makeJulia2D();
    } else if (keyCode == DOWN) {
      if (BAILOUT != 1) { //  If we aren't at a Bailout of 1 and the user presses the DOWN key, decrement the BAILOUT value
        BAILOUT--;
        currentImage = makeJulia2D();
      }
    }
  }
}

/*
*  A method that draw sthe orbits given whatever seed we are currently moused over
 */
void drawOrbits(ComplexNumber seed) {
  //  Set our arbitrary starting circle radius
  float startingRadius = 20;
  //  Run a bailout check on the seed
  for (int i = 0; i < BAILOUT; i++) {
    if (seed.abs() > ESCAPE_DISTANCE) {
      break;
    }
    //  If it hasn't bailed out yet, create a hollow yellow circle at the imaginary number we are currently on
    noFill();
    stroke(255, 255, 102);
    //  The x of the seed we are at
    float x = (float)seed.getReal();
    //  The y of the seed we are at
    float y = (float)seed.getImaginary();
    //  Mapped to our actual render screen
    float drawX = (int)map(x, LOWER_BOUND, UPPER_BOUND, 0, width);
    float drawY = (int)map(y, LOWER_BOUND, UPPER_BOUND, 0, height);
    //  Draw the circle
    circle(drawX, drawY, startingRadius);
    //  Make the next one smaller
    startingRadius -= 2;
    //  Call the julia set equation again
    seed = f(seed);
  }
}

/*
*  This method changes the c value to whatever seed we click on.
 *
 */
void mouseClicked() {
  //  Grab our mouse positions
  int x = mouseX;
  int y = mouseY;
  //  Map them to the real + imaginary parts of an imaginary number
  double real = map(x, 0, width, LOWER_BOUND, UPPER_BOUND);
  double imaginary      = map(y, 0, height, UPPER_BOUND, LOWER_BOUND);
  //  Create our new c value
  c = new ComplexNumber(real, imaginary);
  //  Reset the draw image
  currentImage = makeJulia2D();
}
/*
*  The work engine of this program, this is what generates a julia
 *  set for a given C value.
 *
 */
PImage makeJulia2D() {
  //  Make an image the size of our viewing window
  PImage juliaImage = new PImage(width, height);
  //  Set the background to red, which will be written over regardless
  background(255, 0, 0);
  //  Establish a variable for the color of a given seed
  color c = color(0, 0, 0);
  //  The y-axis runs top to bottom, from 2i to -2i in the imaginary axis
  for (float y = UPPER_BOUND - INCREMENT; y >= LOWER_BOUND; y -= INCREMENT) {
    //  Maps our current y value to the viewing frame values
    int drawY = (int)map(y, LOWER_BOUND, UPPER_BOUND, 0, height);
    //  The x-axis runs from -2 to 2 on the real axis
    for (float x = LOWER_BOUND; x < UPPER_BOUND; x += INCREMENT) {
      //  Maps our current x value on the real axis to the viewing frame
      int drawX = (int)map(x, LOWER_BOUND, UPPER_BOUND, 0, width);
      //  Runs a check to see if the seed escapes or not
      color escapeColor = escapes(new ComplexNumber(x, y));
      juliaImage.pixels[((int)drawY * width + (int)drawX)] = escapeColor;
    }
  }
  return juliaImage;
}

// Start with a seed, and iterate using f(x) until
// we determine whether the orbit escapes (goes off to infinity)
// What counts as "escaping"?  if seed gets > ESCAPE_DISTANCE
// How many times should we iterate before we stop trying? Use BAILOUT.
// After BAILOUT iterations, if we still haven't escaped, assume we won't
color escapes(ComplexNumber seed) {
  color c1 = color(255, 0, 0);     //  The color of non-escaped seeds
  color c2 = color(0, 255, 0);     //  The color of slowest escaped seed
  for (int i = 0; i < BAILOUT; i++) {
    if (seed.abs() > ESCAPE_DISTANCE) {
      if (i == 0) {
        return c1;  //  This is done to prevent Off-By-One errors
      }
      return lerpColor(c1, c2, ((float)i)/(BAILOUT - 1)); // escaped
    }
    seed = f(seed);
  }
  return color(0, 0, 0); //  didn't escape
}

// The function that we will iterate over to determine
// If a seed escapes or nto
ComplexNumber f(ComplexNumber x) { 
  return x.multiply(x).add(c);
}

/* A tester method for determining if the complex numbers class 
 * works 
 */
private void testComplexNumbers() {
  //  Create two complex numbers to test, 2 + 4i and 3 + 5i
  ComplexNumber c1 = new ComplexNumber(2.0, 4.0); 
  ComplexNumber c2 = new ComplexNumber(3.0, 5.0);
  //  Demonstrates the toString() works
  println(c1);               //  Expected: 2.0 + 4.0i
  println(c2);               //  Expected  3.0 + 5.0i
  println(c1.add(c2));       //  Expected 5.0 + 9.0i
  println(c1.subtract(c2));  //  Expected: -1.0 + -1.0i
  println(c1.divide(c2));    //  Expected: 0.764... + 0.0588...i 
  println(c1.multiply(c2));  //  Expected: -14.0 + 22.0i
  println(c2.abs());         //  Expected: 5.83...
}

public class ComplexNumber {
  /*
  *  Complex numbers possess a real part @var realPart
   *  and an imaginary part @var imaginaryPart
   *  There are a series of math operations that can be performed on and between them
   *  @constructor ComplexNumber
   *  @method add, @method subtract, @method multiply, @method divide, @method abs, @method toString
   */

  // Private members for the real and imaginary part
  private double realPart;
  private double imaginaryPart;
  // Our constructor which takes a real and imaginary part
  public ComplexNumber(double real, double imaginary) {
    realPart = real;
    imaginaryPart = imaginary;
  }
  //  Adds the real parts and the imaginary parts together, returns a new CN
  public ComplexNumber add(ComplexNumber otherNum) {
    return new ComplexNumber(realPart + otherNum.getReal(), imaginaryPart + otherNum.getImaginary());
  }
  //  Subtracts the real parts and the imaginary parts, returns a new CN
  public ComplexNumber subtract(ComplexNumber otherNum) {
    return new ComplexNumber(realPart - otherNum.getReal(), imaginaryPart - otherNum.getImaginary());
  }
  //  Muliplication using the FOIL method
  public ComplexNumber multiply(ComplexNumber otherNum) {
    //  Grab the REAL part of base CN, and the REAL part of the second CN
    double first = realPart * otherNum.getReal();
    //  Grab the IMAGINARY part of base CN, and the REAL part of the second CN
    double inner = imaginaryPart * otherNum.getReal();
    //  Grab the REAL part of base CN, and the IMAGINARY part of the second CN
    double outer = realPart * otherNum.getImaginary();
    //  Grab the IMAGINARY part of base CN, and the IMAGINARY part of the second CN
    //  Multiply by negative 1 to account for the i^2 the multiplication produces
    double last  = -1 * imaginaryPart * otherNum.getImaginary();
    //  The total real component is the result of first added to the result of last (No i components left)
    double totalReal = first + last;
    //  The total imaginary component is the combination of both values still left with an i
    double totalImaginary = inner + outer;
    //  Return our built number
    return new ComplexNumber(totalReal, totalImaginary);
  }
  //  To divide, we multiply the top and the bottom of the division by the conjugate of the bottom
  public ComplexNumber divide(ComplexNumber otherNum) {
    //  Our numerator is our current number multiplied by the conjugate of the other Number
    ComplexNumber numerator = this.multiply(new ComplexNumber(otherNum.getReal(), -otherNum.getImaginary()));
    //  Our denominator will be a real number, found by multiplying otherNum by its conjugate
    ComplexNumber denominator = otherNum.multiply(new ComplexNumber(otherNum.getReal(), -otherNum.getImaginary()));
    //  Our new complexNumber is the real part and imaginary parts of our numerator divided by our denominator, which is a real number
    return new ComplexNumber(numerator.getReal()/denominator.getReal(), numerator.getImaginary()/denominator.getReal());
  }
  //  The absolute value of a given complex number is the same as calculating the distance from zero
  public double abs() {
    return Math.sqrt(Math.pow(realPart, 2) + Math.pow(imaginaryPart, 2));
  }
  //  The toString formats a given imaginary number in the a + bi format
  public String toString() {
    return "" + realPart + " + " + imaginaryPart + "i";
  }
  //  Getter for the Real component
  public double getReal() {
    return realPart;
  }
  //  Getter for the imaginary component
  public double getImaginary() {
    return imaginaryPart;
  }
}
