import java.util.Stack;

public class StackProblems {

	public static void main(String[] args) {
		// You can write code to test the methods here 
		
	}

	/*
	 * Computes the sum of all the numbers in the stack
	 */
	public static int sum(Stack<Integer> data) {
		int total = 0;
		while(!data.empty()) {
			total = total + data.pop();
		}
		return total;
	}
	
	/*
	 * Computes the alternating sum of all the numbers in the stack. For example, if the
	 * stack contained 4, 1, 2, 7, 2, 8, 4, then the numbers that would
	 * be added would be 4 - 1 + 2 - 7 + 2 - 8 + 4 = -4
	 * Here 4 is the top of the stack and there always going to be odd number of integers
	 * in the stack.
	 */
	public static int alternatingSum(Stack<Integer> data) {
		int sign = 1;
		int total = 0;
		while(!data.empty()) {
			total = total + (sign * data.pop());
			sign = sign * -1;
		}
		
		return total;
	}	

	/*
	 * Computes the sum of all the numbers in the stack. However, if two or more
	 * numbers in a row are equal, only add one of them. So, for example, if the
	 * stack contained 4, 1, 2, 2, 7, 2, 8, 8, 8, 4, then the numbers that would
	 * be added would be 4 + 1 + 2 + 7 + 2 + 8 + 4 = 28. Here 4 is the top of the stack.
	 */
	public static int sumSkipDuplicates(Stack<Integer> data) {
		int total = 0;
		while(!data.empty()) {
			int tempvalue = data.pop();
			total = total + tempvalue;
			while(!data.isEmpty() && tempvalue == data.peek()) {
				data.pop();
			}
		}
		return total;
		
	}

	/*
	 * Puts all of the characters of a string into a stack, with the first
	 * character of the string at the bottom of the stack and the last character
	 * of the string at the top of the stack.
	 */
	public static Stack<Character> stringToStack(String s) {
		Stack<Character> charStack = new Stack<Character>();
		for(int i = 0; i < s.length(); i++) {
			charStack.push(s.charAt(i));
		}
		return charStack;

	}

	/*
	 * Puts all of the digits of an integer into a stack, with the first
	 * digit of the integer at the bottom of the stack and the last digit
	 * of the integer at the top of the stack.
	 */
	public static Stack<Integer> integerToStack(int n) {
		String grabber = n + "";
		Stack<Integer> intStacker = new Stack<Integer>();
		for(int i = 0; i < grabber.length(); i++) {
			if(i == grabber.length()-1) {
				intStacker.push(Integer.parseInt(grabber.substring(grabber.length()-1)));
			}
			else {
				intStacker.push(Integer.parseInt(grabber.substring(i, i+1)));
			}
		}
		return intStacker;
		

	}
	
	
	/*
	 * Reverses a given stack, so that the top of the stack becomes the bottom
	 * and the bottom becomes the top.
	 */
	public static void reverseStack(Stack<Integer> s) {
	Stack<Integer> reverseStacker = new Stack<Integer>();
	String reverseString = "";
	while(!s.empty()) {
		reverseString = reverseString + s.pop();
	}
	for(int i = 0; i < reverseString.length(); i++) {
		
		if(i == reverseString.length()-1) {
			s.push(Integer.parseInt(reverseString.substring(reverseString.length()-1)));
		}
		else {
			s.push(Integer.parseInt(reverseString.substring(i, i+1)));
		}
	
		
	}
	}

	/*
	 * A palindrome reads the same forward and backward. Use a stack to
	 * determine if a given string is a palindrome. Challenge: try not to use
	 * any additional variables (except a counter for any loop). Just the given
	 * string and a stack of Characters.
	 */
	public static boolean isPalindrome(String s) {
		Stack<Character> pallyStack = new Stack<Character>();
		for(int i = 0; i <s.length(); i++) {
			if(i < s.length()/2) {
				pallyStack.push(s.charAt(i));
			}
			else {
				if(s.length() % 2 == 1 && i == s.length()/2) {
					
				}
				else if(pallyStack.pop() != s.charAt(i)) {
					return false;
				}
			}
		}
		return true;
		
		
	}

}
