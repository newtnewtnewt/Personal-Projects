import java.lang.Throwable;
import java.util.EmptyStackException;

public class LinkedStack<T> implements StackInterface<T> {
	private Node head;
	private int size = 0; 
	

	public void LinkedStack() {
		
	}
	@Override
	public void push(T newEntry) {
	/** Adds a new entry to the top of this stack.
    @param newEntry  An object to be added to the stack. */
		Node newNode = new Node();
		newNode.data = newEntry;
		if(size == 0) {
			head = newNode;
			size++;
		}
		
		else {
			newNode.next = head;
			head = newNode;
			size++;
		}
		
	}

	@Override
	public T pop(){
	/** Removes and returns this stack's top entry.
    @return  The object at the top of the stack. 
	@throws  EmptyStackException if the stack is empty before the operation. */
		try {
			if(head == null) {
				throw new EmptyStackException();
			}
			}
			catch(EmptyStackException e) {
				e.printStackTrace();
				return null;
		}
			
		Node returnedNode = head;
		head = head.next;
		size--;
		return returnedNode.data;		
		
	}

	@Override
	public T peek(){
	/** Retrieves this stack's top entry.
	@return  The object at the top of the stack.
	@throws  EmptyStackException if the stack is empty. */
		try {
		if(head == null) {
			throw new EmptyStackException();
		}
		}
		catch(EmptyStackException e) {
			e.printStackTrace();
			return null;
		}
		return head.data;
	}

	@Override
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		head = null;
		size = 0;
	}

	private class Node {
		Node next;
		T data;

		private Node(){
			
		}
	}
	

}
