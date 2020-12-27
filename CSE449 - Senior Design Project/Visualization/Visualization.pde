import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Stack;
/*
* Things we want to display with this visualization
* 1. Average Trip Lengths 
* 2. Number of Requests (Average for a given day) graph of number of requests
* 3. Most Popular trip in the form of expanding dots regions (small dots get bigger when something occurs) AKA hotspots
* 4. Weekdays and Weekends 
* 5. Pickup/Dropoff Weekdays and Weekends in 4 quadrants Monday
* Remember Special Days (4th of July)
* M-T against each other
* F and S compared
* Sunday and Specials
* Put Graph in the ocean with number of trips over time.
* Focus primarily on 2 views on the screen at the same time. 
* Provide some spatial and temporal patterns
* 4 Criteria -> Avg Dist. Avg Time. Avg Speed. Temporal Pattern
*/



/*
 *  TO-DO: Swap Darken to Light from 6:00AM to 6:00PM
 *  TO-DO: Yellow dots for dead end trips 
 *  TO-DO: Making things go away when their trip is over.
 *  Noah Dunn
 *  Instructor: Vaskar Raychoudhury
 *  Date: 11/12/2019
 *  Class: CSE449
 *  Assignment: Visualization For TaxiCab
 *  Map picture ripped straight from Google Maps
 */
 

int framerate = 60; 

//  The four positions that determine the coordinate dimensions of our Chicago map for reference down below
final float MAX_X = -87.468768 - 0.017;
final float MAX_Y = 42.023249 + 0.017; //42.023249;
final float MIN_X = -87.941180 - 0.017;
final float MIN_Y = 41.630294 + 0.017; //41.630294;
// Points that correspond to the above x, y pairs, used to help visualize what's going on
Point topLeft  =    new Point(MIN_X, MAX_Y); 
Point topRight =    new Point(MAX_X, MAX_Y);
Point bottomRight = new Point(MAX_X, MIN_Y);
Point bottomLeft =  new Point(MIN_X, MIN_Y);


// Current Width and Height that correspond to the image file of Chicago that I am using
int screen_Width = 792;
int screen_Height = 844;
//  The PImage object that holds the chicago map
PImage chicagoMap;
PImage chicagoMapDark;
//  The scanner we are going to read our lines, line by line with
Scanner sc;
//  Stores the current time stamp we are at
String timeStore = null;
Stack<Float> xCoords = new Stack<Float>();
Stack<Float> yCoords = new Stack<Float>();
Stack<Float> realXCoords = new Stack<Float>();
Stack<Float> realYCoords = new Stack<Float>();
Stack<Float> distanceStack = new Stack<Float>();
Stack<Float> timeStack = new Stack<Float>();
Stack<String> endTimesStack = new Stack<String>();

// Used for tracking the points on the graph
Point bottomLeftGraphPoint;
Point topRightGraphPoint;
Point bottomLeftGraphPoint2;
Point topRightGraphPoint2;
Stack<Float> graphPointsY = new Stack<Float>();
float currentAverage = 0.0;
float currentAverage2 = 0.0;
float currentTimeAverage = 0.0;

// Darken or Brighten mode
final int BLACK = color(0, 0, 0);
final int WHITE = 255;
int colorOfDay = WHITE;

//Alpha-Value so that the heat-map values look right
final float ALPHA_VALUE = 5.0;
final float LINE_ALPHA_VALUE = 55.0;
final int RADIUS_VALUE = 20;

//the file we want to use 
final String fileName = "requestOutput16Peak.txt";



void settings(){
    // Correspond to the size of the image
    size(792, 844);
}
void setup() {  
  //  Determines how fast we want this sucker to run
  frameRate(framerate);
  // Setting up and loading our image
  chicagoMapDark =  negative(loadImage("ChicagoMap.PNG"));//negative(loadImage("ChicagoMap.PNG"));
  chicagoMap = loadImage("ChicagoMap.PNG");
  screen_Width = chicagoMap.width;
  screen_Height = chicagoMap.height;
  bottomLeftGraphPoint = new Point(width/4 * 3, 300);
  topRightGraphPoint = new Point(width - 20, 150);
  // line(80, 800, width/4 + 50, 800);
  // line(80, 800, 80, 650);
  bottomLeftGraphPoint2 = new Point(80, 800);
  topRightGraphPoint2 = new Point(width/4 + 60, 650);
  try  {
    // Begin reading our data file
    String file =  "" + sketchPath() + "/" + fileName;
    file = file.replace("\\", "/");
    sc = new Scanner(new File(file));
    sc.nextLine();
  }
  catch(FileNotFoundException e){
    println("Did not find the specified file");
  }
}

void draw() {
  drawFullPathing(true, false, true);       // Distance + Lines
  //drawFullPathing(false, true, true);     // Time and Heat-Map
  
  
}

void clearStacks(){
  //  Time to learn what value we want to trash now
  Stack<Float> xCoordsCopy = new Stack<Float>();
  Stack<Float> yCoordsCopy = new Stack<Float>();
  Stack<Float> distanceStackCopy = new Stack<Float>();
  Stack<Float> timeStackCopy = new Stack<Float>();
  Stack<String> endTimesStackCopy = new Stack<String>();
  float currentTime = convertTimeToHours(timeStore);
  while(!endTimesStack.isEmpty()){
    float timeValue = Float.parseFloat(endTimesStack.pop());
    if(currentTime == timeValue ){ // If these values' time slots have passed
      xCoords.pop();
      xCoords.pop();
      yCoords.pop();
      yCoords.pop();
      distanceStack.pop();
      timeStack.pop();
    }
    else { // Otherwise add them back to the stack
      xCoordsCopy.push(xCoords.pop());
      yCoordsCopy.push(yCoords.pop());
      xCoordsCopy.push(xCoords.pop());
      yCoordsCopy.push(yCoords.pop());
      distanceStackCopy.push(distanceStack.pop());
      timeStackCopy.push(timeStack.pop());
      endTimesStackCopy.push("" + timeValue);
    }
  }
    Stack<Float> xCoordsCopy2 = new Stack<Float>();
    Stack<Float> yCoordsCopy2 = new Stack<Float>();
    Stack<Float> distanceStackCopy2 = new Stack<Float>();
    Stack<Float> timeStackCopy2 = new Stack<Float>();
    Stack<String> endTimesStackCopy2 = new Stack<String>();
    // Flip the stack so that the items are back in the proper order
    while(!xCoordsCopy.isEmpty()){
      xCoordsCopy2.push(xCoordsCopy.pop());
      xCoordsCopy2.push(xCoordsCopy.pop());
      yCoordsCopy2.push(yCoordsCopy.pop());
      yCoordsCopy2.push(yCoordsCopy.pop());
      distanceStackCopy2.push(distanceStackCopy.pop());
      timeStackCopy2.push(timeStackCopy.pop());
      endTimesStackCopy2.push(endTimesStackCopy.pop());
    }
    //  Set our stacks to the new versions
    xCoords = xCoordsCopy2;
    yCoords = yCoordsCopy2;
    distanceStack = distanceStackCopy2;
    timeStack = timeStackCopy2;
    endTimesStack = endTimesStackCopy2;
    
  
}

/* A faciliation method to draw the lines, poitns, and graphs based on either 
*  @param drawAvgDist as true for distance related graphs or
*  @param drawAvgTime as true for time related graphs
*
*/
void drawFullPathing(boolean drawAvgDist, boolean drawAvgTime, boolean drawGraph){
      // If we run out of data, break the loop
    if(!sc.hasNextLine()){
      noLoop();
      return;
    }
    //  Call a method to process the data in the line file
    String[] dataPoint = convertLineData(sc.nextLine());
    //  When we change time stamps, clear our coordinate storage
    if(timeStore != null && !timeStore.equals(dataPoint[4])){
      // Add a graph point for the current average for a given time
      graphPointsY.push(currentAverage2);
      graphPointsY.push(currentAverage);
      clearStacks();
      // When it gets to be day/night, adjust the color of the map
    }
    // Stash our time stamp
    timeStore = dataPoint[4];
    
    // Color map based on the time of day
    if(isDay(timeStore)){
       colorOfDay = BLACK;
       image(chicagoMap, 0.0, 0.0);
    }
    else {
       colorOfDay = WHITE;
       image(chicagoMapDark, 0.0, 0.0);
    }    
    //  Draw all coordinate points up to this step
    drawAllPoints(drawAvgDist, drawAvgTime);
    //  Save a datapoint based on in the stored data
    float tempX1 = Float.parseFloat(dataPoint[1]);  //generateRandomXVal();
    float tempY1 = Float.parseFloat(dataPoint[0]);  //generateRandomYVal();
    
    //  Generate a second X and Y value in the range to represent the dropoff point
    float tempX2 = Float.parseFloat(dataPoint[3]);   //generateRandomXVal();
    float tempY2 = Float.parseFloat(dataPoint[2]);  //generateRandomYVal();
    
    // We need to save our real coordinates
    float realXCoord1 = tempX1;
    float realXCoord2 = tempX2;
    float realYCoord1 = tempY1;
    float realYCoord2 = tempY2;
    
    //  Map their values within our range
    tempY1 = (screen_Height - 1) - (map(tempY1, MIN_Y, MAX_Y, 0, screen_Height) - 1);
    tempX1 = map(tempX1, MIN_X, MAX_X, 0, screen_Width - 1);
    
    //  Map their values within the frame 
    tempY2 = (screen_Width - 1) - (map(tempY2, MIN_Y, MAX_Y, 0, screen_Width) - 1);
    tempX2 = map(tempX2, MIN_X, MAX_X, 0, 791);
    
    // Store our values
    xCoords.push(tempX1);
    xCoords.push(tempX2);
    yCoords.push(tempY1);
    yCoords.push(tempY2);
    realXCoords.push(realXCoord1);
    realXCoords.push(realXCoord2);
    realYCoords.push(realYCoord1);
    realYCoords.push(realYCoord2);
    
    //  Grab our distance
    endTimesStack.push(dataPoint[5]);
    timeStack.push(Float.parseFloat(dataPoint[6]));
    distanceStack.push(Float.parseFloat(dataPoint[7]));
    
    if(drawAvgDist && !drawAvgTime){
      // Set our line width
      strokeWeight(2);
      //  Set the color of the line to white
      stroke(colorOfDay, colorOfDay, colorOfDay, 5);
      line(tempX1, tempY1, tempX2, tempY2);
      // Set the circle of the beginning point to green
      stroke(color(0, 255, 0));
      fill(color(0, 255, 0));
      circle(tempX1, tempY1, 10);
      //  Set the circle of the end point to red
      stroke(color(255, 0, 0));
      fill(color(255, 0, 0));
      circle(tempX2, tempY2, 10);
      //  Set the color and build the time Text box
      fill(colorOfDay, colorOfDay, colorOfDay);
      textSize(26); 
      text(dataPoint[4], 10, 50); 
      if(drawGraph){
        // First and second values are the scale for the connected line graphs
        drawGraph(12, 25, true, false);
      }
    }
    else if(drawAvgTime && !drawAvgDist){
      stroke(255, 0, 0, ALPHA_VALUE);
      fill(255, 0, 0, ALPHA_VALUE);
      circle(tempX1, tempY1, RADIUS_VALUE);
      //  Set the circle of the end point to red
      stroke(255, 0, 0, ALPHA_VALUE);
      fill(255, 0, 0, ALPHA_VALUE);
      circle(tempX2, tempY2, RADIUS_VALUE);
      //  Set the color and build the time Text box
      fill(colorOfDay, colorOfDay, colorOfDay);
      textSize(26); 
      text(dataPoint[4], 10, 50); 
      if(drawGraph){
        // First and second values are the scale for the heat graph
        drawGraph(30, 1000, false, true);
      }
      textSize(10);
      
    }

}

/* A method to draw the graph based on what is stored */ 
public void drawGraph(int maxRangeVal1, int maxRangeVal2, boolean distanceGraph, boolean timeGraph){
   // We would like to draw a small graph to the right of the page to show what is going on
    stroke(color(colorOfDay, colorOfDay, colorOfDay));
    fill(color(colorOfDay, colorOfDay, colorOfDay));
    line(width/4 * 3, 300, width - 20, 300);
    line(width/4 * 3, 300, width/4 * 3, 150);
    
    line(80, 800, width/4 + 60, 800);
    line(80, 800, 80, 650);
      textSize(10);
      text("Time", width/4 * 3.35, 320);
      text("Time", 150, height - 25);
      // Vary our labels based on what visual we are showing.
      if(distanceGraph && !timeGraph){
        text("Avg.\nDist",width/4 * 3 - 30, height/4);
        text("Avg.\nSpeed", 30,  height/4 * 3 + 100);
      }
      else  {
        text("Avg.\nRide\nTime",width/4 * 3 - 30, 225);
        text("Num.\nRides", 30, height/4 * 3 + 100);
      }
      
      //  Create a copy to avoid permanently deleting data
      Stack<Float> graphPointsCopyY = (Stack<Float>) graphPointsY.clone();
      //  The size is equivalent to the x-value at a given Y value
      int size = graphPointsCopyY.size();
      final int startSize = size;
      //  While there are still points to plot
      while(!graphPointsCopyY.isEmpty()){
         if(startSize == graphPointsCopyY.size()){
           stroke(color(255, 0, 0));
            fill(color(255, 0, 0));
         }
         else{
          stroke(color(217, 129, 15));
          fill(color(217, 129, 15));
         }
         // Graph an average time, map it to the correct range, and plot it
         float averageTime = graphPointsCopyY.pop();
         float tempX = map(size / 2, 0, 96, bottomLeftGraphPoint.x, topRightGraphPoint.x);
         float tempY = map(averageTime, 0, maxRangeVal1, bottomLeftGraphPoint.y, topRightGraphPoint.y);
        
         circle(tempX, tempY, 5);
         averageTime = graphPointsCopyY.pop();
         // Print the values saved in the graph in the bottom left corner
         tempX = map(size / 2, 0, 96, bottomLeftGraphPoint2.x, topRightGraphPoint2.x);
         tempY = map(averageTime, 0, maxRangeVal2, bottomLeftGraphPoint2.y, topRightGraphPoint2.y);
         circle(tempX, tempY, 5);
         // We are essentially graphing backwards
         size -= 2;
      }

}



/* A helper method to turn the map black/white */
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

/* A helper method to determine day or night*/
public boolean isDay(String time) {
  // 6:00 PM
  int hour = Integer.parseInt(time.substring(0, time.indexOf(":")));
  String AM_PM = time.substring(time.indexOf(" ") + 1);
  if(AM_PM.equals("PM") && hour != 12){
    hour += 12; 
  }
  if(AM_PM.equals("AM") && hour == 12){
    hour -= 12;
  }
  if (hour >= 6 && hour < 18){
    return true;
  }
  return false;
}



/* A helper method to plot all existing points up to the current data points */
public void drawAllPoints(boolean avgDistDraw, boolean avgTimeDraw){
  //  We have no points to draw if both storage devices are empty 
  if(xCoords.isEmpty() || yCoords.isEmpty()){
    return;
  }
  else {
    //  Create copies so we don't destroy the actual points we need to save
    Stack<Float> copyX = (Stack<Float>) xCoords.clone();
    Stack<Float> copyY = (Stack<Float>) yCoords.clone();
    float totalAverage = 0.0;
    float totalAverageTwo = 0.0;
      if(avgDistDraw && !avgTimeDraw){
        // We need an average distance
        int totalNumOfDistances = distanceStack.size();
        totalNumOfDistances++;
        Stack<Float> timesCopy = (Stack<Float>) timeStack.clone();
        Stack<Float> distancesCopy = (Stack<Float>) distanceStack.clone();
        while(!distancesCopy.isEmpty()){
         float distance = distancesCopy.pop();
         totalAverage += distance; 
         float time = timesCopy.pop();
         if(time != 0){ // Quick way to prevent divide by 0s
           totalAverageTwo += (distance / (time / 60.0)); 
         }
        }
        totalAverage /= totalNumOfDistances;
        totalAverageTwo /= totalNumOfDistances;
      }
      
        
      
      
      else if(avgTimeDraw && !avgDistDraw){
        Stack<Float> timesCopy = (Stack<Float>) timeStack.clone();
        int totalNumOfTimes = timesCopy.size();
        while(!timesCopy.isEmpty()){
          totalAverage += timesCopy.pop();
        }
        totalAverage /= totalNumOfTimes;
        totalAverageTwo = timeStack.size();
      }
        
      
    //  While we still have points to show
    while(!copyX.isEmpty()){
      //  Grab start and end points
      float tempX1 = copyX.pop();
      float tempY1 = copyY.pop();
      float tempX2 = copyX.pop();
      float tempY2 = copyY.pop();
      
   

      if(avgDistDraw){
        strokeWeight(2);
        //  Set the color of the line to white
        stroke(color(colorOfDay, colorOfDay, colorOfDay, LINE_ALPHA_VALUE));
        line(tempX1, tempY1, tempX2, tempY2);
        // Set the circle of the beginning point to green
        stroke(color(0, 255, 0));
        fill(color(0, 255, 0));
        circle(tempX1, tempY1, 10);
        //  Set the circle of the end point to red
        stroke(color(255, 0, 0));
        fill(color(255, 0, 0));
        circle(tempX2, tempY2, 10);
      }
      else if(!avgDistDraw && avgTimeDraw)  {
        stroke(255, 0, 0, ALPHA_VALUE);
        fill(255, 0, 0, ALPHA_VALUE);
        circle(tempX1, tempY1, RADIUS_VALUE);
        //  Set the circle of the end point to red
        stroke(255, 0, 0, ALPHA_VALUE);
        fill(255, 0, 0, ALPHA_VALUE);
        circle(tempX2, tempY2, RADIUS_VALUE);
      }
      else if(!avgDistDraw && !avgTimeDraw){
        strokeWeight(2);
        //  Set the color of the line to white
        stroke(color(colorOfDay, colorOfDay, colorOfDay));
        // Set the circle of the beginning point to green
        stroke(color(0, 255, 0));
        fill(color(0, 255, 0));
        circle(tempX1, tempY1, 10);
        //  Set the circle of the end point to red
        stroke(color(255, 0, 0));
        fill(color(255, 0, 0));
        circle(tempX2, tempY2, 10);
      }
    }
    stroke(colorOfDay, colorOfDay, colorOfDay);
    fill(colorOfDay, colorOfDay, colorOfDay);
    textSize(26); 
    
  
     // Need to do some string manip to make our average distance look a little cleaner
      String avgString = totalAverage + "";
      String avgStringTwo = totalAverageTwo + "";
      int splitIndex = avgString.indexOf(".");
      // Some lengths do not have anything after the decimal, we account for that here
      if(splitIndex + 5 < avgString.length() && splitIndex != -1) {
        avgString = avgString.substring(0, splitIndex + 1) + avgString.substring(splitIndex + 1, splitIndex + 5);
      }
    String avgDistance = "";
    String otherMetric = "";
    if(avgDistDraw && !avgTimeDraw){
      avgDistance = "Average Distance: \n"  + avgString + " Miles";
      otherMetric = "Average Speed: \n"  + avgStringTwo + " Miles/Hr";
    }
    else if(avgTimeDraw && !avgDistDraw){
       avgDistance = "Average Time: \n"  + avgString + " Minutes";
       otherMetric = "Number of Rides: \n"  + (int) Float.parseFloat(avgStringTwo);
    }
    text(avgDistance, width/4 * 3 - 40, 50); 
    text(otherMetric, width/4 - 150, height/4 * 3 - 80);
    currentAverage = Float.parseFloat(avgString);
    currentAverage2 = Float.parseFloat(avgStringTwo);
  }
}

//  Generates a random y in our range of plausible values
public float generateRandomYVal(){
   return (float)(MIN_Y + (Math.random() * (MAX_Y - MIN_Y)));
}
//  Generate a random x in our range of plausible values
public float generateRandomXVal(){
  return (float)(MIN_X + (Math.random() * (MAX_X - MIN_X)));
}

/* A helper method to determine distance */
public float distance(float x1, float x2, float y1, float y2){
  // There are about 68.703 miles in 1 degree of longitude/latitude
  return sqrt(pow((y2 - y1), 2) + pow((x2 - x1), 2)) *  68.703;
}
public String[] convertLineData(String line){
  // Text is coming in as comma seperated, we need to split based on commas
  String[] lineSplit = split(line, ',');
  //  We need 5 vals: x1, y1, x2, y2, time
  String[] importantVals = new String[8];
  // Grab the 2 x,y value pairs
  importantVals[0] = lineSplit[1];
  importantVals[1] = lineSplit[2];
  importantVals[2] = lineSplit[3];
  importantVals[3] = lineSplit[4];
  // Chop off the time from the date-time data
  String time = lineSplit[5].substring(lineSplit[5].indexOf("T") + 1);
  // Convert the time String to PM/AM
  String currentTime = convertTimeToStandard(time);
  
  //  Save the final needed value
  importantVals[4] = currentTime;
  String endTime = "" + convertTimeToHours(convertTimeToStandard(lineSplit[6].substring(lineSplit[6].indexOf(" ") + 1)));
  importantVals[5] = endTime;
  importantVals[6] = "" + ((Float.parseFloat(lineSplit[7]))/ 60.0);
  importantVals[7] = lineSplit[8];
  
  return importantVals;
}
public String convertTimeToStandard(String input){
   // Split our time up into usable components
   String[] splitInput = split(input, ':');
   // Grab our hour amount
   int hours = Integer.parseInt(splitInput[0]);
   // Grab our minute amount
   int minutes = Integer.parseInt(splitInput[1]);
   // Determine if it is AM or PM and change the display time accordingly
   String AM_PM = "";
   if (hours < 12){
     AM_PM = " AM";
   }
   else {
     AM_PM = " PM";
     // Time is in military time
     hours -= 12;
   }
   if(minutes == 0){
     if(hours != 0){
       return (hours + ":" + "00" + AM_PM); 
     }
     else {
       return ("12:00" + AM_PM);
     }
   }
   if(hours == 0){
       return ("12:" + minutes + AM_PM);
   }
   //  This is our desired Display
   return (hours + ":" + minutes + AM_PM); 

}
public float convertTimeToHours(String time){
  int indexOfColon = time.indexOf(":");
  int indexOfSpace = time.indexOf(" ");
  float totalTime = Float.parseFloat(time.substring(0, indexOfColon)) + Float.parseFloat("0." +time.substring(indexOfColon + 1, indexOfSpace));
  if(time.substring(indexOfSpace + 1).equals("PM")){
    totalTime += 12;
  }
  return totalTime;
}

public class Point {
   float x;
   float y;
   public Point(float x, float y){
     this.x = x; 
     this.y = y;
   }
}
