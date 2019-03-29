package edu.princeton.cs.algs4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GraphTester {
	static final int TREE_SIZE = 10000;
	static final int NUM_OF_EDGES = 100000;
	static final int NUM_OF_TRIALS = 100;
	static final int INCREMENTAL_VALUE = 1000;
	
	public static void main(String[] args) {
//		kruskalUniformTest();
//		kruskalGaussianTest();
//		lazyPrimGaussianTest();
//		lazyPrimUniformTest();
//		eagerPrimUniformTest();
//		eagerPrimGaussianTest();
//		
	}
	
	
	public static void kruskalUniformTest() {
		double[] kNValsF = new double[NUM_OF_EDGES/1000];
		double[] kNValsStdDev = new double[NUM_OF_EDGES/1000];
		double[] kNVals = new double[NUM_OF_TRIALS];
		for(int i = 0; i < NUM_OF_EDGES; i = i + INCREMENTAL_VALUE) {
			System.out.println(i);
			for(int j = 0; j < NUM_OF_TRIALS; j++) {
				EdgeWeightedGraph ewg = generateRandomUniformTree(TREE_SIZE, i);
				Stopwatch sw = new Stopwatch();
				KruskalMST kMST = new KruskalMST(ewg);
				kNVals[j] = sw.elapsedTime();
			}
			kNValsF[i/1000] = PlotRunningTimes.average(kNVals);
			kNValsStdDev[i/1000] = PlotRunningTimes.stdDev(kNVals, kNValsF[i/1000]);
		}
		writeToFile("test1.txt", kNValsF, kNValsStdDev);
		
		
	}
	public static void kruskalGaussianTest() {
		double[] kNValsF = new double[NUM_OF_EDGES/1000];
		double[] kNValsStdDev = new double[NUM_OF_EDGES/1000];
		double[] kNVals = new double[NUM_OF_TRIALS];
		for(int i = 0; i < NUM_OF_EDGES; i = i + INCREMENTAL_VALUE) {
			System.out.println(i);
			for(int j = 0; j < NUM_OF_TRIALS; j++) {
				EdgeWeightedGraph ewg = generateRandomGaussianTree(TREE_SIZE, i);
				Stopwatch sw = new Stopwatch();
				KruskalMST kMST = new KruskalMST(ewg);
				kNVals[j] = sw.elapsedTime();
			}
			kNValsF[i/1000] = PlotRunningTimes.average(kNVals);
			kNValsStdDev[i/1000] = PlotRunningTimes.stdDev(kNVals, kNValsF[i/1000]);
		}
		writeToFile("test2.txt", kNValsF, kNValsStdDev);
	}
	public static void lazyPrimGaussianTest() {
		double[] kNValsF = new double[NUM_OF_EDGES/1000];
		double[] kNValsStdDev = new double[NUM_OF_EDGES/1000];
		double[] kNVals = new double[NUM_OF_TRIALS];
		for(int i = 0; i < NUM_OF_EDGES; i = i + INCREMENTAL_VALUE) {
			System.out.println(i);
			for(int j = 0; j < NUM_OF_TRIALS; j++) {
				EdgeWeightedGraph ewg = generateRandomGaussianTree(TREE_SIZE, i);
				Stopwatch sw = new Stopwatch();
				LazyPrimMST kMST = new LazyPrimMST(ewg);
				kNVals[j] = sw.elapsedTime();
			}
			kNValsF[i/1000] = PlotRunningTimes.average(kNVals);
			kNValsStdDev[i/1000] = PlotRunningTimes.stdDev(kNVals, kNValsF[i/1000]);
		}
		writeToFile("test3.txt", kNValsF, kNValsStdDev);
	}
	public static void lazyPrimUniformTest() {
		double[] kNValsF = new double[NUM_OF_EDGES/1000];
		double[] kNValsStdDev = new double[NUM_OF_EDGES/1000];
		double[] kNVals = new double[NUM_OF_TRIALS];
		for(int i = 0; i < NUM_OF_EDGES; i = i + INCREMENTAL_VALUE) {
			System.out.println(i);
			for(int j = 0; j < NUM_OF_TRIALS; j++) {
				EdgeWeightedGraph ewg = generateRandomUniformTree(TREE_SIZE, i);
				Stopwatch sw = new Stopwatch();
				LazyPrimMST kMST = new LazyPrimMST(ewg);
				kNVals[j] = sw.elapsedTime(); 
			}
			kNValsF[i/1000] = PlotRunningTimes.average(kNVals);
			kNValsStdDev[i/1000] = PlotRunningTimes.stdDev(kNVals, kNValsF[i/1000]);
		}
		writeToFile("test4.txt", kNValsF, kNValsStdDev);
	}
	public static void eagerPrimUniformTest() {
		double[] kNValsF = new double[NUM_OF_EDGES/1000];
		double[] kNValsStdDev = new double[NUM_OF_EDGES/1000];
		double[] kNVals = new double[NUM_OF_TRIALS];
		for(int i = 0; i < NUM_OF_EDGES; i = i + INCREMENTAL_VALUE) {
			System.out.println(i);
			for(int j = 0; j < NUM_OF_TRIALS; j++) {
				EdgeWeightedGraph ewg = generateRandomUniformTree(TREE_SIZE, i);
				Stopwatch sw = new Stopwatch();
				EagerPrimMST kMST = new EagerPrimMST(ewg);
				kNVals[j] = sw.elapsedTime();
			}
			kNValsF[i/1000] = PlotRunningTimes.average(kNVals);
			kNValsStdDev[i/1000] = PlotRunningTimes.stdDev(kNVals, kNValsF[i/1000]);
		}
		writeToFile("test5.txt", kNValsF, kNValsStdDev);
	}
	public static void eagerPrimGaussianTest() {
		double[] kNValsF = new double[NUM_OF_EDGES/1000];
		double[] kNValsStdDev = new double[NUM_OF_EDGES/1000];
		double[] kNVals = new double[NUM_OF_TRIALS];
		for(int i = 0; i < NUM_OF_EDGES; i = i + INCREMENTAL_VALUE) {
			System.out.println(i);
			for(int j = 0; j < NUM_OF_TRIALS; j++) {
				EdgeWeightedGraph ewg = generateRandomGaussianTree(TREE_SIZE, i);
				Stopwatch sw = new Stopwatch();
				EagerPrimMST kMST = new EagerPrimMST(ewg);
				kNVals[j] = sw.elapsedTime();
			}
			kNValsF[i/1000] = PlotRunningTimes.average(kNVals);
			kNValsStdDev[i/1000] = PlotRunningTimes.stdDev(kNVals, kNValsF[i/1000]);
		}
		writeToFile("test6.txt", kNValsF, kNValsStdDev);
	}
	public static void writeToFile(String name, double[] vals, double[] stdDev) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File(name));
			for(int i = 0; i < vals.length; i++) {
				pw.println(vals[i]);
				pw.println(stdDev[i]);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param vertices
	 * @param edges
	 * @return EdgeWeightedGraph
	 * 
	 * Using an edges count = 0, a base connected edge weighted graph will be generated. The number of edges specified
	 * will place edges in addition to the ones already in existence. An edges value of 0 will generate the sparsest 
	 * connected graph possible with normally distributed weights
	 */
	
	public static EdgeWeightedGraph generateRandomUniformTree(int vertices, int edges) {
		EdgeWeightedGraph ewg = new EdgeWeightedGraph(vertices, edges, 'U');
		return ewg;
	}
	/**
	 * 
	 * @param vertices
	 * @param edges
	 * @return EdgeWeightedGraph
	 * 
	 * Using an edges count = 0, a base connected edge weighted graph will be generated. The number of edges specified
	 * will place edges in addition to the ones already in existence. An edges value of 0 will generate the sparsest 
	 * connected graph possible with gaussian distributed weights
	 */
	public static EdgeWeightedGraph generateRandomGaussianTree(int vertices, int edges) {
		EdgeWeightedGraph ewg = new EdgeWeightedGraph(vertices, edges, 'G');
		return ewg;
	}
	
	
}
