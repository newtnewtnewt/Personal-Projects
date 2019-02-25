
import java.util.Arrays;

import edu.princeton.cs.algs4.Stopwatch;

public class PlotRunningTimes {
	
	public static void main(String[] args) {
		double[] nVals = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
		double[] timeVals = {0.025, 0.019, 0.024, 0.007, 0.004, 0.006, 0.004, 0.005, 0.004, 0.008, 0.003, 0.003, 0.4, 0.003, 0.003, 0.003, 0.003, 0.003, 0.004, 0.003, 0.006, 0.003, 0.002, 0.003, 0.003, 0.003, 0.003, 0.003, 0.003, 0.003, 0.004};
		
		plotter(nVals, timeVals);
			
	}
	
	public static void plotter(double[] nValues, double[] timeValues) {
		double yLine = 0;
		for(int i = 0; i < timeValues.length; i++) {
			yLine = yLine + timeValues[i];
		}
		yLine = yLine / timeValues.length;
		StdDraw.setCanvasSize(800, 600);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.5, 0.4, 0.4, 0.3);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.text(0.5, 0.025, "N");
		StdDraw.text(0.1, 0.8, "Runtime(Seconds)");
		
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
		
		double highestNVal = nValues[0];
		for(int i = 1; i < nValues.length; i++) {
			if(nValues[i] > highestNVal) {
				highestNVal = nValues[i];
			}
		}
		
		double highestTimeVal = timeValues[0];
		for(int i = 1; i < timeValues.length; i++) {
			if(timeValues[i] > highestTimeVal) {
				highestTimeVal = nValues[i];
			}
		}
		
		double lowestNVal = nValues[0];
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
		
		
		
		for(int i = 0; i < nValues.length; i++) {
			if(lowestNVal < 0) {
				spaceBetweenX = 0.1 + 0.8 * ((nValues[i] + Math.abs(lowestNVal))/(highestNVal - lowestNVal));
			}
			else {
				spaceBetweenX = 0.1 + 0.8 * ((nValues[i] + -lowestNVal))/(highestNVal - lowestNVal);

			}
			tickX1 = spaceBetweenX;
			tickX2 = spaceBetweenX;
			StdDraw.line(tickX1, tickY1, tickX2, tickY2);
			StdDraw.text(tickX1, 0.05, "" + nValues[i]);
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
				spaceBetweenY = 0.1 + 0.6 * ((timeValues[i] + -lowestTimeVal)/(highestTimeVal - lowestTimeVal));
				System.out.println(spaceBetweenY);
			}
			tickY1 = spaceBetweenY;
			tickY2 = spaceBetweenY;
			StdDraw.line(tickX1, tickY1, tickX2, tickY2);
			StdDraw.text(0.05, tickY1, "" + timeValues[i]);
			yPoints[i] = spaceBetweenY;
		}
		
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(0.015);
		
		for(int i = 0; i < xPoints.length; i++) {
			
			StdDraw.point(xPoints[i], yPoints[i]);
		}
		
		double average = 0.0;
		for(int i = 0; i < nValues.length - 1; i++) {
			average = average + (timeValues[i+1] - timeValues[i])/(nValues[i + 1] - nValues[i]);
		}
		average = average/(nValues.length-1);
		average = ((int)(average * 1000))/1000.0;
		
		
		StdDraw.text(0.1, 0.9, "Average: " + average);
		
		
		
		
		
		
		
		
		
		
	}
	
	public static void logData(double[] nVals, double[] timeValues) {
		for(int i = 0; i < timeValues.length; i++) {
			timeValues[i] = ((int)(Math.log10(timeValues[i]) * 100))/100.0;
			nVals[i] = ((int)(Math.log10(nVals[i]) * 100))/100.0;
		}
	}
		
	
	
	
	

}
