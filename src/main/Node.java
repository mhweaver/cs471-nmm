package main;
//Created By: Matthew Gillatt
public class Node {
  protected Node left, right, up, down;
  private Player player = null;
  private int index;
  private boolean selected;
  
  //x and y coordinates of node on image
  private int x;
  private int y;
  
  public Test test = new Test();
  
  //Node stuff right
  public Node(int index) {
    this.index = index;
    selected = false;
  }
  public Node setLeft(Node left) {
    return this.left = left;
  }
  public Node setRight(Node right) {
    right.setLeft(this);
    return this.right = right;
  }
  public Node setUp(Node up) {
    return this.up = up;
  }
  public Node setDown(Node down) {
    down.setUp(this);
    return this.down = down;
  }
  
  public Player getPlayer() {
    return player;
  }
  public void setPlayer(Player player) {
    this.player = player;
  }
  
  public void setCoords(int i, int j) {
	  this.x = i;
	  this.y = j;
  }
  
  public int getXCoord() {
	  return x;
  }
  
  public int getYCoord() {
	  return y;
  }
  
  public int getIndex() {
	  return index;
  }
  
  
  /** Check if this Node is part of a mill
   * @return true, if this Node is part of a mill; false, otherwise
   */
  public boolean mill() {
    // TODO: mill() REALLY needs tested. (I think it should work, but...)
    // Move both the far left and far top, in a straight line, from this node
    Node farLeft, farUp;
    farLeft = farUp = this;
    for (; farLeft.left != null; farLeft = farLeft.left);
    for (; farUp.up != null; farUp = farUp.up);
    
    // If the farLeft, farLeft.right, and farLeft.right.right all belong to this node's player
    // or farUp, farUp.down, and farUp.down.down all belong to this node's player, then return true
    return (farLeft.player == this.player &&
            farLeft.right.player == this.player &&
            farLeft.right.right.player == this.player) ||
           (farUp.player == this.player &&
            farUp.down.player == this.player &&
            farUp.down.down.player == this.player);
    
  }
  
  public boolean isInRegion(int inX, int inY) {
	  return Math.sqrt(Math.pow(inX-x,2)+Math.pow(inY-y,2)) <=30;
  }
  
  public boolean isNeighbor(Node n) {
    return (n == this.left || n == this.right || n == this.up || n == this.down);
  }
  
  public void select() {
	  selected = true;
  }
  
  public void unSelect() {
	  selected = false;
  }
  
  public boolean isSelected() {
	  return selected;
  }
  
  public class Test {
    public Node getLeft() {
      return left;
    }
    public Node getRight() {
      return right;
    }
    public Node getUp() {
      return up;
    }
    public Node getDown() {
      return down;
    }
    public Player getPlayer() {
      return player;
    }
    public int getIndex() {
      return index;
    }
    public int[] getCoords() {
      int[] i = {x, y};
      return i;
    }
  }
  
}
