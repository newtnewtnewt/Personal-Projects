/*
 * Implements 4 well-known mathematical methods using numerical approximations (which is how
 * it's actually done anyway.
 */

public class MyMath {
	public static void main(String[] args) {
		testExp();
		testSin();
		testCos();
		testSqrt();

	}

	public static void testExp() {

		System.out.println("**** testing exp(x) ****");
		for (double x = -2; x <= 2; x+= .2) {
			System.out.printf("%.2f\t%.5f\t%.5f%n", x, exp(x, 10), Math.exp(x));
		}

	}

	public static void testSin() {

		System.out.println("\n**** testing sin(x) ****");
		for (double x = -2; x <= 2; x+= .2) {
			System.out.printf("%.2f\t%.5f\t%.5f%n", x, sin(x, 10), Math.sin(x));
		}

	}

	public static void testCos() {

		System.out.println("\n**** testing cos(x) ****");
		for (double x = -Math.PI; x <= Math.PI; x+= Math.PI/10) {
			System.out.printf("%.2f\t%.5f\t%.5f%n", x, cos(x, 10), Math.cos(x));
		}

	}

	public static void testSqrt() {

		System.out.println("\n**** testing sqrt(x) ****");
		for (double x = 0; x <= 20; x+= .5) {
			System.out.printf("%.2f\t%.5f\t%.5f%n", x, sqrt(x, 10), Math.sqrt(x));
		}

	}

	/*
	 * Each of the methods below should use only + - * / %.  Do not
	 * use any Math methods.  Keep your computations as simple as possible.
	 * None of these require a lot of code.
	 */



	// Implement exp(x) using the Taylor series approximation from class.  
	// n represents the number of terms in the Taylor series.
	// SEE LAST WEEK'S NOTES
	public static double exp(double x, int n) {
		double sum = 0;
		double num = 1;
		double den = 1;

		for (int i = 1; i <= n; i++) {
			sum += num / den;   
			num *= x;
			den *= i;
			
		}

		return sum;
	}


	// Implement sin(x) using the Taylor series approximation from class.  
	// n represents the number of terms in the Taylor series.
	public static double sin(double x, int n) {
		double sum = 0;
		double num = x;
		double den = 1;
		boolean plus = false;

		for (int i = 3; i <= 2 * n; i+=2) {
			// System.out.println("REEE " + num + " " + den);
			sum += num / den;   
			num *= -1 * (x * x);
			den *= (i) * (i - 1);
		}

		return sum;

	}

	// Implement cos(x) using the Taylor series approximation from class.  
	// n represents the number of terms in the Taylor series.
	public static double cos(double x, int n) {
		double sum = 0;
		double num = 1;
		double den = 1;
		boolean plus = false;

		for (int i = 2; i <= 2 * n; i+=2) {
			sum += num / den;   
			num *= -1 * (x * x);
			den *= (i) * (i - 1);
		}

		return sum;

	}


	// Implement sqrt(x) using the Newton's Method approximation discussed
	// in class. 
	public static double sqrt(double x, int n) {
		// Recall we are trying to find xseed in   (y - yseed) = m(x - xseed)
		//  Where y = 0, yseed is the value we get from seed * seed - x
		//  m is 2 due to the derivatives of the tangent lines, and x = previous seedValue
		
		double seedVal = x;
		for(int i = 0; i < n; i++ ) {
			seedVal = (-1 * (seedVal * seedVal) + x)/(2 * seedVal) + seedVal;
		}
		
		return seedVal;
	}

}