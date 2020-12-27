// Given a set of random points, and a directed line segment from start to end,
// color all the points red that are to the right of the line segment.

PVector[] points; // the random points
PVector start, end;   
float slope;
float yIntercept;

float BOUNDARY = 125; // keeps points from being too close to the walls

void setup() {
  size(800, 600);

  // Establishes the random points
  // Test with 3 points or more. 
  // Assume that no 3 points are colinear
  points = new PVector[20];
  for (int i = 0; i < points.length; i++) {
    points[i] = new PVector(random(BOUNDARY, width - BOUNDARY), random(BOUNDARY, height - BOUNDARY));
  }

  // A random stationary starting point for our directed segment.  
  // The endpoint is set by the mouse in draw().
  // See mouseClicked().
  start = new PVector(random(BOUNDARY, width - BOUNDARY), random(BOUNDARY, height - BOUNDARY));
}

void draw() {
  background(0);
  // A moveable endpoint for our directed segment
  end = new PVector(mouseX, mouseY); 
 
  drawDirectedSegment();
  drawPoints();
}


// Draws a line through two given points.
void drawDirectedSegment() {
  
  //  Compute slope of line through start and end;
  
  float x0, y0, x1, y1;

  x0 = start.x;
  y0 = start.y;
  x1 = end.x;
  y1 = end.y;
  slope = (y1 - y0) / (x1 - x0);
  //println(slope);
  
  // pick a point to the left of the screen such as where x = -10
  // compute the y value for that point so that it is on the same line.
  // repeat with a point to the right of the screen such as where x = width + 10
  // compute its y value
  // draw the segement between those points
 yIntercept = start.y - start.x * slope;
 float actualX1 = -10;
 float actualY1 = slope * actualX1 + yIntercept;
 float actualX2 = width + 10;
 float actualY2 = slope * actualX2 + yIntercept;
  
  // The yellow line
  strokeWeight(5);
  stroke(255, 255, 0);
  if(Float.isInfinite(slope)){
    line(start.x, start.y + height, end.x, end.y - height);
  }
  else{
    line(actualX1, actualY1, actualX2, actualY2);
  }
  // The points. Start is yellow, end is green.
  stroke(255, 255, 0);
  strokeWeight(20);
  point(start.x, start.y); // keep
  stroke(0, 255, 0);
  point(end.x, end.y); // keep 
}

// Returns true if point p is to the right of the directed line segment
// from start to end.
// TODO #2: Figure this out.
// Some helpful questions to consider:
//   How do you know if a point is ON a specified line?
//   How do you know if a point is ABOVE or BELOW a specified line?
//   How can you use the above/below to determine right/left?
boolean isRight(PVector p) {
  if(abs(start.x - end.x) < 0.3){ println(abs(start.x - end.x)); }
  if(abs(start.x - end.x) < 0.3){
    if(end.y < start.y){
       return p.x > end.x;
    } else { 
      println("God save us!");
      return p.x < end.x;
      }
  }
  else if(p.y - (p.x * slope + yIntercept) == 0){
    return false;
  }
  else if(end.x - start.x > 0){
    if(p.y < (slope * p.x + yIntercept)) { return false; }
    return true;
  }

  else{
    if(p.y > (slope * p.x + yIntercept)) { return false; }
    return true;
  }
  
  
  
}

// Draws all the random points.
// TODO #3: Color these points based on whether they are to the left or to the
// right of the directed segment from a to b.  Points on the right should be red.
void drawPoints() {
  strokeWeight(10);
  for (PVector p : points) { 
    // change this based on whether point is on the right or left
    if(isRight(p)){
     stroke(color(255, 0, 0));
    }
    else{
     stroke(color(0, 255, 0));
    }
    point(p.x, p.y);
  }
}

// Lets us choose the new stationary point by clicking the mouse.
void mouseClicked() {
  start = new PVector(mouseX, mouseY);
}


// TODO #4: Are there any edge cases to consider?  
//          Could you manually set start and end to values that would cause problems?
//          If so, how do you fix those problems?
