import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AlternateJUnitTester {
	@Test
	void testLinkedStack() {
		//Create a LinkedStack to see if its null
		LinkedStack link = new LinkedStack();
		assertTrue(link != null);
		//It's not null
	}

	@Test
	void testPush() {
		//Create a Linked Stack, add some items, and make sure it's not empty 
		LinkedStack link = new LinkedStack();
		link.push('C');
		link.push('Q');
		assertFalse(link.isEmpty());
		//Linked Stack is not empty
	}

	@Test
	void testPop() {
		//Create a Linked Stack, add some items
		LinkedStack link = new LinkedStack();
		link.push('C');
		link.push('Q');
		//Remove both items
		link.pop();
		link.pop();
		//Check if its empty
		assertTrue(link.isEmpty());
		//It is empty
		
		
	}

	@Test
	void testPeek() {
		//Create a linked stack 
		LinkedStack link = new LinkedStack();
		//Add items to the stack
		link.push('Q');
		link.push('C');
		link.push('N');
		//Check if the top is the same as predicted
		assertTrue(link.peek().equals('N'));
		//It is
		
	}

	@Test
	void testIsEmpty() {
		//Create a linked stack 
		LinkedStack link = new LinkedStack();
		//Add items to the stack
		link.push('Q');
		link.push('C');
		link.push('N');
		//Check if non-empty stack is empty  
		assertFalse(link.isEmpty());
		//It is not empty
		link.pop();
		link.pop();
		link.pop();
		//Remove all items and check if the stack is empty
		assertTrue(link.isEmpty());
		//It is empty
		
	}

	@Test
	void testClear() {
		//Create a linked stack 
		LinkedStack link = new LinkedStack();
		//Add items to the stack
		link.push('Q');
		link.push('C');
		link.push('N');
		link.push('B');
		//Clear the Stack
		link.clear();
		//Check if its empty 
		assertTrue(link.isEmpty());
		//It is empty
	}
	

		@Test
		void testInfixExpression() {
			//Create an InfixExpression and see if it actually creates the object
			InfixExpression x = new InfixExpression("1 + 2 + 3");
			assertTrue(x != null);
			//It is not null, object created sucessfully
		}

		@Test
		void testToString() {
			//Create an Infix String Object and test if it outputs the correct infix string 
			InfixExpression x = new InfixExpression("1 + 2 + 3");
			//It outputs correctly 
			
		}

		@Test
		void testClean() {
			//Create an infix expression with misplaced spacing.
			InfixExpression x = new InfixExpression("   1+2 + 1 +   5 + 4 + 55 +  209");
			//Test the clean method
			assertEquals(x.toString(), "1 + 2 + 1 + 5 + 4 + 55 + 209");
			//The infix expression cleans as intended
			
			//Create another more complex infix with misplaced spacing
			InfixExpression y = new InfixExpression("   (1+2) + (1 +   5 + 4) + 55 +  (209/2)    ");
			//Test the clean again
			assertEquals(y.toString(),"( 1 + 2 ) + ( 1 + 5 + 4 ) + 55 + ( 209 / 2 ) ");
			//Provides clean output as expected
		}

		@Test
		void testGetPostfixExpression() {
			//Create an Infix expression for the purpose of converting it to postfix
			InfixExpression x = new InfixExpression("   1+2 + 1 +   5 + 4 + 55 +  209");
			//Convert to postfix and compare 
			assertEquals(x.getPostfixExpression(), "1 2 + 1 + 5 + 4 + 55 + 209 +") ;
			//They are equivalent
			
			//Repeat the process with a more complex statement
			InfixExpression y = new InfixExpression("1/2 + 2 * 3 / (4 + 2)");
			
			//Test for correct postfix output
			assertEquals(y.getPostfixExpression(),"1 2 / 2 3 * 4 2 + / +");
			//Postfix is correct
		}

		@Test
		void testEvaluatePostfix() {
			//Create an infix expression to test if its postfix evaluates correctly
			InfixExpression x = new InfixExpression("1/2 + 2 * 3 / (4 + 2)");
			assertEquals(x.evaluatePostfix(), 1);
			//Output matches the correct answer
			
			//Again with a case that could break
			InfixExpression y = new InfixExpression("(2 + 10 * 3) + (0 * 3 + 7)");
			assertEquals(y.evaluatePostfix(), 39);
			//No breaking, outputs the correct answer
			
			//Again with a case that could break
			InfixExpression z = new InfixExpression("(25 % 10 * 1 * (2 + 4)) + ((0 + 2 ^ 3) + 7)");
			assertEquals(z.evaluatePostfix(), 45);
			//No breaking, outputs the correct answer
		}

		@Test
		void testEvaluate() {
			//Create an infix expression and check if it can be evaluated as is
			InfixExpression x = new InfixExpression("1/2 + 2 * 3 / (4 + 2)");
			assertEquals(x.evaluate() , 1);
			//It evaluates correctly
			
			//Try another infix expression and check if it can be evaluated as is
			InfixExpression y = new InfixExpression("(2 + 10 * 3) + (0 * 3 + 7)");
			assertEquals(y.evaluate(), 39);
			//It evaluates correctly
			
			//Try another infix expression and check if it can be evaluated as is
			InfixExpression z = new InfixExpression("(2 % 10 * 3) ^ (0 * 3 + 7)");
			assertEquals(z.evaluate(), 279936);
			//It evaluates correctly
		}
		
	
}
