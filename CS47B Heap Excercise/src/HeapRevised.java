import java.util.*;

/**
 * CS 47B Heap Exercise This exercise is to implement the heap structure in
 * explicitly linked nodes instead of in the conventional array. Despite the
 * complexity of the implementation, the add() and remove() operations still
 * operate at O(log N) in worst case while the constructor, isEmpty() and top()
 * operations run in constant time. This is done by having additional fields:
 * parentofNextInsertion and last.
 * 
 * @author Run Yu Zhang
 * 
 */
public class HeapRevised {

	private int mySize;
	private Node root;
	private Node parentOfNextInsertion;
	private Node last;

	public HeapRevised() {
		mySize = 0;
	}

	/**
	 * Sets the parent node for next insertion after the add function is called
	 */
	private void setParentOfNextInsertion() {
		if (mySize == 1) {
			parentOfNextInsertion = root;
		} else if (parentOfNextInsertion.getRight() != null) {
			if (((mySize + 1) & (-mySize - 1)) == (mySize + 1)) {
				parentOfNextInsertion = root;
				while (parentOfNextInsertion.getLeft() != null)
					parentOfNextInsertion = parentOfNextInsertion.getLeft();
			} else {
				// find first ancestor that's left child of its parent
				while (parentOfNextInsertion.getLeftOrRightChild() != -1) {
					parentOfNextInsertion = parentOfNextInsertion.getParent();
					;
				}
				parentOfNextInsertion = parentOfNextInsertion.getParent()
						.getRight();
				for (; parentOfNextInsertion.getLeft() != null; parentOfNextInsertion = parentOfNextInsertion
						.getLeft())
					;
			}
		}
	}

	/**
	 * Return true exactly when the heap is empty
	 * 
	 * @return true exactly when the heap is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Return the top of the heap.
	 * 
	 * @return top of the heap
	 * @throws NoSuchElementException
	 */
	public int top() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("accessing top of empty heap");
		}
		return root.getValue();
	}

	/**
	 * Remove the top of the heap
	 * 
	 * @throws NoSuchElementException
	 */
	public void remove() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("removing from empty heap");
		}
		mySize--;
		if (mySize == 0)
			root = null;
		else {
			Node removedLeaf = last;
			// disconnect the leaf from the tree
			if (removedLeaf.getLeftOrRightChild() == 1) {
				removedLeaf.getParent().setRight(null);
				last = removedLeaf.getParent().getLeft();
				parentOfNextInsertion = removedLeaf.getParent();
			} else if (removedLeaf.getLeftOrRightChild() == -1) {
				removedLeaf.getParent().setLeft(null);
				// update the last node
				if (((mySize + 1) & (-mySize - 1)) == (mySize + 1)) {
					last = root;
					for (; last.getRight() != null; last = last.getRight())
						;
					parentOfNextInsertion = root;
					for (; parentOfNextInsertion.getLeft() != null; parentOfNextInsertion = parentOfNextInsertion.getLeft())
						;
				} else {
					// find first ancestor that's right child of its parent
					while (last.getParent().getLeftOrRightChild() != 1) {
						last = last.getParent();
						;
					}
					last = last.getParent().getParent().getLeft();
					for (; last.getRight() != null; last = last.getRight())
						;
				}
			}
			removedLeaf.setParent(null);
			Node temp = root;
			while (removedLeaf.getValue() >= smallerValueOfChildren(temp)) {
				if (temp.getRight() != null) {
					Node tempLeft = temp.getLeft();
					Node tempRight = temp.getRight();
					if (tempLeft.getValue() < tempRight.getValue()) {
						temp.setValue(tempLeft.getValue());
						temp = temp.getLeft();
					} else {
						temp.setValue(tempRight.getValue());
						temp = temp.getRight();
					}
				} else if (temp.getLeft() != null) {
					Node tempLeft = temp.getLeft();
					temp.setValue(tempLeft.getValue());
					temp = temp.getLeft();
				}
			}
			temp.setValue(removedLeaf.getValue());
		}
	}

	/**
	 * Returns the smaller value between children of a node. If node is a leaf,
	 * then Integer.Max_Value is returned. If right child is null, then value of
	 * left child is returned
	 * 
	 * @param node
	 *            Node to be examined
	 * @return Smaller value between children if both children are not null.
	 *         Value of the left child if right child is null. Integer.Max_Value
	 *         otherwise.
	 */
	private int smallerValueOfChildren(Node node) {
		if (node.getLeft() == null)
			return Integer.MAX_VALUE;
		else if (node.getRight() == null)
			return node.getLeft().getValue();
		else
			return ((node.getLeft().getValue() < node.getRight().getValue() ? node
					.getLeft().getValue() : node.getRight().getValue()));
	}

	/**
	 * Add an element to the heap
	 * 
	 * @param k
	 *            integer to be added
	 */
	public void add(int k) {
		mySize++;
		if (mySize == 1) {
			root = new Node(k, null, null, null, 0);
			last = root;
		} else {
			// pointer to the last insertion
			Node a;
			if (parentOfNextInsertion.getLeft() == null) {
				a = new Node(0, null, null, parentOfNextInsertion, -1);
				parentOfNextInsertion.setLeft(a);
			} else {
				a = new Node(0, null, null, parentOfNextInsertion, 1);
				parentOfNextInsertion.setRight(a);
			}
			last = a;
			// pointer to the current bubble
			Node temp = a;
			// bubble up
			for (; temp != root && k < temp.getParent().getValue(); temp = temp
					.getParent()) {
				temp.setValue(temp.getParent().getValue());
			}
			temp.setValue(k);
		}
		setParentOfNextInsertion();
	}

	public static void main(String[] args) {
		HeapRevised heap = new HeapRevised();
		heap.add(10);
		heap.add(5);
		heap.add(11);
		heap.add(3);
		heap.add(4);
		heap.remove();
		heap.remove();
		heap.add(9);
		heap.print();
		heap.add(13);
		heap.remove();
		heap.remove();
		heap.print();
	}

	/**
	 * Print the heap tree in a breadth-first fashion.
	 */
	public void print() {
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node current = queue.remove();
			System.out.println(current.getValue());
			if (current.getLeft() != null)
				queue.add(current.getLeft());
			if (current.getRight() != null)
				queue.add(current.getRight());
		}
	}
}