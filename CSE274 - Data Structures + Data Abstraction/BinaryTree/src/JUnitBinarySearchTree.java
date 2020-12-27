import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class JUnitBinarySearchTree {
	
	@Test
	public void testRemoveRoot() {
		BinarySearchTree bst = new BinarySearchTree();
		// Test removing from an empty tree:
		assertFalse(bst.remove("cat"));
		
		// Test removing root from a one-node tree:
		bst.add("cat");
		assertFalse(bst.remove("dog"));
		assertTrue(bst.remove("cat"));
		assertFalse(bst.remove("cat"));
		
		// Test removing root from a tree where
		// the root has exactly one child on the right:
		bst.add("a");
		bst.add("b");
		assertFalse(bst.remove("c"));
		assertTrue(bst.remove("a"));
		assertFalse(bst.remove("a"));
		
		// Test removing root from a tree where
		// the root has exactly one child on the left:
		bst.add("b");
		bst.add("a");
		assertFalse(bst.remove("c"));
		assertTrue(bst.remove("b"));
		assertFalse(bst.remove("b"));
	}
	

	@Test
	public void testRemoveWithBigTree() {
		ArrayList<String> words = randomWordList(1000); //Change this back to 1000 
		BinarySearchTree bst = new BinarySearchTree();
		
		bst.add("This is the root");
		
		for (int i = 0; i < words.size(); i++) {
			bst.add(words.get(i));
		}
		
		assertTrue(bst.remove("This is the root"));
		assertFalse(bst.remove("This is the root"));
		
		Collections.shuffle(words);
		
		while (!words.isEmpty()) {
			String s = words.remove(0);
			
			// Get rid of duplicates in the arraylist
			while (words.contains(s))
				words.remove(s);
			
			bst.remove(s);
			assertFalse(bst.contains(s));
			for (int i = 0; i < words.size(); i++) {
				assertTrue(bst.contains(words.get(i)));
			}
		}
	}
	
	
	
	private void inorderTraversal() {
		// TODO Auto-generated method stub
		
	}


	public String randomWord(int length) {
		String result = "";
		while (result.length() < length) {
			char c = (char)('a' + (int)(26 * Math.random()));
			result += c;
		}
		return result;
	}
	
	public ArrayList<String> randomWordList(int count) {
		ArrayList<String> result = new ArrayList<>();
		while (result.size() < count) {
			result.add(randomWord(4));
		}
		return result;
	}
	
	public boolean containsDuplicates(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).equals(list.get(j))) {
					return true;
				}
			}
		}
		return false;
	}

}
