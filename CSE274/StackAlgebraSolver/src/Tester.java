import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;


class Tester {
	
	public static void main(String[] args) {
		System.out.print("Infix expression: ");
		Scanner in = new Scanner(System.in);
		String postfixExpression = in.nextLine();
		InfixExpression expression = new InfixExpression(postfixExpression);
		
		System.out.print("Enter 1 to get postfix expression and 2 to directly evaluate the infix expression: ");
		int choice = in.nextInt();
		if(choice != 1 && choice != 2) {
			System.out.println("Invalid Choice!");
			System.exit(0);
		}
		
		
		if(choice == 1) {
			System.out.println(expression.getPostfixExpression());
			System.out.println(expression.evaluatePostfix());
		}
		if(choice == 2) {
			System.out.println(expression.evaluate());
		}
	}

	
	

	

}
