//Created By: Matthew Gillatt
public class Node {
  private Node left, right, up, down;
  private Player player = null;
  
  //Node stuff right
  public Node() {
    
  }
  public Node setLeft(Node left) {
    left.setRight(this);
    return this.left = left;
  }
  public Node setRight(Node right) {
    right.setLeft(this);
    return this.right = right;
  }
  public Node setUp(Node up) {
    up.setDown(this);
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
  public boolean mill() {
    // TODO Auto-generated method stub
    
    // possible, simple algorithm to check to see if this piece is part of a mill:
    // Move all the way to the left, then check the 2 nodes to the right
    // else
    // Move all the way up, then check the 2 nodes below
    return false;
  }
}
