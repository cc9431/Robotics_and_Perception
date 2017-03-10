/*Originally from http://algs4.cs.princeton.edu/code/ by Sedgewick and Wane, 
modified by Elias Posen and Charles Calder*/

class Graph {
  private final String NEWLINE = System.getProperty("line.separator");

  private final int V;
  private int E;
  private Bag<Integer>[] adj;
  private int[][] xy;
  private int[][] pxy;
  private String [] cluster_c;
  private int [][] neighbor;
  private int node_width;
  private int node_height;



  public Graph(int V, int h, int w) {
    if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative!!!");
    this.V = V;
    this.E = 0;
    node_width = w;
    node_height = h;
    adj = (Bag<Integer>[]) new Bag[V];
    cluster_c = new String [V];
    xy = new int[V][2]; //Physical xy coordinate field of graph
    pxy = new int[V][2]; //Pixel coordinate field of graph
    neighbor = new int [V][4];
    for (int v = 0; v < V; v++) {
      adj[v] = new Bag<Integer>();
    }
  }

  public void makeNeighbor(int node, boolean above, boolean below, boolean right, boolean left) {
    if (above) neighbor[node][0] = node - 1;

    else neighbor[node][0] = -1;

    if (below) neighbor[node][1] = node + 1;

    else neighbor[node][1] = -1;

    if (right) neighbor[node][2] = node + node_height;

    else neighbor[node][2] = -1;

    if (left) neighbor[node][3] = node - node_height;

    else neighbor[node][3] = -1;
  }


  public int[] getNeighbor(int node) {
    return neighbor[node];
  }

  public void setPixelPos(int node, int x, int y) { 
    pxy[node][0]=x;
    pxy[node][1]=y;
  }

  public int[] getPPos(int node) {
    return pxy[node];
  }

  public void setPos(int node, int x, int y) {
    xy[node][0]=x;
    xy[node][1]=y;
  }

  public int[] getPos(int node) {
    return xy[node];
  }

  public void setColor(int node, String c) {
    cluster_c[node] = c;
  }
  public String getColor(int node) {
    return cluster_c[node];
  }

  public int V() {
    return V;
  }


  public int E() {
    return E;
  }


  private void validateVertex(int v) {
    if (v < 0 || v >= V)
      throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
  }

  public void addEdge(int v, int w) {
    validateVertex(v);
    validateVertex(w);
    E++;
    adj[v].add(w);
    //adj[w].add(v);
  }

  //public void removeEdges(int v)



  public Iterable<Integer> adj(int v) {
    validateVertex(v);
    return adj[v];
  }


  public int degree(int v) {
    validateVertex(v);
    return adj[v].size();
  }


  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(V + " vertices, " + E + " edges " + NEWLINE);
    for (int v = 0; v < V; v++) {
      s.append(v + ": ");
      for (int w : adj[v]) {
        s.append(w + " ");
      }
      s.append(NEWLINE);
    }
    return s.toString();
  }
}