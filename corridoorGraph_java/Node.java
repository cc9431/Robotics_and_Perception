import java.util.Iterator;
import java.util.*;
import java.io.*;

class Node {
  public int value;
  public Node next;

  Node(int value) {
    this.value = value;
  }

  public String toString() {
    if (next != null) return "Value: " + value + " Next: " + next.value;
    else {
      return "Value: " + value + " next == null";
    }
  }
}