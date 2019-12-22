public class Part1{
  
  public static void main(String args[]){
    
    String str1 = "coconut";
    String str2 = "nut";
    int n1 = str1.indexOf("co");
    int n2 = str1.indexOf(str2);
    int n3 = str1.indexOf("con");
    int n4 = str1.indexOf("Co");
    
    System.out.println(n1);
    System.out.println(n2);
    System.out.println(n3);
    System.out.println(n4);
    
    String s1 = "Helping Hands";
    String s2 = "Hand";
    int len = s1.length();
    boolean result1 = s1.equals(s2);
    String result2 = s1.toUpperCase();
    char val1 = s1.charAt(3);
    char val2 = s1.toUpperCase().charAt(1);
    String s3 = s1.substring(0, 4);
    String s4 = s1.substring(3, 12);
    String s5 = s1.substring(9);
    String s6 = s1.substring(s2.length());
    String s7 = s1.substring(s2.length(),9);
    
    System.out.println(len);
    System.out.println(result1);
    System.out.println(result2);
    System.out.println(val1);
    System.out.println(val2);
    System.out.println(s3);
    System.out.println(s4);
    System.out.println(s5);
    System.out.println(s6);
    System.out.println(s7);
    
    String y = "Good", z = "Luck";
    String result4 = y + z;
    String result5 = "Good"+" "+"Bye";
    String result6 = "John";
    result6 += " Smith";
    String wish = "She said,\"Hi!\".";
    
    System.out.println(result4);
    System.out.println(result5);
    System.out.println(result6);
    System.out.println(wish);
    
    System.out.printf("%10s\n%+10d\n%10c\n%10.2f\\\n", "hello", 70, 'a', 1.2365);
    System.out.printf("%-10s\n%-10d\n%-10c\n%-10.2f\\\n", "hello", 70, 'a', 1.2365);
  
  
  
  }
}