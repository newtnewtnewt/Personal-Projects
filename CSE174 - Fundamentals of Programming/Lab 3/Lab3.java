/* Name: Noah Dunn
 * Class: CSE 174 Section: I
 * Instructor: Dr. Alberto-Castro Hernandez
 * Date: September 15th, 2017
 * Assignment: Lab3: Take a name and 3 numbers as input and solve for 
 * the average, maximum, minimum, median.
 * */

import java.util.Scanner;

public class Lab3{
  
  public static void main(String[] args){
    
    System.out.println("Enter the firstname and lastname:");
     
    Scanner nameScanner = new Scanner(System.in);  //Initialize Scanner 
    
    String firstAndLast = nameScanner.nextLine().toUpperCase(); //Input the name and Capitalize it 
    
    System.out.println("Enter 3 scores :");
    
    double firstScore  = nameScanner.nextDouble();    //Get scores
    double secondScore = nameScanner.nextDouble();
    double thirdScore = nameScanner.nextDouble();
    
    double total = firstScore + secondScore + thirdScore; //total it 
    
    double average = total/3.0;  //average them
    
    double maximum = Math.max((Math.max(firstScore, secondScore)),thirdScore); //find the maximum
    
    double minimum =Math.min( Math.min(firstScore, secondScore), thirdScore);  //find the minimum
    

    
    double median = total = total - (maximum + minimum);
    
    //print all the data
    System.out.printf("Name :" + firstAndLast +"%n");  
    System.out.printf("Average = %.2f.%n", average);
    System.out.printf("Maximum = %.2f.%n", maximum);
    System.out.printf("Minimum = %.2f.%n", minimum);
    System.out.printf("Median  = %.2f.%n", median );
    
    
    
    
  } //End method
}   //End class