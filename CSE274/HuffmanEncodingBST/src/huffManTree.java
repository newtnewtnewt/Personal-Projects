import java.util.Arrays;

public class huffManTree {
	/**
	 * This is the object class for huffman tree objects.
	 */
	
	/**Create a root node and a list of our nodes we need to populate */
	Node root;
	Node[] nodeList = new Node[5];
	
	/**The Huffman Tree Constructor creates the tree and fills it with provided Nodes*/ 
	huffManTree(int[] necessaryNodes, char[] charArray){
		/**Populate our Node storage */ 
		for(int i = 0; i < necessaryNodes.length; i++ ) {
			Node tempNode = new Node(necessaryNodes[i], charArray[i]) ;
			nodeList[i] = tempNode;	
		}
		/**Sort from least to greatest */ 
		Arrays.sort(nodeList);
		/**Instantiate our root node */ 
		root = new Node(nodeList[0].frequency + nodeList[1].frequency, '.');	
		root.left = nodeList[0];
		root.right = nodeList[1];
		
		/**Populate the rest of our tree comparing frequencies */ 
		for(int i = 2; i < nodeList.length; i++) {
			Node currentNode = nodeList[i];
			Node newRoot = new Node(root.frequency + currentNode.frequency,'.');
			if(root.frequency < currentNode.frequency) {
				newRoot.left = root;
				newRoot.right = currentNode;
				root = newRoot;
			}
			else {
				newRoot.left = currentNode;
				newRoot.right = root;
				root = newRoot;
			}
		}
	}
	/**This method grabs a huffman code given our established tree and the character we want encoded */ 
	public String getHuffManCode(char searchChar) {
		searchChar = Character.toUpperCase(searchChar);
		Node searcher = root;
		String huffManCode = "";

		while(searcher.left != null && searcher.left.identity != searchChar && searcher.right != null && searcher.right.identity != searchChar) {
			
			if(searcher.left.identity == '.') {
				searcher = searcher.left;
				huffManCode += "0";
			}
			else {
				searcher = searcher.right;
				huffManCode += "1";
			}
			
		}
		if(searcher.right != null && searcher.right.identity == searchChar) {
			return (huffManCode += "1");
		}
		else {
			return (huffManCode += "0");
		}
		
	}
	/**This takes a string of huffman codes and converts it back to normal text */ 
	public String getActualFromHuffman(String huffmanString) {
		String finalString = "";
		Node currentNode = root;
		for(int i = 0; i < huffmanString.length(); i++) {
			if(huffmanString.charAt(i) == '1') {
				if(currentNode.right.identity != '.') {
					finalString = finalString + currentNode.right.identity;
					currentNode = root;
				}
				else {
					currentNode = currentNode.right;
				}
				
			}
			else if(huffmanString.charAt(i) == '0') {
				if(currentNode.left.identity != '.') {
					finalString = finalString + currentNode.left.identity;
					currentNode = root;
				}
				else {
					currentNode = currentNode.left;
				}
			}
		}
		return finalString;
	}
	
		

	
	
/**
 * 
 * Private Node class to handle the implementation of the tree
 * with linked data.
 * @implements Comparable in order to use Arrays.sort fastest.
 */
private class Node implements Comparable{
	private int frequency = 0;
	private char identity = 0;
	private Node right;
	private Node left;
	
	public Node(int freq, char iden){
		frequency = freq;
		identity = iden; 
		right = null;
		left = null;
	}

	@Override
	public int compareTo(Object nodeObj) {
		Node correctnodeObj = (Node)nodeObj;
		if(frequency > correctnodeObj.frequency) {
			return 1;
		}
		else if(frequency == correctnodeObj.frequency) {
			return 0;
		}
		else {
			return -1;
		}
	}
	
	
}



}