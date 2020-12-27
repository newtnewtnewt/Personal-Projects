/* Name: Noah Dunn
 * Class: CSE 174 Section I
 * Instructor: Dr. Alberto-Castro Hernandez 
 * Date: 9/5/2017
 * Description: Print a list of 5 tax rates for 5 different states
 * */


public class TaxRate{
   
   public static void main(String args[]) {
      // Establish the known tax rates for the 5 states picked
      double ohioTax = 5.75;
      double northDakotaTax = 5.00;
      double tennesseeTax = 7.00;
      double texasTax = 6.25;
      double vermontTax = 6.00;
      
      //Print the value of each tax rate corresponding to the state
      System.out.println("Sales Tax Rates");
      System.out.println("---------------");
      System.out.println("Ohio:" + "              "  + ohioTax + "%");
      System.out.println("North Dakota:" + "      "  + northDakotaTax + "%");
      System.out.println("Tennessee:" + "         "  + tennesseeTax + "%");
      System.out.println("Texas:" + "             "  + texasTax + "%");
      System.out.println("Vermont:" + "           "  + vermontTax + "%");
   } // End main method
}   // end TaxRate class 