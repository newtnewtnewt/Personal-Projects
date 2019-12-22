/* Name: Noah Dunn
 * Class: CSE 174 Section I
 * Instructor: Dr. Alberto-Castro Hernandez 
 * Date: 9/26/2017
 * Description: Create a weather algorithm that can tell the season based on month/day

Test Case (month, day)    Expected     Actual
9, 25                      Fall         Fall
9, 20                      Summer       Summer
12, 25                     Winter       Winter
2, 25                      Winter       Winter 
4, 12                      Spring       Spring
6, 25                      Summer       Summer 


 */ 

import java.util.Scanner;

public class SeasonFinder{
   
   public static void main(String[] args){
      
   Scanner input = new Scanner(System.in);
 
   System.out.println("Enter a month: ");
                         
   
   int month = input.nextInt();
   String season = "";
   
   System.out.println("Enter a day: ");
                      
   int day = input.nextInt();
   
   if(month == 1 || month == 2 || month == 3){
      
      season = "Winter";
      
   }
      
  else if(month == 4 || month == 5 || month == 6){
   
     season = "Spring";
   
  }
 
  else if(month == 7 || month == 8 || month == 9){
   
    season = "Summer";
    
    
  }
  
  
  else if( month == 10 || month == 11 || month == 12){
     season = "Fall";
        
  }
  else{
     season = "Winter"; 
  }
  
 if (month % 3 == 0 && day >= 21){
     if( season.equals("Winter")){
        season = "Spring";
     }
     
     else if( season.equals("Spring")){
        season = "Summer";
     }
     
     else if( season.equals("Summer")){
        season = "Fall";
     }
     
     else {
        season = "Winter";
     }
  }
  
  System.out.println(season);
  
  }
}
     
  
  
  