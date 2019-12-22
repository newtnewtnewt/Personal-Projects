/* Name: Noah Dunn
 * Class: CSE 174 Section I
 * Instructor: Dr. Alberto-Castro Hernandez 
 * Date: 9/19/2017
 * Description: Convert a ten digit phone number into a more readable
 * format
 * 
 * */

import java.util.Scanner;
    
public class PhoneParser{
   
   public static void main(String[] args){
      
      Scanner number = new Scanner(System.in);
      
      System.out.print("Insert your 10 digit phone number!");
      
      String fullNumber = number.next();
      
      String firstThree = fullNumber.substring(0,3);
      
      String midThree   = fullNumber.substring(3,6);
      
      String lastFour   = fullNumber.substring(6);
      
      System.out.printf("Your formatted number is (%s) %s-%s %n", firstThree, midThree, lastFour);
      
      
   
   
   } //end main method
} //end class 
     