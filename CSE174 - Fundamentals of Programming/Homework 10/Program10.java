import java.util.Scanner;
public class Program10{
   static Scanner input = new Scanner(System.in);
   static int startPile = 10 + (int)(Math.random() * 101);;
   static int marbles = startPile;
   static boolean startUp = true;
   static int marblesUpperRange = 0;
   static int marblesPicked = 1;
   static String winner = "";
   static int startingPlayer = compChecker();
   static int currentMarbles = 0;
   static int tempPower = 0;
   
   public static void main(String[] args){
      
      int compMode = compChecker();
      stupidMode(compMode);
      smartMode(compMode);
      if(winner.equals("PC"))
         System.out.println("Computer wins :(");
      else
         System.out.println("Human wins!");
      
   }
 
   
   public static int getSmartMove(int marbles){
      if(marbles == 1)
         return 1;
      else
         marblesUpperRange = marbles/2;
      int smartChoice = 0;
      for(int i = 0; Math.pow(2, i) <= marblesUpperRange; i++){
         tempPower = (int)Math.pow(2, i);
      if(tempPower >= 1 && tempPower <= marblesUpperRange){
         if(tempPower != marbles - 1)
           smartChoice = tempPower;
         else
            return getStupidMove(marbles);
      }
    
      }
   
        
      System.out.println("Computer will take " + smartChoice + ".");
      return smartChoice;
      
   }
      
      
   //end getSmartMove method
   
   public static int getStupidMove(int marbles){
     if(marbles == 1)
         return 1;
      else
         marblesUpperRange = marbles/2;
      marblesPicked = 1+ (int)(Math.random() * marblesUpperRange) ;  
      System.out.println("Computer will take " + marblesPicked + ".");
      return marblesPicked;  
      
   }//end getStupidMove
   
   public static int userMove(int marbles){
      if(marbles == 1)
         marblesUpperRange = 1;
      else
         marblesUpperRange = marbles/2;
      marblesPicked = 0;
      while(marblesPicked < 1 || marblesPicked > marblesUpperRange){
         System.out.print("How many do you want to take (1-" + marblesUpperRange + ")");
         marblesPicked = input.nextInt();  
         if(marblesPicked < 1 || marblesPicked > marblesUpperRange)
            System.out.println("*** Illegal entry!");
         else
            break;
      }
      return marblesPicked;
      }
      
        
     
      
      
   public static int compChecker(){
   if((int)(Math.random() * 2) == 0)
      return 0;
   else
      return 1;
   
   }


public static void stupidMode(int compMode){
   if(compMode == 0){  //This is Stupid Mode
         System.out.println("*** The computer is playing stupid");
         System.out.println("*** There are " + marbles + " marbles in the pile.");
         
  
            if(startingPlayer == 0){ //Player goes first, computer is stupid
               while(marbles >= 1){
                  
                  marbles = marbles - userMove(marbles);
                  if(marbles == 0){
                     winner = "PC";
                     break;
                  }
                     System.out.println("*** There are " + marbles + " marbles in the pile.");
                  
                  marbles = marbles - getStupidMove(marbles);
                  
                  if(marbles == 0){
                     winner = "USER";
                     break;
                  }
                  System.out.println("*** There are " + marbles + " marbles in the pile.");
                  }
               }
            
               //Start Stupid Computer with User going second
               
            if(startingPlayer == 1) {
                while(marbles != 0){
                  marbles = marbles - getStupidMove(marbles);
                  if(marbles == 0){
                     winner = "USER";
                     break;
                  }
                   System.out.println("*** There are " + marbles + " marbles in the pile.");
                  
                  marbles = marbles - userMove(marbles);
                  if(marbles == 0){
                     winner = "PC";
                     break;
                  }
                   System.out.println("*** There are " + marbles + " marbles in the pile.");
                }
      }
   
}
}

public static void smartMode(int compMode){
      if(compMode == 1){ //This is Smart Mode
         System.out.println("*** The computer is playing smart");
         System.out.println("*** There are " + marbles + " marbles in the pile.");
      }
      
      if(startingPlayer == 0){ //Player goes first, computer is stupid
               while(marbles >= 1){
                  
                  marbles = marbles - userMove(marbles);
                  if(marbles == 0){
                     winner = "PC";
                     break;
                  }
                     System.out.println("*** There are " + marbles + " marbles in the pile.");
                  
                  marbles = marbles - getSmartMove(marbles);
                  
                  if(marbles == 0){
                     winner = "USER";
                     break;
                  }
                  System.out.println("*** There are " + marbles + " marbles in the pile.");
                  }
               }
          
            if(startingPlayer == 1) {
                while(marbles != 0){
                  marbles = marbles - getSmartMove(marbles);
                  if(marbles == 0){
                     winner = "USER";
                     break;
                  }
                   System.out.println("*** There are " + marbles + " marbles in the pile.");
                  
                  marbles = marbles - userMove(marbles);
                  if(marbles == 0){
                     winner = "PC";
                     break;
                  }
                   System.out.println("*** There are " + marbles + " marbles in the pile.");
                }
      }
}
}
     
                                
   

      