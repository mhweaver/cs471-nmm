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
  public int unplacedPieces() {
    return unplacedPieces;
  }
  public int piecesOnBoard() {
    return piecesOnBoard;
  }
}
