import java.util.Arrays;

import edu.princeton.cs.algs4.Stopwatch;

public class QuickInsertionBlend {
	static int N = 1000000;
	static Integer[] testArray = new Integer[N];
	
	public static void main(String args[]) {
		for(int j = 0; j <= 30; j++) {
			for(int i = 0; i < testArray.length; i++) {
				testArray[i] = (Integer)StdRandom.uniform(0, Integer.MAX_VALUE - 1); 
			}
			Stopwatch sw = new Stopwatch();
			quickInsertion(0, testArray.length-1, 0);
			double time = sw.elapsedTime();
			System.out.println("N = " + N + "      m = " + j + "  Runtime: " + time);
		}
	}
	
	

	public static void quickInsertion(int lo, int hi, int m) {
		
		if(hi - lo + 1 <= m && m != 0) {
			insertionSort(lo, hi); 
		}
		else {
			if(lo < hi) {
				Integer leftB = lo;
				Integer rightB = hi - 1;
				Integer pivot = testArray[hi];
			
			
			
		
			while(leftB <= rightB ) {
	
				if(testArray[leftB].compareTo(pivot) < 0 ) {
					leftB++;
					
				}
				else if(testArray[rightB].compareTo(pivot) > 0) {
					rightB--;
				
				}
				else if(testArray[leftB].equals(testArray[rightB])) {
					break;
				}
				else {
					int temp = testArray[leftB];
					testArray[leftB] = testArray[rightB];
					testArray[rightB] = temp;
				}
			}
			
			int temp = testArray[leftB];
			testArray[leftB] = pivot;
			testArray[hi] = temp;
				
			quickInsertion(lo, leftB - 1, m);
			quickInsertion(leftB + 1, hi , m);
		}
		}
		
	}
			
		
	



	private static void insertionSort(int lo, int hi) {
		for(int i = lo; i <= hi; i++ ) {
			int insertPiece = testArray[i];
				int j = 0;
				boolean found = false;
				while(j <= i ) {
					if(testArray[j].compareTo(insertPiece) > 0 || found) {
						found = true;
						int temp = testArray[j];
						testArray[j] = insertPiece;
						insertPiece = temp;
					}
					j++;
				}
				
			}
			
		}
	
	public static boolean isSorted() {
		for(int i = 0; i < testArray.length - 1; i++) {
			if(testArray[i] > testArray[i+1]) {
				return false;
			}
			
		}
		return true;
	}
		
	}
	
	
	
	

