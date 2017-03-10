/*Originally from http://algs4.cs.princeton.edu/code/ by Sedgewick and Wane, 
modified by Elias Posen and Charles Calder*/

import java.util.Iterator;
import java.util.*;
import java.io.*;

class BreadthFirstDirectedPaths {
  private static final int INFINITY = Integer.MAX_VALUE;
  private boolean[] marked;  // marked[v] = is there an s->v path?
  private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
  private int[] distTo;      // distTo[v] = length of shortest s->v path


  public BreadthFirstDirectedPaths(Graph G, int s) {
    marked = new boolean[G.V()];
    distTo = new int[G.V()];
    edgeTo = new int[G.V()];
    for (int v = 0; v < G.V(); v++)
      distTo[v] = INFINITY;
    bfs(G, s);
  }


  public BreadthFirstDirectedPaths(Graph G, Iterable<Integer> sources) {
    marked = new boolean[G.V()];
    distTo = new int[G.V()];
    edgeTo = new int[G.V()];
    for (int v = 0; v < G.V(); v++)
      distTo[v] = INFINITY;
    bfs(G, sources);
  }

  // BFS from single source
  private void bfs(Graph G, int s) {
    Queue<Integer> q = new Queue<Integer>();
    marked[s] = true;
    distTo[s] = 0;
    q.enqueue(s);
    while (!q.isEmpty()) {
      int v = q.dequeue();
      for (int w : G.adj(v)) {
        if (!marked[w]) {
          edgeTo[w] = v;
          distTo[w] = distTo[v] + 1;
          marked[w] = true;
          q.enqueue(w);
        }
      }
    }
  }

  // BFS from multiple sources
  private void bfs(Graph G, Iterable<Integer> sources) {
    Queue<Integer> q = new Queue<Integer>();
    for (int s : sources) {
      marked[s] = true;
      distTo[s] = 0;
      q.enqueue(s);
    }
    while (!q.isEmpty()) {
      int v = q.dequeue();
      for (int w : G.adj(v)) {
        if (!marked[w]) {
          edgeTo[w] = v;
          distTo[w] = distTo[v] + 1;
          marked[w] = true;
          q.enqueue(w);
        }
      }
    }
  }


  public boolean hasPathTo(int v) {
    return marked[v];
  }


  public int distTo(int v) {
    return distTo[v];
  }


  public Iterable<Integer> pathTo(int v) {
    if (!hasPathTo(v)) return null;
    Stack<Integer> path = new Stack<Integer>();
    int x;
    for (x = v; distTo[x] != 0; x = edgeTo[x])
      path.push(x);
    path.push(x);
    return path;
  }
}