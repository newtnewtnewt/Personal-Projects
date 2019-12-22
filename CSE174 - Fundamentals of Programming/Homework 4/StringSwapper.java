/* Name: Noah Dunn
 * Class: CSE 174 Section I
 * Instructor: Dr. Alberto-Castro Hernandez 
 * Date: 9/19/2017
 * Description: Create a program that swaps two letters in a word
 * 
 * Under the normal circumsances of the pseudocode provided, this code
 * would return a logic error due to middle being defined as i + 1 to j - 1 
 * In order to obtain the correct results, the j - 1  must be just j due to 
 * substrings pulling everything UP to the final digit listed.
 * 
 * 
 * Test Cases: 
 * butterfly(2,4) = buettrfly (Correct)
 * Gateway(2,4)   = Gawetay   (Correct)
 * newt(0,3)      = tewn      (Correct)
 * monster(3,5)   = monetsr   (Correct)            
   oranges(2,3)   = ornages   (Correct)
   
   cellular(5, 3) = Error
   toaster(1, 1)  = Error 
   
   salesman(0, 7) = nalesmas (Correct)
   doomsday(3, 6) = dooasdmy (Correct)
   lucrative(0, 4) = aucrltive (Correct)
   
   
   
   
   
 */

import java.util.Scanner;

   public class StringSwapper{
   
      public static void main(String args[]){
         
         Scanner stringScanner = new Scanner(System.in);
       
         //Grab user input for word and locations
         System.out.print("Insert a word");
            
         String str = stringScanner.next();
         
         System.out.print("Provide the integer location of two letters within the word");
         
         int i = stringScanner.nextInt();
         int j = stringScanner.nextInt();
         
      
         //Grab parts of the word entered 
         String first = str.substring(0, i);
         String middle = str.substring(i + 1, j);  
         String last = str.substring(j + 1);
         char iLetter = str.charAt(i);
         char jLetter = str.charAt(j);
         
         //concatenate the string
         String swappedLetters = first + jLetter + middle + iLetter + last;
            
         //print the new string out 
         System.out.println("Here is your new word!");
            
         System.out.println(swappedLetters);
        
      
   } // End Main method
  
}// End Class Cartoon
      
      