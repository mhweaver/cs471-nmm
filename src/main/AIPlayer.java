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
        doNextPlacePiece();
        break;
      case Move:
        doNextMovePiece();
        break;
      case Remove:
        doNextRemovePiece();
        break;
      case None:
    }
  }

  private void doNextPlacePiece() {
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
  
  private void doNextRemovePiece() {
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
  
  private void doNextMovePiece() {
    Board scratch = game.board.copy();
    
    // Find a mill-creating move
    for (Node n : myNodes()) {
      int nIndex = n.getIndex();
      int dest = -1;
      Node nScratch = scratch.getNode(nIndex);
      
      // Look for possible mills, if this piece is moved
      nScratch.setPlayer(null);
      for (int j = 0; j<4; j++) {
        Node adj;
        switch (j) {
          case 1: adj = nScratch.left;
          case 2: adj = nScratch.right;
          case 3: adj = nScratch.up;
          case 4: 
          default: adj = nScratch.down;
        }
        if (adj != null) {
          adj.setPlayer(this);
          if (adj.mill()) {
            dest = adj.getIndex();
          }
          adj.setPlayer(null);
        }
      }
      
      // If a mill can be made, make it
      if (dest != -1) {
        try {
          game.movePiece(nIndex, dest);
          return;
        } catch (IllegalMoveException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      nScratch.setPlayer(this);
    }
    
    // No mills possible, so just do something random
    LinkedList<Node> myNodes = myNodes();
    Random r = new Random();
    do {
      Node n = myNodes.get(r.nextInt(myNodes.size()));
      Node to;
      int direction = r.nextInt(4);
      switch (direction) {
        case 1: to = n.left;
        case 2: to = n.right;
        case 3: to = n.up;
        case 4: 
        default: to = n.down;
      }
      if (to != null && to.getPlayer() == null) {
        try {
          game.movePiece(n.getIndex(), to.getIndex());
        } catch (IllegalMoveException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        return;
      }
    } while (true);
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

  private LinkedList<Node> myNodes() {
    LinkedList<Node> nodes = new LinkedList<Node>();
    for (int i = 0; i<24; i++) {
      if (game.board.getNode(i).getPlayer() == this) {
        nodes.add(game.board.getNode(i));
      }
    }
    
    return nodes;
  }
}
