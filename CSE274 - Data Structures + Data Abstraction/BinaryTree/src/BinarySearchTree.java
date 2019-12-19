
public class BinarySearchTree {

	Node root;
	
	public BinarySearchTree() {
		root = null;
	}

	/*
	 * Adds the specified node to the BST
	 */
	public String add(String value) {
		if(root == null) {
			root = new Node(value);
			return value;
		}
		return add(root, value);
	}
	
	private String add(Node root, String value) {
		String result = null;
		int comparison = value.compareTo(root.data);
		if(comparison == 0) {
			return result;
		}
		if(comparison < 0) {
			if(root.left != null) {
				return add(root.left,value);
			}
			root.left = new Node(value);
			return value;
		}
		if(comparison > 0) {
			if(root.right != null) {
				return add(root.right, value);
			}
			root.right = new Node(value);
			return value;
		}
		return result;
		
		
	}
	
	/*
	 * Returns true if the string is found in the BST
	 */
	public boolean contains(String value) {
		return contains(root, value);
	}
	private boolean contains(Node root, String value) {
		if(root == null) return false;
		int comparison = value.compareTo(root.data);
		
		if(comparison == 0) return true;
		if(comparison < 0) return contains(root.left,value);
		return contains(root.right, value);
		
	}
	
	/*
	 * Checks whether the tree is empty or not
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/*
	 * Removes the specified string from the BST
	 */
	public boolean remove(String s) {
		
		
		/**
		 * Special Case 1: Number you are trying to remove doesn't exist.
		 */
		if(!contains(s)) {
			return false;
		}
		
		/**
		 * Special Case 2: Remove the root no children
		 */
		
		if(root.data.equals(s) && root.left == null && root.right == null) {
			root = null;
			return true;
		}
		/**
		 * Special Case 3: Remove the root with 1 child
		 */
		if(root.data.equals(s) && root.left == null) {
			root = root.right;
			return true;
		}
		if(root.data.equals(s) && root.right == null) {
			root = root.left;
			return true;
		}
		
		return remove(s, root, null);
		
	}
	
	
	
	
	/**
	 * Private helper method
	 */
	private boolean remove(String s, Node otherRoot, Node p) {
		
		//Traverse the tree if we haven't found the value yet 
		if(!otherRoot.data.equals(s)) {		
			if(otherRoot.data.compareTo(s) < 0) {
				p = otherRoot;
				return remove(s, otherRoot.right, p);
			
				
			}
			else {
				p = otherRoot;
				return remove(s, otherRoot.left, p);
			}
		}
		
		//Execute this block if we found the value and it's a leaf on the tree
		else if(otherRoot.data.equals(s) && otherRoot.right == null && otherRoot.left == null) {
			if(p.right != null && p.right.data.equals(s)) {
				p.right = null;
			}
			else {
				p.left = null;
			}
			return true;
		}
		//Execute this block if we found the value and it has a single child.
		else if(otherRoot.data.equals(s) && (otherRoot.right == null || otherRoot.left == null)) {
			if(p.right != null && p.right.data.equals(s)) {
				if(otherRoot.right != null) {
					p.right = otherRoot.right; 
					return true;
				}
				else {
					p.right = otherRoot.left; 
					return true;
				}
			}
			else 
				{
				if(otherRoot.right != null) {
					p.left = otherRoot.right; 
					return true;
				}
				else {
					p.left = otherRoot.left;
					return true;
				}
			}

		}
		//Due this if the given root has 2 children
		else if(otherRoot.data.equals(s) && otherRoot.right != null && otherRoot.left != null) {	
			inorderTraversal();
			//Needs replaced is set to the node we are trying to remove
			Node needsReplaced = otherRoot;
			//OtherRoot becomes our tracer root
			otherRoot = otherRoot.left;
			//We loop through to get the maximum element
			while(otherRoot.right != null && otherRoot.right.right != null) {
				otherRoot = otherRoot.right; //Iterate through to grab the correct root;
			}
			//Do this if the replacement root has no kids
			if(otherRoot.right != null && otherRoot.right.right == null && otherRoot.right.left == null) {
				
				//Do this if the root removed is the top Node
				if(p == null) {
					if(otherRoot.data.equals(root.left.data) && otherRoot.right == null) {
						root.left = otherRoot.left;
						root.data = otherRoot.data;
					}
					else {
						root.data = otherRoot.right.data;
						otherRoot.right = null;
					}
				}
				//Do this if the root removed is a leaf anywhere else
				else {
					
					needsReplaced.data = otherRoot.right.data;
					otherRoot.right = null;
				}
				return true;
				
			}
			//Do this if the replacement root has one child on the left
			else {
				//Do this if the area to the left of the replaced Node is empty
				if(needsReplaced.left == null && otherRoot.right != null){
					needsReplaced.data = otherRoot.right.data;
					otherRoot.right = null;
					return true;
				}
				//Do this if the area to the left of the replaced Node is non-empty
				else if(needsReplaced.left != null && otherRoot.right != null){
					needsReplaced.data = otherRoot.right.data;
					otherRoot.right = otherRoot.right.left;
					return true;
					
				}
				//Do this if the node is directly adjacent to the one we are trying to remove
				else if(otherRoot.right == null) {
					needsReplaced.left = otherRoot.left;
					needsReplaced.data = otherRoot.data;
					return true;
				}
		
				}
		}

		return false;
	}
	
		
	

	// Removes the largest node in the given tree,
	// which will be the rightmost node, and returns
	// the value from that node. The largest node
	// will always have 0 or 1 children (if it had
	// 2 children, then the right node would be larger).
	
	//	private String removeLargestFromLeftSubtree(Node n) {
	//		// To do, optional, you can accomplish the same task in the remove method. In that case you don't
	//		// have to define this method.
	//		
	//	}

	
	/**
	 * Prints the inorder traversal of this tree
	 */
	public void inorderTraversal() {
		inorderTraversal(root);
	}
	private void inorderTraversal(Node root) {
		if(root == null)
			return;
		inorderTraversal(root.left);
		System.out.print(root.data + " ");
		inorderTraversal(root.right);
	}
	//change to private
	private class Node{
		String data;
		Node left;
		Node right;
		
		public Node(String data) {
			this.data = data;
		}
	}
	
	/*
	 * Returns the height of the tree
	 */
	public int getHeight() {
		return getHeight(root);
	}
	
	private int getHeight(Node root) {
		if(root == null)
			return 0;
		return 1 + Math.max(getHeight(root.left), getHeight(root.right));
		
	}
	
	
	public static void main(String [] args) {
		BinarySearchTree bst = new BinarySearchTree();
		
		bst.add("D");
		bst.add("B");
		bst.add("F");
		bst.add("A");
		bst.add("C");
		bst.add("E");
		bst.add("G");
		
		System.out.println("The height: " + bst.getHeight());
		
		System.out.print("BST Inorder: ");
		bst.inorderTraversal();
		System.out.println();
		System.out.println(bst.contains("A"));
		System.out.println(bst.contains("B"));
		System.out.println(bst.contains("C"));
		System.out.println(bst.contains("D"));
		System.out.println(bst.contains("E"));
		System.out.println(bst.contains("F"));
		System.out.println(bst.contains("G"));

		System.out.println(bst.contains("X"));
		System.out.println(bst.contains("M"));
		System.out.println(bst.contains("L"));
		
		/**
		 * Bonus Test using Dr. Gani's tree
		 */
		
		
		
//		BinarySearchTree otherbst = new BinarySearchTree();
//		
//		otherbst.add("j");
//		otherbst.add("f");
//		otherbst.add("p");
//		otherbst.add("c");
//		otherbst.add("h");
//		otherbst.add("b");
//		otherbst.add("d");
//		otherbst.add("i");
//		otherbst.add("m");
//		otherbst.add("n");
//		otherbst.add("o");
//		otherbst.add("a");
//		
//		otherbst.remove("a");
//		System.out.println(otherbst.contains("a"));
//		otherbst.remove("d");
//		System.out.println(otherbst.contains("d"));
//		otherbst.remove("i");
//		System.out.println(otherbst.contains("i"));
//		otherbst.remove("o");
//		System.out.println(otherbst.contains("o"));
//		
//		System.out.println();
//		otherbst.remove("b");
//		System.out.println(otherbst.contains("b"));
//		System.out.println(otherbst.contains("a"));
//		otherbst.remove("h");
//		System.out.println(otherbst.contains("h"));
//		System.out.println(otherbst.contains("i"));
//		otherbst.remove("p");
//		System.out.println(otherbst.contains("p"));
//		System.out.println(otherbst.contains("m"));
//		otherbst.remove("m");
//		System.out.println(otherbst.contains("m"));
//		System.out.println();
//		
//	
//		otherbst.remove("f");
//		System.out.println(otherbst.contains("f"));
//		System.out.println(otherbst.contains("c"));
//		System.out.println(otherbst.contains("h"));
//		
//		otherbst.remove("c");
//		System.out.println(otherbst.contains("c"));
//		System.out.println(otherbst.contains("b"));
//		System.out.println(otherbst.contains("d"));
	
//		BinarySearchTree bstMe = new BinarySearchTree();
//		bstMe.add("j");
//		System.out.println(bstMe.remove("j"));
//		System.out.println(bstMe.contains("j"));
//		
//		bstMe.add("j");
//		bstMe.add("f");
//		System.out.println(bstMe.remove("j"));
//		System.out.println(bstMe.remove("f"));
//		System.out.println(bstMe.contains("f"));
//		System.out.println(bstMe.contains("j"));
//		
//		bstMe.add("j");
//		bstMe.add("p");
//		System.out.println(bstMe.remove("j"));
//		System.out.println(bstMe.remove("p"));
//		
//		System.out.println(bstMe.contains("j"));
//		System.out.println(bstMe.contains("p"));
		
		
//		BinarySearchTree finalbst = new BinarySearchTree();
//		
//		finalbst.add("j");
//		finalbst.add("f");
//		finalbst.add("p");
//		finalbst.add("c");
//		finalbst.add("h");
//		finalbst.add("b");
//		finalbst.add("d");
//		finalbst.add("i");
//		finalbst.add("m");
//		finalbst.add("n");
//		finalbst.add("o");
//		finalbst.add("a");
//		
//		
//			
//		finalbst.remove("f");
//		System.out.println(finalbst.contains("f"));
//		System.out.println(finalbst.contains("c"));
//		System.out.println(finalbst.contains("h"));
//		System.out.println(finalbst.contains("b"));
//		System.out.println(finalbst.contains("d"));
//		System.out.println(finalbst.contains("a"));
//		System.out.println(finalbst.contains("i"));
//			
//		finalbst.remove("c");
//		System.out.println(finalbst.contains("c"));
//		System.out.println(finalbst.contains("b"));
//		System.out.println(finalbst.contains("d"));
//		System.out.println(finalbst.contains("a"));
//		
//		finalbst.remove("j");
//		System.out.println(finalbst.contains("j"));
//		System.out.println(finalbst.contains("f"));
//		System.out.println(finalbst.contains("p"));
//		System.out.println(finalbst.contains("m"));
//		
//		finalbst.inorderTraversal();
		
	}

}
