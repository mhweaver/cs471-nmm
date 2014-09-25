package main;

public class Game {

  // Phase 1: Place pieces
  // Phase 2: Move pieces
  // Phase 3: Flying
  private int phase;
  
  private int turnsPlayed = 0;
  private boolean removeTurn = false; // Does someone need to remove a piece this turn?
  
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
    if (removeTurn) {
      throw new IllegalMoveException("A piece needs to be removed before a new piece can be placed");
    }
    if (phase != 1) {
      throw new IllegalMoveException("Pieces can only be placed in phase 1");
    }
    if (currentPlayer != player) {
      throw new IllegalMoveException("Pieces can only be placed during the current player's turn");
    }
    
    Node n = board.getNode(location);
    if (n.getPlayer() != null) {
      throw new IllegalMoveException("Pieces can only be placed on empty spots");
    }
    // any other cases to not allow?
    
    n.setPlayer(player);
    // TODO: make any appropriate changes to the player (number of pieces left to place, etc)
    
    if (n.mill()) {
      removeTurn = true;
    } else {
      nextTurn();
    }
  }
  
  public void removePiece(Player player, int location) throws IllegalMoveException {
    if (!removeTurn) {
      throw new IllegalMoveException("It is not an appropriate time to remove a piece");
    }
    if (currentPlayer != player) {
      throw new IllegalMoveException("Wrong player");
    }
    
    Node n = board.getNode(location);
    if (n.getPlayer() == null) {
      throw new IllegalMoveException("No piece to remove");
    }
    if (n.getPlayer() != currentPlayer) {
      throw new IllegalMoveException("Wrong player");
    }
    
    n.setPlayer(null);
    //TODO: make any appropriate changes to the player (number of pieces left to place, etc)
    
    removeTurn = false;
  }

  private void nextTurn() {
    currentPlayer = (currentPlayer == players[0] ? players[1] : players[0]);
    turnsPlayed++;
  }
}
