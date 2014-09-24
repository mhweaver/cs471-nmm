package main;
//Created By: Matthew Gillatt
public class Node {
  protected Node left, right, up, down;
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
            farLeft.right != null && farLeft.right.player == this.player &&
            farLeft.right.right != null && farLeft.right.right.player == this.player) ||
           (farUp.player == this.player &&
            farUp.down != null && farUp.down.player == this.player &&
            farUp.down.down != null && farUp.down.down.player == this.player);
    
  }
}
