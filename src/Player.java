
public class Player {
  public static final int BLACK = 1, WHITE = 2;
  public String name;
  public int color;
  public Player() {
    this("Unnamed player");
  }
  public Player(String name) {
    this.name = name;
  }
  public void setColor(int color) {
    this.color = color;
  }
}
