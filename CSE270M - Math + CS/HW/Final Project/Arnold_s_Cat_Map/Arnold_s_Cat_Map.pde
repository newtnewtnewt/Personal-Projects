/*
*  Noah Dunn
 *  Instructor: Norm Krumpe
 *  Date: 11/12/2019
 *  Class: CSE270M 
 *  Assignment: Arnold's Cat Map, Final Project
 */
PImage originalPic;
PImage currentIteration;
CatMap[] allImages = new CatMap[8];
int picWidth = 250;
int picHeight = 250;
int framerate = 1;
String[] fileNames = {"baxter.jpg", "freddie.jpg", "javier.jpg", "josh.jpg", "kluber.jpg", "skyler.jpg", "sunset.jpg", "theCrew.jpg"};
boolean randomizedPictures = false;
boolean zoomedIn = false;
boolean menuEnabled = true;
int zoomedImage = 0;
int imageMaxLength = 400;
int imageMinLength = 100;

void settings(){
  size((picWidth * 4), (picHeight * 2));
}
void setup() {  
  surface.setResizable(true);
  setupHelper();
}

void draw() {
  frameRate(framerate);
  for(int i = 0; i < allImages.length; i++){
    allImages[i].displayStep();
  }
  textSize(20);
  if(menuEnabled){
    text("Hit B to toggle blown out TV Screen Mode", width/2 - width/8,  height/2 - height/6  );
    text("Left Click to Zoom on an image, right click to back out",  width/2 - width/8,  height/2 - height/8);
    text("Current Frame Rate: " + framerate, width/2 - width/8, height/2 - height/12);
    text("Current Width/Height: " + picHeight, width/2 - width/8, height/2 - height/32);
    text("Try Hitting Your Arrow Keys R-L-U-D", width/2 - width/8, height/2 + height/32);
    text("Hit O to toggle menu", width/2 - width/8, height/2 + height/24);
  }
}

void setupHelper(){
    if(!zoomedIn){
      surface.setSize((picWidth * 4), (picHeight * 2));
      allImages = new CatMap[8];
      for(int i = 0; i < fileNames.length; i++){
          if(!randomizedPictures){
            originalPic = loadImage(fileNames[i]);  
          }
          else {
            originalPic = generateRandomImage();
          } 
          originalPic.resize(picWidth, picHeight); 
          image(originalPic, i % 4 * picWidth, i / 4 * picHeight); 
          CatMap imageMap = new CatMap(originalPic, i % 4 * picWidth, i / 4 * picHeight);
          allImages[i] = imageMap;
        }
    }
    else {
      surface.setSize(picHeight, picWidth);
      allImages = new CatMap[1];
      PImage loadImage;
      if(!randomizedPictures){
            loadImage = loadImage(fileNames[zoomedImage]);  
          }
          else {
            loadImage = generateRandomImage();
      } 
      loadImage.resize(picWidth, picHeight);
      allImages[0] = new CatMap(loadImage, 0, 0);
      surface.setSize(picWidth, picHeight);
      zoomedIn = true;
    }
  }
PImage generateRandomImage(){
  PImage customImage = new PImage(picWidth, picHeight);
  for(int i = 0; i < customImage.pixels.length; i++){
    customImage.pixels[i] = color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
  }
  return customImage;
}

/*
*  This method activates when we press any key, but
*  we really only care when any of the arrow keys or the 
*  B key is pressed. 
*/
void keyPressed() {
  if (key == CODED) {     //  If the key is in processing's default codes
    if (keyCode == UP) {  //  If the keyPress is the up key
       if(!(framerate > 60)){
         framerate += 10;
       }
    } else if (keyCode == DOWN) {
      if (framerate != 1) { //  If we aren't at a Bailout of 1 and the user presses the DOWN key, decrement the BAILOUT value
        framerate -= 10;
      }
    } else if(keyCode == LEFT) {
      if(picWidth > imageMinLength){
        picWidth  -= 50;
        picHeight -= 50;
        setupHelper();
      }
    } else if(keyCode == RIGHT) {
      if(picWidth < imageMaxLength){
        picHeight += 50;
        picWidth  += 50; 
        setupHelper();
      }
    } 
  }
  if(key == 'B' || key == 'b') {
      randomizedPictures = !randomizedPictures;
      setupHelper();
  }
  if(key == 'o' || key == 'O'){
     menuEnabled = !menuEnabled;
  }
}

void mouseClicked() {
  //  Grab our mouse positions
  int x = mouseX;
  int y = mouseY;
  //  Map them to the real + imaginary parts of an imaginary number
  if (mouseButton == LEFT) {
    zoomedImage = mouseX / picWidth + (mouseY / picHeight * 4);
    zoomedIn = true;
    picWidth = 400;
    picHeight = 400;
    imageMaxLength = 800;
    setupHelper();
    
  } else if (mouseButton == RIGHT) {
    imageMaxLength = 400;
    zoomedIn = false;
    picWidth = 250;
    picHeight = 250;
    setupHelper();
  }
}



public class CatMap {
  private PImage orgImage;
  private PImage currImage;
  private int count;
  private int xLocation;
  private int yLocation;
  private boolean isFinished = false;
  
  public CatMap(PImage org, int x, int y){
    orgImage = org;
    count = 0; 
    currImage = orgImage;
    xLocation = x;
    yLocation = y;
  }
  
  void catMapFunction(){
    PImage tempImage =  new PImage(currImage.width, currImage.height);
    for(int i = 0; i < currImage.pixels.length; i++){
      int x = i % currImage.width;
      int y = i / currImage.width;
      int newX = (2 * x + y) % currImage.width;
      int newY = (x + y) % currImage.height;
      tempImage.pixels[newY * currImage.width + newX] = currImage.pixels[i];
    }
    currImage = tempImage;
  }
  
  boolean isFinished(){
  for(int i = 0; i < orgImage.pixels.length; i++){
    if(orgImage.pixels[i] != currImage.pixels[i]){
      return false;    
    }
  }
  return true;
  }
  
  void displayStep(){
    image(currImage, xLocation, yLocation);
    textSize(30);  //  Set the size of our text to 15
    text(" " + count, xLocation + 10, yLocation + 40);
    if(!isFinished && count != 0 && isFinished()){
      isFinished = true;
    }
    if(!isFinished){
        catMapFunction();
        count++;
    }
    
  }
  
  
  
  


}
