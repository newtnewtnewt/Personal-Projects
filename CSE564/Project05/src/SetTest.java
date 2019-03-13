import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SetTest {
	
	Set<Integer> testSet;
	Set<Integer> testSet2;
	@BeforeEach public void intitialize() {
		testSet = new Set<Integer>();
		testSet2 = new Set<Integer>();
	}
	
	@Test
	void testHashCode() {
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		assertEquals(testSet.hashCode(), 8);
		
	}

	@Test
	void testSet() {
		assertNotNull(testSet);
	}

	@Test
	void testAdd() {
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		assertTrue(testSet.contains(1));
		assertTrue(testSet.contains(2));
		assertTrue(testSet.contains(3));
	}

	@Test
	void testRemove() throws Exception {
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		assertTrue(testSet.contains(1));
		assertTrue(testSet.contains(2));
		assertTrue(testSet.contains(3));
		
		
		
		testSet.remove(2);
		testSet.remove(3);
		testSet.remove(1);
		
		
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		
		
		/**
		 * It is not possible to achieve 100% coverage due to this method checking a thrown exception.
		 */
		assertThrows(Exception.class, () -> {testSet.remove(4);});
		
	}

	@Test
	void testUnion() {
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		
		testSet2.add(5);
		testSet2.add(2);
		
		Set<Integer> combo = testSet.union(testSet2);
		assertEquals(combo.size(), 4);
		assertTrue(combo.contains(1));
		assertTrue(combo.contains(2));
		assertTrue(combo.contains(3));
		assertTrue(combo.contains(5));



	}

	@Test
	void testIntersection() {
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		
		testSet2.add(5);
		testSet2.add(2);
		
		Set<Integer> testSet3 = new Set<Integer>();
		
		
		Set<Integer> combo = testSet.intersection(testSet2);
		assertEquals(combo.size(),1);
		assertTrue(combo.contains(2));
		
		combo = testSet.intersection(testSet3);
		assertNull(combo);
		
	}

	@Test
	void testDifference() {
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		
		testSet2.add(5);
		testSet2.add(2);
		
		Set<Integer> combo = testSet.difference(testSet2);
		assertEquals(combo.size(), 2);
		assertTrue(combo.contains(1));
		assertTrue(combo.contains(3));
		
		Set<Integer> testSet3 = new Set<Integer>();
		combo = testSet.difference(testSet3);
		assertEquals(testSet, combo);
	}

	@Test
	void testContains() {
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		
		assertTrue(testSet.contains(2));
		assertFalse(testSet.contains(4));
	}

	@Test
	void testIsEmpty() {
		assertTrue(testSet.isEmpty());
		testSet.add(1);
		assertFalse(testSet.isEmpty());
	}

	@Test
	void testSize() {
		assertEquals(testSet.size(), 0);
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		assertEquals(testSet.size(), 3);
	}

	@Test
	void testIterator() {
		assertNotNull(testSet.iterator());
	}

	@Test
	void testToString() {
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		
		testSet2.add(5);
		testSet2.add(2);
		assertEquals(testSet.toString(), "1 2 3");
	}

	@Test
	void testEqualsObject() {
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		
		testSet2.add(5);
		testSet2.add(2);
		
		assertFalse(testSet.equals(testSet2));
		
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		
		Set<Integer> testSet3 = new Set<Integer>();
		testSet3.add(3);
		testSet3.add(2);
		testSet3.add(1);
		
		assertTrue(testSet.equals(testSet3));
		
		testSet.add(4);
		testSet3.add(5);
		
		assertFalse(testSet.equals(testSet3));
		
		
		
	}

}
