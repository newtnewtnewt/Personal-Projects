import java.util.Comparator;
import java.util.Arrays;
import java.util.Stack;

//  Our full set of globals

//  How we intend to store our instructions
Stack <AngleVector> instructions;

//  Our initial set of points, before processing ro sorting
AngleVector[] points;
//  A way to keep track of our directional components
AngleVector start, end;

//  A counter for use in the draw loop
int count = 1;
//  Vectors for tracking the lowest y-val point, and the current point we are examining
AngleVector lowestPoint;
AngleVector currentPoint;
//  Variables for checking isRight
double slope = -2;
double yIntercept = -2;
//  Variable to check if we are done
boolean finished = false;

//  Number of calculations
int timeChecks = 0;


void setup() {
  //  Set our frame rate so the 'animation' is visible
  frameRate(5);
  size(1000, 800);
  instructions = new Stack <AngleVector>();
  points = new AngleVector[40];
  //  Y is flipped in this IDE
  double lowestVal = Double.MIN_VALUE;
  //  This helps us track where the lowest value is
  int lowestIndex = -1;

  //  This generates random points with margins of 100 on the width, and 50 on the top and bottom
  for (int i = 0; i < points.length; i++) {
    points[i] = new AngleVector(100 + random(width - 200), 50 + random(height - 100), 0);
    if (points[i].y > lowestVal) {
      lowestIndex = i;
      lowestVal = points[i].y;
    }
  }
  
  //  Calculate all the angles relative to the lowest point
  for (int i = 0; i < points.length; i++) {
    if (i != lowestIndex) {
      points[i].setAngle(points[lowestIndex]);
    }
  }
  //  Order goes 1. Positive Values
  //             2. Negative Values
  //             3. Size of Negative Values
  //  This is necessary for the sake of viewing points relative to a y-axis built from the lowest point
  Comparator <AngleVector> comp = new Comparator <AngleVector>() {
    public int compare(AngleVector av1, AngleVector av2) {
      if (av1.angle == av2.angle) {
        return (int) Math.signum(av1.x - av2.x);
      } else {
      if (av1.angle < 0 && av2.angle > 0) {
        return -1;
      } else if (av1.angle > 0 && av2.angle < 0) {
        return 1;
      }
        return (int) Math.signum(av2.angle - av1.angle);
     }
  
    }




};
 // Save our lowest point
 lowestPoint = points[lowestIndex];
 // Swap our lowest point for the end point to delete it
 points[lowestIndex] = points[points.length - 1];
 // Delete the lowest point
 points = (AngleVector[]) shorten(points);
 // Sort using our comparator
 Arrays.sort(points, comp);
 // Get our first start and end
 start = lowestPoint;
 end = points[0];
 // Send over our first instructions
 instructions.push(start);
 instructions.push(end);
 // Prep the green brush
 stroke(0, 255, 0);
}

void draw() {
  //  Black background
  background(0, 0, 0);
 
  noStroke(); // Prevent borders from appearing around the screen 
  drawPoints(); //  First round of points 
  
  //  Grab current point we are looking at
  currentPoint = points[count];
  //  Set color for the line to Green
  stroke(0, 255, 0);
  
  //  Check if the point is on right 
  if (isRight(currentPoint)) {
    timeChecks++;
    //  This step is purely for visualization purposes
    instructions.push(currentPoint);
    //  Draw lines with the point we are examining connected
    drawLines(instructions);
    //  Cut the non-necessary point off and the point that forced us to turn right
    instructions.pop();
    instructions.pop();
    //  Create the new end
    end = instructions.pop();
    start = instructions.pop();
    
  while (isRight(currentPoint)) {  //  Check if the point in question is on the right or the left with the new start and end
    //  Remove more disruptive points
    timeChecks++;
    end = start;
    start = instructions.pop();
    
    //  These operations are purely for graphical reasons 
    instructions.push(start);
    instructions.push(end);
    instructions.push(currentPoint);
    drawLines(instructions);
    instructions.pop();
    instructions.pop();
    instructions.pop();
  }
  //  Once we finally hit a point on the left, we rebuild the line and draw the line 
  instructions.push(start);
  instructions.push(end);
  instructions.push(currentPoint);
  drawLines(instructions);
  
  //  Craft the new line
  start = end;
  end = currentPoint;
 } else {
  //If it's on the left side of the line, the process is super easy
  timeChecks++;
  instructions.push(currentPoint);
  start = end;
  end = currentPoint;
  drawLines(instructions);
 }
 count++;

 if (count >= points.length) {
  //  The exit to our loop
  noLoop();
  finished = true;
  println("We performed: " + timeChecks + " checks");
  drawLines(instructions);
  println("Points in the Vector Hull");
  while(!instructions.isEmpty()){
    println(instructions.pop());
  }
  return;
 }



}
void drawLines(Stack <AngleVector> stackInstructions) {
  //  Generate a copy to avoid memory clashes
  Object stackinstructions = stackInstructions.clone();
  Stack <AngleVector> instructions = (Stack <AngleVector> ) stackinstructions;
  background(0, 0, 0);
  noStroke();
  drawPoints();
  //  Check if we are concerned with closing the shape, or generating a blue point connector
  AngleVector v1, v2;
  if (!finished) {
    stroke(0, 0, 255);
  } else {
    stroke(0, 255, 0);
  }
  //  Checks to make sure we don't pull from an empty lot
  if (instructions.isEmpty()) {
    return;
  }
  v1 = instructions.pop();
  //  Checks to make sure we don't pull from an empty lot
  if (instructions.isEmpty()) {
    return;
  }
  v2 = instructions.pop();
  
  
  //  Build our final line as a blue one
  line(v1.x, v1.y, v2.x, v2.y);
  stroke(0, 255, 0);
  if (finished) {
    //  Close the sucker off
    line(v1.x, v1.y, lowestPoint.x, lowestPoint.y);
  }
  while (!instructions.isEmpty()) { //  Loop and generate green lines until we get back at the first place
    v1 = v2;
    v2 = instructions.pop();
    line(v1.x, v1.y, v2.x, v2.y);
  }
  //  Close to the lowest point
  line(v1.x, v1.y, lowestPoint.x, lowestPoint.y);

}

boolean isRight(AngleVector p) {
 slope = (end.y - start.y) / (end.x - start.x); 
 yIntercept = start.y - start.x * slope;
 
 //  If the line is horizontal
 if (abs(start.x - end.x) < 0.3) {
  if (end.y < start.y) {
   return p.x > end.x;
  } else {
   return p.x < end.x;
  }
 } else if (p.y - (p.x * slope + yIntercept) == 0) { //  In the case where we are on the line
  return false;
 } else if (end.x - start.x > 0) { //  If the endpoint is to the right of the pivot
  if (p.y < (slope * p.x + yIntercept)) {
   return false;
  }
  return true;
 } else {  //  If the endpoint is to the left of the pivot
  if (p.y > (slope * p.x + yIntercept)) {
   return false;
  }
  return true;
 }
}
// draws all the points
// see pushStyle() and popStyle() if you are interested in saving stroke, weights, fill, etc. 
void drawPoints() {
 strokeWeight(10);
 stroke(120, 255, 120); // red points
 point(lowestPoint.x, lowestPoint.y);
 stroke(255, 255, 255);
 for (AngleVector p: points) {
  point(p.x, p.y);
 }
}

class AngleVector {  // A custom class that simply adds an angle component for use
 float x = 0.0;
 float y = 0.0;
 float angle = 0.0;
 public AngleVector(float x, float y, float angle) {
  this.x = x;
  this.y = y;
  this.angle = angle;
 }
 public void setAngle(AngleVector av) {
  angle = atan((av.y - this.y) / (av.x - this.x)); // This generates a right triangle to use for angle calculation between two points
 }
 public String toString() {
  return this.x + " " + this.y + " ";
 }
}
