import java.util.Arrays;

public class ResizableArraySet implements SetInterface {
	
	private int currentSlot = 0;
	private int[] setArray;

	public ResizableArraySet() {
		/**
		 * Creates a set with default size set at 10
		 */
		setArray = new int[10];
	}
	
	public ResizableArraySet(int sizeArray) {
		/**
		 * Creates a set with fault size set at that of @param sizeArray
		 */
		setArray = new int[sizeArray];
	}
	
	@Override
	public int getSize() {
		/**
		 * Gets the current number of entries in this set.
		 * 
		 * @return The integer number of entries currently in the set.
		 */
		return currentSlot;
	}

	@Override
	public boolean isEmpty() {
		/**
		 * Sees whether this set is empty.
		 * 
		 * @return True if the set is empty, or false if not.
		 */
		if(currentSlot == 0) {
			return true;
		}
		
		return false;
	}
	

	@Override
	public boolean add(int newValue) {
		/**
		 * Adds a new entry to this set, avoiding duplicates.
		 * 
		 * @param newEntry
		 *            The integer to be added as a new entry.
		 * @return True if the addition is successful, or false if the item already
		 *         is in the set.
		 */
		if(contains(newValue)) {
			return false;
		}
		
		if(currentSlot < setArray.length) {
			setArray[currentSlot] = newValue;
			currentSlot++;
			return true;
		}
		
		else {
			int[] tempArray = new int[setArray.length * 2];
			for(int i = 0; i < setArray.length; i++) {
				tempArray[i] = setArray[i];
				if(i == setArray.length-1) {
					tempArray[i+1] = newValue;
					
				}
				
			}
			currentSlot++;
			setArray = tempArray;
			return true;
		}
		
			
		
	}

	@Override
	public boolean remove(int aValue) {
		/**
		 * Removes a specific entry from this set, if possible.
		 * 
		 * @param anEntry
		 *            The entry to be removed.
		 * @return True if the removal was successful, or false if not.
		 */
		for(int i = 0; i < currentSlot; i++) {
			if(setArray[i] == (aValue)) {
				setArray[i] = setArray[currentSlot - 1];
				setArray[currentSlot - 1] = 0;
				currentSlot--;
				return true;
			}
		}
		return false;
	}

	@Override
	public int remove() {
		/**
		 * Removes one unspecified entry from this set, if possible.
		 * 
		 * @return Either the removed entry, if the removal was successful, or Integer.MIN_VALUE.
		 */
		if(currentSlot > 0) {
			int temp = setArray[0];
			setArray[0] = setArray[currentSlot-1];
			setArray[currentSlot - 1] = 0;
			currentSlot --;
			return temp;
		}
		return Integer.MIN_VALUE;
		
	}

	@Override
	public void clear() {
		/** Removes all entries from this set. */
		for(int i = 0; i < currentSlot; i++) {
				setArray[i] = 0;
			}
		currentSlot = 0;
		}
		
	

	@Override
	public boolean contains(int anEntry) {
		/**
		 * Tests whether this set contains a given entry.
		 * 
		 * @param anEntry
		 *            The entry to locate.
		 * @return True if the set contains anEntry, or false if not.
		 */
		if(currentSlot == 0) {
			return false;
		}
		for(int i = 0; i < currentSlot; i++) {
			if(setArray[i] == anEntry) {
				return true;
			}
		}
		return false;
	}

	@Override
	public SetInterface union(SetInterface anotherSet) {
		/**
		 * Computes the union of this set with a given set
		 * 
		 * @param anotherSet
		 *            another set
		 * @return the union of this set with anotherSet
		 */
		int[] otherArray = anotherSet.toArray();
		int[] thisArray = this.toArray();
		int[] unionArray = new int[otherArray.length + thisArray.length];
		int unionSlot = 0;
		
		for(int i = 0; i < thisArray.length; i++) {
			for(int j = 0; j < unionArray.length; j++) {
				if(thisArray[i] == unionArray[j]) {
					break;
				}
				
				if(thisArray[i] != unionArray[j] && j == unionArray.length-1) {
					unionArray[unionSlot] = thisArray[i];
					unionSlot++;
				}
			}
		}
		
		for(int i = 0; i < otherArray.length; i++) {
			for(int j = 0; j < unionArray.length; j++) {
				if(otherArray[i] == unionArray[j]) {
					break;
				}
				
				if(otherArray[i] != unionArray[j] && j == unionArray.length-1) {
					unionArray[unionSlot] = otherArray[i];
					unionSlot++;
				}
			}
		}
		
		
		
		if(unionSlot == 0) {
			ResizableArraySet emptySet = new ResizableArraySet(0);
			return emptySet;
		}
		else {
			ResizableArraySet tempSet = new ResizableArraySet(unionArray.length);
			for(int i = 0; i < unionSlot; i++) {
				tempSet.add(unionArray[i]);
			}
			return tempSet;
		}
	}

	@Override
	public SetInterface intersection(SetInterface anotherSet) {
		/**
		 * Computes the intersection of this set with a given set
		 * 
		 * @param anotherSet
		 *            another set
		 * @return the intersection of this set with anotherSet
		 */
		int[] otherArray = anotherSet.toArray();
		int[] thisArray = this.toArray();
		int[] intersectionArray = new int[otherArray.length + thisArray.length];
		int intersectionSlot = 0;		
		
		for(int i = 0; i < thisArray.length; i++) {
			for(int j = 0; j < otherArray.length; j++) {
				if(thisArray[i] == otherArray[j]) {
					intersectionArray[intersectionSlot] = thisArray[i];
					intersectionSlot++;
					break;
				}
			}
		}
		if(intersectionSlot == 0) {
			ResizableArraySet emptySet = new ResizableArraySet(0);
			return emptySet;
		}
		
		else {
			ResizableArraySet tempSet = new ResizableArraySet(intersectionArray.length);
			for(int i = 0; i < intersectionSlot; i++) {
				tempSet.add(intersectionArray[i]);
			}
			return tempSet;
		}
		
		
		
	}

	@Override
	public SetInterface difference(SetInterface anotherSet) {
		/**
		* Computes the elements in this set except for those that are also in anotherSet
	    *
		* @param anotherSet
		*            another set
		* @return the result of removing all elements of this set that are in anotherSet
		*/
		int[] otherArray = anotherSet.toArray();
		int[] thisArray = toArray();
		int[] differenceArray = new int[otherArray.length + thisArray.length];
		int differenceSlot = 0;
		
		for(int i = 0; i < thisArray.length; i++) {
			for(int j = 0; j < otherArray.length; j++) {
				if(thisArray[i] != otherArray[j]) {
					if(j == otherArray.length-1) {
						differenceArray[differenceSlot] = thisArray[i];
						differenceSlot++;
						break;
					}
				}
				else {
					break;
				}
			}
		}
		if(differenceSlot == 0) {
			ResizableArraySet emptySet = new ResizableArraySet(0);
			return emptySet;
		}
		else {
			ResizableArraySet tempSet = new ResizableArraySet(differenceArray.length);
			for(int i = 0; i < differenceSlot; i++) {
				tempSet.add(differenceArray[i]);
			}
			return tempSet;
		}
	}

	@Override
	public int[] toArray() {
		/**
		 * Retrieves all entries that are in this set.
		 * 
		 * @return A newly allocated array of all the entries in the set, where the
		 *         size of the array is equal to the number of entries in the set.
		 */
		if(currentSlot == 0) {
			int[] temp = new int[0];
			return temp;
		}
		
		int[] temp = new int[currentSlot];
		for(int i = 0; i < currentSlot; i++) {
			temp[i] = setArray[i];
		}
		
		return temp;
	}

}
