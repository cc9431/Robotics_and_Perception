/*By Elias Posen and Charles Calder*/

class CorridoorGraph{
  PImage test; //Image from overhead camera
  Graph G; 

  int num_clust;//amount of clusters

  int p; //Columns of culsters
  int q; //Rows of culsters
  int node_width; //Width of cluster in pixels
  int node_height; //Height of cluster in pixels

  //Color thresholds
  int orange_thresh, blue_thresh, yellow_thresh, green_thresh;
  //orange clusters color
  int r_o, g_o, b_o;
  //yellow cluster color
  int r_y, g_y, b_y;
  boolean havent_found_end;
  int end; // node key of end in graph
  //green cluster color
  int r_g, g_g, b_g;
  boolean havent_found_source;
  int source;// node key of source in graph
  //blue cluster color
  int r_b, g_b,  b_b;
  boolean havent_found_start;
  int start;// node key of start in graph

  int top_right; //node key of top right cluster in graph
  int bottom_right; // node key of bottom right cluster in graph

  // gathers rgb values from .txt file that find_colors.pde writes to
  String[] lines = loadStrings("path_colors.txt");

  CorridoorGraph(){
    blue_thresh = 10;
    yellow_thresh = 10;
    green_thresh = 30;
    orange_thresh = 40;
    //orange
    r_o =  210;
    b_o = 22;
    g_o = 31;
    //blue
    r_b = int(lines[0]);
    g_b = int(lines[1]);
    b_b = int(lines[2]);
    havent_found_end = true;
    //green
    r_g = int(lines[3]);
    g_g = int(lines[4]);
    b_g = int(lines[5]);
    havent_found_source = true;
    //yellow
    r_y = int(lines[6]);
    g_y = int(lines[7]);
    b_y = int(lines[8]);
    havent_found_start = true;

    //rgb values of orange was pretty consistant
    orange_thresh = 40;
    r_o =  210;
    b_o = 22;
    g_o = 31;

    r_b = int(lines[0]);
    g_b = int(lines[1]);
    b_b = int(lines[2]);
    havent_found_end = true;

    r_g = int(lines[3]);
    g_g = int(lines[4]);
    b_g = int(lines[5]);
    havent_found_source = true;

    r_y = int(lines[6]);
    g_y = int(lines[7]);
    b_y = int(lines[8]);
    havent_found_start = true;

    //Image initialization
    test = loadImage("test0.jpg");
    //size(2048, 1536); // Correspods to the size of the image from our overhead cameras
    //image(test, 0, 0);
    //loadPixels();


    //Graph initialization
    p = 64;
    q = 64;
    node_height = height/q;
    node_width = width/p;
    top_right = node_height * (node_width - 1);
    bottom_right = (node_height * node_width) - 1;
    //Intialized to zero because they are currently unkown
    source = 0;
    end = 0;
    start = 0;

    num_clust = (width*height) / (p*q);

    G = new Graph(num_clust, node_height, node_width);

    fill(0,0,0);
    rect(0,64*20,width,4*64); // Deals with the shine that usually came in at the bottom of the picture
  }

  public Graph make(){
    int idx = 0; 
    //Moves to next cluster once current has been assinged a color
    boolean exit =false;

    int x_mult =0;
    int y_mult =0;

    for (int x = 0; x < width; x+=p) {
      for (int y = 0; y < height; y+=q) { //Loops through each cluster
        //to set loction of cluster to be the center pixel of the cluster
        G.setPixelPos(idx, (x + (p/2)), (y + (p/2)));
        G.setPos(idx, x_mult*119, y_mult*115);

        for (int x1 = x; x1 < x+p; x1++) { //Loops through each pixel in cluster idx
          for (int y1 = y; y1 < y+q; y1++) {
            color px = get(x1, y1);

            /*finds green start cluster given some rgb threshold. 
            Boolean havent_found_source ensures only one green cluster is marked in the graph*/
            if (red(px) > r_g - green_thresh && red(px) < r_g + green_thresh && blue(px) > b_g - green_thresh 
                && blue(px) < b_g + green_thresh && green(px) > g_g - green_thresh && green(px) < g_g + green_thresh && havent_found_source) {
              G.setColor(idx, "GREEN");
              source = idx;
              havent_found_source = false;
              // Picutre: colors the end yellow cluser bright yellow for graph visualization
              for (int x2 = x; x2 < x + p; x2++) {
                for (int y2 = y; y2 < y + p; y2++) {
                  set(x2, y2, color (0, 255, 0));
                }
              }
              exit = true;
            }

            //find blue clusters
            else if (red(px) > r_b - blue_thresh && red(px) < r_b + blue_thresh && blue(px) > b_b - blue_thresh 
                     && blue(px) < b_b + blue_thresh && green(px) > g_b - blue_thresh && green(px) < g_b + blue_thresh && havent_found_start) {
              G.setColor(idx, "BLUE");
              start = idx;
              havent_found_start = false;
              // Picutre: colors the source cluser blue yellow for graph visualization
              for (int x2 = x; x2 < x + p; x2++) {
                for (int y2 = y; y2 < y + p; y2++) {
                  set(x2, y2, color (0, 0, 255));
                }
              }
              exit = true;
            }

            //find yellow clusters
            else if (red(px) > r_y - yellow_thresh && red(px) < r_y + yellow_thresh && blue(px) > b_y - yellow_thresh 
                     && blue(px) < b_y + yellow_thresh && green(px) > g_y - yellow_thresh && green(px) < g_y + yellow_thresh && havent_found_end) {
              G.setColor(idx, "YELLOW");
              end = idx;
              havent_found_end = false;
              // Picutre: colors the end cluser bright yellow for graph visualization
              for (int x2 = x; x2 < x + p; x2++) { 
                for (int y2 = y; y2 < y + p; y2++) {
                  set(x2, y2, color (255, 255, 25));
                }
              }
              exit = true;
            }

            /*Find orange clusters according to some threshold. 
            Marks cluster as ORANGE or "unreachable" in graph*/
            else if (red(px) > r_o - orange_thresh   &&   blue(px) < b_o + orange_thresh   &&   green(px) < g_o + orange_thresh   &&   green(px) > g_o - orange_thresh) {
              G.setColor(idx, "ORANGE");
              // Picture: colors the clusers with orange wall pink for graph visualization
              for (int x2 = x; x2 < x + p; x2++) { 
                for (int y2 = y; y2 < y + p; y2++) {
                  set(x2, y2, color(255, 192, 203));
                }
              }
              exit = true;
            }
            /*If cluster is not green, orange, blue or yellow then mark cluster white. 
              Free movabale space.*/
            else {
              G.setColor(idx, "WHITE");
            }
            generateNeighbors(idx);

            if (exit) break;
        }
          if (exit) {
            exit=false;
            break;
          }
        }
        /// last section before moving on to a new cluster
        for (int n : G.getNeighbor(idx)) {
          if (n != -1 && G.getColor(idx) == "WHITE" || n != -1 && G.getColor(idx) == "GREEN" || n != -1 && G.getColor(idx) == "YELLOW" || n != -1 && G.getColor(idx) == "BLUE") {
            G.addEdge(idx, n);
          }
        }
        y_mult++;
        idx++; // looking at new cluster
      }
      x_mult++;
      y_mult = 0;
    }

    return G;
  }
  //Given the index of the node, how many neighbors should that node have?
  public void generateNeighbors(int idx){
    if (idx == 0) {
        G.makeNeighbor(idx, false, true, true, false);
      }
      //left column (no neighbors on left)
      else if (idx > 0 && idx < (node_height - 1)) {
        G.makeNeighbor(idx, true, true, true, false);
      }
      //bottom left (no neighbors to left or below)
      else if (idx == node_height - 1) {
        G.makeNeighbor(idx, true, false, true, false);
      }
      //top row (no neighbors above)
      else if (idx != 0 && idx != top_right && idx % node_height == 0) {
        G.makeNeighbor(idx, false, true, true, true);
      }
      //bottom row (no neighbors below)
      else if (idx != (node_height - 1) && idx != bottom_right && (idx + 1) % node_height == 0) {
        G.makeNeighbor(idx, true, false, true, true);
      }
      //top right (no neighbors above or on right)
      else if (idx == top_right) {
        G.makeNeighbor(idx, false, true, false, true);
      }
      //right column (no neighbors on right)
      else if (idx > top_right && idx < bottom_right) {
        G.makeNeighbor(idx, true, true, false, true);
      }
      //bottom right (no neighbors above or on right)
      else if (idx == bottom_right) {
        G.makeNeighbor(idx, true, false, false, true);
      }
      //middle (neighbors on all sides)
      else {
        G.makeNeighbor(idx, true, true, true, true);
      }
  }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

CorridoorGraph cg = new CorridoorGraph();
//Creating image for graph visualization
size(2048, 1536);
image(cg.test, 0, 0);
loadPixels();

Graph G = cg.make();

BreadthFirstDirectedPaths bfs_start = new BreadthFirstDirectedPaths(G, cg.start);

BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, cg.source);

//Picture: places a black dot in the middle of each cluster on the path from source to the start
for (int node : bfs_start.pathTo(cg.source)) {
  fill(50, 50, 50);
  ellipse(G.getPPos(node)[0], G.getPPos(node)[1], 10, 10);
}
//Picture: places a black dot in the middle of each cluster on the path from source to the start
for (int node : bfs.pathTo(cg.end)) {
  fill(255, 255, 255);
  ellipse(G.getPPos(node)[0], G.getPPos(node)[1], 10, 10);
}

save("test0_path.jpg"); //Path without staircase applied

//create staircase from source to end
Staircase s = new Staircase(bfs.pathTo(cg.end), G);
//create staircase from start to source
Staircase st = new Staircase(bfs_start.pathTo(cg.source), G);

//list of commands for python code to interpret
String[] python_commands_st = new String[(st.s_length() + s.s_length()) * 2 - 2];

//give python the initial coordinates of robot

int count;
int x;
int y;

count = 0;

// Writes positions of path to txt file

for (int node_st : st.path) {
  fill(0, 255, 0);
  ellipse(G.getPPos(node_st)[0], G.getPPos(node_st)[1], 20, 20); // marks updated path with green circles
  x = G.getPos(node_st)[0];
  y = G.getPos(node_st)[1];
  python_commands_st[count] = str(x);
  count++;
  python_commands_st[count] = str(y);
  count++;
}
count = count - 2;

for (int node : s.path) {
  fill(0, 255, 0);
  ellipse(G.getPPos(node)[0], G.getPPos(node)[1], 20, 20); // marks updated path with green circles
  x = G.getPos(node)[0];
  y = G.getPos(node)[1];
  python_commands_st[count] = str(x);
  count++;
  python_commands_st[count] = str(y);
  count++;
}

save("test0_staircase_path.jpg");

saveStrings("path_directions.txt", python_commands_st);