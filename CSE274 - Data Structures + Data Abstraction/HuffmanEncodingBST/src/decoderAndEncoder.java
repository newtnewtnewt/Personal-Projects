import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/** 
 * This code takes a file within the working directory, input.txt, converts it into Huffman encoding
 * and writes it into the file output.txt. It then converts everything back from Huffman encoding into
 * plaintext and stores the file into huffToTextOutput.txt
 * */ 
public class decoderAndEncoder {
	public static void main(String[]args) {
		/**
		 * Read in the following strings and count letters
		 */
		int[] charCounter = new int[26];
		char[] charArray = {'A', 'B', 'C', 'D','E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R','S','T','U', 'V', 'W','X', 'Y', 'Z'};
		Scanner in = null;
		try {
			in = new Scanner(new File("input.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(in.hasNextLine()) {
			String currentWord = in.nextLine();
			for(int i = 0; i < currentWord.length(); i++) {
				switch(currentWord.charAt(i)) {
					case 'a': case 'A':
						charCounter[0]++;
						break;
					case 'b': case 'B':
						charCounter[1]++;
						break;
					case 'c': case 'C':
						charCounter[2]++;
						break;
					case 'd': case 'D':
						charCounter[3]++;
						break;
					case 'e': case 'E':
						charCounter[4]++;
						break;
					case 'f': case 'F':
						charCounter[5]++;
						break;
					case 'g': case 'G':
						charCounter[6]++;
						break;
					case 'h': case 'H':
						charCounter[7]++;
						break;
					case 'i': case 'I':
						charCounter[8]++;
						break;
					case 'j': case 'J':
						charCounter[9]++;
						break;
					case 'k': case 'K':
						charCounter[10]++;
						break;
					case 'l': case 'L':
						charCounter[11]++;
						break;
					case 'm': case 'M':
						charCounter[12]++;
						break;
					case 'n': case 'N':
						charCounter[13]++;
						break;
					case 'o': case 'O':
						charCounter[14]++;
						break;
					case 'p': case 'P':
						charCounter[15]++;
						break;
					case 'q': case 'Q':
						charCounter[16]++;
						break;
					case 'r': case 'R':
						charCounter[17]++;
						break;
					case 's': case 'S':
						charCounter[18]++;
						break;
					case 't': case 'T':
						charCounter[19]++;
						break;
					case 'u': case 'U':
						charCounter[20]++;
						break;
					case 'v': case 'V':
						charCounter[21]++;
						break;
					case 'w': case 'W':
						charCounter[22]++;
						break;
					case 'x': case 'X':
						charCounter[23]++;
						break;
					case 'y': case 'Y':
						charCounter[24]++;
						break;
					case 'z': case 'Z':
						charCounter[25]++;
						break;
				}
			}
		}
		in.close();
		
		/**We create our tree object, which is filled according to the characters in charArray, and the 
		 * frequencies of those characters stored in charCounter.
		 * */ 
		huffManTree necessaryTree = new huffManTree(charCounter, charArray);
		Scanner translateForward = null;
		PrintWriter output = null;
		try {
			translateForward = new Scanner(new File("input.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			output = new PrintWriter(new File("output.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**We take the lines saved from input.txt and write them as Huffman codes in output.txt */ 
		while(translateForward.hasNextLine()) {
			String currentReadLine = translateForward.nextLine();
			String currentWriteLine = "";
			for(int i = 0; i < currentReadLine.length();i++) {
				if(currentReadLine.charAt(i) != ' ') {
					currentWriteLine = currentWriteLine + necessaryTree.getHuffManCode(currentReadLine.charAt(i));
				}
			}
			output.write(currentWriteLine + "\n");
		}
		translateForward.close();
		output.close();
		
		
		/**We take the huffman codes in output.txt and write them in plaintext into huffToTextOutput */ 
		Scanner huffToText = null;
		try {
			huffToText = new Scanner(new File("output.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter huffToTextWriter = null;
		try {
			huffToTextWriter = new PrintWriter(new File("huffToTextOutput.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(huffToText.hasNextLine()) {
			String line = huffToText.nextLine();
			String convertedline = necessaryTree.getActualFromHuffman(line);
			huffToTextWriter.write(convertedline + "\n");
		}
		huffToText.close();
		huffToTextWriter.close();
				
	}
}
