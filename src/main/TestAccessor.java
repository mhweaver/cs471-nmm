package main;

import java.util.LinkedList;

public class TestAccessor {
  
  public static class Game {
    public static main.Game.Move Place = main.Game.Move.Place;
    public static main.Game.Move Move = main.Game.Move.Move;
    public static main.Game.Move Remove = main.Game.Move.Remove;
    public static main.Game.Move None = main.Game.Move.None;
    public static main.Player getPlayer1(main.Game game) {
      return game.player1;
    }
    public static main.Player getPlayer2(main.Game game) {
      return game.player2;
    }
    public static main.Board getBoard(main.Game game) {
      return game.board;
    }
    public static main.Game.Move getExpectedMove(main.Game game) {
      return game.expectedMove;
    }
    public static main.Player getCurrentPlayer(main.Game game) {
      return game.currentPlayer;
    }
    public static void setExpectedMove(main.Game game, main.Game.Move move) {
      game.expectedMove = move;
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

  public static class Node {
    public static main.Node getLeft(main.Node node) {
      return node.left;
    }
    public static main.Node getRight(main.Node node) {
      return node.right;
    }
    public static main.Node getUp(main.Node node) {
      return node.up;
    }
    public static main.Node getDown(main.Node node) {
      return node.down;
    }
  }
  
}
