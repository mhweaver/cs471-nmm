//Created By: Matthew Gillatt
public class Node {
  private Node left, right, up, down;
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
}
