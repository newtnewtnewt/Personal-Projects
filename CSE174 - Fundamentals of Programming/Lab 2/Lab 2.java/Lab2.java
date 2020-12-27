/* Name: Noah Dunn
 * Instructor: Dr. Alberto-Castro Hernandez 
 * Section: I
 * Date: 9/8/2017
 * Assignment: Lab 2 Part 2, Create a tool
 * that takes two radii and calculates
 * surface area and volume */

import java.util.Scanner;

public class Lab2{

  public static void main(String args[]){
    
    
    Scanner sc = new Scanner(System.in);  //Initialize Scanner 
    
    System.out.println("Enter the sphere's radius:");
    
    double radius = sc.nextDouble(); //Call for user input 
    
    //Establish variables and formulas 
    double fourThirds = 4/3;      
    double sA = 4 * Math.PI * Math.pow(radius, 2); 
    double v  = (fourThirds) * Math.PI * Math.pow(radius, 3);      
    
    //Print the results
    System.out.println("surface area = " + sA);
    System.out.println("volume = " + v);
    
    System.out.println("Enter a new radius:"); 
    
    //Repeat 
    radius = sc.nextFloat();
    sA = 4 * Math.PI * Math.pow(radius, 2); 
    v  = (fourThirds) * Math.PI * Math.pow(radius, 3);
    
    System.out.println("surface area = " + sA);
    System.out.println("volume = " + v);
    
    
    
   
    
  } //End main method
}// end Class