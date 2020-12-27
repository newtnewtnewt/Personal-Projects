void setup(){
  size(800, 600);
}

void draw(){
  background(0);
  strokeWeight(4);
  stroke(0, 255, 0);
  line(width/2, height/2, 3*width/4, height/2);
  stroke(255, 0, 0);
  line(width/2, height/2, mouseX, mouseY);
  
  println(atan2(mouseY - height/2, mouseX - width/2));
  // compute r
  println(dist(mouseX, mouseY, width/2, height/2));
}
