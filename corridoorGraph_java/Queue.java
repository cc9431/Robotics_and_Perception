/*Originally from http://algs4.cs.princeton.edu/code/ by Sedgewick and Wane, 
modified by Elias Posen and Charles Calder*/

import java.util.Iterator;
import java.util.*;
import java.io.*;

class Queue<Item> implements Iterable<Item> {
  private int N;         // number of elements on queue
  private Node first;    // beginning of queue
  private Node last;     // end of queue

  // helper linked list class
  class Node {
    private Item item;
    private Node next;
  }

  public Queue() {
    first = null;
    last  = null;
    N = 0;
  }

  public boolean isEmpty() {
    return first == null;
  }

  public int size() {
    return N;
  }

  public int length() {
    return N;
  }


  public Item peek() {
    if (isEmpty()) throw new NoSuchElementException("Queue underflow");
    return first.item;
  }

  public void enqueue(Item item) {
    Node oldlast = last;
    last = new Node();
    last.item = item;
    last.next = null;
    if (isEmpty()) first = last;
    else           oldlast.next = last;
    N++;
  }

  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Queue underflow");
    Item item = first.item;
    first = first.next;
    N--;
    if (isEmpty()) last = null;   // to avoid loitering
    return item;
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    for (Item item : this)
      s.append(item + " ");
    return s.toString();
  } 


  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  // an iterator, doesn't implement remove() since it's optional
  class ListIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() { 
      return current != null;
    }
    public void remove() { 
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next; 
      return item;
    }
  }
}