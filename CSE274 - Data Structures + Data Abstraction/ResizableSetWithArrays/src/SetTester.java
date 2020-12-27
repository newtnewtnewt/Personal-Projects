import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class SetTester {

	@Test
	void testResizableArraySet() {
		//Create an empty set of default size
		ResizableArraySet set = new ResizableArraySet();
		//Make sure that the object was actually created
		assertTrue(set != null);
		//Object is created
	}

	@Test
	void testResizableArraySetInt() {
		//Create a set with a set size
		ResizableArraySet set = new ResizableArraySet(15);
		//Make sure the object was actually created
		assertTrue(set != null);
		//Object is created
	}

	@Test
	void testGetSize() {
		//Create a set of size 10
		ResizableArraySet set = new ResizableArraySet();
		assertTrue(set.getSize() == 0);
		for(int i = 0; i < 10; i++) {
			set.add(i);
			//Make sure that we can actually put 10 elements in that set.
			assertTrue(set.getSize() == i + 1);
			//10 elements are added successfully
		}
		set.add(11);
		//Make sure that the size changes to accommodate new elements when the set is full
		assertTrue(set.getSize() == 11);
		//Size changes to add the new item
		
		set.add(1);
		//Make sure size doesn't change for duplicates(No duplicates allowed
		assertTrue(set.getSize() == 11);
		//Size does not change for duplicates
		
	}

	@Test
	void testIsEmpty() {
		//Create an empty set
		ResizableArraySet set = new ResizableArraySet(10);
		//Check if the set is empty by default
		assertTrue(set.isEmpty());
		//Set is empty by default
		
		//Add an element to check if the set is not empty
		set.add(1);
		//Check if bag is empty
		assertFalse(set.isEmpty());
		//Bag is empty
		
		//Remove the element to see if the set returns to empty
		set.remove();
		assertTrue(set.isEmpty());
		//Set is now empty again
	}

	@Test
	void testAdd() {
		//Create a default set to test adding on
		ResizableArraySet set = new ResizableArraySet();
		for(int i = 0; i < 10; i++) {
			//Checks to make sure new integers can be added to the set
			assertTrue(set.add(i));
			//Integers are added
		}
		//Checks to make sure duplicates will not add
		assertFalse(set.add(9));
		//Duplicates won't add
		
		
		//Create a set with a custom size to test out
		ResizableArraySet otherSet = new ResizableArraySet(15);
		for(int i = 0; i < 10; i++) {
			//Checks to make sure new integers can be added to the set
			assertTrue(otherSet.add(i));
			//Integers are added
		}
		//Checks to make sure duplicates will not add
		assertFalse(otherSet.add(9));
		//Duplicates won't add
	}

	@Test
	void testRemoveInt() {
		//Create an empty set for testing
		ResizableArraySet set = new ResizableArraySet(10);
		//Add several elements to insure element removal is not fixed to a location
		set.add(1);
		set.add(2);
		set.add(4);
		set.add(5);
		//Try to remove 4
		set.remove(4);
		assertEquals(set.getSize(),3);
		assertFalse(set.contains(4));
		//4 is sucessfully removed from the set 
		
		
	}

	@Test
	void testRemove() {
		//Create an empty set for testing
		ResizableArraySet set = new ResizableArraySet(10);
		//Add elements to be removed
		set.add(1);
		set.add(2);
		set.add(4);
		set.add(5);
		int initialSize = 4;
		
		for(int i = 0; i < initialSize; i++) {
			set.remove();
			//Determine if an arbitrary element is removed
			assertTrue(set.getSize() == initialSize - (i + 1));
			//An arbitrary element is removed
			
		}
		//Trying to remove an element when the array should return MIN VALUE
		assertEquals(set.remove(),Integer.MIN_VALUE);
		//It returns MIN_VALUE
		assertTrue(set.getSize() == 0);
		
	}

	@Test
	void testClear() {
		//Create an array for testing 
		ResizableArraySet set = new ResizableArraySet(10);
		//Add some elements 
		set.add(1);
		set.add(2);
		set.add(4);
		set.add(5);
		set.clear();
		//Clear the set to check the function
		assertTrue(set.getSize() == 0);
		//The set has been cleared
	}

	@Test
	void testContains() {
		//Create an empty set and fill it with elements for testing
		ResizableArraySet set = new ResizableArraySet(10);
		set.add(1);
		set.add(2);
		set.add(4);
		set.add(5);
		//Check if each element in the array works for contains
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
		assertTrue(set.contains(4));
		assertTrue(set.contains(5));
		//The contains method works for all elements
		
		//Check if contains returns false for removed elements
		set.remove(1);
		assertFalse(set.contains(1));
		set.remove(2);
		assertFalse(set.contains(2));
		set.remove(4);
		assertFalse(set.contains(4));
		set.remove(5);
		assertFalse(set.contains(5));
		//Contains returns false for all removed elements
		
		
		
	}

	@Test
	void testUnion() {
		//Create two sets and add elements to them in order to test
		ResizableArraySet set = new ResizableArraySet(15);
		set.add(1);
		set.add(2);
		set.add(4);
		set.add(5);
		
		ResizableArraySet otherSet = new ResizableArraySet(10);
		otherSet.add(3);
		otherSet.add(6);
		otherSet.add(2);
		otherSet.add(4);
		otherSet.add(5);
		
		//Create a set that is the union of the two previously created sets
		//The union of {1,2,4,5} and {3,6,2,4,5} should be {1,2,4,5,3,6}
		ResizableArraySet test =(ResizableArraySet)set.union(otherSet);
		assertTrue(test.toArray().length == 6);
		assertTrue(test.toArray()[0] == 1);
		assertTrue(test.toArray()[1] == 2);
		assertTrue(test.toArray()[2] == 4);
		assertTrue(test.toArray()[3] == 5);
		assertTrue(test.toArray()[4] == 3);
		assertTrue(test.toArray()[5] == 6);
		//The union function returns the correct answer even with duplicates and different sized sets
		
		//The union of {3,6,2,4,5} and {1,2,4,5} should be {3,6,2,4,5,1} 
		ResizableArraySet otherTest =(ResizableArraySet)otherSet.union(set);
		assertTrue(otherTest.toArray().length == 6);
		assertTrue(otherTest.toArray()[0] == 3);
		assertTrue(otherTest.toArray()[1] == 6);
		assertTrue(otherTest.toArray()[2] == 2);
		assertTrue(otherTest.toArray()[3] == 4);
		assertTrue(otherTest.toArray()[4] == 5);
		assertTrue(otherTest.toArray()[5] == 1);
		//Outputs the correct union answer
		
	}

	@Test
	void testIntersection() {
		//Create two sets and add elements to them in order to test
		ResizableArraySet set = new ResizableArraySet(10);
		set.add(1);
		set.add(2);
		set.add(2);
		set.add(4);
		set.add(5);
		ResizableArraySet otherSet = new ResizableArraySet(10);
		otherSet.add(3);
		otherSet.add(6);
		otherSet.add(2);
		otherSet.add(4);
		otherSet.add(5);
		
		//The intersection of {1,2,2,4,5} and {3,6,2,4,5} should be {2,4,5}
		//Create a set to test this 
		ResizableArraySet test = (ResizableArraySet)set.intersection(otherSet);
		
		assertTrue(test.toArray().length == 3);
		assertTrue(test.toArray()[0] == 2);
		assertTrue(test.toArray()[1] == 4);
		assertTrue(test.toArray()[2] == 5);
		//The set resulting from intersection is the correct answer/
		
		//Check to make sure intersecting two empty sets doesn't break the code
		ResizableArraySet zeroTest = new ResizableArraySet(0);
		ResizableArraySet zeroTestP2 = new ResizableArraySet(10);
		ResizableArraySet zeroTestFinal = (ResizableArraySet)zeroTest.intersection(zeroTestP2);
		assertTrue(zeroTestFinal.toArray().length == 0);
		//Code does not break
		
		//Check to make sure one empty set and another non empty set doesn't break the code
		ResizableArraySet otherZeroTest = new ResizableArraySet(0);
		ResizableArraySet otherZeroTestP2 = new ResizableArraySet(1);
		otherZeroTestP2.add(1);	
		ResizableArraySet otherZeroTestFinal = (ResizableArraySet)otherZeroTest.intersection(otherZeroTestP2);
		assertTrue(otherZeroTestFinal.toArray().length == 0);
		//Code does not break
		
	}

	@Test
	void testDifference() {
		//Create two sets and add elements to each for testing
		ResizableArraySet set = new ResizableArraySet(10);
		set.add(1);
		set.add(2);
		set.add(2);
		set.add(4);
		set.add(5);
		ResizableArraySet otherSet = new ResizableArraySet(10);
		otherSet.add(3);
		otherSet.add(6);
		otherSet.add(2);
		otherSet.add(4);
		otherSet.add(5);
		
		//The difference between {1,2,2,4,5} and {3,6,2,4,5} should be {1}
		ResizableArraySet test = (ResizableArraySet)set.difference(otherSet);
		assertTrue(test.toArray().length == 1);
		assertTrue(test.toArray()[0] == 1);
		//The correct result is achieved
		
		//The difference between  {3,6,2,4,5} and {1,2,2,4,5} should be {3,6}
		ResizableArraySet otherTest = (ResizableArraySet)otherSet.difference(set);
		assertTrue(otherTest.toArray().length == 2);
		assertTrue(otherTest.toArray()[0] == 3);
		assertTrue(otherTest.toArray()[1] == 6);
		//It is correct
		
		//Check to make sure the difference between two sets that are exactly the same is an empty set
		ResizableArraySet finalTest = (ResizableArraySet)set.difference(set);
		assertTrue(finalTest.toArray().length == 0);
		//It is an empty set
	}	

	@Test
	void testToArray() {
		//Create a set and fill it with values for testing 
		ResizableArraySet set = new ResizableArraySet(10);
		set.add(1);
		set.add(2);
		set.add(2);
		set.add(4);
		set.add(5);

		//Call the toArray to check if all elements in the array match what was added
		int[] testArray = set.toArray();
		assertTrue(testArray.length == 4);
		assertTrue(testArray[0] == 1);
		assertTrue(testArray[1] == 2);
		assertTrue(testArray[2] == 4);
		assertTrue(testArray[3] == 5);
		//They match in the correct order
		
		//Check if toArray on an emptySet returns an empty Array
		ResizableArraySet emptySet = new ResizableArraySet(0);
		assertTrue(emptySet.toArray().length == 0);
		//The array is empty
		
	}

}
