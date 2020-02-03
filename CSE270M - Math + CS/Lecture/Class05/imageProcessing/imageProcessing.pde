//  PImage will hold a file loaded from our
PImage old;

void settings(){
  old = loadImage("smolBaxter.png"); 
  size(old.width, old.height);
}

void setup(){
   image(rotate(old), 0, 0);
   //image(darken(old, 3), 0, 0);
   //image(darken(old, 0.5), 0, 0);
}

void draw(){
  //float factor = map(mouseX, 0, old.width, 0, 1);
  //image(darken(old, factor), 0, 0);
}
//  Our first image takes an image
//  and returns a new image without messing with 
//  the original
PImage rotate(PImage source){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  
  // copy all the pixels from source to result
  for(int i = 0; i < source.pixels.length; i++){
    result.pixels[i] = source.pixels[source.pixels.length - 1 - i];
  }
  
  
  result.updatePixels();
  return result;
}

//  Darkens an image
//  Note: You can darken by dividing the color components by the same value,
//  or multiplying them by the same value (from 0 to 1).

PImage darken(PImage source, float factor){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  // in result, store darker version of what is in source
  for(int i = 0; i < source.pixels.length; i++){
   // First, extract the rgb components of the source image:
   float r = red(source.pixels[i]);
   float g = green(source.pixels[i]);
   float b = blue(source.pixels[i]);
   result.pixels[i] = color(r * factor, g * factor, b * factor);
  }
  return result;
}

//  Does something dumb with an image
PImage dumb(PImage source){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  // in result, store darker version of what is in source
  for(int i = 0; i < source.pixels.length; i++){
   // First, extract the rgb components of the source image:
   float r = red(source.pixels[i]);
   float g = green(source.pixels[i]);
   float b = blue(source.pixels[i]);
   result.pixels[i] = color(r % 100, g % 100, b % 100);
  }
  return result;
}

//  Create a greyscale version based on averaging the components
PImage greyScale(PImage source){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  // in result, store darker version of what is in source
  for(int i = 0; i < source.pixels.length; i++){
   // First, extract the rgb components of the source image:
   float r = red(source.pixels[i]);
   float g = green(source.pixels[i]);
   float b = blue(source.pixels[i]);
   
   float avg = (r + g + b) / 3;
   
   result.pixels[i] = color(avg, avg, avg);
  }
  return result;
}

PImage negative(PImage source){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  // in result, store darker version of what is in source
  for(int i = 0; i < source.pixels.length; i++){
   // First, extract the rgb components of the source image:
   float r = red(source.pixels[i]);
   float g = green(source.pixels[i]);
   float b = blue(source.pixels[i]);
   
   float avg = (r + g + b) / 3;
   
   result.pixels[i] = color(255 - avg, 255 - avg, 255 - avg);
  }
  return result;
}

PImage negativeFlip(PImage source){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  // in result, store darker version of what is in source
  for(int i = 0; i < source.pixels.length; i++){
   // First, extract the rgb components of the source image:
   float r = red(source.pixels[i]);
   float g = green(source.pixels[i]);
   float b = blue(source.pixels[i]);
   
   float avg = (r + g + b) / 3;
   
   result.pixels[i] = color(255 - r, 255 - g, 255 - b);
  }
  return result;
}





//  Rotates the original image by 180 degrees 
void reversePractice(){
 old.loadPixels();
 for(int i = 0; i < old.height * old.width / 2.0; i++){
   color temp = old.pixels[i];
   old.pixels[i] = old.pixels[old.pixels.length - 1 - i];
   old.pixels[old.pixels.length - 1 - i] = temp;
 }
 
 
 old.updatePixels();
 image(old, 0, 0);
}

void messWithImage(){
  old.loadPixels();  //  Always do this before changing the image
  
  old.pixels[0] = color(0, 255, 0);
  old.pixels[old.pixels.length - 1] = color(255, 0, 0);
  //old.set(0, 0, color(0, 255, 0));  //  Is inefficient
  
  //  Make the entrie top row yellow
  for(int i = 0; i < old.width; i++){
    old.pixels[i] = color(255, 255, 0);
  }
  
  //  Make the left border red
  for(int i = 0; i < old.height; i++){
     old.pixels[i * old.width + 200] = color(255, 0, 0);
  }

  old.updatePixels(); //  Always do this after changing the image
  image(old, 0, 0, 1000, 1000);
}

void run(){
  
}
