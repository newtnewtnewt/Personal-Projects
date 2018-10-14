import java.util.ArrayList;
import java.util.Arrays;

public class InfixExpression {
	private String infix;
	
	public InfixExpression(String x){
		/** Constructs a cleaned up Infix expression
		 *  @throws Illegal Argument Exception if the String parameter is not valid
		 */
		infix = x;
		clean();
		try {
		if(isValid() == false) {
			throw new IllegalArgumentException();
		}
		}
		catch(IllegalArgumentException e) {
			System.out.println("Fail: Invalid expression");
			System.exit(0);
		}
		
	}
	
	public String toString() {
		/** @return the infix expression as a string
		 */
		return infix;
	}
	
	private boolean isBalanced() {
		/** @return true if the infix expression has balanced parenthesis, even if the rest is broken
		 */
		LinkedStack<Character> delimStack = new LinkedStack<Character>();
		
		int charCount = infix.length();
		boolean isBalanced = true;
		int index = 0;
		char nextChar = ' ';
		
		while(isBalanced && (index < charCount)){
			nextChar = infix.charAt(index);
			
			switch(nextChar) {
			case '(' : case '[': case '{':
				delimStack.push(nextChar);
				break;
			
			case ')' : case ']': case '}':
				if(delimStack.isEmpty()) {
					return false;
				}
				else {
					char openDelim = delimStack.pop();
					isBalanced = isPaired(openDelim, nextChar);
				}
				break;

			default: break;
			}
			index++;
		}
		if(!delimStack.isEmpty()) {
			return false;
		}
		return isBalanced;
	}
	private boolean isPaired(char open, char close) {
		return(open == '(' && close == ')') || (open == '[' && close == ']') || (open == '{' && close == '}');  
	}
	
	private boolean isValid() {
		/**@return true if the infix expression is valid in all respects 
		 * 
		 */
		boolean validChecker = true;
		if(isBalanced() != true) {
			return false;
		}
		String validChars = "+\\-*/%^()1234567890 ";
		String validOperators = "+\\-*/%^";
		int operandCount = 0;
		int operatorCount = 0;
		String cleanInfix = infix.replaceAll(" ", "");
		
		
		for(int i = 0; i < cleanInfix.length(); i++) {
			if(!validChars.contains(""+cleanInfix.charAt(i))) {

				return false;
			}
						
			while(i != cleanInfix.length() && Character.isDigit(cleanInfix.charAt(i))) {
				
				if((i == cleanInfix.length()-1 && Character.isDigit(cleanInfix.charAt(i))) || ((i !=cleanInfix.length() - 2 && !Character.isDigit(cleanInfix.charAt(i+1))) || (i == cleanInfix.length() - 2 && cleanInfix.charAt(i+1) == ')'))) {
					operandCount++;	
				}
				i++;
			}
			
			if(i != cleanInfix.length() && validOperators.contains("" + cleanInfix.charAt(i))) {
				operatorCount++;
				
				if(i - 1 < 0 || (!Character.isDigit(cleanInfix.charAt(i-1)) && cleanInfix.charAt(i-1) != ')') || (!Character.isDigit(cleanInfix.charAt(i+1)) && cleanInfix.charAt(i+1) != '(') ) {
					
					return false;
				}
			}
			if(i != cleanInfix.length() && cleanInfix.charAt(i) == '(') {
				if(cleanInfix.charAt(i + 1) == ')') {
					
					return false;
				}
			}
			
			
			
		}
			
		if(operatorCount != 1  && operandCount-operatorCount != 1 ) {
			return false;
		}
		
		
		
		
		
		
		
		return validChecker;
	}
	
	public void clean() {
		/**This method puts a space between all adjacent tokens and removes all trailing/leading/unnecessary spaces
		 * This exists to help the constructor.
		 */
		infix = infix.replaceAll(" ", "");
		String fixedString = "";
		for(int i = 0; i < infix.length(); i++) {
			while(i != infix.length() && Character.isDigit(infix.charAt(i))) {
				fixedString = fixedString + "" + infix.charAt(i);
				i++;
				if(i != infix.length() && !Character.isDigit(infix.charAt(i))) {
					fixedString = fixedString + " ";
				}
			}
			if(i == infix.length()) {
				break;
			}
			if((!Character.isDigit(infix.charAt(i)))) {
				fixedString = fixedString + "" + infix.charAt(i);
			}
			if(i != infix.length()) {
				fixedString = fixedString + " ";
			}
		}
		infix = fixedString;
		
		
		
	}
	
	public String getPostfixExpression() {
		/** @return the postfix expression that corresponds to the given infix expression. 
		 * This method will take input from infix and transform the given input into proper postfix notation.
		 * This will be used in the constructor for Infix expressions, in order to provide correct evaluation and notation.
		 */
		LinkedStack<Character> operatorStack = new LinkedStack<Character>();
		String postfixString = "";
		
		for(int i = 0; i < infix.length(); i++) {
			char nextChar = infix.charAt(i);
			
			switch(nextChar) {
				case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': case '0':
					String variable = "";
					while(i != infix.length() && Character.isDigit(infix.charAt(i))) {
						variable = variable + infix.charAt(i);
						i++;
					
					}
					postfixString = postfixString + variable + " ";
					
					break;
				
				case '^' :
					operatorStack.push(nextChar);
					break;
				
				case '+' : case '-' : case '*' : case '/': case '%':
					while(!operatorStack.isEmpty() && precedenceEval(nextChar) <= precedenceEval(operatorStack.peek())) {
						postfixString = postfixString + operatorStack.peek() + " ";
						operatorStack.pop();
					}
					operatorStack.push(nextChar);
					break;
				
				case '(' :
					operatorStack.push(nextChar);
					break;
				
				case ')':
					char topOperator = operatorStack.pop();
					while(topOperator != '(') {
						postfixString = postfixString + topOperator + " ";
						topOperator = operatorStack.pop();
					}
					break;
				default: break;
			}
			
		}
		while(!operatorStack.isEmpty()) {
			char topOperator = operatorStack.pop();
			postfixString = postfixString + topOperator + " "; 
		}
		if(postfixString.charAt(postfixString.length()-1) == ' ') {
			postfixString = postfixString.substring(0, postfixString.length()-1);
			
		}
		
		return postfixString;
	}
	
	private int precedenceEval(char precedenceInput) {
		switch(precedenceInput) {
			case '-' : case '+':
				return 0;
				
				
			case '*' : case '/': case '%':
				return 1;
				
			case '^':
				return 2;

			default:
				return -1;
		}
		
	}
	
	public int evaluatePostfix() {
		/**@return the result of the calculated postfix expression
		 * This method takes the postfix expression generated by the code and evaluates its value. 
		 */
		LinkedStack<String> evalStack = new LinkedStack<String>();
		String postfixString = getPostfixExpression();
		for(int i = 0; i < postfixString.length(); i++) {
			char nextChar = postfixString.charAt(i);
			switch(nextChar) {
			case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': case '0':
				
				String variable = "";
				while(i != postfixString.length() && Character.isDigit(postfixString.charAt(i))) {
					variable = variable + postfixString.charAt(i);
					i++;
				}
				evalStack.push(variable);
				break;
			
			case '+' : case '-' : case '*' : case '/' : case '%': case '^':
				int operandTwo = Integer.parseInt(evalStack.pop());
				int operandOne = Integer.parseInt(evalStack.pop());
				int result = 0;
				if(nextChar == '+') {
					result = operandOne + operandTwo;
				}
				else if(nextChar == '-') {
					result = operandOne - operandTwo;
				}
				else if(nextChar == '*') {
					result = operandOne * operandTwo;
				}
				else if(nextChar == '/') {
					result = operandOne / operandTwo;
				}
				else if(nextChar == '%') {
					result = operandOne % operandTwo;
				}
				else if(nextChar == '^') {
					result = (int) Math.pow(operandOne, operandTwo);
				}
				evalStack.push("" + result);
				break;
			
			default: break;
			}
	}
		return Integer.parseInt(evalStack.pop());
	}
	
	public int evaluate() {
		/** @return the result of the calculated infix expression
		 *  This method calculates the infix expression without requiring a transfer to postfix notation
		 */
		LinkedStack<Character> operatorStack = new LinkedStack<Character>();
		LinkedStack<String> valueStack = new LinkedStack<String>();
		
		for(int i = 0; i < infix.length(); i++) {
			char nextChar = infix.charAt(i);
			
			switch(nextChar) {
				case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': case '0':
					String variable = "";
					while(i != infix.length() && Character.isDigit(infix.charAt(i))) {
						variable = variable + infix.charAt(i);
						i++;
					
					}
					valueStack.push(variable);
					break;
				
				case '^' :
					
					operatorStack.push(nextChar);
					break;
				
				case '+' : case '-' : case '*' : case '/' : case '%':
					while(!operatorStack.isEmpty() && precedenceEval(nextChar) <= precedenceEval(operatorStack.peek()) ) {
						char topOperator = operatorStack.pop();
						int operandTwo = Integer.parseInt(valueStack.pop());
						int operandOne = Integer.parseInt(valueStack.pop());
						int result = 0;
						if(topOperator == '+') {
							result = operandOne + operandTwo;
						}
						else if(topOperator == '-') {
							result = operandOne - operandTwo;
						}
						else if(topOperator == '*') {
							result = operandOne * operandTwo;
						}
						else if(topOperator == '/') {
							result = operandOne / operandTwo;
						}
						else if(topOperator == '%') {
							result = operandOne % operandTwo;
						}
						else if(topOperator == '^') {
							
							result = (int) Math.pow(operandOne, operandTwo);
						}
						valueStack.push("" + result);
					}
					operatorStack.push(nextChar);
					break;
				
				case '(' :
					operatorStack.push(nextChar);
					break;
				
				case ')':
					char topOperator = operatorStack.pop();
					while(topOperator != '(') {
						
						int operandTwo = Integer.parseInt(valueStack.pop());
						int operandOne = Integer.parseInt(valueStack.pop());
						int result = 0;
						if(topOperator == '+') {
							result = operandOne + operandTwo;
						}
						else if(topOperator == '-') {
							result = operandOne - operandTwo;
						}
						else if(topOperator == '*') {
							result = operandOne * operandTwo;
						}
						else if(topOperator == '/') {
							result = operandOne / operandTwo;
						}
						else if(topOperator == '%') {
							result = operandOne % operandTwo;
						}
						else if(topOperator == '^') {
							
							result = (int) Math.pow(operandOne, operandTwo);
						}
						valueStack.push("" + result);
						topOperator = operatorStack.pop();
						
					}
					
					break;
				default: break;
			}
		}
		while(!operatorStack.isEmpty()) {
			char topOperator = operatorStack.pop();
			int operandTwo = Integer.parseInt(valueStack.pop());
			int operandOne = Integer.parseInt(valueStack.pop());
			int result = 0;
			if(topOperator == '+') {
				result = operandOne + operandTwo;
			}
			else if(topOperator == '-') {
				result = operandOne - operandTwo;
			}
			else if(topOperator == '*') {
				result = operandOne * operandTwo;
			}
			else if(topOperator == '/') {
				result = operandOne / operandTwo;
			}
			else if(topOperator == '%') {
				result = operandOne % operandTwo;
			}
			else if(topOperator == '^') {
				result = (int) Math.pow(operandOne, operandTwo);
			}
			valueStack.push("" + result);

		}
		return Integer.parseInt(valueStack.peek());
		
	}
	
}
