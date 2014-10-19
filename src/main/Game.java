package main;

public class Game {

  
  private int turnsPlayed = 0;
  
  protected Board board;
  protected Player player1, player2;
  protected Player currentPlayer;
  
  protected enum Move { Place, Remove, Move, None }
  protected Move expectedMove;
  
  public Game() {
    board = new Board();
    expectedMove = Move.Place;
    initPlayers();
  }
  
  private void initPlayers() {
    player1 = currentPlayer = new Player("Player 1");
    player1.setColor(Player.WHITE);
    player2 = new Player("Player 2");
    player2.setColor(Player.BLACK);
  }
  
  public Player placePiece(int location) throws IllegalMoveException {
    if (expectedMove != Move.Place) {
      throw new IllegalMoveException("Invalid move attempted (place)");
    }
    
    Node n = board.getNode(location);
    if (n.getPlayer() != null) {
      throw new IllegalMoveException("Pieces can only be placed on empty spots");
    }
    // any other cases to not allow?
    
    n.setPlayer(currentPlayer);
    currentPlayer.placePiece();
    Player currPlayer = currentPlayer;
    
    if (n.mill()) {
      expectedMove = Move.Remove;
      return currPlayer;
    } else {
      nextTurn();
      return currPlayer;
    }
  }
  
  public void removePiece(int location) throws IllegalMoveException {
    if (expectedMove != Move.Remove) {
      throw new IllegalMoveException("Invalid move attempted (remove)");
    }
    Node n = board.getNode(location);
    if (n.getPlayer() == null) {
      throw new IllegalMoveException("Select an opponent's piece");
    }
    if (n.getPlayer() == currentPlayer) {
      throw new IllegalMoveException("You can't remove your own piece");
    }
    
    if (n.mill()) {
      for (int i = 0; i< 24; i++) {
        Node tempNode = board.getNode(i);
        if (tempNode.getPlayer() == null) continue;
        if (tempNode.getPlayer() != currentPlayer && 
            !tempNode.mill()) {
          throw new IllegalMoveException("You may not remove a piece from a mill unless there are no other options");
        }
      }
    }
    
    n.getPlayer().removePiece();
    n.setPlayer(null);
    
    nextTurn();
  }
  
  public void movePiece(int from, int to) throws IllegalMoveException {
    if (expectedMove != Move.Move) {
      throw new IllegalMoveException("Invalid move attempted (move)");
    }
    
    Node fromNode, toNode;
    fromNode = board.getNode(from);
    toNode = board.getNode(to);
    
    // Make sure the player owns the piece they are trying to move
    if (fromNode.getPlayer() != currentPlayer) {
      throw new IllegalMoveException("Player attempted to move from a spot they don't currently own");
    }
    
    // Check to see if the destination node is a legal destination
    // Is the destination node occupied already?
    if (toNode.getPlayer() != null) {
      throw new IllegalMoveException("Destination spot is already occupied");
    }
    // Can the player fly?
    if (currentPlayer.unplacedPieces() == 0 && currentPlayer.piecesOnBoard() > 3) {
      // Nope. Are they trying to?
      if (!fromNode.isNeighbor(toNode)) {
        throw new IllegalMoveException("Player is not yet allowed to fly. Pieces may only be moved to adjacent spots");
      }
    }
    
    fromNode.setPlayer(null);
    toNode.setPlayer(currentPlayer);
    
    if (toNode.mill()) {
      expectedMove = Move.Remove;
    } else {
      nextTurn();
    }
    
  }

  private void nextTurn() {
    currentPlayer = (currentPlayer == player1 ? player2 : player1);
    turnsPlayed++;
    Player winner = getWinner();
    if (winner == null) {
      expectedMove = currentPlayer.unplacedPieces() > 0 ? Move.Place : Move.Move;
    } else {
      expectedMove = Move.None;
    }
  }
  
  public Player getWinner() {
    if (!player1.hasAvailableMoves(board)) return player2;
    if (!player2.hasAvailableMoves(board)) return player1;
    return null;
  }
}
