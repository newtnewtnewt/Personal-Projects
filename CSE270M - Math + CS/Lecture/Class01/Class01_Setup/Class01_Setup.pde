
void setup() {
  size(800, 800);
  strokeWeight(5);    
  point(50,50);
  strokeWeight(1);
  ellipse(400,400,200,100);
}

//This method tries to run 60 times per second
void draw(){
 //background(0,0,0); //black 
 //A red ellipse that follows the mouse
 fill(255, 0, 0);
 //calculates distance from previous mouse location to new mouse location 
 float diam = dist(mouseX, mouseY, pmouseX, pmouseY);  
 ellipse(mouseX, mouseY, diam, diam);
}
