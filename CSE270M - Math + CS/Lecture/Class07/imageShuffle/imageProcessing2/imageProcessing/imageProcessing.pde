//  PImage will hold a file loaded from our
PImage old;
int factor = 1;

void settings(){
  old = loadImage("smolBaxter.png"); 
  old.resize(old.width/4, old.height/4);
  size(old.width * 4, old.height * 2 );
}

void setup(){
   
}

void draw(){
  image(zoom(old), 0, 0);
   image(old, old.width, 0);
   image(old, old.width * 2, 0);
   image(old, old.width * 3, 0);
   image(old, 0, old.height);
   image(old, old.width, old.height);
   image(old, old.width * 2, old.height);
   image(old, old.width * 3, old.height);
}
//  Our first image takes an image
//  and returns a new image without messing with 
//  the original
PImage zoom(PImage src){
  PImage result = createImage(src.width, src.height, RGB);
  result.loadPixels();
  
  final int W = src.width/2;
  final int H = src.height/2;
  
  // x and y refer to the coordinates in the RESULT image
  for(int x = 0; x < src.width; x++){
    for(int y = 0; y < src.height; y++){
      //  How far is (x, y) from the center (W, H)
      int deltaX = x - W;
      int deltaY = y - H;
      int randomX = (int)random(-10, 5);
      int randomY = (int)random(-10, 5);
      //  now move only half of that distance in each direction
      //  to grab the pixel you want 
      
      result.set(x, y, src.get(W + deltaX/factor + randomX, H + deltaY/factor + randomY));
    }
  }
  return result;
}
  PImage kailedoScope(PImage src){
  PImage result = createImage(src.width, src.height, RGB);
  result.loadPixels();
  
  final int W = src.width/2;
  final int H = src.height/2;
  
  // x and y refer to the coordinates in the RESULT image
  for(int x = 0; x < src.width; x++){
    for(int y = 0; y < src.height; y++){
      //  How far is (x, y) from the center (W, H)
      int deltaX = abs(W - x);
      int deltaY = abs(H - y);
      //  now move only half of that distance in each direction
      //  to grab the pixel you want 
      
      result.set(x, y, src.get(x, y));
    }
  }
  return result;




}

void mouseClicked(){
  if(mouseButton == LEFT){
    factor++;
  }
  else{
    if(factor != 1){
      factor--;
    }
  }
}
