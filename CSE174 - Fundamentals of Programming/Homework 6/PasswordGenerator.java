/*Name: Noah Dunn
 *Instructor: Dr. Alberto-Castro Hernandez 
 *Class: CSE 174 Section I
 *Date: 10/3/2017
 *Assignment: Create a Password Generator that accepts multiplication problem answers
 * in order to encrypt a password.
 * */
import java.util.Scanner;

public class PasswordGenerator{
   
   public static void main(String[] args){
      
      int pass1 = 0;
      int pass2 = 0;
      int pass3 = 0;
      int pass4 = 0;
      
      System.out.println("---Welcome to Password Generator---");
      System.out.print("Enter the Mulitplication Limit: ");
      Scanner input = new Scanner(System.in);
     
      
      int multLimit = input.nextInt();
      long startTime = System.currentTimeMillis();
      System.out.println("The timer starts...now!");
      
      boolean exitLoop = true;
      int numOne = 0;
      int numTwo = 0;
      
   //Evaluate First Quiz   
      while(exitLoop){
         numOne= 1 + (int) (Math.random() * (multLimit));
         numTwo= 1 + (int) (Math.random() * (multLimit));
         if(numOne * numTwo <= multLimit){
            exitLoop = false;
         }
      }
      int product1 = numOne * numTwo;     
      exitLoop = true;
      
     
     
      System.out.print("***Quiz 1\n" + numOne + " x " + numTwo + "= ");
      int answer1 = input.nextInt();
      
      boolean wrongAnswer = false;
      
      if(answer1 == product1 && wrongAnswer == false){
         pass1 = answer1 % 28 + 63;
      }
      
      else if(answer1 != product1 && wrongAnswer == false){
         System.out.println("Wrong Answer\n\nPassword Generation failed!!"); 
         wrongAnswer = true;
      }
      
     
   //Evaluate Second Quiz 
     while(exitLoop){
         numOne= 1 + (int) (Math.random() * (multLimit));
         numTwo= 1 + (int) (Math.random() * (multLimit));
         if(numOne * numTwo <= multLimit){
            exitLoop = false;
         }
      }
      
      product1 = numOne * numTwo;
      exitLoop = true;
      if(wrongAnswer == false){
      System.out.print("***Quiz 2\n" + numOne + " x " + numTwo + "= ");
      answer1 = input.nextInt();
      }
      
      
      if(answer1 == product1 && wrongAnswer == false){
         pass2 = answer1 % 28 + 63;
      }
      
      else if(answer1 != product1 && wrongAnswer == false){
         System.out.println("Wrong Answer\n\nPassword Generation failed!!"); 
         wrongAnswer = true;
         
      }
     
    //Evaluate Third Quiz   
     while(exitLoop){
         numOne= 1 + (int) (Math.random() * (multLimit));
         numTwo= 1 + (int) (Math.random() * (multLimit));
         if(numOne * numTwo <= multLimit){
            exitLoop = false;
         }
      }
      
      product1 = numOne * numTwo;
      exitLoop = true;
      
      if(wrongAnswer == false){
      System.out.print("***Quiz 3\n" + numOne + " x " + numTwo + "= ");
      answer1 = input.nextInt();
      }
      
      
      if(answer1 == product1 && wrongAnswer == false){
         pass3 = answer1 % 28 + 63;
      }
      
      else if(answer1 != product1 && wrongAnswer == false){
         System.out.println("Wrong Answer\n\nPassword Generation failed!!"); 
         wrongAnswer = true;   
      }
      
      //Evaluate 4th Quiz 
      
      while(exitLoop){
         numOne= 1 + (int) (Math.random() * (multLimit));
         numTwo= 1 + (int) (Math.random() * (multLimit));
         if(numOne * numTwo <= multLimit){
            exitLoop = false;
         }
      }
      
      product1 = numOne * numTwo;
      exitLoop = true;
      
      if(wrongAnswer == false){
      System.out.print("***Quiz 4\n" + numOne + " x " + numTwo + "= ");
      answer1 = input.nextInt();
      }
      
      if(answer1 == product1 && wrongAnswer == false){
         pass4 = answer1 % 28 + 63;
      }
      
      else if(answer1 != product1 && wrongAnswer == false){
         System.out.println("Wrong Answer\n\nPassword Generation failed!!"); 
         wrongAnswer = true;   
      }
 
      long stopTime = System.currentTimeMillis();
      long totalTimeMillis = stopTime - startTime;
      int totalTimeSeconds = (int) (totalTimeMillis/1000.0);
      
      System.out.println("The timer stops...now! You answered in " + totalTimeSeconds + " seconds.");
      
      String firstChar = Character.toString((char)pass1);
      String secondChar = Character.toString((char)pass2);
      String thirdChar = Character.toString((char)pass3);
      String fourthChar = Character.toString((char)pass4);
      
      System.out.println("--Your Password: " + firstChar + secondChar + thirdChar + fourthChar);
      
      int strength = 50; 
      
      
      if(firstChar.equals("?") || firstChar.equals("@") || secondChar.equals("?") || secondChar.equals("@") ||
         thirdChar.equals("?") || thirdChar.equals("@") || fourthChar.equals("?") || fourthChar.equals("@"))
      {
         strength = strength + 20;
      }
      
      if((firstChar.equals(secondChar) && secondChar.equals(thirdChar)) || (secondChar.equals(thirdChar) && 
         thirdChar.equals(fourthChar)))
            {
         strength = strength - 40;
      }
         
     System.out.println("Strength of your password: " + strength);
     
     if(strength > 50){
        System.out.println("Very Strong!");
     }
     
     else if(strength == 50){
        System.out.println("Strong!");
       
     }
        
     else if(strength < 50 && strength > 29){
        System.out.println("Moderate!");  
     }
     
     else
     {
        System.out.println("Weak!");
     }
     
    
   
   
   } //end main method
   
}//end class Password Generator 