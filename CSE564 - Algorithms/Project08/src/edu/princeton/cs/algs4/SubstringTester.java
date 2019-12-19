package edu.princeton.cs.algs4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class SubstringTester {

		static final int NUM_OF_TRIALS = 100;
		static final int NUM_OF_KEY_PHRASES = 1000;
		
		public static void main(String[] args) {
			try {
				StringBuilder totFullText = new StringBuilder();
			
				Scanner sc = new Scanner(new File("tale.txt"));
				while(sc.hasNextLine()) {
					totFullText.append(sc.nextLine());
				}
			
				sc.close();
				//BFTester(totFullText.toString());
				specificPhraseTester(totFullText.toString(),"it is a far better thing that i do than i have ever done");
				
				
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			
		}

		
		
		
		public static void BFTester(String txt)  {
			double[] ssAvgBF = new double[NUM_OF_KEY_PHRASES];
			double[] ssStdDevBF = new double[NUM_OF_KEY_PHRASES];
			double[] ssLengthBF = new double[NUM_OF_KEY_PHRASES];
			
			double[] ssAvgBM = new double[NUM_OF_KEY_PHRASES];
			double[] ssStdDevBM = new double[NUM_OF_KEY_PHRASES];
			double[] ssLengthBM = new double[NUM_OF_KEY_PHRASES];
			
			double[] ssAvgKMP = new double[NUM_OF_KEY_PHRASES];
			double[] ssStdDevKMP = new double[NUM_OF_KEY_PHRASES];
			double[] ssLengthKMP = new double[NUM_OF_KEY_PHRASES];
			
			double[] ssAvgRK = new double[NUM_OF_KEY_PHRASES];
			double[] ssStdDevRK = new double[NUM_OF_KEY_PHRASES];
			double[] ssLengthRK = new double[NUM_OF_KEY_PHRASES];
			
			
			
			for(int i = 0; i < NUM_OF_KEY_PHRASES; i++) {
				
				System.out.println(i);
				int startString = txt.length() - 1;
				int endString = 0;
				while(startString == txt.length() - 1 ) {
					startString = StdRandom.uniform(0, txt.length());
					
				}
				endString = StdRandom.uniform(startString + 1, txt.length());
				String patternString = txt.substring(startString, endString);
				char[] pattern = patternString.toCharArray();
				double[] tempStore = new double[NUM_OF_TRIALS];
				//===This is the block for the brute force Check===\\
				BruteForceSearch bf = new BruteForceSearch(Arrays.copyOf(pattern, pattern.length));
				for(int j = 0; j < NUM_OF_TRIALS; j++) {
					
					Stopwatch sw = new Stopwatch();
					try {
						if(bf.find(txt) != true) {
							
							throw new Exception("Your code is broken!");
						}
					}
					catch(Exception e) {
						System.out.println("We broke on the brute force!");
					}
					tempStore[j] = sw.elapsedTime();
					
				}
				ssAvgBF[i] = PlotRunningTimes.average(tempStore);
				double average = ssAvgBF[i];
				ssStdDevBF[i] = PlotRunningTimes.stdDev(tempStore,average);
				ssLengthBF[i] = patternString.length();
				
				//===This is the block for the brute force Check===\\
				
				//===This is the block for the Boyer Moore Check===\\
				BoyerMoore bm = new BoyerMoore(patternString);
				for(int j = 0; j < NUM_OF_TRIALS; j++) {
					Stopwatch sw = new Stopwatch();
					try {
						if(bm.search(txt) == txt.length()) {
							throw new Exception("Your code is broken!");
						}
					
					tempStore[j] = sw.elapsedTime();
					}
					catch(Exception e) {
						System.out.println("We broke on Boyer Moore!");
					}
					
				}
				
				ssAvgBM[i] = PlotRunningTimes.average(tempStore);
				average = ssAvgBM[i];
				ssStdDevBM[i] = PlotRunningTimes.stdDev(tempStore,average);
				ssLengthBM[i] = patternString.length();
				
				//===This is the block for the Boyer Moore Check===\\
				
				//===This is the block for the Knuth-Morris-Pratt Check===\\
				KMP kmp = new KMP(patternString);
				for(int j = 0; j < NUM_OF_TRIALS; j++) {
					
					Stopwatch sw = new Stopwatch();
					try {
						if(kmp.search(txt) == txt.length()) {
							throw new Exception("Your code is broken!");
						}
					}
					catch(Exception e) {
						System.out.println("We broke on KMP");
					}
					
					tempStore[j] = sw.elapsedTime();
					
				}
				ssAvgKMP[i] = PlotRunningTimes.average(tempStore);
				average = ssAvgKMP[i];
				ssStdDevKMP[i] = PlotRunningTimes.stdDev(tempStore,average);
				ssLengthKMP[i] = patternString.length();
				//===This is the block for the Rabin-Karp Check===\\
				RabinKarp rk = new RabinKarp(patternString);
				for(int j = 0; j < NUM_OF_TRIALS; j++) {
					
					Stopwatch sw = new Stopwatch();
					try {
						if(rk.search(txt) == txt.length()) {
							
							throw new Exception("Your code is broken!");
						}
						tempStore[j] = sw.elapsedTime();
					}
					catch(Exception e) {
						System.out.println("We broke on Rabin-Karp");
					}
				}
				ssAvgRK[i] = PlotRunningTimes.average(tempStore);
				average = ssAvgRK[i];
				ssStdDevRK[i] = PlotRunningTimes.stdDev(tempStore,average);
				ssLengthRK[i] = patternString.length();
				
				//===This is the block for the Rabin-Karp Check===\\
				
				
				
			}
			writeToFile("BruteForce.txt", ssAvgBF, ssStdDevBF, ssLengthBF);
			writeToFile("BoyerMoore.txt", ssAvgBM, ssStdDevBM, ssLengthBM);
			writeToFile("KnuthMorrisPratt.txt", ssAvgKMP, ssStdDevKMP, ssLengthKMP);
			writeToFile("RabinKarp.txt", ssAvgRK, ssStdDevRK, ssLengthRK);
			    
			
		}
		
		
		public static boolean specificPhraseTester(String txt, String pattern) {
			char[] patternRay = pattern.toCharArray();
			double[] tempStore = new double[1000];
			boolean found = false;
			//===This is the block for the brute force Check===\\
			BruteForceSearch bf = new BruteForceSearch(Arrays.copyOf(patternRay,patternRay.length));
			for(int j = 0; j < 1000; j++) {
				
				Stopwatch sw = new Stopwatch();
				bf.find(txt);
				tempStore[j] = sw.elapsedTime();	
			}
			double average = PlotRunningTimes.average(tempStore);
			System.out.println("Average for Brute Force: " + average);
			System.out.println("StdDev for Brute Force: " + PlotRunningTimes.stdDev(tempStore, average));
			System.out.println();
			
			BoyerMoore bm = new BoyerMoore(pattern);
			for(int j = 0; j < 1000; j++) {
				Stopwatch sw = new Stopwatch();
				if(bm.search(txt) != txt.length()) {
					found = true;
				}
				tempStore[j] = sw.elapsedTime();
			}
				
			
			average = PlotRunningTimes.average(tempStore);
			System.out.println("Average for Boyer Moore: " + average);
			System.out.println("StdDev for BoyerMoore: " + PlotRunningTimes.stdDev(tempStore, average));
			System.out.println();
			
			KMP kmp = new KMP(pattern);
			for(int j = 0; j < 1000; j++) {
				Stopwatch sw = new Stopwatch();
				kmp.search(txt);
				tempStore[j] = sw.elapsedTime();
			}
			
			average = PlotRunningTimes.average(tempStore);
			System.out.println("Average for KMP " + average);
			System.out.println("StdDev for KMP: " + PlotRunningTimes.stdDev(tempStore, average));
			System.out.println();
			
			
			RabinKarp rk = new RabinKarp(pattern);
			for(int j = 0; j < NUM_OF_TRIALS; j++) {
				
				Stopwatch sw = new Stopwatch();
				rk.search(txt);
				tempStore[j] = sw.elapsedTime();
				
			}
			average = PlotRunningTimes.average(tempStore);
			System.out.println("Average for Rabin Karp: " + average);
			System.out.println("Std Dev for Rabin Karp: " + PlotRunningTimes.stdDev(tempStore, average));
			System.out.println();
			return found;
		}
		
		
		
		
		public static void writeToFile(String name, double[] vals, double[] stdDev, double[] ssLength) {
			PrintWriter pw;
			try {
				pw = new PrintWriter(new File(name));
				for(int i = 0; i < vals.length; i++) {
					pw.println(vals[i]);
					pw.println(stdDev[i]);
					pw.println(ssLength[i]);
				}
				pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}

}
