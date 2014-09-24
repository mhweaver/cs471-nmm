
public class Game {

  // Phase 1: Place pieces
  // Phase 2: Move pieces
  // Phase 3: Flying
  private int phase;
  
  protected Board board;
  protected Player[] players;
  protected Player currentPlayer;
  
  public Game() {
    phase = 1;
    board = new Board();
    initPlayers();
  }
  
  private void initPlayers() {
    players = new Player[2];
    players[0] = currentPlayer = new Player("Player 1");
    players[0].setColor(Player.WHITE);
    players[1] = new Player("Player 2");
    players[1].setColor(Player.BLACK);
  }
  
  public void placePiece(Player player, int location) throws IllegalMoveException {
    if (phase != 1) {
      throw new IllegalMoveException("Pieces can only be placed in phase 1");
    }
    if (currentPlayer != player) {
      throw new IllegalMoveException("Pieces can only be placed during the current player's turn");
    }
    // if there's a piece at location, exception
    // any other cases to not allow?
    
    // put a piece at location. I'm thinking maybe add a color variable to Node. Or maybe track which player owns the node, since color = player...
  }
}
