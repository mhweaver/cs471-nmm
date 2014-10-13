package testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.*;

public class TestPlayer {
  
  Player p;
  Board b;

  @Before
  public void setUp() throws Exception {
    p = new Player();
    b = new Board();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public final void testPlayer() {
    assertEquals(p.name, "Unnamed player");
  }

  @Test
  public final void testPlayerString() {
    p = new Player("Bob");
    assertEquals(p.name, "Bob");
  }

  @Test
  public final void testSetColor() {
    p.setColor(Player.BLACK);
    assertEquals(p.getColor(), 1);
  }

  @Test
  public final void testPlacePiece() {
    int unplacedPieces = p.unplacedPieces();
    int piecesOnBoard = p.piecesOnBoard();
    p.placePiece();
    assertEquals(unplacedPieces - 1, p.unplacedPieces());
    assertEquals(p.unplacedPieces(), 8);
    assertEquals(piecesOnBoard + 1, p.piecesOnBoard());
    assertEquals(p.piecesOnBoard(), 1);
  }

  @Test
  public final void testRemovePiece() {
    p.placePiece();
    p.removePiece();
    assertEquals(p.piecesOnBoard(), 0);
  }

  @Test
  public final void testTotalPieces() {
    assertEquals(p.totalPieces(), 9);
    p.placePiece();
    assertEquals(p.totalPieces(), 9);
    p.removePiece();
    assertEquals(p.totalPieces(), 8);
  }

  @Test
  public final void testHasAvailableMoves() {
    // Empty board
    assertTrue(p.hasAvailableMoves(b));
    
    for (int i = 0; i<9; i++) {
      // TODO: Complete this test...
    }
  }

}
