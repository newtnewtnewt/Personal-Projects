/* Name: Noah Dunn
 * Class: CSE 174 Section I
 * Instructor: Dr. Alberto-Castro Hernandez 
 * Date: 9/19/2017
 * Description: Compute Easter Sunday with an algorithm 
 * 
 * */
 
import java.util.Scanner;
   
   public class EasterSunday{
   
      public static void main(String[] args){
      
          Scanner year = new Scanner(System.in);
          System.out.print("Insert a year:");
          //Build the algorithm
          int y = year.nextInt();
          int a,b,c,d,e,f,g,h,j,k,m,r,n,p;
          a = y % 19;
          b = y/100;
          c = y%100;
          d = b/4;
          e = b%4;
          g = (8 * b +13)/25;
          h = (19 * a + b - d - g + 15) % 30;
          j = c / 4;
          k = c % 4 ;
          m = (a + 11 * h)/ 319;
          r = (2 * e + 2 * j -k - h + m + 32)% 7;
          n = (h - m + r + 90)/25;
          p = (h - m + r + n + 19)% 32;
          
          //Print the month/date/year combo
          System.out.printf("In the year %s, Easter Sunday fell on %d/%d/%s %n", y,n,p,y);
         
             
           
      
   } // end of method
} // end of class