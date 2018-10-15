/**   A class that implements the ADT queue by using an expandable   circular array.   @author Frank M. Carrano   @author Timothy M. Henry   @author Joseph Erickson   @version 4.0 */public final class ArrayQueue<T> implements QueueInterface<T>{	// To Do: Declare instance variables	private int currentCapacity;	private int frontIndex;	private int backIndex;	private T[] circularArray;	private static final int DEFAULT_CAPACITY = 3; // Small capacity for testing	private static final int MAX_CAPACITY = 10000; 	public ArrayQueue()	{		this(DEFAULT_CAPACITY);	} // end default constructor	public ArrayQueue(int initialCapacity)	{		// To Do:		checkCapacity(currentCapacity);				circularArray = (T[]) new Object[initialCapacity];		currentCapacity = initialCapacity;		frontIndex = 0;		backIndex = -1;					} // end constructor	public void enqueue(T newEntry)	{		// To Do:		checkInitialization();		ensureCapacity();		backIndex = (backIndex + 1 % circularArray.length);		circularArray[backIndex] = newEntry;	} // end enqueue	public T getFront()	{		// To Do:		checkInitialization();		if(isEmpty())			throw new EmptyQueueException("The queue is Empty");		else			return circularArray[frontIndex]; 			} // end getFront	public T dequeue()	{		// To Do:		checkInitialization();		if(isEmpty()) {			throw new EmptyQueueException();		}		else {			T front = circularArray[frontIndex];			circularArray[frontIndex] = null;			frontIndex = (frontIndex + 1) % circularArray.length;			return front; 		}	} // end dequeue	public boolean isEmpty()	{		// To Do:		return frontIndex == ((backIndex + 1) % circularArray.length);	} // end isEmpty	public void clear()	{		// To Do:		currentCapacity = 1;		circularArray = (T[]) new Object[currentCapacity];		frontIndex = 0;		backIndex = -1;	} // end clear	// Throws an exception if this object is not initialized.	private void checkInitialization()	{		// To Do:					try {				if(this == null) {					throw new Exception("Initialization Failed!");				}			}			 catch (Exception e) {				// TODO Auto-generated catch block				e.printStackTrace();			}					} // end checkInitialization		// Throws an exception if the client requests a capacity that is too large.	private void checkCapacity(int capacity)	{		// To Do:		if(capacity + 1 == MAX_CAPACITY) {			try {				throw new Exception("Maximum Capacity Reached!");			} catch (Exception e) {				// TODO Auto-generated catch block				e.printStackTrace();			}		}			} // end checkCapacity	// Doubles the size of the array queue if it is full.	// Precondition: checkInitialization has been called.	private void ensureCapacity()	{		// To Do:		if(frontIndex == ((backIndex + 2) % circularArray.length)) {			T[] oldQueue = circularArray; 			int oldSize = oldQueue.length;			int newSize = 2 * oldSize; 			checkCapacity(newSize);			T[] tempQueue = (T[]) new Object[newSize];			circularArray = tempQueue;			for(int i = 0; i < oldSize - 1; i++) {				circularArray[i] = oldQueue[frontIndex];				frontIndex = (frontIndex + 1) % oldSize;			}			currentCapacity = newSize;			frontIndex = 0;			backIndex = oldSize - 2;					}			} // end ensureCapacity}  // end ArrayQueue