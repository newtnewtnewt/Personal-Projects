import java.util.Iterator;
import java.util.LinkedList;


public class Set<Item> {
	LinkedList<Item> setBox;
	int size;
	
	public Set(){
		setBox =  new LinkedList<Item>();
		size = 0;
	}
	
	public void add(Item item) {
		if(!contains(item)) {
			setBox.add(item);
			size++;
		}

	}
	
	public void remove(Item item) throws Exception {
		if(!contains(item)) {
			throw new Exception("Error: Item not contained, cannot be removed!");
		}
		setBox.remove(item);
		size--;
		 
	}
	
	public Set<Item> union(Set<Item> thatSet){
		Set<Item> unionBox = new Set<Item>();
		
		for(int i = 0; i < this.size; i++) {
			unionBox.add(setBox.get(i));
		}
		for(int i = 0; i < thatSet.size(); i++) {
			unionBox.add(thatSet.setBox.get(i));
		}
		return unionBox;
		
	}
	
	public Set<Item> intersection(Set<Item> thatSet){
		Set<Item> intersectionBox = new Set<Item>();
		if(thatSet.size() == 0) {
			return null;
		}
		
		for(int i = 0; i < this.size; i++) {
			if(thatSet.contains(setBox.get(i))) {
				 intersectionBox.add(setBox.get(i));
			}
			
		}
		return  intersectionBox;
		
	}
	
	public Set<Item> difference(Set<Item> thatSet){
		Set<Item> differenceBox = new Set<Item>();
		if(thatSet.size() == 0) {
			return this;
		}
		
		for(int i = 0; i < this.size; i++) {
			if(!thatSet.contains(setBox.get(i))) {
				 differenceBox.add(setBox.get(i));
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
		for(int i = 0; i < size; i++) {
			hash = hash +  i * (int) setBox.get(i);
		}
		return hash;
		
	}
	public Iterator<Item> iterator(){
		return setBox.iterator();
		
	}
	public String toString() {
		String total = "";
		for(int i = 0; i < size(); i++) {
			total = total + setBox.get(i) + " ";
		}
		return total.substring(0, total.length() - 1);
		
	}
	public boolean equals(Object o) {
		Set<Item> other = (Set<Item>) o;
		if(size() != other.size()) {
			return false;
		}
		for(int i = 0; i < size; i++) {
			if(!other.contains(setBox.get(i))){
				return false;
			}
		}
		return true;
		
	}
	
	
	

}
