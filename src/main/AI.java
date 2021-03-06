package main;

import java.util.LinkedList;
import java.util.Random;

public class AI {
  private Game game;
  private Player me;
  private boolean hardMode;
  
  public AI(Player player, Game game, boolean hardMode) {
    this.me = player;
    this.game = game;
    this.hardMode = hardMode;
  }
  
  public void doNextMove() throws IllegalMoveException {
    if (game.currentPlayer != me) 
      throw new IllegalMoveException("Out of turn move by AI player");
    switch (game.expectedMove) {
      case Place:
        doPlacePiece();
        break;
      case Move:
        doMovePiece();
        break;
      case Remove:
        doRemovePiece();
        break;
      case None:
    }
  }

  private void doPlacePiece() {
    // First, attempt to create mills
    for (int i = 0; i<24 && this.hardMode; i++) {
      if (isPotentialMill(i, me)) {
        try {
          game.placePiece(i);
          return;
        } catch (IllegalMoveException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    
    // Next, attempt to block potential mills
    for (int i = 0; i<24 && this.hardMode; i++) {
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
    int best = -1, bestOpenNeighbors = -1;
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
  
  private void doRemovePiece() {
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
      int rand = new Random().nextInt(opponentNodes.size());
      int removeIndex = opponentNodes.get(rand).getIndex();
      game.removePiece(removeIndex);
    } catch (IllegalMoveException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
    }
  }
  
  private void doMovePiece() {
    Board scratch = game.board.copy();
    
    // Find a mill-creating move
    for (Node n : myNodes()) {
      if (!this.hardMode) break; // If this is easy mode, don't try to make mills
      int nIndex = n.getIndex();
      int dest = -1;
      Node nScratch = scratch.getNode(nIndex);
      
      // Look for possible mills, if this piece is moved
      nScratch.setPlayer(null);
      for (int j = 0; j<4; j++) {
        Node adj;
        switch (j) {
          case 1: adj = nScratch.left;
                  break;
          case 2: adj = nScratch.right;
                  break;
          case 3: adj = nScratch.up;
                  break;
          case 4: 
          default: adj = nScratch.down;
        }
        if (adj != null && adj.getPlayer() == null) {
          adj.setPlayer(me);
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
      nScratch.setPlayer(me);
    }
    
    // Hard mode - Attempt to fly
    if (hardMode && this.me.unplacedPieces() == 0 && this.me.piecesOnBoard() <= 3) {
      // Loop through all pieces, looking for potential mills
      for (int i = 0; i<24; i++) {
        if (isPotentialMill(i, this.me)) { // Potential mill found
          for (Node n : myNodes()) { // Loop through my pieces, to find the one that can be moved to form a mill
            Node nScratch = scratch.getNode(n.getIndex());
            // Move the piece on the scratch board to the potential mill
            nScratch.setPlayer(null);
            scratch.getNode(i).setPlayer(this.me);
            if (scratch.getNode(i).mill()) { // Did a mill get formed? If so, make the move
              try {
                this.game.movePiece(nScratch.getIndex(), i);
              } catch (IllegalMoveException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
              return;
            }
            // A mill didn't form, so undo that move on the scratch board
            nScratch.setPlayer(this.me);
            scratch.getNode(i).setPlayer(null);
            
          }
        }
        
      }
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
                break;
        case 2: to = n.right;
                break;
        case 3: to = n.up;
                break;
        case 4: 
        default: to = n.down;
      }
      // Attempt to fly
      if (this.me.unplacedPieces() == 0 && this.me.piecesOnBoard() <= 3) {
        // Set 'to' to a random node, which may or may not be adjacent to the node being moved
        to = this.game.board.getNode(r.nextInt(23)); 
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
  
  protected boolean isPotentialMill(int index, Player player) {
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
    do {
      if (hNode.getPlayer() == player) 
        playersNodes++;
      if (playersNodes == 2)
        return true;
    }  while ((hNode = hNode.right) != null);

    // Check if there are 2 nodes owned by the player in this column
    // If so, return true    
    playersNodes = 0;
    do {
      if (vNode.getPlayer() == player) 
        playersNodes++;
      if (playersNodes == 2)
        return true;
    } while ((vNode = vNode.down) != null);

    // If we made it this far, then it's not possible to form a mill from
    // this node
    return false;
  }
  
  protected Player getOpponent() {
    return (me == game.player1) ? game.player2 : game.player1;
  }
  
  public Player getPlayer() {
    return me;
  }

  protected LinkedList<Node> myNodes() {
    LinkedList<Node> nodes = new LinkedList<Node>();
    for (int i = 0; i<24; i++) {
      if (game.board.getNode(i).getPlayer() == me) {
        nodes.add(game.board.getNode(i));
      }
    }
    
    return nodes;
  }
}
