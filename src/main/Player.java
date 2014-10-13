package main;

public class Player {
  public static enum Color { BLACK, WHITE }
  public static final Color BLACK = Color.BLACK, WHITE = Color.WHITE;
  public String name;
  public Color color;
  private int unplacedPieces, piecesOnBoard;
  
  public Player() {
    this("Unnamed player");
  }
  public Player(String name) {
    this.name = name;
    unplacedPieces = 9;
    piecesOnBoard = 0;
  }
  public void setColor(Color color) {
    this.color = color;
  }
  public void placePiece() {
    unplacedPieces--;
    piecesOnBoard++;
  }
  public void removePiece() {
    piecesOnBoard--;
  }
  public int unplacedPieces() {
    return unplacedPieces;
  }
  public int piecesOnBoard() {
    return piecesOnBoard;
  }
  public int totalPieces() {
    return unplacedPieces + piecesOnBoard;
  }
  public boolean hasAvailableMoves(Board b) {
    // Player has pieces left to place
    if (unplacedPieces > 0) return true;
    // Player has no pieces left on board
    if (piecesOnBoard == 0) return false;
    // Player can fly -- From above, we know they have no unplaced pieces left, so no need to check that
    if (piecesOnBoard <= 3) return true;
    
    // Loop through all nodes and check the ones this player owns, to see if there are any adjacent empty spots
    for (int i = 0; i < 24; i++) {
      Node n = b.getNode(i);
      if (n.getPlayer() == this) {
        if (n.left != null && n.left.getPlayer() == null ||
            n.right != null && n.right.getPlayer() == null ||
            n.up != null && n.up.getPlayer() == null ||
            n.down != null && n.down.getPlayer() == null) {
          return true;
        }
      }
    }
    
    return false;
  }
}
