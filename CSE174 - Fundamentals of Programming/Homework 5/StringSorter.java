/* Name: Noah Dunn
 * Class: CSE 174 Section I
 * Instructor: Dr. Alberto-Castro Hernandez 
 * Date: 9/26/2017
 * Description: Read three strings inputted by a user and then 
 * 
 Test Case(word word word)        Expected Ouput           Actual
 neWTS aRE rad                    Are Newts Rad            Are Newts Rad
 Some Cool Chickens               Chickens Cool Some       Chickens Cool Some 
 Why Are Chickens                 Are Chickens Why         Are Chickens Why 
 Rocket      Powered  Beaver      Beaver Powered Rocket    Beaver Powered Rocket
 Slime    Slippery     Slug       Slime Slippery Slug      Slime Slippery Slug 
 */ 

import java.util.Scanner;

public class StringSorter{
   
   public static void main(String[] args){
      
      Scanner keyboard = new Scanner(System.in);
      
      System.out.print("Enter the three names:");
      
      //Take lowercase input in one line
      String originalNames = keyboard.nextLine().toLowerCase(); //Take 3 names in 
      
      //Extract the first word
      int firstBlank = originalNames.indexOf(' ');
      String firstWord = originalNames.substring(0, firstBlank);
      firstWord = firstWord.substring(0, 1).toUpperCase() + firstWord.substring(1);
      int i = firstBlank;
     
      
      boolean blankHit = true;
      char n = originalNames.charAt(i) ;
      int secondStart = 0;
      
      //Determine the start of the second word
      while(i < originalNames.length() && blankHit){
         n = originalNames.charAt(i);
         
         if(n == (' ')){
            i++;
         }
         
        else{
            secondStart = i;
            blankHit = false;
        }
      }
     
     //Determine the end of the second word
     int thirdBlank = originalNames.indexOf(' ', secondStart);
     
     String secondWord = originalNames.substring(secondStart, thirdBlank);
     secondWord = secondWord.substring(0,1).toUpperCase() + secondWord.substring(1);
     
     //Obtain the start of the third word
     i = thirdBlank; 
     int thirdStart = 0;
     blankHit = true;
     
     
     while(i < originalNames.length() && blankHit){
         n = originalNames.charAt(i);
         if(n == (' ')){
            i++;
         }
         
        else{
            
            thirdStart = i;
            blankHit = false;
        }
      }
     int thirdFinish = originalNames.lastIndexOf(' ');
     
     
     
     String thirdWord = originalNames.substring(thirdStart, thirdFinish);
     thirdWord = thirdWord.substring(0,1).toUpperCase() + thirdWord.substring(1);
 
  //Determine the order of the words in lexographical order
    
  String item1 = " ";
  String item2 = " ";
  String item3 = " ";
  
  
 if(firstWord.compareTo(secondWord) <= 0 && firstWord.compareTo(thirdWord) <= 0){
    
    if(secondWord.compareTo(thirdWord) <= 0){
    
       item1 = firstWord;
       item2 = secondWord;
       item3 = thirdWord;
    
    
    }
    
    else{
    
       item1 = firstWord;
       item2 = thirdWord;
       item3 = secondWord;
    }
 }
    
    
    
    if(firstWord.compareTo(secondWord) >= 0 && firstWord.compareTo(thirdWord) <= 0){ 
    
       item1 = secondWord;
       item2 = firstWord;
       item3 = thirdWord;
    
    }
    
    if(firstWord.compareTo(secondWord) <= 0 && firstWord.compareTo(thirdWord) > 0){ // first word is smaller than third but bigger than second
    
       item1 = thirdWord ;
       item2 = firstWord;
       item3 = secondWord;
    
    }
    
    if(firstWord.compareTo(secondWord) >= 0 && firstWord.compareTo(thirdWord) >= 0){   
    
       if(secondWord.compareTo(thirdWord) <= 0){
    
    
    item1 = secondWord;
    item2 = thirdWord;
    item3 = firstWord;
    
       }
    
       else{
    
    
          item1 = thirdWord ;
          item2 = secondWord;
          item3 = firstWord;
       }
    
    }
    
    
    
    
    System.out.println("The 3 names are : " + firstWord + " " + secondWord + " " + thirdWord);
    
    System.out.println("***In sorted order: " + item1 + " " + item2 + " " + item3);
    
   
   
   
   
   } // end of method
}
// end of class
      
        