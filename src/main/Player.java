package main;

public class Player {
  public static enum Color { BLACK, WHITE }
  public static final Color BLACK = Color.BLACK, WHITE = Color.WHITE;
  public String name;
  public Color color;
  public Player() {
    this("Unnamed player");
  }
  public Player(String name) {
    this.name = name;
  }
  public void setColor(Color color) {
    this.color = color;
  }
}
