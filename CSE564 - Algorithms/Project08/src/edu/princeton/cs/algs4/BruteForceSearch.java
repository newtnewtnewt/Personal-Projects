package edu.princeton.cs.algs4;

import java.util.Arrays;

public class BruteForceSearch {
	char[] pattern;
	
	BruteForceSearch(char[] pattern){
		this.pattern = pattern;
	}
	BruteForceSearch(String pattern){
		this.pattern = pattern.toCharArray();
	}
	
	public boolean find(String txt) {
		//System.out.println(txt.length());
		//System.out.println(Arrays.toString(pattern));
		for(int i = 0; i < (txt.length() - pattern.length); i++){
			for(int j = 0; j < pattern.length; j++) {
				if(pattern[j] != txt.charAt(i + j)) {
					break;
				}
				if(j == pattern.length-1) {
					return true;
				}
			}
		}
		return false;
		
	
	}
	
}
