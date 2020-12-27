/* Name: Noah Dunn
 * Instructor: Dr. Alberto-Castro Hernandez
 * Class: CSE 174 Section I
 * Date: 9/12/2017
 * Assignment: Multiply two complex numbers together
 * */


import java.util.Scanner;

public class ComplexNumberPdt{
   
   public static void main(String[] args){
      
      Scanner sc = new Scanner(System.in);
         
      // Gather the numbers
      System.out.print("***Complex Number 1: \nEnter the real and imaginary parts:");
      
      double num1 = sc.nextDouble();
      double num2 = sc.nextDouble();
      
      System.out.print("***Complex Number 2: \nEnter the real and imaginary parts:");
      
      double num3 = sc.nextDouble();
      double num4 = sc.nextDouble();
     
      
      System.out.printf("Number 1 = (%.1f + %.2f)%n", num1, num2);
      
      System.out.printf("Number 2 = (%.1f + %.2f)%n", num3, num4);
      
     // Multiply them together
      double multNum1 = (num1 * num3) + -(num2 * num4);
      double multNum2 = (num1 * num4) + (num2 * num3); 
      
      // Display the product to two decimal places
      System.out.printf("Product  = ( %.2f + %.2f", multNum1, multNum2); 
      System.out.println("i )");
 }// End main method
} // End class        
      
      