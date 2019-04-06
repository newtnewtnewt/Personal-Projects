import java.util.Iterator;
import java.util.LinkedList;


public class Set<Item extends Comparable<Item>> {
	RedBlackBST<Item, Item> setBox;
	int size;
	
	public Set(){
		setBox =  new RedBlackBST<Item, Item>();
		size = 0;
	}
	
	public void add(Item item) {
		if(!contains(item)) {
			setBox.put(item, item);
			size++;
		}

	}
	
	public void remove(Item item) throws Exception {
		if(!contains(item)) {
			throw new Exception("Error: Item not contained, cannot be removed!");
		}
		setBox.delete(item);
		size--;
		 
	}
	
	public Set<Item> union(Set<Item> thatSet){
		Set<Item> unionBox = new Set<Item>();
		
		for(Item x: setBox.keys()) {
			unionBox.add(x);
		}
		for(Item x: thatSet.setBox.keys()) {
			unionBox.add(x);
		}
		return unionBox;
		
	}
	
	public Set<Item> intersection(Set<Item> thatSet){
		Set<Item> intersectionBox = new Set<Item>();
		if(thatSet.size() == 0) {
			return null;
		}
		
		for(Item x: setBox.keys()) {
			if(thatSet.contains(x)) {
				 intersectionBox.add(x);
			}
			
		}
		return  intersectionBox;
		
	}
	
	public Set<Item> difference(Set<Item> thatSet){
		Set<Item> differenceBox = new Set<Item>();
		if(thatSet.size() == 0) {
			return this;
		}
		
		for(Item x: setBox.keys()) {
			if(!thatSet.contains(x)) {
				 differenceBox.add(x);
			}
			
		}
		return differenceBox;
	}
	
	public boolean contains(Item item){
		return setBox.contains(item);
		
	}
	
	public boolean isEmpty() {
		return size == 0;
		
	}
	
	public int size() {
		return size;
		
	}
	public int hashCode() {
		int hash = 0;
		int i = 0;
		for(Item x: setBox.keys()) {
			hash = hash +  i * (int) x;
			i++;
		}
		return hash;
		
	}
	public Iterable<Item> iterator(){
		return setBox.keys();
		
	}
	public String toString() {
		String total = "";
		for(Item x: setBox.keys()) {
			total = total + x + " ";
		}
		return total.substring(0, total.length() - 1);
		
	}
	public boolean equals(Object o) {
		Set<Item> other = (Set<Item>) o;
		if(size() != other.size()) {
			return false;
		}
		for(Item x: setBox.keys()) {
			if(!other.contains(x)){
				return false;
			}
		}
		return true;
		
	}
	
	
	

}
