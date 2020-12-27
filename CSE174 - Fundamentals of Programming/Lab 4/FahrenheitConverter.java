/* Name: Noah Dunn
 * Instructor: Dr. Alberto-Castro Hernandez
 * Class: CSE174 Section I
 * Date: September 22, 2017 
 * Assignment: Lab 4 
 * */


import java.util.Scanner; 


public class FahrenheitConverter{
  public static void main(String[] main){
    
    Scanner temp = new Scanner(System.in);
    
    System.out.print("Enter Fahrenheit temperature:");
    double fahrenheitTemp = temp.nextDouble();
    double celciusTemp = (fahrenheitTemp - 32)*(5.0/9.0);
    String state = "";
    if(celciusTemp <= 0){
      state = "Solid";
    }  
    
    if(celciusTemp >= 100){
      state = "Gas";
    }
    
    if(celciusTemp >0 && celciusTemp <100){
      state = "Liquid";
    }
    
    System.out.printf("%.3f Fahrenheit = %.3f Celcius %nWater is %s %n", fahrenheitTemp, celciusTemp, state);

                        
  }//end class 

}// end main method 