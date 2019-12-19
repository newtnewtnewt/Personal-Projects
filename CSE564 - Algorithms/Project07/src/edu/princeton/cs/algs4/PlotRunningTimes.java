package edu.princeton.cs.algs4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import edu.princeton.cs.algs4.Stopwatch;

public class PlotRunningTimes {
	
	public static void main(String[] args) throws FileNotFoundException {
		/**
		 * I chose to store all means and Standard Deviation values in alternating rows 
		 * of several text documents. The main method grabs all these values, throws them into
		 * arrays, and then plots them alongside a log graph.
		 */
		int[] nVals = new int[10000];
		for(int i = 1; i < 10001; i++) {
			nVals[i-1] = i;
		}
		double[] averageRuns = new double[10000]; 
		double[] stdDev = new double[10000];
			
		Scanner sc = new Scanner(new File("fileName.txt"));
		
		for(int i = 0; i < 10000; i++) {
			averageRuns[i] = sc.nextDouble();
			stdDev[i] = sc.nextDouble();
		}
		
		plotter(nVals, averageRuns, stdDev);
		
		
		
	}
	
	public static void plotter(int[] nValues, double[] timeValues, double[] stdDev) {
		double yLine = 0;
		for(int i = 0; i < timeValues.length; i++) {
			yLine = yLine + timeValues[i];
		}
		yLine = yLine / timeValues.length;
		StdDraw.setCanvasSize(800, 600);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.5, 0.4, 0.4, 0.3);
		//StdDraw.rectangle(0.5, 0.4, 0.4, 0.3);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.text(0.5, 0.025, "Node Checked");
		StdDraw.text(0.1, 0.8, "Time to Find(s)");
		
		double nLength = 0;
		double timeLength = 0;
		
		StdDraw.setPenColor(StdDraw.BLACK);
		
		double tickX1 = 0.1;
		double tickY1 = 0.125;
		double tickX2 = 0.1; 
		double tickY2 = 0.075;
		
		double spaceBetweenX = 0;
		double[] xPoints = new double[nValues.length];
		double[] yPoints = new double[timeValues.length];
		
		int highestNVal = nValues[0];
		for(int i = 1; i < nValues.length; i++) {
			if(nValues[i] > highestNVal) {
				highestNVal = nValues[i];
			}
		}
		
		double highestTimeVal = timeValues[0];
		for(int i = 1; i < timeValues.length; i++) {
			if(timeValues[i] > highestTimeVal) {
				highestTimeVal = timeValues[i];
			}
		}
		
		int lowestNVal = nValues[0];
		for(int i = 1; i < nValues.length; i++) {
			if(nValues[i] < lowestNVal) {
				lowestNVal = nValues[i];
			}
		}
		
		double lowestTimeVal = timeValues[0];
		for(int i = 1; i < timeValues.length; i++) {
			if(timeValues[i] < lowestTimeVal) {
				lowestTimeVal = timeValues[i];
			}
		}
		
		double maxStdDev = stdDev[0];
		for(int i = 1; i < stdDev.length; i++) {
			if(stdDev[i] > maxStdDev) {
				maxStdDev = stdDev[i];
			}
		}
		
		
		for(int i = 0; i < nValues.length; i++) {
			if(lowestNVal < 0) {
				spaceBetweenX = 0.1 + 0.8 * ((nValues[i] + Math.abs(lowestNVal))/(highestNVal - lowestNVal));
			}
			else {
				spaceBetweenX = 0.1 + 0.8 * ((nValues[i] + -lowestNVal))/(highestNVal - lowestNVal);

			}
			tickX1 = spaceBetweenX;
			tickX2 = spaceBetweenX;
			//StdDraw.line(tickX1, tickY1, tickX2, tickY2);
			//StdDraw.text(tickX1, 0.05, "" + nValues[i]);
			xPoints[i] = spaceBetweenX;
		} 
		
		double spaceBetweenY = 0;
		tickX1 = 0.075;
		tickX2 = 0.125;
		
		for(int i = 0; i < timeValues.length; i++) {
			if(lowestTimeVal < 0 ) {
				spaceBetweenY = 0.1 + 0.6 * ((timeValues[i] + Math.abs(lowestTimeVal))/(highestTimeVal - lowestTimeVal));
				
			}
			else {
				spaceBetweenY = 0.1 + 0.6 * ((timeValues[i] + -lowestTimeVal)/(highestTimeVal - lowestTimeVal + 0.6 * maxStdDev));
			}
			tickY1 = spaceBetweenY;
			tickY2 = spaceBetweenY;
			//StdDraw.line(tickX1, tickY1, tickX2, tickY2);
			//StdDraw.text(0.05, tickY1, "" + timeValues[i]);
			yPoints[i] = spaceBetweenY;
		}
		
		
		//StdDraw.setPenRadius(0.015);
		StdDraw.setPenRadius(0.00015);
		StdDraw.setPenColor(StdDraw.BLACK);
		
		StdDraw.line(0.075, 0.7, 0.125, 0.7);
		StdDraw.text(0.05, 0.7, "" + (((int)((timeValues[9999])*100))/100.0));
		
		StdDraw.line(0.9, 0.075, 0.9, 0.125);
		StdDraw.text(0.9, 0.05, "" + nValues[9999]);
		
		StdDraw.setPenColor(StdDraw.RED);
		/**
		 * This graphs all the points alongside the data obtained from RedBlackBSTDriver 
		 * 
		 */
		for(int i = 0; i < xPoints.length; i++) {
			StdDraw.point(xPoints[i], yPoints[i]);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.line(xPoints[i], yPoints[i] + 0.6 * (0.6 * stdDev[i])/(highestTimeVal - lowestTimeVal + 0.6 * maxStdDev), xPoints[i], yPoints[i] - 0.6 * (stdDev[i])/(highestTimeVal - lowestTimeVal + 0.6 * maxStdDev));
			StdDraw.setPenColor(StdDraw.RED);
		}
		StdDraw.setPenColor(StdDraw.GREEN);
		
		
		/**
		 * This graphs the logistic curve with Y = log(N) - 0.5
		 */
		double[] logPoints = new double[10000];
		for(int i = 0; i < 10000; i++) {
			logPoints[i] = 0.1 + 0.6 * (Math.log(nValues[i]) - 0.5 + 0.5)/((Math.log(nValues[9999]) - 0.5) + Math.abs((Math.log(nValues[0]) - 0.5)));
		}
	
		
		
		for(int i = 0; i < xPoints.length; i++) {
			StdDraw.point(xPoints[i], logPoints[i]);// + 0.6 * maxStdDev));
		}
		
		
		
		
		
		
//		double average = 0.0;
//		for(int i = 0; i < nValues.length - 1; i++) {
//			average = average + (timeValues[i+1] - timeValues[i])/(nValues[i + 1] - nValues[i]);
//		}
//		average = average/(nValues.length-1);
//		average = ((int)(average * 1000))/1000.0;
		
		
		//StdDraw.text(0.1, 0.9, "Average: " + average);
		
		
		
		
		
		
		
		
		
		
	}
	
	public static void plotter(int[] nValues, String[] timeValues, char[] colorValues) {
		double yLine = 0;
		for(int i = 0; i < timeValues.length; i++) {
			yLine = yLine + Double.parseDouble((timeValues[i].substring(2,timeValues[i].length())));
		}
		yLine = yLine / timeValues.length;
		StdDraw.setCanvasSize(800, 600);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.5, 0.4, 0.4, 0.3);
		//StdDraw.rectangle(0.5, 0.4, 0.4, 0.3);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.text(0.5, 0.025, "Test Number");
		StdDraw.text(0.1, 0.8, "Time to Run (s)");
		
		double nLength = 0;
		double timeLength = 0;
		
		StdDraw.setPenColor(StdDraw.BLACK);
		
		double tickX1 = 0.1;
		double tickY1 = 0.125;
		double tickX2 = 0.1; 
		double tickY2 = 0.075;
		
		double spaceBetweenX = 0;
		double[] xPoints = new double[nValues.length];
		double[] yPoints = new double[timeValues.length];
		
		int highestNVal = nValues[0];
		for(int i = 1; i < nValues.length; i++) {
			if(nValues[i] > highestNVal) {
				highestNVal = nValues[i];
			}
		}
		
		double highestTimeVal = Double.parseDouble(timeValues[0].substring(2,timeValues[0].length()));
		for(int i = 1; i < timeValues.length; i++) {
			if(Double.parseDouble(timeValues[i].substring(2,timeValues[i].length())) > highestTimeVal) {
				highestTimeVal = Double.parseDouble(timeValues[i].substring(2,timeValues[i].length()));
			}
		}
		
		int lowestNVal = nValues[0];
		for(int i = 1; i < nValues.length; i++) {
			if(nValues[i] < lowestNVal) {
				lowestNVal = nValues[i];
			}
		}
		
		double lowestTimeVal = Double.parseDouble(timeValues[0].substring(2,timeValues[0].length()));
		for(int i = 1; i < timeValues.length; i++) {
			if(Double.parseDouble(timeValues[i].substring(2,timeValues[i].length())) < lowestTimeVal) {
				lowestTimeVal = Double.parseDouble(timeValues[i].substring(2,timeValues[i].length()));
			}
		}
	
		
		
		for(int i = 0; i < nValues.length; i++) {
			if(lowestNVal < 0) {
				spaceBetweenX = 0.1 + 0.8 * ((nValues[i] + Math.abs(lowestNVal))/(highestNVal - lowestNVal));
			}
			else {
				spaceBetweenX = 0.1 + 0.8 * ((nValues[i] + -lowestNVal))/(highestNVal - lowestNVal);

			}
			tickX1 = spaceBetweenX;
			tickX2 = spaceBetweenX;
			//StdDraw.line(tickX1, tickY1, tickX2, tickY2);
			//StdDraw.text(tickX1, 0.05, "" + nValues[i]);
			xPoints[i] = spaceBetweenX;
		} 
		
		double spaceBetweenY = 0;
		tickX1 = 0.075;
		tickX2 = 0.125;
		
		for(int i = 0; i < timeValues.length; i++) {
			if(lowestTimeVal < 0 ) {
				spaceBetweenY = 0.1 + 0.6 * ((Double.parseDouble(timeValues[i].substring(2,timeValues[i].length())) + Math.abs(lowestTimeVal))/(highestTimeVal - lowestTimeVal));
				
			}
			else {
				spaceBetweenY = 0.1 + 0.6 * ((Double.parseDouble(timeValues[i].substring(2,timeValues[i].length())) + -lowestTimeVal)/(highestTimeVal - lowestTimeVal));
			}
			tickY1 = spaceBetweenY;
			tickY2 = spaceBetweenY;
			//StdDraw.line(tickX1, tickY1, tickX2, tickY2);
			//StdDraw.text(0.05, tickY1, "" + timeValues[i]);
			yPoints[i] = spaceBetweenY;
		}
		
		
		//StdDraw.setPenRadius(0.015);
		StdDraw.setPenRadius(0.005);
		StdDraw.setPenColor(StdDraw.BLACK);
		
		StdDraw.line(0.075, 0.7, 0.125, 0.7);
		StdDraw.text(0.05, 0.7, "" + (((int)((Double.parseDouble(timeValues[timeValues.length-1].substring(2,timeValues[timeValues.length-1].length())))* 1000))/1000.0));
		
		StdDraw.line(0.9, 0.075, 0.9, 0.125);
		StdDraw.text(0.9, 0.05, "" + nValues[nValues.length-1]);
		
		StdDraw.setPenColor(StdDraw.RED);
		/**
		 * This graphs all the points alongside the data obtained from RedBlackBSTDriver 
		 * 
		 */
		for(int i = 0; i < xPoints.length; i++) {
			if(colorValues[i] == 'S') {
				//StdDraw.setPenRadius(0.0075);
				StdDraw.setPenColor(StdDraw.BLACK);
				//StdDraw.setPenRadius(0.0015);
			}
			if(colorValues[i] == 'U') {
				StdDraw.setPenColor(StdDraw.RED);
			}
			
			StdDraw.point(xPoints[i], yPoints[i]);
			
				
		}
		StdDraw.setPenColor(StdDraw.GREEN);
	
		
	
	}
	
	public static void logData(double[] nVals, double[] timeValues) {
		for(int i = 0; i < timeValues.length; i++) {
			timeValues[i] = ((int)(Math.log10(timeValues[i]) * 100))/100.0;
			nVals[i] = ((int)(Math.log10(nVals[i]) * 100))/100.0;
		}
	}
	public static double average(double[] nVals) {
		double total = 0; 
		for(int i = 0; i < nVals.length; i++) {
			total = total + nVals[i];
		}
		return (total/nVals.length);
	}
	public static double stdDev(double[] nVals, double avg) {
		double sum = 0;
		for(int i = 0; i < nVals.length; i++) {
			sum = sum + Math.pow(Math.abs(nVals[i] - avg), 2);
		}
		return (Math.sqrt((sum / nVals.length)));
		
	}
	
	
		
	
	
	
	

}
