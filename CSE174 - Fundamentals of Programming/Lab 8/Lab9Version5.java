import java.util.Scanner;
public class Lab9Version5
{
  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    System.out.println("starting number to test?");
    int counter = 0;
    int startNum = input.nextInt();
    System.out.println("ending number to test?");
    int endNum = input.nextInt();
    int finalNum = 0;
    int finalMax = 0;
 
   
    int maxCounter = 0;
    int maxNum = startNum;
  for(int n = startNum; n <= endNum; n++){
   counter = 0;
   maxNum = n;
    for(int i = n; i > 1; counter++){
      if(i % 2 == 1){
        i = 3 * i + 1;
        if(i >= maxNum){
          maxNum = i;
        }
        
      }
    
    else{
      i = i / 2;
      if(i >= maxNum){
          maxNum = i;
        }
      
    }
   
    
    }
     
    if(counter >= maxCounter){
    maxCounter = counter;
    finalNum = n;
    finalMax = maxNum;
    
  }
    
  }
  System.out.print("\n" + "The number that requires the most steps is: " + finalNum + " with " +  maxCounter + " steps" + " and the max value was " + finalMax + "\n");
  }
}
 


  
