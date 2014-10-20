package testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.*;

public class TestGame {
  private Game game;
  private Board board;
  private Player player1, player2;
  private Node[] nodes;
  
  @Before
  public void setUp() throws Exception {
    game = new Game();
    board = TestAccessor.Game.getBoard(game);
    player1 = TestAccessor.Game.getPlayer1(game);
    player2 = TestAccessor.Game.getPlayer2(game);
    nodes = TestAccessor.Board.getNodes(board);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public final void testGame() {
    for (int i = 0; i<24; i++) {
      assertNull(nodes[i].getPlayer());
    }
    assertEquals(TestAccessor.Game.getCurrentPlayer(game), player1);
    assertNotEquals(TestAccessor.Game.getPlayer1(game), player2);
    assertEquals(TestAccessor.Game.getExpectedMove(game), TestAccessor.Game.Place);
  }

  @Test
  public final void testPlacePiece() {
    try {
      assertNull(nodes[0].getPlayer());
      game.placePiece(0);
      assertEquals(nodes[0].getPlayer(), player1);
    } catch (IllegalMoveException e) {
      e.printStackTrace();
      fail();
    }
    boolean caught = false;
    try {
      game.placePiece(0);
    } catch (IllegalMoveException e) {
      caught = true;
    }
    assertTrue(caught);

    //    WW------------WW------------WW
    //    |             |              |
    //    |    03-------04------05     |
    //    |    |        |        |     |
    //    |    |    06--07--08   |     |
    //    |    |    |        |   |     |
    //    BB---BB---11      12---13---14
    //    |    |    |        |   |     |
    //    |    |    15--16--17   |     |
    //    |    |        |        |     |
    //    |    18-------19------20     |
    //    |             |              |
    //    21------------22------------23
    
    try {
      game.placePiece(9);
      game.placePiece(1);
      game.placePiece(10);
      game.placePiece(2);
    } catch (IllegalMoveException e) {
      fail();
    }
    assertEquals(TestAccessor.Game.getExpectedMove(game), TestAccessor.Game.Remove);
  }

  @Test
  public final void testRemovePiece() {
    //    WW------------WW------------02
    //    |             |              |
    //    |    03-------04------05     |
    //    |    |        |        |     |
    //    |    |    06--07--08   |     |
    //    |    |    |        |   |     |
    //    09---10---11      12---13---14
    //    |    |    |        |   |     |
    //    |    |    15--16--17   |     |
    //    |    |        |        |     |
    //    |    18-------19------20     |
    //    |             |              |
    //    BB------------BB------------23/BB
    nodes[0].setPlayer(player1);
    nodes[1].setPlayer(player1);
    nodes[21].setPlayer(player2);
    nodes[22].setPlayer(player2);
    
    // Try to place a piece, then remove an opponent's piece
    try {
      game.placePiece(2);
      game.removePiece(22);
    } catch (IllegalMoveException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      fail();
    }
    assertNull(nodes[22].getPlayer());
    
    // Try to remove a piece in a mill, when there is another option
    nodes[22].setPlayer(player2);
    nodes[4].setPlayer(player1);
    boolean caught = false;
    try {
      game.placePiece(23);
      game.removePiece(0);
    } catch (IllegalMoveException e) {
      // TODO Auto-generated catch block
      caught = true;
    }
    assertTrue(caught);
    caught = false;
    
    // Try to remove a piece from a mill, when there aren't any other options
    nodes[4].setPlayer(null);
    try {
      game.removePiece(0);
    } catch (IllegalMoveException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public final void testMovePiece() {
    //    00------------01------------02
    //    |             |              |
    //    |    03-------04------05     |
    //    |    |        |        |     |
    //    |    |    06--07--08   |     |
    //    |    |    |        |   |     |
    //    09---10---11      12---13---14
    //    |    |    |        |   |     |
    //    |    |    15--16--17   |     |
    //    |    |        |        |     |
    //    |    18-------19------20     |
    //    |             |              |
    //    21------------22------------23
    
    // Attempt to place a piece
    nodes[0].setPlayer(player1);
    TestAccessor.Game.setExpectedMove(game, TestAccessor.Game.Move);
    try {
      game.movePiece(0, 1);
    } catch (IllegalMoveException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      fail();
    }
    assertNull(nodes[0].getPlayer());
    assertEquals(nodes[1].getPlayer(), player1);
    
    // Attempt to move a piece belonging to other player
    TestAccessor.Game.setExpectedMove(game, TestAccessor.Game.Move);
    boolean caught = false;
    try {
      game.movePiece(1, 0);
    } catch (IllegalMoveException e) {
      caught = true;
    }
    assertTrue(caught);
    
    // Attempt to move a piece from an empty spot
    TestAccessor.Game.setExpectedMove(game, TestAccessor.Game.Move);
    caught = false;
    try {
      game.movePiece(0, 1);
    } catch (IllegalMoveException e) {
      caught = true;
    }
    assertTrue(caught);
  }

  @Test
  public final void testGetWinner() {
    assertNull(game.getWinner());
    
    // Empty board
    assertTrue(player1.hasAvailableMoves(board));
    
    // Place all 9 pieces - Still has pieces on board
    for (int i = 0; i<9; i++) {
      player1.placePiece();
      nodes[i].setPlayer(player1);
      player2.placePiece();
      nodes[i + 10].setPlayer(player2);
    }
    assertTrue(player1.hasAvailableMoves(board));
    assertNull(game.getWinner());
    
    // Take away all of player2's pieces
    for (int i = 0; i<9; i++) {
      player2.removePiece();
    }
    assertFalse(player2.hasAvailableMoves(board));
    
    assertEquals(player1, game.getWinner());
  }

}
