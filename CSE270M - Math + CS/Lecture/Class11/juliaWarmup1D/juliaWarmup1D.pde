// Julia sets are based on a simple iterated function: f(x) = x^2 + c (where c is some constant)
// We ask: given the iterated function f(x) = x^2 + c, which seeds escape, and which don't?
// Technically, the Julia set is the boundary between the points that escape and the points that don't.
// Julia sets are really done with all complex numbers.  We are working with real numbers instead.
// But everything else is the same!

final float ESCAPE_DISTANCE = 5; //  At what point will we say the orbit escaped and won't return 
final int BAILOUT = 10;          //  When we are choosing to stop running the checks 
float c = 0;
final float LOWER_BOUND = -4;
final float UPPER_BOUND = 0.5;
final float INCREMENT = 0.005;

void setup() {
  background(0);
  size(800, 800);
  strokeWeight(1);
}

void makeJulia1D() {
  background(0);
  // Each value of c gives a different picture (number line)
  // Let's make it easy to see the pictures by moving the mouse
  // Let's map the mouse x-coordinate to a range of c-values we would like
  // to explore. 
  // c = map(mouseX, 0, width, LOWER_BOUND, UPPER_BOUND);
  for (c = LOWER_BOUND; c <= UPPER_BOUND; c += INCREMENT) {
    float drawY = map(c, LOWER_BOUND, UPPER_BOUND, 0, height);
    //  This draws the "julia set" for a given C value
    //  Which seeds from -3 to 3 "escape"?
    for (float x = -3; x <= 3; x += INCREMENT) {
      //  draw a umber line across the screen
      //  coloring the escapees red, and non-escapees green 
      float drawX = map(x, -3, 3, 0, width);

      if (escapes(x)) {
        stroke(255, 0, 0);  // escape = red
      } else {
        stroke(0, 255, 0);  // escape = green
      }
      point(drawX, drawY);
    }
  }
}

void draw() {
  makeJulia1D();
}



// Start with a seed, and iterate using f(x) until
// we determine whether the orbit escapes (goes off to infinity)
// What counts as "escaping"?  if seed gets > ESCAPE_DISTANCE
// How many times should we iterate before we stop trying? Use BAILOUT.
// After BAILOUT iterations, if we still haven't escaped, assume we won't
boolean escapes(float seed) {
  for (int i = 0; i < BAILOUT; i++) {
    if (seed > ESCAPE_DISTANCE)
      return true; // escaped
    seed = f(seed);
  }
  return false; //  didn't escape
}

// The function that we will iterate
float f(float x) {
  return x * x + c;
}
