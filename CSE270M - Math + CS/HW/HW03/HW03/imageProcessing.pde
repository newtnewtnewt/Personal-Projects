//  PImage will hold a file loaded from our
PImage old;

void settings(){
  old = loadImage("kluber.png"); 
  old.resize((old.width * 2)/4, (old.height * 2)/4);
  size(old.width * 4, old.height * 2 );
}

void setup(){
   image(old, 0, 0);
   image(brighten(old, 75), old.width, 0);
   image(posterize(old, 100), old.width * 2, 0);
   image(redscale(old), old.width * 3, 0);
   image(blackAndWhite(old, 100), 0, old.height);
   image(darkToGreen(old, 100), old.width, old.height);
   image(mirrorLeft(old), old.width * 2, old.height);
   image(flipVertical(old), old.width * 3, old.height);
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

PImage brighten(PImage source, float delta){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  // in result, store darker version of what is in source
  for(int i = 0; i < source.pixels.length; i++){
   // First, extract the rgb components of the source image:
   float r = red(source.pixels[i]);
   float g = green(source.pixels[i]);
   float b = blue(source.pixels[i]);
   result.pixels[i] = color(r + delta, g + delta, b + delta);
  }
  return result;
}
PImage posterize(PImage source, int interval){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  // in result, store darker version of what is in source
  for(int i = 0; i < source.pixels.length; i++){
   // First, extract the rgb components of the source image:
   int r = (int)red(source.pixels[i]);
   int g = (int)green(source.pixels[i]);
   int b = (int)blue(source.pixels[i]);
   result.pixels[i] = color(r / interval * interval, g / interval * interval, b / interval * interval);
  }
  return result;
  
}
PImage redscale(PImage source){
  PImage result = createImage(source.width, source.height, RGB);
  
  result.loadPixels();
  
  // in result, store darker version of what is in source
  for(int i = 0; i < source.pixels.length; i++){
   // First, extract the rgb components of the source image:
   float r = red(source.pixels[i]);
   float g = green(source.pixels[i]);
   float b = blue(source.pixels[i]);
   
   float avg = (r + g + b) / 3;
   
   result.pixels[i] = color(avg, 0, 0);
  }
  return result;
  
}
PImage blackAndWhite(PImage source, int threshold){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  // in result, store darker version of what is in source
  for(int i = 0; i < source.pixels.length; i++){
   // First, extract the rgb components of the source image:
   float r = red(source.pixels[i]);
   float g = green(source.pixels[i]);
   float b = blue(source.pixels[i]);
   
   float avg = (r + g + b) / 3;
   
   if(avg >= threshold){
     result.pixels[i] = color(255, 255, 255);
   }
   else{
     result.pixels[i] = color(0, 0, 0);
   }
  }
  return result;
  
}
PImage darkToGreen(PImage source, int threshold){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  // in result, store darker version of what is in source
  for(int i = 0; i < source.pixels.length; i++){
   // First, extract the rgb components of the source image:
   float r = red(source.pixels[i]);
   float g = green(source.pixels[i]);
   float b = blue(source.pixels[i]);
   
   float avg = (r + g + b) / 3;
    if(avg >= threshold){
     result.pixels[i] = color(r, g, b);
     }
     else{
       result.pixels[i] = color(0, g, 0);
     }
   
  }
  return result;
}
PImage mirrorLeft(PImage source){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  
  // copy all the pixels from source to result
  for(int i = 0; i < source.pixels.length; i++){
    if(i % (source.width) > (source.width / 2)){
      result.pixels[i] = source.pixels[source.width/2 - i % (source.width / 2) + source.width  * (i / (source.width))];
    }
    else{
      result.pixels[i] = source.pixels[i];
    }
  }
  return result;
  
  
}
PImage flipVertical(PImage source){
  PImage result = createImage(source.width, source.height, RGB);
  result.loadPixels();
  
  
  // copy all the pixels from source to result
  for(int i = 0; i < source.pixels.length; i++){
    int rowNumber = i / source.width;
    int oppositeRowNumber = source.height - rowNumber - 1;
    result.pixels[i] = source.pixels[oppositeRowNumber * source.width + i % source.width];
  }
  return result;
  
}
