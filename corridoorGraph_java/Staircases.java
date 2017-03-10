/*By Elias Posen and Charles Calder*/

import java.util.Iterator;
import java.util.*;
import java.io.*;


class Staircase {
  public int [] path;
  int counter; //counts the amount of nodes in linked list and assists with other counting task
  int i;
  int size;

  boolean run;
  Node first;
  Node fin;
  Node to_add;
  Node n0;
  Node n1;
  Node n2;
  Node n_prev;
  Node n_next;
  Node n_next_left;

  Staircase(Iterable<Integer> nodes, Graph G) {
    path = staircase(nodes, G);
  }

  public int s_length() {
    return size;
  }

  public int[] staircase(Iterable<Integer> nodes, Graph G) { //simplifies the path to avoid arbitrary movment
    counter = 0;
    i = 0;
    //convert Iterable into a linked list
    for (int n : nodes) {
      if (counter == 0) {
        first = new Node(n);
        n0 = first;
      } else {
        n1 = new Node(n);
        n0.next = n1;
        n0 = n1;
      }
      counter++;
    }
    fin = n0;
    int [] path = new int[counter]; // worst case size
    size = counter;
    n0 = first;
    n1 = n0.next;
    n2 = n1.next;
    int [] n0n = G.getNeighbor(n0.value);
    int [] n1n = G.getNeighbor(n1.value);

    path[i] = n0.value; //adds first node to path array at index 0
    //System.out.println(path[0]);
    i++;

    while (true) {
      counter = 0;
      run = true;
      if (i > 1) {
        n0 = n_prev;
        if (n0.next == null) {
          path[i] = fin.value;
          break;
        }
        else if (n0.next.next == null) {
          path[i] = fin.value;
          break;
        }
        n1 = n0.next;
        n2 = n1.next;
        n0n = G.getNeighbor(n0.value);
        n1n = G.getNeighbor(n1.value);
      }
      //accending staircases
      //right accending staircase
      if (n1.value == n0n[2] && n2.value == n1n[0]) { //case 1
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next = n_prev.next;
        while (run) {
          if (counter % 2 == 1) { //check if above
            if (n_next.value == G.getNeighbor(n_prev.value)[0]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
              }
            } else {
              to_add = n_prev;
              run = false;
            }
          } else { //check if right
            if (n_next.value == G.getNeighbor(n_prev.value)[2]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
              }
            } else {
              to_add = n_prev;
              run = false;
            }
          }
          counter++;
        }
      } else if (n1.value == n0n[0] && n2.value == n1n[2]) { //case 2
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next = n_prev.next;
        while (run) {
          //System.out.println("n_prev: " + n_prev.value + " n_next: " + n_next.value);
          if (counter % 2 == 1) { //check if right
            if (n_next.value == G.getNeighbor(n_prev.value)[2]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
              }
            } else {
              to_add = n_prev;
              run = false;
            }
          } else { //check if above
            if (n_next.value == G.getNeighbor(n_prev.value)[0]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
              }
            } else {
              to_add = n_prev;
              run = false;
            }
          }
          counter++;
        }
      }

      //left accending staircases
      else if (n1.value == n0n[3] && n2.value == n1n[0]) { //case 1
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next = n_prev.next;
        
        while (run) {
          //System.out.println("n_prev: " + n_prev.value + " n_next: " + n_next.value);
          if (counter % 2 == 1) { //check if above
            if (n_next.value == G.getNeighbor(n_prev.value)[0]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              run = false;
              
            }
          } else { //check if left
            if (n_next.value == G.getNeighbor(n_prev.value)[3]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              run = false;
              
            }
          }
          counter++;
        }
      } else if (n1.value == n0n[0] && n2.value == n1n [3]) { //case 2
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next = n_prev.next;
        
        while (run) {
          //System.out.println("n_prev: " + n_prev.value + " n_next: " + n_next.value);
          if (counter % 2 == 1) { //check if left
            if (n_next.value == G.getNeighbor(n_prev.value)[3]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              run = false;
              
            }
          } else { //check if above
            if (n_next.value == G.getNeighbor(n_prev.value)[0]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              run = false;
              
            }
          }
          counter++;
        }
      }
      // decending staircases
      //right decending staircase
      else if (n1.value == n0n[2] && n2.value == n1n[1]) { //case 1
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next = n_prev.next;
        
        while (run) {
          //System.out.println("n_prev: " + n_prev.value + " n_next: " + n_next.value);
          if (counter % 2 == 1) { //check if down
            if (n_next.value == G.getNeighbor(n_prev.value)[1]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              
              run = false;
            }
          } else { //check if right
            if (n_next.value == G.getNeighbor(n_prev.value)[2]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              
              run = false;
            }
          }
          counter++;
        }
      } else if (n1.value == n0n[1] && n2.value == n1n[2]) { //case 2
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next = n_prev.next;
        
        while (run) {
          //System.out.println("n_prev: " + n_prev.value + " n_next: " + n_next.value);
          if (counter % 2 == 1) { //check if right
            if (n_next.value == G.getNeighbor(n_prev.value)[2]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              
              run = false;
            }
          } else { //check if down
            if (n_next.value == G.getNeighbor(n_prev.value)[1]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              
              run = false;
            }
          }
          counter++;
        }
      }
      //decending left staircase
      else if (n1.value == n0n[3] && n2.value == n1n[1]) { //case 1
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next = n_prev.next;
        
        while (run) {
          //System.out.println("n_prev: " + n_prev.value + " n_next: " + n_next.value);
          if (counter % 2 == 1) { //check if down
            if (n_next.value == G.getNeighbor(n_prev.value)[1]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              
              run = false;
            }
          } else { //check if left
            if (n_next.value == G.getNeighbor(n_prev.value)[3]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              
              run = false;
            }
          }
          counter++;
        }
      } 
      else if (n1.value == n0n[1] && n2.value == n1n[3]) { //case 2
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next = n_prev.next;
        
        while (run) {
          if (counter % 2 == 1) { //check if left
            if (n_next.value == G.getNeighbor(n_prev.value)[3]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              
              run = false;
            }
          } else { //check if down
            if (n_next.value == G.getNeighbor(n_prev.value)[1]) { //the staircase continues
              n_prev = n_next;
              n_next = n_prev.next;
              if (n_next == null) {
                to_add = n_prev;
                run = false;
                
              }
              
            } else {
              to_add = n_prev;
              
              run = false;
            }
          }
          counter++;
        }
      }
      //horizantal staircases
      //left staircase
      else if (n1.value == n0n[3] && n2.value == n1n[3]) {
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next_left = n_prev.next;
        while (run) {
          if (n_next_left != null && n_next_left.value == G.getNeighbor(n_prev.value)[3]) { //the staircase continues
            n_prev = n_next_left;
            n_next_left = n_prev.next;
            if (n_next_left == null) {
              to_add = n_prev;
              run = false;
              
            }
            
          } 
          else {
            to_add = n_prev;
            
            run = false;
          }
        }
      }
      //right staircase
      else if (n1.value == n0n[2] && n2.value == n1n[2]) {
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next = n_prev.next;
        
        while (run) {
          if (n_next != null && n_next.value == G.getNeighbor(n_prev.value)[2]) { //the staircase continues
            n_prev = n_next;
            n_next = n_prev.next;
            if (n_next == null) {
              to_add = n_prev;
              run = false;
              
            }
            
          } else {
            to_add = n_prev;
            
            run = false;
          }
        }
      }
      //Verticle staircases
      //ascending
      else if (n1.value == n0n[0] && n2.value == n1n[0]) {
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next = n_prev.next;
        
        while (run) {
          if (n_next.value == G.getNeighbor(n_prev.value)[0]) { //the staircase continues
            n_prev = n_next;
            n_next = n_prev.next;
            if (n_next == null) {
              to_add = n_prev;
              run = false;
              
            }
            
          } else {
            to_add = n_prev;
            
            run = false;
          }
        }
      }
      //decending
      else if (n1.value == n0n[1] && n2.value == n1n[1]) {
        path[i] = n0.value;
        i++;
        n_prev = n2;
        n_next = n_prev.next;
        
        while (run) {
          if (n_next.value == G.getNeighbor(n_prev.value)[1]) { //the staircase continues
            n_prev = n_next;
            n_next = n_prev.next;
            if (n_next == null) {
              to_add = n_prev;
              run = false;
              
            }
          } else {
            to_add = n_prev;
            
            run = false;
          }
        }
      }
      path [i] = to_add.value;
    }

    //resize array
    int [] return_path = new int[i];

    for (int j = 0; j < i; j++) {
      return_path[j] = path[i - j];
    }

    //reset size to new array length for s_length function
    size = i;

    return return_path;
  }
}