package edu.princeton.cs.algs4;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class SubstringJUnit {

	@Test
	void BruteForceTester() {
		String notIn = "thisStringIsNotInThere";
		String in = "iminthere";
		String searchString = "sommadummaloomasumagettingusedtoyouasasuperhumanimintheredemonstratingmorethaneverdevastinghowtogiveamotherlovingaudience";
		//These are fairly simple tests, but they are sufficient for determining
		//whether or not a true or a false is returned with knowledge of 
		BruteForceSearch foundBFS = new BruteForceSearch(in);
		BruteForceSearch notFoundBFS = new BruteForceSearch(notIn);
		assertTrue(foundBFS.find(searchString));
		assertFalse(notFoundBFS.find(searchString));	
	}
	
	@Test
	void SubstringTestBookTester() {
		//Since this runs 100 trials of 4 different tests on 1000 different keywords, this just shows that
		//the tester does not break and covers what It needs to. 
		//The exceptions will not be reached because they are only thrown when the substring is not in the 
		//Overall string
		String searchString = "sommadummaloomasumagettingusedtoyouasasuperhumanimintheredemonstratingmorethaneverdevastinghowtogiveamotherlovingaudience";
		SubstringTester.BFTester(searchString);
		
	}
	@Test 
	void SubstringTestSpecificPhrase() {
		//Nearly the same as the brute force tester, this one
		//ensures there are no errors and the phrase in question is found at least once.
		String searchString = "sommadummaloomasumagettingusedtoyouasasuperhumanimintheredemonstratingmorethaneverdevastinghowtogiveamotherlovingaudience";
		assertFalse(SubstringTester.specificPhraseTester(searchString, "woeisi"));
		assertTrue(SubstringTester.specificPhraseTester(searchString, "superhuman"));
	}
	@Test
	void WriteToFileTest() {
		//This method insures that my write to file method works
		
		//Prep data
		double[] vals = {1.0, 2.0};
		double[] stdDev = {3.0, 4.0};
		double[] ssLength = {5.0, 6.0};
		
		//Write to file
		SubstringTester.writeToFile("testWriter.txt",vals , stdDev, ssLength);
		
		try {
			//Scan line by line and insure the lines match up
			Scanner sc = new Scanner(new File("testWriter.txt"));
			assertTrue(sc.next().equals("1.0"));
			assertTrue(sc.next().equals("3.0"));
			assertTrue(sc.next().equals("5.0"));
			
			assertTrue(sc.next().equals("2.0"));
			assertTrue(sc.next().equals("4.0"));
			assertTrue(sc.next().equals("6.0"));
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	

}
