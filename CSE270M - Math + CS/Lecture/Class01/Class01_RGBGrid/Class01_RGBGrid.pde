int b = 0;

void setup(){
 size(256, 256); 
}
//Biodigital Jazz Man
void draw(){
   for(int r = 0; r < 256; r++){
     for(int g = 0; g < 256; g++){
       set(r, g, color(r,g,b));
       //stroke(r,g,0);
       //point(r,g);
     } 
   }
   b = (b + 1) % 256;
}
