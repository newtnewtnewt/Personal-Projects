int level = 0;
int col = 0;

void setup() {
  size(800, 800);
  sierpinski(0, height, width/2, 0, width, height, 10);
  // background(255);
  //recursiveCircle(width/2, height/2, width, level);
  //col = 0;
}

void sierpinski(int x1, int y1, int x2, int y2, int x3, int y3, int depth) {
  if (depth == 0) {
    return;
  }
  fill(255);
  triangle(x1, y1, x2, y2, x3, y3);
  sierpinski(x1, y1, (x1 + x2)/2, (y1 + y2)/2, (x1 + x3)/2, (y1 + y3)/2, depth - 1);
  sierpinski((x1 + x2)/2, (y1 + y2)/2, x2, y2, (x2 + x3)/2, (y2 + y3)/2, depth - 1);
  sierpinski((x1 + x3)/2, (y1 + y3)/2, (x2 + x3)/2, (y2 + y3)/2, x3, y3, depth - 1);
}

void mousePressed() {
  level++;
}
void recursiveCircle(float x, float y, float diam, int depth) {

  fill(col, 0, 0);
  circle(x, y, diam);
  col++;
  if (depth == 0) return; 
  recursiveCircle(x - diam/4, y, diam/2, depth - 1);   //  left small circle
  recursiveCircle(x + diam/4, y, diam/2, depth - 1);  //  right small circle
}
