import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.princeton.cs.algs4.Stopwatch;

public class RedBlackBSTDriver {
	public static double rotNumbers = 0;
	
	
	public static void main(String[] args) throws FileNotFoundException  {
		//		internalPathCalculator();
		//		rotationCalculator();
		//		heightCalculator();
	
	}
	
	
	public static void internalPathCalculator() throws FileNotFoundException {
	
		int[] nVals = new int[10000];
		
		RedBlackBST<Integer, Integer> rbBST = new RedBlackBST<Integer, Integer>();
		PrintWriter pw = new PrintWriter(new File("internalPaths.txt"));
		
		for(int i = 1; i < 10001; i++) {
			nVals[i-1] = i;
		}
		
			double[] pathLengths = new double[1000];
	
		for(int i = 1; i < 10001; i++) {
			for(int j = 0; j < 1000; j++) {
				Integer[] keys = new Integer[i];
				for(int k = 0; k < i; k++) {
					Integer key = StdRandom.uniform(0, Integer.MAX_VALUE -1);
					keys[k] = key;
					rbBST.put(key, StdRandom.uniform(0,Integer.MAX_VALUE - 1));
				}
				double internalPathLength = 0;
				
				for(int k = 0; k < i; k++) {
					internalPathLength = internalPathLength + rbBST.get(keys[k]);
				}
				internalPathLength = (internalPathLength / i) + 1;
				pathLengths[j] = internalPathLength;
				rbBST.clear();
				
	
				
			}
			double average = PlotRunningTimes.average(pathLengths);
			double stdDev = PlotRunningTimes.stdDev(pathLengths, average);
			
			pw.println(average);
			pw.println(stdDev);
			System.out.println(i);
			
			
			
		}
		pw.close();
	

	}
	
public static void rotationCalculator() throws FileNotFoundException {
		
		int[] nVals = new int[10000];
		
		RedBlackBST<Integer, Integer> rbBST = new RedBlackBST<Integer, Integer>();
	
		PrintWriter pw = new PrintWriter(new File("rotationNumber.txt"));
		
		for(int i = 1; i < 10001; i++) {
			nVals[i-1] = i;
		}
		
		double[] treeHeights = new double[1000];
	
		for(int i = 1; i < 10001; i++) {
			for(int j = 0; j < 1000; j++) {
				Integer[] keys = new Integer[i];
				for(int k = 0; k < i; k++) {
					Integer key = StdRandom.uniform(0, Integer.MAX_VALUE -1);
					keys[k] = key;
					rbBST.put(key, StdRandom.uniform(0,Integer.MAX_VALUE - 1));
				}
				
				treeHeights[j] = rotNumbers;
				rotNumbers = 0;
				rbBST.clear();
				
	
				
			}
			double average = PlotRunningTimes.average(treeHeights);
			double stdDev = PlotRunningTimes.stdDev(treeHeights, average);
			
			pw.println(average);
			pw.println(stdDev);
			System.out.println(i);
			
			
			
		}
		pw.close();
	

	}
	
	public static void heightCalculator() throws FileNotFoundException {
		
		int[] nVals = new int[10000];
		
		RedBlackBST<Integer, Integer> rbBST = new RedBlackBST<Integer, Integer>();
		PrintWriter pw = new PrintWriter(new File("heightNumbers.txt"));
		
		for(int i = 1; i < 10001; i++) {
			nVals[i-1] = i;
		}
		
		double[] treeHeights = new double[1000];
	
		for(int i = 1; i < 10001; i++) {
			for(int j = 0; j < 1000; j++) {
				Integer[] keys = new Integer[i];
				for(int k = 0; k < i; k++) {
					Integer key = StdRandom.uniform(0, Integer.MAX_VALUE -1);
					keys[k] = key;
					rbBST.put(key, StdRandom.uniform(0,Integer.MAX_VALUE - 1));
				}
				
				treeHeights[j] = rbBST.height();
				rbBST.clear();
				
	
				
			}
			double average = PlotRunningTimes.average(treeHeights);
			double stdDev = PlotRunningTimes.stdDev(treeHeights, average);
			
			pw.println(average);
			pw.println(stdDev);
			System.out.println(i);
			
			
			
		}
		pw.close();
	

	}
	
	
	

}
