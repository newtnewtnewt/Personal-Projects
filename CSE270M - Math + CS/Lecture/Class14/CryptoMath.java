import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// See cryptoMathNotes.pdf in Notes folder

public class CryptoMath {
  
  public static void main(String[] args) {
// Uncomment the lines below as you solve your methods.
	 testIsPrime();
	 testGcd();
	 testPow();
     testPrimeFactorization();
    
  }
  
  /*
   * Returns true if n is prime, and false otherwise.
   */
  public static boolean isPrime(long n) {
	if(n == 1) {
		return false;
	}
	if(n == 2) {
		return true;
	}
	if(n % 2 == 0) {
		return false;
	}
    for(long i = 3; i <= Math.sqrt(n); i+=2) {
    		if(n % i == 0) {
    			return false;
    		}
    }
    return true;    
    
  }
  
  /*
   * Uses the Euclidean Algorithm to determine the gcd
   * of a and b. See 
   */
  public static long gcd(long a, long b) {
	long high = a;
	long low = b;
	long interim = 0;
    while(high % low != 0) {
    	if(interim != 0 && interim == high % low) return 1;
    	interim = high % low;
    	high = low;
    	low = interim;
    }
    return low;
    
  }
  
  /*
   * Computes a^b mod m, using the technique from class.
   */
  public static long pow(long a, long b, long m) {
    Queue<Character> binaryInts = new LinkedList<Character>();
    long temp = b;
    while(temp != 0) {
    	binaryInts.add((char) (temp % 2));
    	temp /= 2;
    }
    long currentBase = -1;
    Queue<Long> runningVals = new LinkedList<Long>();
    while(!binaryInts.isEmpty()) {
    		int binaryNum = binaryInts.poll();
    		if(currentBase == -1) {
    			currentBase = a % m;
    			if(binaryNum != 0) {
    				runningVals.add(currentBase);
    			}
    		}
    		else {
    			currentBase = (currentBase * currentBase % m);
    			if(binaryNum != 0) {
    				runningVals.add(currentBase);
    			}
    		}
    }
    long firstVal = runningVals.poll();
    while(!runningVals.isEmpty()) {
    	firstVal = (firstVal * runningVals.poll()) % m;
    }
    return firstVal;
    
  }
  
  
  /*
   * Computes the prime factorization of integer n > 1, returning
   * the result as an array of the prime factors which,
   * when multiplied give n as a result.  For example, 
   * primeFactorization(45) would return an array containing {3, 3, 5},
   * primeFactorization(17) would return an array containing {17}
   */
  public static long[] primeFactorization(long n) {
	  if(isPrime(n)) {
		  return new long[] {n};
	  }
	  else {
		  ArrayList<Long> allFactors = new ArrayList<Long>();
		  primeHelper(allFactors, n);
		  long[] allFactorsArray = new long[allFactors.size()];
		  for(int i = 0; i < allFactors.size(); i++) {
			  allFactorsArray[i] = allFactors.get(i);
		  }
		  Arrays.sort(allFactorsArray);
		  return allFactorsArray;
	  }
  }
  public static void primeHelper(ArrayList<Long> fullList, long factor) {
	  if(isPrime(factor)) {
		  fullList.add(factor);
		  return;
	  }
	  double size = Math.sqrt(factor);
	  for(long i = 2; i <= size; i++) {
		  if(factor % i == 0) {
			  primeHelper(fullList, i);
			  primeHelper(fullList, factor / i);
			  return;
		  }
		  
	  }
	  return;
  }
    
  public static void testIsPrime() {
    long test = 1;
    System.out.println(isPrime(test) + " should be false");
    
    test = 2;
    System.out.println(isPrime(test) + " should be true");
    
    test = 3;
    System.out.println(isPrime(test) + " should be true");
    
    test = 997;
    System.out.println(isPrime(test) + " should be true");
    
    test = 1001;
    System.out.println(isPrime(test) + " should be false");
    
    test = 994009;
    System.out.println(isPrime(test) + " should be false");
    
    test = 78742675849L;
    System.out.println(isPrime(test) + " should be false");
    
    test = 2199999973L;
    System.out.println(isPrime(test) + " should be true");
    
    test = 9223372036854775783L;
    System.out.println(isPrime(test) + " should be true");
  }
  
  public static void testGcd() {
    long a = 175;
    long b = 210;
    System.out.println(gcd(a, b) + " should be 35");
    
    a = 997;
    b = 1003;
    System.out.println(gcd(a, b) + " should be 1");
    
    a = 9223372036854775783L;
    b = 9223372036854775783L;
    System.out.println(gcd(a, b) + " should be 9223372036854775783");
    
    a = 9223372036854775783L;
    b = 9223372036854775784L;
    System.out.println(gcd(a, b) + " should be 1");
    
    a = 12586269025L;
    b = 20365011074L;
    System.out.println(gcd(a, b) + " should be 1");
    
    a = 229046807697L;
    b = 147190474692L;
    System.out.println(gcd(a, b) + " should be 3");
    
  }
  
  public static void testPow() {
    long a = 2;
    long b = 3;
    long m = 97;
    System.out.println(pow(a, b, m) + " should be 8");
    
    a = 2;
    b = 5;
    m = 97;
    
    System.out.println(pow(a, b, m) + " should be 32");
    
    a = 2;
    b = 6;
    m = 97;
    
    System.out.println(pow(a, b, m) + " should be 64");
    
    a = 2;
    b = 999;
    m = 457043;    
    System.out.println(pow(a, b, m) + " should be 390621");
    
    a = 17;
    b = 23;
    m = 4897;    
    System.out.println(pow(a, b, m) + " should be 3720");
    
    a = 437197L;
    b = 288531L;
    m = 10982825501L;    
    System.out.println(pow(a, b, m) + " should be 6456066299");
    
  }
  
  public static void testPrimeFactorization() {
    System.out.println(Arrays.toString(primeFactorization(30)) + " should return {2, 3, 5}");
    System.out.println(Arrays.toString(primeFactorization(128)) + " should return {2, 2, 2, 2, 2, 2, 2}");
    System.out.println(Arrays.toString(primeFactorization(17)) + " should return {17}"); 
    System.out.println(Arrays.toString(primeFactorization(78742675849L)) + " should return {224737, 350377}");
    System.out.println(Arrays.toString(primeFactorization(13272425279607917L)) + " should return {431, 431, 431, 523, 563, 563}");
    System.out.println(Arrays.toString(primeFactorization(9223372036854775783L)) + " should return {9223372036854775783}");
    System.out.println(Arrays.toString(primeFactorization(25071688922457241L)) + " should return {541, 541, 541, 541, 541, 541}");
    
  }
  
  
  
  
  
}