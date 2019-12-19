
public class CustomLL {
	Node head;
	int size = 0;
	
	public void add(int numOf, String word) { 
		/**
		 * This method adds @param numOf number of instances of @param word to the custom Linked List
		 * 
		 */
		
		Node current = head;
		if(contains(word)) {
			while(current != null) {
			
				if(current.word.equals(word)) {
					numOf = current.frequency + numOf;
					if(current.previous != null && current.next != null) {
						
						current.next.previous = current.previous;
						current.previous.next = current.next;
						
					}
					else if(current.previous != null && current.next == null) {
						current.previous.next = null;
					}
					else if(current == head && head.next == null) {
						head = null;
					}
					else if(current == head && head.next != null) {
						head = head.next;
					}
					else {
						System.out.println();
						current.next.previous = null;
					}
					size--;
					break;
				}
				
				current = current.next;
			}
		}
	
		current = head;
		size++;
		Node addNode = new Node(word, numOf);

		if(head == null) {
		
			head = addNode;
			return;
		}
		
		else if(head.compareTo(addNode) < 0){
			
			addNode.next = head;
			addNode.next.previous = addNode;
			head = addNode;
			return;
		}
		
		
		while(current.next != null) {
			if(current.compareTo(addNode) > 0 && current.next.compareTo(addNode) < 0) {
				current.next.previous = addNode;
				addNode.next = current.next;
				addNode.previous = current;
				current.next = addNode;
				return;
			}
			current = current.next;
			
		}
		if(current.compareTo(addNode) > 0) {
			current.next = addNode;
			addNode.previous = current;
			return;
		}
		else {
			current.previous.next = addNode;
			addNode.previous = current.previous;
			addNode.next = current;
			current.previous = addNode;
			return;
		}
		
	}
	public boolean contains(String word) {
		/**
		 * This method checks if a given word is in the Linked List
		 */
		Node current = head;
		if(head == null) {
			return false;
		}
		
		while(current.next != null ) {
			if(current.word.equals(word)) {
				return true;
			}
			current = current.next;
		}
		if(current != null && current.word.equals(word)) {
			return true;
		}
		return  false;
	}
	
	public String getNum(int n) {
		/**
		 * This method finds the nth highest occurring word in the set
		 */
		Node current = head;
		for(int i = 0; i < n; i++) {
			current = current.next;
		}
		return current.word;
	}
	
	public int getCount(String s) {
		/**
		 * This method grabs the frequency at which word @param s occurs
		 */
		Node current = head;
		
		while(current != null) {
			if(current.word.equals(s)) {
				return current.frequency;
			}
			current = current.next;
		}
		return 0;
	}
	
	
	
	
	
	
	private class Node implements Comparable{
		Node next = null;
		Node previous = null;
		String word = null;
		int frequency = -1;
		public Node(String w, int freq) {
			word = w;
			frequency = freq;
		}
		@Override
		public int compareTo(Object o) {
			Node otherNode = (Node) o;
			if(frequency - otherNode.frequency > 0) {
				return 1;
			}
			else if(frequency - otherNode.frequency < 0) {
				return -1;
			}
			else {
				if(word.compareTo(otherNode.word) > 0) {
					return -1;
				}
				else if(word.compareTo(otherNode.word) < 0) {
					return 1;
				}
				else {
					return 0;
				}
			}
			
			
		}
		
		
	}
	

}
