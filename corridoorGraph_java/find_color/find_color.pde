int mX, mY;
int imgX, imgY;
PImage test;
String[] path_colors;
int count;

public void setup(){
  path_colors = new String[9];
  test = loadImage("classtest.jpg");
  
  size(2048,1536);
  centerImage();
  
 
}

public void mousePressed()
{
 // set variables for holding mouseposition offset 
 // to the image
 mX = mouseX-imgX;
 mY = mouseY-imgY;
}

// any key pressed, will re-center the image
public void keyPressed()
{
centerImage();
saveStrings("/Applications/Processing/Final/corridoorGraph_java/path_colors.txt", path_colors);
}

// center image function
public void centerImage()
{
 imgX = (width-test.width)/2;
 imgY = (height-test.height)/2;
}

public void mouseClicked(){
  
  color px = get(mouseX,mouseY);
  println("red: " + red(px) + " green: " + green(px) + " blue: " + blue(px));
  String red = str(red(px));
  String green = str(green(px));
  String blue = str(blue(px));
  path_colors[count] = red;
  count++;
  path_colors[count] = green;
  count++;
  path_colors[count] = blue;
  count++;
  //println(mouseX + " " + mouseY);
}

public void draw(){
 background(0);
 if(mousePressed){ // is the mousebutton being held?
   imgX = mouseX-mX;
   imgY = mouseY-mY;
 }
 image(test,imgX,imgY);
}