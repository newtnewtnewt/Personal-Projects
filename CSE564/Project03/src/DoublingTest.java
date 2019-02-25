import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Selection;
import edu.princeton.cs.algs4.Shell;
import edu.princeton.cs.algs4.Stopwatch;

public class DoublingTest {

	public static void main(String[] args) {
		System.out.println("=======Insertion Sort Test=======");
		double[] runTimes = new double[20];
		int marker = 0;
		double a =  findDedicatedA();
		
		for(int i = 1000; i <= 64000; i = i * 2) {
			
			Comparable[] testRay = new Comparable[i];
			for(int j = 0; j < i; j++) {
				testRay[j] = StdRandom.uniform(0, Integer.MAX_VALUE - 1); 
			}
			
			
			Stopwatch sw = new Stopwatch();
			Insertion.sort(testRay);
			double actualTime = sw.elapsedTime();
			String predictedTime;
			String ratio;
			if(i == 1000) {
				predictedTime = "N/A";
				ratio = "N/A";
				runTimes[marker] = actualTime;
				
			}
			else {
				predictedTime = " " + a * (Math.pow(i, 2));
				ratio = " " + actualTime/runTimes[marker-1];
				runTimes[marker] = actualTime;
			}
			marker++;
			
			
			
			
			System.out.println("n = " + i + "   Predicted # of Seconds: " + predictedTime + "   Actual # of Seconds: " + actualTime + "   Ratio:  "  + ratio);
			
			
			
			
		}
		
		System.out.println("=======Selection Sort Test=======");
		runTimes = new double[20];
		marker = 0;
		a =  findDedicatedASelection();
		
		for(int i = 1000; i <= 64000; i = i * 2) {
			
			Comparable[] testRay = new Comparable[i];
			for(int j = 0; j < i; j++) {
				testRay[j] = StdRandom.uniform(0, Integer.MAX_VALUE - 1); 
			}
			
			
			Stopwatch sw = new Stopwatch();
			Selection.sort(testRay);
			double actualTime = sw.elapsedTime();
			String predictedTime;
			String ratio;
			if(i == 1000) {
				predictedTime = "N/A";
				ratio = "N/A";
				runTimes[marker] = actualTime;
				
			}
			else {
				predictedTime = " " + a * (Math.pow(i, 2));
				ratio = " " + actualTime/runTimes[marker-1];
				runTimes[marker] = actualTime;
			}
			marker++;
			
			
			
			
			System.out.println("n = " + i + "   Predicted # of Seconds: " + predictedTime + "   Actual # of Seconds: " + actualTime + "   Ratio:  "  + ratio);
			
			
			
			
		}
		System.out.println("=======Shell Sort Test=======");
		runTimes = new double[20];
		marker = 0;
		a =  findDedicatedAShell2();
		
		for(int i = 1000; i <= 64000; i = i * 2) {
			
			Comparable[] testRay = new Comparable[i];
			for(int j = 0; j < i; j++) {
				testRay[j] = StdRandom.uniform(0, Integer.MAX_VALUE - 1); 
			}
			
			
			Stopwatch sw = new Stopwatch();
			Shell.sort(testRay);
			double actualTime = sw.elapsedTime();
			String predictedTime;
			String ratio;
			if(i == 1000) {
				predictedTime = "N/A";
				ratio = "N/A";
				runTimes[marker] = actualTime;
				
			}
			else {
				predictedTime = " " + a * (Math.pow(i, 1));
				ratio = " " + actualTime/runTimes[marker-1];
				runTimes[marker] = actualTime;
			}
			marker++;
			
		
			
			
			System.out.println("n = " + i + "   Predicted # of Seconds: " + predictedTime + "   Actual # of Seconds: " + actualTime + "   Ratio:  "  + ratio);
			
		}
	}
	public static double findDedicatedASelection() {
		double average = 0;
		for(int i = 0; i < 5; i++) {
			
			Comparable[] testRay = new Comparable[4000];
			for(int j = 0; j < 4000; j++) {
				testRay[j] = StdRandom.uniform(0, Integer.MAX_VALUE - 1); 
			}
			Stopwatch sw = new Stopwatch();
			Selection.sort(testRay);
			double actualTime = sw.elapsedTime();
			
			if(i > 0 ) {
				average = (average + (actualTime/(Math.pow(4000, 2))))/2;
			}
			else {
				average = actualTime/(Math.pow(4000, 2));
			}
		}
		return average;
	}
	
	public static double findDedicatedA() {
		double average = 0;
		for(int i = 0; i < 5; i++) {
			
			Comparable[] testRay = new Comparable[4000];
			for(int j = 0; j < 4000; j++) {
				testRay[j] = StdRandom.uniform(0, Integer.MAX_VALUE - 1); 
			}
			Stopwatch sw = new Stopwatch();
			Insertion.sort(testRay);
			double actualTime = sw.elapsedTime();
			
			if(i > 0 ) {
				average = (average + (actualTime/(Math.pow(4000, 2))))/2;
			}
			else {
				average = actualTime/(Math.pow(4000, 2));
			}
		}
		return average;
	}
	
	
	public static double findDedicatedAShell1() {
		double average = 0;
		for(int i = 0; i < 5; i++) {
			
			Comparable[] testRay = new Comparable[4000];
			for(int j = 0; j < 4000; j++) {
				testRay[j] = StdRandom.uniform(0, Integer.MAX_VALUE - 1); 
			}
			Stopwatch sw = new Stopwatch();
			Shell.sort(testRay);
			double actualTime = sw.elapsedTime();
			
			if(i > 0 ) {
				average = (average + (actualTime/(Math.pow(4000, 1))))/2;
			}
			else {
				average = actualTime/(Math.pow(4000, 1));
			}
		}
		return average;
	}
	
	public static double findDedicatedAShell2() {
		double average = 0;
		for(int i = 0; i < 5; i++) {
			
			Comparable[] testRay = new Comparable[4000];
			for(int j = 0; j < 4000; j++) {
				testRay[j] = StdRandom.uniform(0, Integer.MAX_VALUE - 1); 
			}
			Stopwatch sw = new Stopwatch();
			Shell.sort(testRay);
			double actualTime = sw.elapsedTime();
			
			if(i > 0 ) {
				average = (average + (actualTime/(Math.pow(4000, 2))))/2;
			}
			else {
				average = actualTime/(Math.pow(4000, 2));
			}
		}
		return average;
	}



}
