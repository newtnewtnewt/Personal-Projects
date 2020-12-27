import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Timer;
import java.util.List;

/* Name: Noah Dunn
 * Course: CSE465
 * Date: 1/30/2020
 * Professor: Dr. Zmuda
 * Assignment: Z+- Interpreter
 */

public class Main {
	//  Some initial data stuctures created for the purpose of stashing all the important information we need
	
	//  All the words we can't use as variable names 
	static public ArrayList<String> reservedWords = new ArrayList<String>(Arrays.asList("PRINT", "FOR", "ENDFOR",
			"PROC", "ENDPROC", "CALL", "INT", "BOOL", "STRING", "TRUE", "FALSE"));
	//  All our data types that are legal
	static ArrayList<String> dataTypes = new ArrayList<String>(Arrays.asList("INT", "BOOL", "STRING"));
	//  All legal operators
	static ArrayList<String> operators = new ArrayList<String>(Arrays.asList("=", "+=", "-=", "*="));
	//  A hashtable to store all our Variables by name
	static Hashtable<String, Variable> variableList = new Hashtable<String, Variable>();
	//  Something to keep track of where we are at in the program
	public static int lineNumber = 1;

	public static void main(String args[]) {
		try {
			//  We open up our Z+- program from whatever is listed as the first argument to CLI
			String fileName = args[0];
			Scanner sc = new Scanner(new File(fileName));
			int lineNumber = 1;
			while (sc.hasNext()) {
				try {
					//  A catch-all method that parses one line at a time
					parseLine(sc.nextLine());
				} catch (Exception e) {
					// If we break anywhere in the code, we will be able to determine what line it's on
					System.err.println("RUNTIME ERROR: line " + lineNumber);
					System.exit(0);
				}
				lineNumber++;
			}
			sc.close();
		//  In case the user attempts to input an invalid name for a file
		} catch (FileNotFoundException e) {
			System.err.println("The file you have requested to load does not exist.");
		//  How we evaluate if the index we are trying to access goes out of bounds
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("You have not provided a file name as the first argument.");
		//  What occurs when we try to parse invalid strings as ints
		} catch (NumberFormatException e) {
			System.err.println("RUNTIME ERROR: line " + lineNumber);
			System.exit(0);
		}
	}

	public static void parseLine(String line) throws Exception {
		//  Split our line of code into Array pieces
		String[] codePieces = line.split(" ");
		//  Our type and variable name are case independent
		String varType = codePieces[0].toUpperCase();
		String varName = codePieces[1].toUpperCase();
		/*
		 * These are our initializations or Prints, lines of length 3
		 * PRINT x ; INT x ; STRING x ; BOOL x ;
		 */
		if (codePieces.length == 3) {
			//  If we call a print, we want to make sure it printing something valid
			if (varType.equals("PRINT")) {
				//  This block covers printing any and all of our literals
				if (!variableList.containsKey(varName)) {
					if (varName.equals("TRUE") || varName.equals("FALSE")) {
						System.out.println(varName);
					} else if (varName.startsWith("\"") && varName.endsWith("\"")) {
						System.out.println(varName.substring(1, varName.length() - 1));
					} else {
						System.out.println(Integer.parseInt(varName));
					}
				} else {
					//  This block covers printing variables currently stored in memory
					Variable printVar = variableList.get(varName);
					// If the value is not initialized, we cannot print it
					if(printVar.getInitState() == false) {
						throw new Exception();
					}
					// Printing in all upper case for booleans is required
					if (printVar.getVarType().equals("BOOL")) {
						if (printVar.getVal().equalsIgnoreCase(("FALSE"))) {
							System.out.println("FALSE");
						} else {
							System.out.println("TRUE");
						}
					//  If the value is a String, we want to print it without any quotation marks
					} else if (printVar.getVarType().equals("STRING")) {
						System.out.println(printVar.getVal().substring(1, printVar.getVal().length() - 1));
					} else {
					// If it's an INT, we just print the INT straight up
						System.out.println(variableList.get(varName).getVal());
					}
				}
			} else {
				//  This is a helper method to instantiate variables
				instantiationAction(codePieces);
			}
		}
		/* This is the workhorse for any and all of our traditional operations
		 * x = y ; x = 3 ; x = "string" ; x = true ; x += 3 ; x *= 3 ; x -= 3 ;
		 */
		else if (codePieces.length == 4) {
			//  The operator we need
			String selectedOperator = codePieces[1];
			//  The name of our variable to the left side of the operation
			String leftVariableName = codePieces[0].toUpperCase();
			//  The name of our variable to the right side of the operation
			String rightVariableName = codePieces[2].toUpperCase();
			//  A helper method to carry out the operation between these two values
			twoVarOperation(leftVariableName, selectedOperator, rightVariableName);
		  //  Our procress for handling FOR loops
		} else if (varType.equals("FOR")) {
			//  We need to run a check to make sure all of our FOR loops are closed
			int forCounts = 0; 
			int endForCounts = 0;
			for(int i = 0; i < codePieces.length; i++) {
				if(codePieces[i].toUpperCase().equals("FOR")) {
					forCounts++;
				}
				else if(codePieces[i].toUpperCase().equals("ENDFOR")) {
					endForCounts++;
				}
			}
			//  If we don't close all the FOR loops, we have a serious issue
			if(forCounts != endForCounts) {
				throw new Exception();
			}
			//  A helper to carry out the for loop
			forAction(codePieces);
		}
		else {
			throw new Exception();
		}
	}

	public static void twoVarOperation(String leftVariableName, String selectedOperator, String rightVariableName)
			throws Exception {
		//  The operation is only carried out between two variables
		Variable leftVariable;
		Variable rightVariable;
		//  If the variable on the left is unknown, there is an error
		if (!variableList.containsKey(leftVariableName)) {
			throw new Exception();
		}
		//  If we try to use an invalid operator, we cannot carry out the operation
		if (!operators.contains(selectedOperator)) {
			throw new Exception();
		}
		//  We need the variable if it's valid
		leftVariable = variableList.get(leftVariableName);
		//  If the value on the right side of the operation is a variable and not a literal, we need it as well
		if (variableList.containsKey(rightVariableName)) {
			rightVariable = variableList.get(rightVariableName);
		} else {
			//  Create a variable to check if our literal is appropriate to use
			rightVariable = new Variable("literal", true, leftVariable.getVarType(), rightVariableName);
		}
		//  We attempt the operation
		boolean operationSucceeded = leftVariable.operate(rightVariable, selectedOperator);
		//  If it fails, we break out
		if (!operationSucceeded) {
			throw new Exception();
		}
	}

	public static void forAction(String[] codePieces) throws Exception {
		//  The number of times we are running the for loop, given a value
		int numOfRuns = Integer.parseInt(codePieces[1]);
		//  We need to run the for loop according to the value listed
		for (int q = 0; q < numOfRuns; q++) {
			//  We start from the actual statements in the for loop to process the body
			for (int i = 2; i < codePieces.length; i++) {
				//  In the case of a nested for loop, we need to know where the nested loop begins
				int indexForBeginning = i;
				//  If our for loop is nested
				if (codePieces[i].toUpperCase().equals("FOR")) {
					//  We need to find the end of the for loop
					for (int indexForEnd = i; indexForEnd < codePieces.length; indexForEnd++) {
						if (codePieces[indexForEnd].equals("ENDFOR")) {
							//  We recursively call the nested for loop with a subset of our initial code block
							forAction(Arrays.copyOfRange(codePieces, indexForBeginning, indexForEnd + 1));
							//  We need to skip past the nested for loop and check for additional statements
							i = indexForEnd;
							break;
						}
						//  If we haven't found the ENDFOR, we need to bail
						else if(indexForEnd == codePieces.length - 1) {
							throw new Exception();
						}
					}
				} else if (!codePieces[i].toUpperCase().equals("ENDFOR")) {
					//  Carry out an operation between two variable as normal inside the for loop
					twoVarOperation(codePieces[i].toUpperCase(), codePieces[i + 1].toUpperCase(), codePieces[i + 2].toUpperCase());
					//  Skip the statement after we perform the actions
					i += 3;
				}
			}
		}
	}

	public static void instantiationAction(String[] codePieces) throws Exception {
		//  If the data type is not one of the supported ones, throw an exception
		if (!dataTypes.contains(codePieces[0].toUpperCase())) {
			throw new Exception();
		} else {
			String varName = codePieces[1].toUpperCase();
			String varType = codePieces[0].toUpperCase();
			//  If the variable list already contains the variable, we cannot re-instantiate
			if (variableList.containsKey(varName)) {
				throw new Exception();
			}
			//  Add the variable to the list
			variableList.put(varName, new Variable(varName, false, varType, ""));
		}
	}

}
