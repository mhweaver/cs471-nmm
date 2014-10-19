package main;

import java.util.LinkedList;

public class TestAccessor {
  
  public static class Game {
    public static main.Player getPlayer1(main.Game game) {
      return game.player1;
    }
    public static main.Player getPlayer2(main.Game game) {
      return game.player2;
    }
    public static main.Board getBoard(main.Game game) {
      return game.board;
    }
    
  }
  
  public static class Board {
    public static main.Node[] getNodes(main.Board board) {
      return board.nodes;
    }
  }
  
  public static class AI {
    public static boolean isPotentialMill(main.AI ai, int index, main.Player player) {
      return ai.isPotentialMill(index, player);
    }
    public static main.Player getOpponent(main.AI ai) {
      return ai.getOpponent();
    }
    public static LinkedList<main.Node> myNodes(main.AI ai) {
      return ai.myNodes();
    }
  }

  
}
