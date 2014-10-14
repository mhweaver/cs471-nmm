package main;

import java.util.LinkedList;
import java.util.Random;

public class AIPlayer extends Player {
  private Game game;
  
  public AIPlayer() {
    this("Computer player");
  }

  public AIPlayer(String name) {
    super(name);
  }
  public AIPlayer(String name, Game game) {
    
  }
  
  public void doNextMove() throws IllegalMoveException {
    if (game.currentPlayer != this) 
      throw new IllegalMoveException("Out of turn move by AI player");
    switch (game.expectedMove) {
      case Place:
        nextPlacePiece();
        break;
      case Move:
        nextMovePiece();
        break;
      case Remove:
        nextRemovePiece();
        break;
      case None:
    }
  }

  private void nextPlacePiece() {
    // First, attempt to block potential mills
    for (int i = 0; i<24; i++) {
      if (isPotentialMill(i, getOpponent())) {
        try {
          game.placePiece(i);
          return;
        } catch (IllegalMoveException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    
    // If no potential mills, then try to place at an intersection with the max 
    // # of open neighbors
    int best = 0, bestOpenNeighbors = 0;
    for (int i = 0; i<24; i++) {
      Node n = game.board.getNode(i);
      if (n.getPlayer() != null) continue;
      
      int openNeighbors = 0;
      if (n.left != null && n.left.getPlayer() == null) openNeighbors++;
      if (n.right != null && n.right.getPlayer() == null) openNeighbors++;
      if (n.up != null && n.up.getPlayer() == null) openNeighbors++;
      if (n.down != null && n.down.getPlayer() == null) openNeighbors++;
      
      if (openNeighbors > bestOpenNeighbors) {
        best = i;
        bestOpenNeighbors = openNeighbors;
      }
    }
    
    // best should be the index of the node with the most open neighbors, now
    try {
      game.placePiece(best);
    } catch (IllegalMoveException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private void nextRemovePiece() {
    Player opponent = getOpponent();
    LinkedList<Node> opponentNodes = new LinkedList<Node>();
    
    // Find all of the opponent's nodes
    for (int i = 0; i<24; i++) {
      if (game.board.getNode(i).getPlayer() == opponent) {
        opponentNodes.add(game.board.getNode(i));
      }
    }
    
    // Remove a random opponent node
    try {
      game.removePiece(new Random().nextInt(opponentNodes.size()));
    } catch (IllegalMoveException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private void nextMovePiece() {
    
  }
  
  private boolean isPotentialMill(int index, Player player) {
    Node n = game.board.getNode(index);
    
    // Node is already occupied, so a piece can't be placed here
    if (n.getPlayer() != null) return false;
    
    // Move both the far left and far top, in a straight line, from this node
    Node hNode, vNode;
    hNode = vNode = n;
    for (; hNode.left != null; hNode = hNode.left);
    for (; vNode.up != null; vNode = vNode.up);
    
    int playersNodes = 0;
    
    // Check if there are 2 nodes owned by the player in this row
    // If so, return true
    while ((hNode = hNode.right) != null) {
      if (hNode.getPlayer() == player) 
        playersNodes++;
      if (playersNodes == 2)
        return true;
    }

    // Check if there are 2 nodes owned by the player in this column
    // If so, return true    
    playersNodes = 0;
    while ((vNode = vNode.down) != null) {
      if (vNode.getPlayer() == player) 
        playersNodes++;
      if (playersNodes == 2)
        return true;
    }

    // If we made it this far, then it's not possible to form a mill from
    // this node
    return false;
  }
  
  private Player getOpponent() {
    return (this == game.player1) ? game.player2 : game.player1;
  }

}