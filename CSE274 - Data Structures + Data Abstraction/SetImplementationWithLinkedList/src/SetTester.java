import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class SetTester {

	@Test
	void testSortedSet() {
		/**
		 * Checks to make sure Sorted sets that are generated are not null objects 
		 */
		SortedSet set = new SortedSet();
		assertTrue(set != null);
		/**
		 * They are not null
		 */
		
	}

	@Test
	void testGetCurrentSize() {
		
		 //Creates a set, checks if the default set has a size of 0
		 
		SortedSet set = new SortedSet();
		assertEquals(set.getCurrentSize(), 0);
		
		 //Default set is empty
		set.add("hey");
		set.add("you");
		set.add("guys");
		
		//Adds three items and checks to make sure the size of the list is 3 items long
		assertEquals(set.getCurrentSize(), 3);
		//It is three items long
	}

	@Test
	void testIsEmpty() {
		//Creates a new set, checks if the default set is empty
		SortedSet set = new SortedSet();
		assertTrue(set.isEmpty());
		//It is empty
		set.add("test");
		//Adds an entry and checks if the set is no longer empty
		assertFalse(set.isEmpty());
		//It is no longer empty
		set.remove();
		//Remove the item and check if it is empty again
		assertTrue(set.isEmpty());
		//It is empty again
		
		//Add and remove the same item
		set.add("woo");
		set.remove("woo");
		//Check if the set is empty 
		
		assertTrue(set.isEmpty());
		//The set is empty
	}

	@Test
	void testAdd() {
		//Create a set and check if an item is successfully able to be added.
		SortedSet set = new SortedSet();
		assertTrue(set.add("B"));
		//It is added
		
		//Check to make sure duplicates are not added
		assertFalse(set.add("B"));
		//Duplicates are not added
		set.add("A");
		set.add("X");
		set.add("J");
		
		String[] test = set.toArray();
		//Assert that all entries exist in the set
		assertTrue(test[0].equals("A"));
		assertTrue(test[1].equals("B"));
		assertTrue(test[2].equals("J"));
		assertTrue(test[3].equals("X"));
		//All entries are added sucesfully
		
	}

	@Test
	void testRemoveString() {
		//Create a set and add some items
		SortedSet set = new SortedSet();
		set.add("Q");
		set.add("X");
		set.add("A");
		set.add("Z");
		
		//Remove an item and then make sure it is removed
		assertTrue(set.remove("X"));
		assertTrue(!(set.contains("X")));
		//It is successfully removed
		
		//Check that the user cannot remove figures that do not exist
		assertFalse(set.remove("X"));
		//Cannot remove something that isn't there
		
	}

	@Test
	void testRemove() {
		//Add some elements to a set for testing
		SortedSet set = new SortedSet();
		set.add("Q");
		set.add("X");
		set.add("A");
		set.add("Z");
		
		//Assert that all items are removable arbitrarily(first Slot)
		assertEquals(set.remove(), "A");
		assertEquals(set.remove(), "Q");
		assertEquals(set.remove(), "X");
		assertEquals(set.remove(), "Z");
		assertEquals(set.remove(), null);
		//Empties the set successfully
		
	}

	@Test
	void testClear() {
		//Create a set for testing
		SortedSet set = new SortedSet();
		set.add("Q");
		set.add("X");
		set.add("A");
		set.add("Z");
		
		set.clear();
		//Clear the set and check if the size is 0 and check if all items are gone
		assertEquals(set.getCurrentSize(), 0);
		assertFalse(set.contains("Q"));
		assertFalse(set.contains("X"));
		assertFalse(set.contains("A"));
		assertFalse(set.contains("Z"));
		//Items are gone, size is 0
	}

	@Test
	void testContains() {
		//Create a set and add items
		SortedSet set = new SortedSet();
		set.add("Q");
		set.add("X");
		set.add("A");
		set.add("Z");
		//Assert that all items added are in the set
		assertTrue(set.contains("Q"));
		assertTrue(set.contains("X"));
		assertTrue(set.contains("A"));
		assertTrue(set.contains("Z"));
		//All items are present and accounted for
	}
	

	@Test
	void testToString() {
		//Create a set and add items
		SortedSet set = new SortedSet();
		set.add("Q");
		set.add("X");
		set.add("A");
		set.add("Z");
		//Check that the string output is as desired
		assertTrue(set.toString().equals("[ A Q X Z ]"));
		//String output is as desired
	}

	@Test
	void testToArray() {
		//Create a set and add items for testing
		SortedSet set = new SortedSet();
		set.add("Q");
		set.add("X");
		set.add("A");
		set.add("Z");
		
		//Create the correct array
		String[] answer =  new String[]{"A","Q","X","Z"};
		String[] input = set.toArray();
		//Use a for-loop with a boolean to see if the array output is correct
		boolean arrayCompare = true;
		
		for(int i = 0; i <answer.length; i++) {
			if(answer.length != input.length) {
				arrayCompare = false;
				break;
			}
			if(!(answer[i].equals(input[i]))) {
				arrayCompare = false;
				break;
			}
		}
		assertTrue(arrayCompare);
		//Array Output is correct
		}
	

}
