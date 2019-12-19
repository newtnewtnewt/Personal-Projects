import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedDequeTester {

	@Test
	void testLinkedDeque() {
		//Create a LinkedDeque and check if its null
		LinkedDeque x = new LinkedDeque();
		assertTrue(x != null);
		//It is not null
	}

	@Test
	void testClear() {
		//Create a linked Deque, add some items, and make sure isEmpty returns false
		LinkedDeque x = new LinkedDeque();
		x.addToFront(1);
		x.addToFront(2);
		x.addToFront(3);
		assertFalse(x.isEmpty());
		//Returns false
		
		//Clear it and try again
		x.clear();
		assertTrue(x.isEmpty());
		//The Linked Deque is indeed empty.
	}

	@Test
	void testAddToBack() {
		//Create a Linked Deque, add elements one by one, and check to make sure they are correctly ordered
		LinkedDeque x = new LinkedDeque();
		x.addToBack(1);
		assertEquals(x.toString(), "[1]");
		x.addToBack(2);
		assertEquals(x.toString(), "[1, 2]");
		x.addToBack(3);
		assertEquals(x.toString(), "[1, 2, 3]");
		//Each case passes.
	}

	@Test
	void testAddToFront() {
		//Create a Linked Deque, add elements one by one, and check to make sure they are correctly ordered
		LinkedDeque x = new LinkedDeque();
		x.addToFront(1);
		assertEquals(x.toString(), "[1]");
		x.addToFront(2);
		assertEquals(x.toString(), "[2, 1]");
		x.addToFront(3);
		assertEquals(x.toString(), "[3, 2, 1]");
		//Each case passes.
	}

	@Test
	void testGetBack() {
		//Create a Linked Deck, add elements to front, check if back remains the same
		LinkedDeque x = new LinkedDeque();
		x.addToFront(1);
		assertEquals(x.getBack(), 1);
		x.addToFront(2);
		assertEquals(x.getBack(), 1);
		x.addToFront(3);
		assertEquals(x.getBack(), 1);
		//Back remains the same
		
		
		//Add elements to back and see if back changes dynamically
		x.addToBack(4);
		assertEquals(x.getBack(), 4);
		x.addToBack(5);
		assertEquals(x.getBack(), 5);
		x.addToBack(6);
		assertEquals(x.getBack(), 6);
		//Back changes with each add
		
		//Check exception throwing
		LinkedDeque z = new LinkedDeque();
		z.getBack();
		//Exception thrown
	}

	@Test
	void testGetFront() {
		
		//Create a Linked Deck, Add elements to front and see if front changes dynamically
		LinkedDeque x = new LinkedDeque();
		x.addToFront(1);
		assertEquals(x.getFront(), 1);
		x.addToFront(2);
		assertEquals(x.getFront(), 2);
		x.addToFront(3);
		assertEquals(x.getFront(), 3);
		//Back remains the same
				
				
		
		//Add elements to back, check if front remains the same
		x.addToBack(4);
		assertEquals(x.getFront(), 3);
		x.addToBack(5);
		assertEquals(x.getFront(), 3);
		x.addToBack(6);
		assertEquals(x.getFront(), 3);
		//Back changes with each add
		
		LinkedDeque z = new LinkedDeque();
		z.getFront();
		//Exception thrown
	}

	@Test
	void testRemoveFront() {
		//Create a Linked Deque, add elements, and check if removing the front works correctly
		LinkedDeque x = new LinkedDeque();
		x.addToFront(1);
		assertEquals(x.getFront(), 1);
		x.addToFront(2);
		assertEquals(x.getFront(), 2);
		x.addToFront(3);
		assertEquals(x.getFront(), 3);
		x.removeFront();
		assertEquals(x.toString(), "[2, 1]");
		//Removing the front actually removes the front
		
		//Check exception throwing
		LinkedDeque z = new LinkedDeque();
		z.removeFront();
		//Exception thrown
	}

	@Test
	void testRemoveBack() {
		//Create a Linked Deque, add elements, and check if removing the back works correctly
		LinkedDeque x = new LinkedDeque();
		x.addToFront(1);
		assertEquals(x.getFront(), 1);
		x.addToFront(2);
		assertEquals(x.getFront(), 2);
		x.addToFront(3);
		assertEquals(x.getFront(), 3);
		x.removeBack();
		assertEquals(x.toString(), "[3, 2]");
		//Removing the back actually removes the back
		
		//Check exception throwing
		LinkedDeque z = new LinkedDeque();
		z.removeBack();
		//Exception thrown
	}

	@Test
	void testIsEmpty() {
		//Check if an empty Linked Deque is empty
		LinkedDeque x = new LinkedDeque();
		assertTrue(x.isEmpty());
		//It is 
		
		//Add a few elements and check again
		x.addToBack(1);
		x.addToFront(2);
		assertFalse(x.isEmpty());
		//It says a non-empty deque is not empty
		
	}

	@Test
	void testToString() {
		//Create a linked Deque, insure empty ToString
		LinkedDeque x = new LinkedDeque();
		assertEquals(x.toString(),"[]");
		//To String is empty
		
		//Add elements to linked Deque
		x.addToFront(1);
		x.addToFront(2);
		x.addToFront(3);
		//Check if toString ordering is correct
		assertEquals(x.toString(), "[3, 2, 1]");
		//It is 
		
		//Add elements to linked Deque
		LinkedDeque y = new LinkedDeque();
		y.addToBack(1);
		y.addToBack(2);
		y.addToBack(3);
		//Check if toString ordering is correct
		assertEquals(y.toString(), "[1, 2, 3]");
		//It is
		
	}

}
