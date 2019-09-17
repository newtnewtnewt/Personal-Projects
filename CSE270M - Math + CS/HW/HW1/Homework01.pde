int numberOfDots = 0; 
ArrayList<Point> dotArray = null;
String distanceMeasure = "Euclidean";


public class Point{
   float x = 0;
   float y = 0;
   color c =  color(0, 0, 0);
   public Point(){
     c = color(random(0, 256), random(0, 256), random(0, 256));
     x = random(0, 700 + 1);
     y = random(0, 700 + 1);
   }
   public Point(float x1, float y1){
      c = color(random(0, 256), random(0, 256), random(0, 256));
      x = x1; 
      y = y1;
   }
   
   public void drawPoint(){
    stroke(0, 0, 0);
    strokeWeight(4.0);
    point(x, y); 
   }
   public String toString(){
       return "The point is present at (" + x + ", " + y + ") " + "with color " + c;
   }
   public boolean equals(Point p){
     if(this.x == p.x && this.y == p.y && this.c == p.c) { return true; }
     else{ return false; }
       
   }
}

void setup(){
  noStroke();
  size(700, 700);
  strokeWeight(4.0);
  numberOfDots = int(random(0, 101));
  dotArray = new ArrayList<Point>();
  
  for(int i = 0; i < numberOfDots; i++){
    dotArray.add(i, new Point());
  }
}

  
void draw(){
   background(200);
   for(int y = 0; y < 700; y++){
     for(int x = 0; x < 700; x++){
        set(x,y,dotArray.get(findClosestPoint(x,y, distanceMeasure)).c);
     }
   }
   for(int i = 0; i < numberOfDots; i++){
      if(dotArray.get(i) != null){
         dotArray.get(i).drawPoint();
     }
        
   }
   
}

void mouseClicked(){
   if(mouseButton == LEFT){
     float x = mouseX;
     float y = mouseY;
     Point genPoint = new Point(x, y);
     dotArray.add(genPoint);
     println("Point created at (" + mouseX + ", " + mouseY + ") ");
     //point(mouseX, mouseY);
     numberOfDots++;
   }
   if(mouseButton == RIGHT){
     int closest = findClosestPoint(mouseX, mouseY, "Euclidean");
      
     if(dotArray.size() != 0){
       println("Point deleted at (" + dotArray.get(closest).x + ", " + dotArray.get(closest).y + ") ");
       dotArray.remove(closest);
       numberOfDots--;
       redraw();
     }
     else{
       println("No dots remaining!");
     }
     
   }
}
void keyPressed(){
   if(distanceMeasure.equals("Euclidean")){ 
     println("Distance measure set to Taxicab");
     distanceMeasure = "Taxicab";
   }
   else if(distanceMeasure.equals("Taxicab")){ 
     println("Distance measure set to Chebyshev");
     distanceMeasure = "Chebyshev";
   }
   else if(distanceMeasure.equals("Chebyshev")){ 
     println("Distance measure set to Euclidean");
     distanceMeasure = "Euclidean";
   }
   redraw();
   
   }
int findClosestPoint(float x, float y, String method){
   float closeDistance = Integer.MAX_VALUE;
   float curDistance = 0;
   int pointNumber = -1;
   for(int i = 0; i < numberOfDots; i++){
      Point p = dotArray.get(i); 
      curDistance = euclideanDistance(x, y, p.x, p.y);
      if(method.equals("Euclidean")){ curDistance = euclideanDistance(x, y, p.x, p.y);}
      else if(method.equals("Taxicab")){ curDistance = chebyshevDistance(x, y, p.x, p.y);}
      else{ curDistance = taxiDistance(x, y, p.x, p.y); }
      
      if(curDistance < closeDistance){
           closeDistance = curDistance;
           pointNumber = i; 
        }
    }
   
   return pointNumber;
}
     
float euclideanDistance(float x1, float y1, float x2, float y2){
    return (float)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)); 
}

float taxiDistance(float x1, float y1, float x2, float y2){
    return (float)(Math.abs(x2 - x1) + Math.abs(y2 - y1)); 
} 
float chebyshevDistance(float x1, float y1, float x2, float y2){
    return (float)Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
}
