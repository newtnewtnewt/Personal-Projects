/**
 * A linked-node implementation of the Set ADT in which elements of the set
 * are always sorted (in this case, lexicographically, which is a fancy
 * way of saying "alphabetically").  Note that the String class has a compareTo
 * method which you should be using to assist you in keeping the set sorted.
 * 
 */
public class SortedSet implements SetInterface<String> {
	
	private Node head;
	private int itemCount = 0;
	/** Create a Sorted set object and set initial head to null
	 */
	public SortedSet() {
		
		head = null;
	}
	
	@Override
	/** Gets the current number of entries in this set.
    @return  The integer number of entries currently in the set. */
	public int getCurrentSize() {
		
		return itemCount;
	}

	@Override
	/** Sees whether this set is empty.
    @return  True if the set is empty, or false if not. */
	public boolean isEmpty() {
		if(head == null) {
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	/** Adds a new entry to this set, avoiding duplicates.
    @param newEntry  The object to be added as a new entry.
    @return  True if the addition is successful, or
             false if the item already is in the set. */
	public boolean add(String newEntry) {
		
		if(head == null) {
			Node newNode = new Node(newEntry);
			head = newNode;
			itemCount++;
		    return true;
		}
		
		if(this.contains(newEntry)) {
			return false;
		}
		
		Node current = head;
		while(current != null) {
			
			if(current.next == null) {
				if(current.data.compareTo(newEntry) > 0) {
					if(current.equals(head)) {
						Node newNode = new Node(newEntry);
						newNode.next = current;
						head = newNode;
						itemCount++;
						return true;
					}
					
					Node newNode = new Node(newEntry);
					newNode.next = current;
					itemCount++;
					return true;
				}
				else {
					Node newNode = new Node(newEntry);
					current.next = newNode; 
					itemCount++;
					return true;
				}
			}
			
				
			else if(current.next.data.compareTo(newEntry) > 0) {
				if(current.equals(head)) {
					Node newNode = new Node(newEntry);
					newNode.next = current;
					head = newNode;
					itemCount++;
					return true;
				}
				
				Node newNode = new Node(newEntry);
				newNode.next = current.next;
				current.next = newNode;
				itemCount++;
				return true;
			}
			
			
			
			else {
				current = current.next;
			}
		}
		
		return false;

		
	}



	@Override
	/** Removes a specific entry from this set, if possible.
    @param anEntry  The entry to be removed.
   @return  True if the removal was successful, or false if not. */
	public boolean remove(String anEntry) {
		
		Node current = head;
		if(head == null) {
			return false;
		}
		if(head.data.equals(anEntry)) {
			itemCount--;
			head = head.next;
			return true;
		}
		
		
		while(current.next != null) {
			if(current.next.data.equals(anEntry)) {
				if(current.next.next == null) {
					current.next = null;
					itemCount--;
					return true;
				}
				else {
					current.next = current.next.next;
					itemCount--;
					return true;
				}
			}
			current = current.next;
			
		}
		
		
		return false;
	}

	@Override
	 /** Removes one unspecified entry from this set, if possible.
    @return  Either the removed entry, if the removal
             was successful, or null. */
	public String remove() {
		if(head == null) {
			return null;
		}
		
		else {
			String removedItem = head.data;
			head = head.next;
			itemCount--;
			return removedItem;
		}
		
	}

	@Override
	/** Removes all entries from this set. */
	public void clear() {
		itemCount = 0;
		head = null;
	}

	@Override
	/** Tests whether this set contains a given entry.
    @param anEntry  The entry to locate.
    @return  True if the set contains anEntry, or false if not. */
	public boolean contains(String anEntry) {
		Node current = head;
		if(head == null) {
			return false;
		}
		while(current != null) {
			if(current.data.equals(anEntry)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}
	
	/*
	 * returns a string representation of the items in the set,
	 * specifically a space separated list of the strings, enclosed
	 * in square brackets [].  For example, if the set contained
	 * cat, dog, then this should return "[cat dog]".  If the
	 * set were empty, then this should return "[]".
	 */
	@Override
	public String toString() {
		String bigString = "[ ";
		
		Node current = head;
	
		
		while(current != null) {
			bigString += current.data + " ";
			current = current.next;
		}
		bigString += "]";
		return bigString;
		
	}
	
	@Override
	/** Retrieves all entries that are in this set.
	 @return  A newly allocated array of all the entries in the set. */
	public String[] toArray() {
		if(head == null) {
			return new String[0];
		}
		
		Node current = head;
		
		String[] neededArray = new String[itemCount];
		int entry = 0;
		
		while(current != null) {
			neededArray[entry] = current.data;
			entry++;
			current = current.next;
		}
		return neededArray;
	}
	
	
	
	private class Node{
		/**
		 * Nodes act a means of linking data from point to point with saved 
		 */
		String data; 
		Node next;
		
		private Node() {
			
		}
		
		private Node(String data) {
			this.data = data;
		}
		
		
	}

}
