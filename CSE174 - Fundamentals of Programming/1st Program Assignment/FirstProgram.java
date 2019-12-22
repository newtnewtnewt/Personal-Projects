// Name: Noah Dunn
// Instructor: Dr. Alberto Castro-Hernandez 
// CSE 174, Section I
// Date: 8/31/2017
// Filename: FirstProgram.java 
// Description: Practice with writing, saving, 
// and compiling code


import java.util.Scanner; // We need this for user input

// Demonstrates some basic programming concepts by
// getting information from the keyboard, displaying
// results to the screen, and playing an interactive 
// game with the user
public class FirstProgram{
   
   // The starting point for the program. This method 
   //calls on the other three methods as needed
   public static void main(String[] args) {
      
      // Declaring local variables for later use
      
      String firstName, lastName;
      int goalNumber, userGuess, countGuesses;
      int triangleHeight;
      Scanner keyboardReader = new Scanner(System.in);
      
      // Get user's name 
      System.out.print("Enter first and last name: ");
      firstName = keyboardReader.next();
      lastName = keyboardReader.next();
      
      //Display a marquee with a personal greeting
      printBorder('#', 40);
      greet(firstName + " " + lastName);
      printBorder('#', 40);
      
      // Explain how to play the game
      explainGame(firstName);
      
      // Set up the game
      goalNumber = (int)(1 + 100 * Math.random());
      countGuesses = 1;
      
      // Get first guess
      printBorder('#', 40);
      System.out.print("Enter guess #" + countGuesses + ": ");
      userGuess = keyboardReader.nextInt();
      
      //Loop until the guess is correct
      while (userGuess != goalNumber){
         
         // Give advice 
         if (userGuess < goalNumber) {
            System.out.println("Too low. Guess higher. ");
         }
         
         else{
            System.out.println("Too high. Guess lower.");
            
         }
         
         countGuesses = countGuesses + 1 ;
         
         System.out.print("Enter guess #" + countGuesses + ": ");
         userGuess= keyboardReader.nextInt();
         
      } // end loop
        
      // At this point, we know the user made the right guess
      // Print an appropriate message 
      
      // If the user guessed in under 7 times, congratulate
      if (countGuesses < 7) {
         System.out.print("Good job " + firstName + ", ");
         System.out.println("you got it in " + countGuesses + " tries.");
      }
      
      // Otherwise inform them that they did not win
      else {
         System.out.print("OK " + firstName + ", ");
         System.out.println("you got it, but it took too many tries.");
      }
      
      // Some artwork:
      printBorder('#', 40);
      System.out.println("And now, a triangle of money!");
      triangleHeight = (int) (1 + 30 * Math.random());
      
      // Display a triangle
      for (int row = 1; row <= triangleHeight; row++){
         printBorder('\u00A2', row );
         
      }
     
      
      
      
   } // end main method
   
   //Prints a personalized welcome message
   public static void greet(String name) {
      System.out.println("Hello " + name + ",");
      System.out.println("Welcome to my first CSE 174 program!");
      System.out.println("Enjoy the show!");
      System.out.println(" Sincerely, \n Noah Dunn");
                      
                            
      
   } // end greet method 
   
   //Prints a personalized game introduction
   public static void explainGame(String name) {
      System.out.println("Let's play a game, " + name + "...");
      System.out.println("I'm thinking of a number from 1 to 100.");
      System.out.println("See if you can guess it in fewer than 7 tries.");
   } //end explainGame method
   
   // Prints a border by repeating the specifed symbol 
   public static void printBorder(char symbol, int size) {
      for (int i = 1; i <= size; i++){
         System.out.print(symbol);
         
         
      }
      
      System.out.println(); //moves to the next line
   } // end printBorder method
   
} // end class