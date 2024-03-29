
import java.util.*;

public class Heap {

    private final boolean DEBUGGING = true;
    private int mySize;
    private int [ ] myElements;

    // Maintain a binary max heap of ints that contains mySize elements. 
    // The entries of the heap are stored sequentially in the myElements array; 
    // thus the array represents a binary tree of mySize nodes whose depth is minimal 
    // and in which the bottom level is as far "left" as possible.  The parent of the entry 
    // at position k is at position (k-1)/2; the two children are at positions 2*k+1 and 2*k+2. 
    // Each entry in the heap has at least as large a value as its children.
    // Initialize an empty heap to hold up to the given number of elements.

    public Heap (int capacity) {
        mySize = 0;
        myElements = new int [capacity];
        if (DEBUGGING) {
            display ( );
            check ( );
        }
    }

    // Return true exactly when the heap is empty.
    public boolean isEmpty ( ) {
        return mySize == 0;
    }

    // Return the top of the heap.
    public int top ( ) throws NoSuchElementException {
        if (isEmpty ( )) {
            throw new NoSuchElementException ("accessing top of empty heap");
        }
        return myElements[0];
    }

    // Remove the top of the heap.
    public void remove ( ) throws NoSuchElementException {
        if (isEmpty ( )) {
            throw new NoSuchElementException ("removing from empty heap");
        }
        if (mySize == 1) {
            mySize = 0;
        } else {
            myElements[0] = myElements[mySize-1];
            mySize--;
            bubbleDown (0);
        }
        if (DEBUGGING) {
            display ( );
            check ( );
        }
    }

    // Add an element to the heap.
    public void add (int newElement) {
        myElements[mySize] = newElement;
        mySize++;
        bubbleUp (mySize-1);
        if (DEBUGGING) {
            display ( );
            check ( );
        }
    }

    // Move the heap entry indexed by k down the heap until it's larger than both its children.  
    // Each move exchanges the entry with its larger child.
    private void bubbleDown (int k) {
        assert k >= 0 && k < mySize: "Assertion failed while bubbling " + k + " down";
        while (2*k+1 < mySize-1) {
            int maxChildIndex = isGreater (2*k+1, 2*k+2)? 2*k+1: 2*k+2;
            if  (!isGreater (maxChildIndex, k)) {
                return;
            }
            exchange (maxChildIndex, k);
            k = maxChildIndex;
        }
        if (2*k+1 == mySize-1) {    /* maybe only one child */
            if (isGreater (mySize-1, k)) {
                exchange (mySize-1, k);
            }
        }
    }

    // Move the heap entry indexed by k up the heap until it's smaller than its parent.
    private void bubbleUp (int k) {
        assert k >= 0 && k < mySize: "Assertion failed while bubbling " + k + " up";
        while (k > 0 && isGreater (k, (k-1)/2)) {
            exchange (k, (k-1)/2);
            k = (k-1)/2;
        }
    }

    // Exchange the two heap elements at the given positions.
    private void exchange (int k1, int k2) {
        assert k1 >= 0 && k2 >= 0 && k1 < mySize && k2 < mySize:
            "Assertion failed while exchanging " + k1 + " with " + k2;
        int temp = myElements[k1];
        myElements[k1] = myElements[k2];
        myElements[k2] = temp;
    }



    // Return true exactly when the heap entry with index k1
    // is greater than the heap entry with index k2.
    private boolean isGreater (int k1, int k2) {
        assert k1 >= 0 && k2 >= 0 && k1 < mySize && k2 < mySize:
            "Assertion failed while comparing " + k1 + " and " + k2;
        return myElements[k1] > myElements[k2];
    }

    // Display the heap.
    public void display ( ) {
        System.out.println ("Heap (" + mySize + " elements):");
        displayHelper (0, 0);
        System.out.println ( );
    }

    // Display the subheap rooted at element k,
    // indenting each element according to the indent level argument.
    private void displayHelper (int k, int indentLevel) {
        if (k >= mySize) {
            return;
        }
        displayHelper (2*k+1, indentLevel+1);
        for (int j=0; j<indentLevel; j++) {
            System.out.print ("  ");
        }
        System.out.println (myElements[k]);
        displayHelper (2*k+2, indentLevel+1);
    }

    // Check the heap for consistency.
    // Each element must be at least as large as its children.
    private void check ( ) {
        int k;
        for (k=0; 2*k+1<mySize-1; k++) {
            assert !isGreater(2*k+1,k): "Assertion failed while checking left child of " + k;
            assert !isGreater(2*k+2,k): "Assertion failed while checking right child of " + k;
        }
        if (2*k+1 == mySize-1) {
            assert !isGreater(2*k+1,k): 
                "Assertion failed while checking left (only) child of " + k;
        }
    }

    public static void main (String [ ] args) {
        Heap h = new Heap (9);
        for (int i=1; ; i++) {
            try {
                h.add (i);
            } catch (IndexOutOfBoundsException e) {
                System.out.println ("Heap filled up at insertion " + i);
                break;
            }
        }
        for (int i=1; ; i++) {
            try {
                h.remove ( );
            } catch (NoSuchElementException e) {
                System.out.println ("Heap emptied at removal " + i);
                break;
            }
        }
        Random r = new Random ( );
        for (int i=1; ; i++) {
            try {
                int newElement = r.nextInt ( );
                newElement = newElement < 0? (-newElement)%10: newElement%10; 
                h.add (newElement);
            } catch (IndexOutOfBoundsException e) {
                System.out.println ("Heap filled up at insertion " + i);
                break;
            }
        }
        for (int i=1; ; i++) {
            try {
                h.remove ( );
            } catch (NoSuchElementException e) {
                System.out.println ("Heap emptied at removal " + i);
                break;
            }
        }
    }
}