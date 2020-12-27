import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;

public class SumsAnalysis {
	
	/**
	 *  The {@code ThreeSum} class provides static methods for counting
	 *  and printing the number of triples in an array of integers that sum to 0
	 *  (ignoring integer overflow).
	 *  <p>
	 *  This implementation uses a triply nested loop and takes proportional to n^3,
	 *  where n is the number of integers.
	 *  <p>
	 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/14analysis">Section 1.4</a> of
	 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
	 *
	 *  @author Robert Sedgewick
	 *  @author Kevin Wayne
	 */

	
	   

	    /**
	     * Returns the number of triples (i, j, k) with {@code i < j < k}
	     * such that {@code a[i] + a[j] + a[k] == 0}.
	     *
	     * @param  a the array of integers
	     * @return the number of triples (i, j, k) with {@code i < j < k}
	     *         such that {@code a[i] + a[j] + a[k] == 0}
	     */
	    public static int countThree(int[] a) {
	        int n = a.length;
	        int count = 0;
	        for (int i = 0; i < n; i++) {
	            for (int j = i+1; j < n; j++) {
	                for (int k = j+1; k < n; k++) {
	                    if (a[i] + a[j] + a[k] == 0) {
	                        count++;
	                    }
	                }
	            }
	        }
	        return count;
	    } 
	    
	   
	    
	    /**
	     *  The {@code ThreeSumFast} class provides static methods for counting
	     *  and printing the number of triples in an array of distinct integers that
	     *  sum to 0 (ignoring integer overflow).
	     *  <p>
	     *  This implementation uses sorting and binary search and takes time 
	     *  proportional to n^2 log n, where n is the number of integers.
	     *  <p>
	     *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/14analysis">Section 1.4</a> of
	     *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
	     *
	     *  @author Robert Sedgewick
	     *  @author Kevin Wayne
	     */

	    // returns true if the sorted array a[] contains any duplicated integers
	    private static boolean containsDuplicates(int[] a) {
	        for (int i = 1; i < a.length; i++)
	            if (a[i] == a[i-1]) return true;
	        return false;
	    }


	    public static int countThreeFast(int[] a) {
	        int n = a.length;
	        Arrays.sort(a);
	        if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers");
	        int count = 0;
	        for (int i = 0; i < n; i++) {
	            for (int j = i+1; j < n; j++) {
	                int k = Arrays.binarySearch(a, -(a[i] + a[j]));
	                if (k > j) count++;
	            }
	        }
	        return count;
	    } 
	    
	    public static int countTwo(int[] a) {
	        int n = a.length;
	        int count = 0;
	        for (int i = 0; i < n; i++) {
	                for (int k = i+1; k < n; k++) {
	                    if (a[i]  + a[k] == 0) {
	                        count++;
	                    }
	                }
	            }
	        return count;
	    } 
	    
	    public static int countTwoFast(int[] a) {
	        int n = a.length;
	        Arrays.sort(a);
	        if (containsDuplicates(a)) throw new IllegalArgumentException("array contains duplicate integers");
	        int count = 0;
	        for (int i = 0; i < n; i++) {
	                int k = Arrays.binarySearch(a, -a[i]);
	                if (k > i) count++;
	            }
	        
	        return count;
	    } 
	    
	    
	  
	 
	
	    
	public static void main(String[] args) {
		int size = 16000;
		int[] testArray = new int[size];
		for(int i = 0; i < size; i++) {
			testArray[i] = StdRandom.uniform(0, Integer.MAX_VALUE - 1);
		}
		
        Stopwatch timer = new Stopwatch();
        int firstCount = countTwo(testArray);
        double time = timer.elapsedTime();
        StdOut.println("elapsed time Two Sum = " + time);
//        
//        int secondCount = countTwoFast(testArray);
//        time = timer.elapsedTime() - time;
//        StdOut.println("elapsed time Two Sum Fast = " + time);
//        
//        int thirdCount = countThree(testArray);
//        time = timer.elapsedTime() - time;
//        StdOut.println("elapsed time Three Sum = " + time);
//       
//        int fourthCount = countThreeFast(testArray);
//        time = timer.elapsedTime() - time;
//        StdOut.println("elapsed time Three Sum Fast = " + time);
	}
	
	
	/******************************************************************************
	 *  Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
	 *
	 *  This file is part of algs4.jar, which accompanies the textbook
	 *
	 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
	 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
	 *      http://algs4.cs.princeton.edu
	 *
	 *
	 *  algs4.jar is free software: you can redistribute it and/or modify
	 *  it under the terms of the GNU General Public License as published by
	 *  the Free Software Foundation, either version 3 of the License, or
	 *  (at your option) any later version.
	 *
	 *  algs4.jar is distributed in the hope that it will be useful,
	 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
	 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	 *  GNU General Public License for more details.
	 *
	 *  You should have received a copy of the GNU General Public License
	 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
	 ******************************************************************************/

}
