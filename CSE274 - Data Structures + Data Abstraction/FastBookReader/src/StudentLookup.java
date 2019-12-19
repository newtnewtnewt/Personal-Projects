/**
 * Your implementation of the LookupInterface.  The only public methods
 * in this class should be the ones that implement the interface.  You
 * should write as many other private methods as needed.  Of course, you
 * should also have a public constructor.
 * 
 * @author // Noah Dunn
 */
  
 
public class StudentLookup implements LookupInterface {
	CustomLL storage;
	public StudentLookup() {
		storage = new CustomLL();
		
	}

	@Override
	public void addString(int amount, String s) {
		storage.add(amount, s);
	}

	@Override
	public int lookupCount(String s) {
		return storage.getCount(s);
	}

	@Override
	public String lookupPopularity(int n) {
		return storage.getNum(n);
	}

	@Override
	public int numEntries() {
		return storage.size;
	}
    
}
