/* Name: Noah Dunn
 * Instructor: Dr. Alberto-Castro Hernandez
 * Class: CSE 174 Section I
 * Date: 9/12/2017
 * Assignment: Calculate the number of boxes needed to ship
 * X amount of widgets
 * Test Cases: WidgetShipper
   These work correctly: 0, 1, 22, 23, 24, 46, 50, 69, 200, 222
   Given the values tested, I believe my code will satisfy all values within
   the domain
 */

import java.util.Scanner;

public class WidgetShipper{
   
   public static void main(String[] args){
      
      Scanner widgetScanner = new Scanner(System.in);
      
      final int EMPTY_BOX = 23;
      
      System.out.println("How many widgets will be shipped today?");
      
      int widgets = widgetScanner.nextInt();
      int fullBoxes = widgets / EMPTY_BOX;
      int partialBoxes = ((widgets % EMPTY_BOX) + 22)/ 23;
      int totalBoxes = fullBoxes + partialBoxes;
      
      System.out.println("Number of Boxes needed = " + totalBoxes);
       
         
      
         
   
   } // End main method
   
}  // End class

  