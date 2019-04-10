package edu.princeton.cs.algs4;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class RegexTester {
		
	public static void main(String[] args) {
		File readFile;
		String regex = "";
		if(args.length == 0) {
			readFile = new File("words.txt");
		}
		else {
			readFile = new File((String) args[0]);
			regex = (String) args[1];
		}
		System.out.println("Please insert a regex expression: ");
		regex = StdIn.readString();
		if(regex.equals("")) {
			regex = "";
		}
		//runGraphRegex(readFile, new File("test1.txt"), new File("test2.txt"), regex, 'R');
		runGraphRegex(readFile, new File("test1.txt"), new File("test2.txt"), null, 'C');
		
		
		
	}
	
	
	public static void runGraphRegex(File readFile,File fileChoice, File fileChoiceTwo, String regex, char option) {
		try {
			Scanner sc = new Scanner(readFile);
			
			int counter = 0;
			while(sc.hasNextLine()) {
				counter++;
				sc.nextLine();
			}
			
			//Grab all the words from the specified word document
			String[] wordList = new String[counter];
			sc.close();
			sc = new Scanner(readFile);
			
			for(int i = 0; i < counter; i++){
				wordList[i] = sc.nextLine();
			}
			
			//Give them a good mix around to prevent worst case scenario trees.
			StdRandom.shuffle(wordList);
			
		
			
			
			if(option == 'R') {
				runRegexTester(wordList, fileChoice, fileChoiceTwo, regex);
			}
			else {
				runStringTester(wordList, fileChoice, fileChoiceTwo, 20000);
			}
			
			sc = new Scanner(fileChoice);
			counter = 0;
			while(sc.hasNextLine()) {
				counter++;
				sc.nextLine();
			}
			
			int[] nValues = new int[counter];
			String[] timeValues = new String[counter];
			char[] colorVals  = new char[counter];
			for(int i = 0; i < nValues.length; i++) {
				nValues[i] = i;
			}
			sc.close();
			
			sc = new Scanner(fileChoice);
			int track = 0;
			while(sc.hasNextLine()) {
				
				timeValues[track] = sc.nextLine();
				if(timeValues[track].charAt(2) == ' ') {
					timeValues[track] = "U 0.0";
				}
				colorVals[track] = timeValues[track].charAt(0);
				track++;
			}
			sc.close();
			PlotRunningTimes.plotter(nValues, timeValues, colorVals);
			
			
			sc = new Scanner(fileChoiceTwo);
			counter = 0;
			while(sc.hasNextLine()) {
				counter++;
				sc.nextLine();
			}
			
			nValues = new int[counter];
			timeValues = new String[counter];
			colorVals  = new char[counter];
			for(int i = 0; i < nValues.length; i++) {
				nValues[i] = i;
			}
			sc.close();
			
			sc = new Scanner(fileChoiceTwo);
			track = 0;
			while(sc.hasNextLine()) {
				timeValues[track] = sc.nextLine();
				if(timeValues[track].charAt(2) == ' ') {
					timeValues[track] = "U 0.0";
				}
				colorVals[track] = timeValues[track].charAt(0);
				track++;
			}
			sc.close();
			
			PlotRunningTimes.plotter(nValues, timeValues, colorVals);
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
	
	}
	public static void runRegexTester(String[] words, File saveFile, File saveFile2, String regex) throws FileNotFoundException {
		
		TST<String> tTree = new TST<String>();
		BST<String, String> bTree = new BST<String, String>();
		
		for(int i = 0; i < words.length; i++) {
			tTree.put(words[i], words[i]);
			bTree.put(words[i], words[i]);
		}
		PrintWriter pw = new PrintWriter(saveFile);
		PrintWriter pw2 = new PrintWriter(saveFile2);
		tTree.keysThatMatch(regex, pw);
		bTree.containsRegex(regex, pw2);
		pw.close();
		pw2.close();
		
	}
	
	public static void runStringTester(String[] words, File saveFile, File saveFile2, int numOfTestStrings) throws FileNotFoundException {
		BST<String, String> bTree = new BST<String, String>();
		TST<String> tTree = new TST<String>();
		
		for(int i = 0; i < words.length; i++) {
			bTree.put(words[i], words[i]);
			tTree.put(words[i], words[i]);
		}
		PrintWriter pw = new PrintWriter(saveFile);
		PrintWriter pw2 = new PrintWriter(saveFile2);
		double lastRecordedTime = 0;
		Stopwatch sw = new Stopwatch();
		
		//These runs are for the successful tests
		for(int i = 0; i < numOfTestStrings; i++) {
			String randomString = words[StdRandom.uniform(words.length)];
			
			tTree.contains(randomString);
			lastRecordedTime = sw.elapsedTime() - lastRecordedTime;
			pw.println("S " + lastRecordedTime);
			
			bTree.contains(randomString);
			lastRecordedTime = sw.elapsedTime() - lastRecordedTime;
			pw2.println("S " + lastRecordedTime);

		}
		
		//These runs are for unsuccessful tests(Creating random jarble out of improbable words
		String randomString = "x";
		for(int i = 0; i < numOfTestStrings; i++) {
			randomString = randomString + ((char)(StdRandom.uniform(26) + 'a'));
			
			tTree.contains(randomString);
			lastRecordedTime = sw.elapsedTime() - lastRecordedTime;
			pw.println("U " + lastRecordedTime);
			
			bTree.contains(randomString);
			lastRecordedTime = sw.elapsedTime() - lastRecordedTime;
			pw2.println("U " + lastRecordedTime);
		}
		pw.close();
		pw2.close();
	
	
	
	
	
	
	}
	
	
}