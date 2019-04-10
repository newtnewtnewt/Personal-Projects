package edu.princeton.cs.algs4;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class TreeTrieTester {

	@Test
	void BSTTests() {
		//Prepping essentials for this test Suite
		BST<String, String> bst = new BST<String,String>();
		String[] data = {"dog", "cat", "horse", "horsey","ham", "hamburger"};
		File testFile = new File("testerFile.txt");
		
		//Throw some arbitrary strings in my BST
		bst.put("dog", "dog");
		bst.put("cat", "cat");
		bst.put("horse", "horse");
		bst.put("horsey", "horsey");
		bst.put("ham", "ham");
		bst.put("hamburger", "hamburger");
		PrintWriter pw;
		
		try {
			//Prepping a dummy file that takes the sucesses and failures we have
			pw = new PrintWriter(new File("testerFile.txt"));
			bst.containsRegex("dog", pw);
			pw.close();
			//Can we find just dog?
			Scanner sc = new Scanner(testFile);
			int successCount = 0;
			
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
					successCount++;
				}
				
			}
			assertEquals(successCount, 1);
			//Yep
			
			//Can we find our three, three lettered words
			pw = new PrintWriter(new File("testerFile.txt"));
			bst.containsRegex("...", pw);
			pw.close();
			
			sc = new Scanner(testFile);
			successCount = 0;
			
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
					successCount++;
				}
				
			}
			//Yep
			assertEquals(successCount, 3);
			
			pw = new PrintWriter(new File("testerFile.txt"));
			bst.containsRegex("........", pw);
			pw.close();
			
			sc = new Scanner(testFile);
			successCount = 0;
			
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
					successCount++;
				}
				
			}
			//There should be no words 8 letters long
			assertEquals(successCount, 0);
			
			//As a final test, we check to make sure we don't grab more than we ask for
			//This should give us just horse, not horsey			
			pw = new PrintWriter(new File("testerFile.txt"));
			bst.containsRegex("hors.", pw);
			pw.close();
			
			sc = new Scanner(testFile);
			successCount = 0;
			
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
					successCount++;
				}
				
			}
			assertEquals(successCount, 1);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
		
	}
	
	@Test
	void TSTTests() {
		//Prepping essentials for this test Suite
		TST<String> tst = new TST<String>();
		String[] data = {"dog", "cat", "horse", "horsey","ham", "hamburger"};
		File testFile = new File("testerFile.txt");
		
		//Throw some arbitrary strings in my BST
		tst.put("dog", "dog");
		tst.put("cat", "cat");
		tst.put("horse", "horse");
		tst.put("horsey", "horsey");
		tst.put("ham", "ham");
		tst.put("hamburger", "hamburger");
		PrintWriter pw;
		
		try {
			//Prepping a dummy file that takes the sucesses and failures we have
			pw = new PrintWriter(new File("testerFile.txt"));
			tst.keysThatMatch("dog", pw);
			pw.close();
			//Can we find just dog?
			Scanner sc = new Scanner(testFile);
			int successCount = 0;
			
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
					successCount++;
				}
				
			}
			assertEquals(successCount, 1);
			//Yep
			
			//Can we find our three, three lettered words
			pw = new PrintWriter(new File("testerFile.txt"));
			tst.keysThatMatch("...", pw);
			pw.close();
			
			sc = new Scanner(testFile);
			successCount = 0;
			
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
					successCount++;
				}
				
			}
			//Yep
			assertEquals(successCount, 3);
			
			pw = new PrintWriter(new File("testerFile.txt"));
			tst.keysThatMatch("........", pw);
			pw.close();
			
			sc = new Scanner(testFile);
			successCount = 0;
			
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
					successCount++;
				}
				
			}
			//There should be no words 8 letters long
			assertEquals(successCount, 0);
			
			//As a final test, we check to make sure we don't grab more than we ask for
			//This should give us just horse, not horsey			
			pw = new PrintWriter(new File("testerFile.txt"));
			tst.keysThatMatch("hors.", pw);
			pw.close();
			
			sc = new Scanner(testFile);
			successCount = 0;
			
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
					successCount++;
				}
				
			}
			assertEquals(successCount, 1);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
		
	}
	
	
	
	@Test
	void runRegexTesterTest() {
		String[] data = {"dog", "cat", "horse", "horsey","ham", "hamburger"};
		File readFile = new File("readFile.txt");
		PrintWriter pw;
		
		File testFile1 = new File("testFile1.txt");
		File testFile2 = new File("testFile2.txt");
		
		try {
			//We test the Regex Tester on both the file storing for the TST and the BST
			RegexTester.runRegexTester(data, testFile1, testFile2, "...");
			
			Scanner sc = new Scanner(testFile1);
			int successCount = 0;
			
			//First the TST
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
					successCount++;
				}
				
			}
			//Works
			assertEquals(successCount, 3);
			
			sc = new Scanner(testFile2);
			successCount = 0;
			
			//Now we test with the BST
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
						successCount++;
				}
					
			}
			//All good
			assertEquals(successCount, 3);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void runStringTester() {
		String[] data = {"dog", "cat", "horse", "horsey","ham", "hamburger"};
		File readFile = new File("readFile.txt");
		PrintWriter pw;
		
		File testFile1 = new File("testFile1.txt");
		File testFile2 = new File("testFile2.txt");
		
		try {
			//We test the String tester 100 times on both the file storing for the TST and the BST
			RegexTester.runStringTester(data, testFile1, testFile2, 100);
			
			Scanner sc = new Scanner(testFile1);
			int successCount = 0;
			
			//First the TST
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
					successCount++;
				}
				
			}
			//Works
			assertEquals(successCount, 100);
			
			sc = new Scanner(testFile2);
			successCount = 0;
			
			//Now we test with the BST
			while(sc.hasNextLine()) {
				if(sc.nextLine().charAt(0) == 'S') {
						successCount++;
				}
					
			}
			//All good
			assertEquals(successCount, 100);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void runGraphRegexTest() {
		
		
		
		File storeFile1 = new File("storeFile1.txt");
		File storeFile2 = new File("storeFile2.txt");
		File storeFile3 = new File("storeFile3.txt");
		File storeFile4 = new File("storeFile4.txt");
		try {
			String[] data = {"dog", "cat", "horse", "horsey","ham", "hamburger"};
			File readFile = new File("readFile.txt");
			PrintWriter pw = new PrintWriter(readFile);
			for(int i = 0; i < data.length; i++) {
				pw.println(data[i]);
			}
			pw.close();
			
			
			//Uncomment these to have the test not finish, but demonstrate graphing
			//ability
			
			//RegexTester.runGraphRegex(readFile, storeFile1, storeFile2, "...", 'R');
			//RegexTester.runGraphRegex(readFile, storeFile3, storeFile4, "...", 'C');
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		
	}
	

}
