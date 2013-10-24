
public class Node {
	private int value;
	private Node left;
	private Node right;
	private Node parent;
	private int leftOrRightChild;
	
	public Node(int value, Node left, Node right, Node parent, int leftOrRightChild){
		this.value = value;
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.leftOrRightChild = leftOrRightChild;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getLeftOrRightChild() {
		return leftOrRightChild;
	}

	public void setLeftOrRightChild(int leftOrRightChild) {
		this.leftOrRightChild = leftOrRightChild;
	}
	
	public String toString(){
		return "" + value;
	}

	public static void main(String[] args){
		Node a = new Node (1, null, null, null, 0);
		Node b = new Node (2, null, null, a, 1);
		a.setRight(b);
		Node c = new Node (3, null, null, b, -1);
		b = a;
		System.out.println(c.getParent().getValue());
	}
	
}
