import java.util.Collections;
import java.util.List;
import java.util.Arrays;

PImage old;

/* I opted to use a custom picture of my dog */
void settings() {
  old = loadImage("smolBaxter.png");  
  old.resize((old.width * 2)/4, (old.height * 2)/4); //  There is nothing particular to these divisions, I just got the size I wanted from it
  size(old.width * 4, old.height * 2 ); //  We want 8 images for a gallery
}

// Build all of our desired filters in the gallery at specific locations
void setup() {
  color[] crayons = randomPalette(10); //  Number of crayons we want for our best Match filter
  image(soften(old, 0), 0, 0);         //  I left this as the original image
  image(soften(old, 7), old.width, 0); //  Blur of gradient 7 
  image(soften(old, 15), old.width * 2, 0); //  Blur of gradient 15
  image(voidRipper(old), old.width * 3, 0); //  We only were supposed to do 1 custom filter but this was too good to pass up
  image(rotate(old, 45), 0, old.height);   //  Rotate the image by the given number of degrees
  image(ghosties(old, 5), old.width, old.height); //  I called this one ghosties because it makes images appear ghastly in nature, the second parameter is the amount of ghostiness
  image(bestMatch(old, crayons), old.width * 2, old.height); //  Checking best match with our crayons
  image(scramble(old, 5, 4), old.width * 3, old.height); //  Scramble into a 4 x 5
}

void draw() {
}

/* This method rotates an image by the specified angle amount */
PImage rotate(PImage src, float angle){
  
  PImage result = createImage(src.width, src.height, RGB);
  result.loadPixels();
  
  //  Establish our picture width and height
  final int W = src.width, H = src.height;
   
  //  Using x and y to make readability easier
  for(int x = 0; x < W; x++){
    for(int y = 0; y < H; y++){
      
      //  Grab the angle of a given x,y pair
      float otherAngle = atan2(y - (H/2), x - (W/2));
      //  Calculate radius to x,y from center
      float radius = dist(x, y, (W/2), (H/2));
      
      //  Add our given angle to the current angle
      float srcAngle = otherAngle + radians(-angle);
      //  Figure out what pixels belongs in this location
      int xVal = Math.round(((radius * cos(srcAngle)) + W/2.0));
      int yVal = Math.round(((radius * sin(srcAngle)) + H/2.0));
      //  Set the color pixel to this location
      result.set(x, y, src.get(xVal, yVal));
    }
  }
  for(int i = 0; i < result.pixels.length; i++){
    //  Color in the dark areas
    if(result.pixels[i] == 0){
      result.pixels[i] = color(0, 255, 0);
    }
  }
  return result;

}

/* A bonus custom built filter I found while trying to get angle right */
PImage voidRipper(PImage src){
  PImage result = createImage(src.width, src.height, RGB);
  float angle = 0;
  result.loadPixels();
  
  final int W = src.width, H = src.height;
  for(int x = 0; x < W; x++){
    for(int y = 0; y < H; y++){
      
      float otherAngle = atan2(y - H/2.0, x - W/2.0);
      float radius = dist(x, y, W/2.0, H/2.0);
      color colorOfCurrent = src.get(x, y);
      
      // The only big difference between this and the above is a pointless conversion that
      // Achieves a crazy circlular pattern
      float srcAngle = degrees(otherAngle) + degrees(angle);
      int xVal = (int)((radius * cos(srcAngle)) + W/2.0);
      int yVal = (int)((radius * sin(srcAngle)) + H/2.0);
      result.set(xVal, yVal, colorOfCurrent);
    }
  }
  return result;

}

/* Generates an array of random colors */
color[] randomPalette(int n) {
  color[] randomColors = new color[n];
  for (int i = 0; i < n; i++) {
    //  Grab random components for R, G, and B
    randomColors[i] = color(random(0, 255), random(0, 255), random(0, 255));
  }
  return randomColors;
}

//  Creates ghosts of distance strength
PImage ghosties(PImage src, int strength) {
  PImage result = createImage(src.width, src.height, RGB);
  result.loadPixels();

  //  Our final resultant pixels
  color[] newPixels = new color[src.height * src.width];
  
  //  The Quarantined Chunk of the center of our image, to be superimposed on our others for a ghostly feel
  color[] ghostChunk = new color[(strength * 2 + 1) * (strength * 2 + 1) ];
  
  //  Create our ghost chunk from the center of the image
  int startingPixel = src.width/2 + src.width * src.height/2 - strength;
  for (int row = 0; row < strength * 2 + 1; row++) {
    for (int column = 0; column < strength * 2 + 1; column++) {
      color newColor = src.pixels[startingPixel + row * src.width + column];
      //  Each color has an alpha value of 0.3 to insure it does not block the underlying image
      ghostChunk[row * strength + column] = newColor + color(red(newColor), green(newColor), blue(newColor), 0.3);
    }
  }
  
  //  For every single pixel, we lay a copy of the ghostChunk over that area, blended with the existant chunk
  for (int row = 0; row < src.height - (strength * 2 + 1); row++) {
    for (int column = 0; column < src.width - (strength * 2) + 1; column+= strength ) {
      // A seperate counter for the ghost chunk
      int counter = 0;
      for (int squareRow = row; squareRow < row + strength * 2 + 1; squareRow++) {
        for (int squareColumn = 0; squareColumn < strength * 2 + 1; squareColumn++) {
          //  Blend each pixels of our output array with our ghost image
          newPixels[squareRow * src.width + column + squareColumn] =  src.pixels[squareRow * src.width + column + squareColumn] + ghostChunk[counter];
          counter++;
        }
      }
    }
  }
  result.pixels = newPixels;
  return result;
}


/* Soften optimized with bit-shifting */
PImage soften(PImage src, int strength) {
  PImage result = createImage(src.width, src.height, RGB);
  result.loadPixels();
  /* Initializing values in hex */
  int counter = 0x0;
  int red = 0x0; 
  int blue = 0x0; 
  int green = 0x0;
  color[] newPixels = new color[src.height * src.width];
  //  Starting our clock
  float timeStart = millis();
  for (int row = 0; row < src.height; row++) {
    for (int column = 0; column < src.width; column++) {
      //  Resetting our values so we can obtain correct averages
      counter = 0x0;
      red = 0x0;
      green = 0x0; 
      blue = 0x0;
      // The loop that allows us to calculate the average color of all surrounding pixels
      for (int squareRow = row - strength; squareRow <= (row + strength); squareRow++) {
        for (int squareColumn = squareRow * src.width + column - strength; squareColumn <= squareRow * src.width + column + strength; squareColumn++) {
          //  Prevents Top OOB
          if (squareRow < 0) {
            break; 
          } else if (squareRow > src.height - 1) {  //  Avoid Bottom OOB 
            break;
          } else if (squareColumn >= (squareRow + 1) * src.width) {  //  Prevent right-most OOB
            break;
          } else if (squareColumn < squareRow * src.width) {  //  Prevent left-most OOB
            continue;
          } else {
            red = red + (src.pixels[squareColumn] >> 16 & 0xFF);  //  Clip only the red part of the hex color
            green = green + ((src.pixels[squareColumn] >> 8) & 0xFF);  //  Clip only the green part of the hex color
            blue = blue + (src.pixels[squareColumn] & 0xFF);  //  Clip only the blue part of the hex color
            counter++;
          }
        }
      }
      newPixels[row * src.width + column] = color(red/counter, green/counter, blue/counter);  //  Add the average of the surrounding colors
    }
  }
  println("Congrats: your code ran in : " + (millis() - timeStart) + " milliseconds.");  //  Our finish time
  result.pixels = newPixels;

  return result;
}

/* Scramble our image based on xDivs rows and yDivs columns */
PImage scramble(PImage src, int xDivs, int yDivs) {
  PImage result = createImage(src.width, src.height, RGB);
  result.loadPixels();
  color[] newPixels = new color[src.height * src.width];
  if (src.width % xDivs != 0 ) {  //  Chuck some errors in bad cases
    throw new IllegalArgumentException("xDivs " + xDivs + " is not a divisor of " + src.width);
  } else if (src.height % yDivs != 0) {
    throw new IllegalArgumentException("yDivs " + yDivs + " is not a divisor of " + src.height);
  } else {
    int xDivisions = src.width/xDivs;  //  Size of each X-chunk
    int yDivisions = src.height/yDivs;  //  Size of each Y-chunk 
    List<Integer> xValsList = new ArrayList<Integer>();  //  Store these guys as lists to call Sort
    List<Integer> yValsList = new ArrayList<Integer>();

    //  Grab all starting X Values 
    for (int i = 0; i < xDivs; i++) {
      xValsList.add(i * xDivisions);
    }
    //  Grab all starting Y Values
    for (int i = 0; i < yDivs; i++) {
      yValsList.add(i * yDivisions);
    }
    //  A 2D array to stash our X-Y pairs
    int[][] allPoints = new int[xDivs * yDivs][2];
    Collections.shuffle(xValsList);
  
    for (int i = 0; i < xDivs; i++) {
      Collections.shuffle(yValsList);
      for (int j = 0; j < yDivs; j++) {
        allPoints[i * yDivs + j][0] = xValsList.get(i);
        allPoints[i * yDivs + j][1] = yValsList.get(j);
      }
    }
     //  Actual values in the array we want to start at
    int[] actualStarts = new int[xDivs * yDivs];
    for (int i = 0; i < xDivs * yDivs; i++) {
      actualStarts[i] = allPoints[i][0] + allPoints[i][1] * src.width;
    }

    //  A series of divisions + Modulus math I used instead of using .get() because
    //  I am an idiot who didn't check the documentation right away
    for (int row = 0; row / yDivisions < yDivs; row++) {
      int currentRow = row % yDivisions;
      int setPoint = row / yDivisions;
      for (int column = 0; (column) / (xDivisions) < xDivs; column++) {
        newPixels[(currentRow + setPoint * yDivisions) * src.width + column] = 
          (src.pixels[actualStarts[setPoint * (xDivs) + column/xDivisions] 
          + (currentRow * src.width) + column % xDivisions]);
      }
    }
    //  Finish 'er off
    result.pixels = newPixels;
    return result;
  }
}

// Find the best match for each pixel and swap it for its best match color
PImage bestMatch(PImage src, color[] palette) {
  PImage result = createImage(src.width, src.height, RGB);
  result.loadPixels();
  for (int i = 0; i < src.pixels.length; i++) {
    result.pixels[i] = closestColor(src.pixels[i], palette);
  }

  return result;
}

//  A helper method that uses Euclidean "distance" to find the color closest to one in an array of color
color closestColor(color c1, color[] palette) {
  color cColor = color(0, 255, 0);
  double closestVal = Integer.MAX_VALUE;
  for (color c2 : palette) {
    double distance = sqrt(pow(red(c2) - red(c1), 2) + pow(green(c2) - green(c1), 2) + pow(blue(c2) - blue(c1), 2));
    if (distance < closestVal) {
      closestVal = distance;
      cColor = c2;
    }
  }
  return cColor;
}
