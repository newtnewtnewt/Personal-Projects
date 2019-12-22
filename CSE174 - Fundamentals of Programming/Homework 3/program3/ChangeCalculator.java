/* Name: Noah Dunn
 * Instructor: Dr. Alberto-Castro Hernandez
 * Class: CSE 174 Section I
 * Date: 9/12/2017
 * Assignment: Calculate the amount of weights needed to be
 * put on a scale 
 * */


import java.util.Scanner;

public class ChangeCalculator {
   
   public static void main(String args[]){
      
     Scanner sc = new Scanner(System.in);
     System.out.println("Enter the weight in grams");
     
     int weight = sc.nextInt();
     
     System.out.println("Denominations of " + weight);
     System.out.println("-----------------");
     //Gather all gram weights
     int w_100 = (weight / 100); 
     int w_50  = (weight % 100) / 50;
     int w_10  = (((weight % 100) % 50) / 10); 
     int w_5   = ((((weight % 100) % 50) % 10) / 5); 
     int w_2   = ((((weight % 100) % 50) % 10) % 5) / 2;  
     int w_1   = (((((weight % 100) % 50) % 10) % 5) % 2)/1; 
     
     //Print gram weights 
     System.out.println("|100  gm |       " + w_100); 
     System.out.println("| 50  gm |       " + w_50);
     System.out.println("| 10  gm |       " + w_10);   
     System.out.println("|  5  gm |       " + w_5);
     System.out.println("|  2  gm |       " + w_2);
     System.out.println("|  1  gm |       " + w_1);
                   
   }  // main method end
}     // class end
     
     
     
      