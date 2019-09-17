// an array of random points
PVector[] points;
int start, end;
float shortestPath;
PVector[] shortestPair;
int count = 0; 


void setup() {
  size(1000, 800);
  
  // This will be our only loop we write on our own 
  // because draw will do the rest of the looping for us
  // Fill the array
  shortestPath = Integer.MAX_VALUE;
  points = new PVector[20];
  shortestPair = new PVector[2];
  for(int i = 0; i < points.length; i++){
    points[i] = new PVector(random(width), random(height));
  }
  start = 0; // first point 
  end = 1;  // second point
  
  
  
}
//loops roughly 60 times / sec
void draw(){
  fill(0, 0, 0, 50);   // Set a transparent black color for strobe effect.
  noStroke();          // Prevent borders from appearing around the screen 
  rect(0, 0, width, height); // draw a black rectangle on the whole screen
  drawPoints(); 
  // connect start and end points with a green segment: 
  stroke(0, 255, 0);  // Use green lines for all the brute force tries
  
  if(shortestPair[0] != null){ // We do this as long as we have found our first pair
     stroke(0, 0, 255);
     line(shortestPair[0].x, shortestPair[0].y, shortestPair[1].x, shortestPair[1].y);
     stroke(0, 255, 0); 
  }
  
  if(start != -1 && start != end){ //Our cutoff condition 
     count++;
     line(points[start].x, points[start].y, points[end].x, points[end].y); // Draw all the brute force green lines
     if(points[start].dist(points[end]) < shortestPath){ // Swap the points out if we hit a new smallest distance 
       shortestPath = points[start].dist(points[end]);
       shortestPair[0] = points[start];
       shortestPair[1] = points[end];
     }
    if(end == points.length - 1){ // Adjust our vars when we get to the actual end
      if(start == points.length - 2){
        start = -1;
      }
      else{
        start++;
        end = start + 1; 

      }
      
    }
    else{
      end++;
    }
}
else{ // Our final run will draw the last line 
  noStroke();
  fill(0, 0, 0);
  rect(0, 0, width, height);
  drawPoints();
  stroke(0, 0, 255);
  line(shortestPair[0].x, shortestPair[0].y, shortestPair[1].x, shortestPair[1].y);
  println("The points " + "(" + shortestPair[0].x + "," + shortestPair[0].y + ")"  + " and "  + "(" + shortestPair[1].x + "," + shortestPair[1].y + ")" + " are the closest with a distance of " + shortestPair[0].dist(shortestPair[1]));
  println("Count : " + count);
  noLoop();
}
}

// draws all the points
// see pushStyle() and popStyle() if you are interested in saving stroke, weights, fill, etc. 
void drawPoints(){
  strokeWeight(5);
  stroke(255, 0, 0); // red points
  for(PVector p: points){
    point(p.x, p.y);  
  }
}
