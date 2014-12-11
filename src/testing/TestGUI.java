package testing;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import main.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGUI {
	GUI gui;
	Game game;
	Board board;

	@Test
	public final void testNewGameButton() {
		Game g1 = TestAccessor.GUI.getGUIGame(gui);
		TestAccessor.GUI.getNewGameButton(gui).doClick();
		Game g2 = TestAccessor.GUI.getGUIGame(gui);
		assertNotEquals(g1,g2);
	}
	
	@Test
	public final void testEasyAIButton() {
		Game g1 = TestAccessor.GUI.getGUIGame(gui);
		TestAccessor.GUI.getComputerButton(gui).doClick();
		Game g2 = TestAccessor.GUI.getGUIGame(gui);
		assertNotEquals(g1,g2);
		assertTrue(TestAccessor.GUI.getGUIAIMode(gui));
	}
	
	@Test
  public final void testHardAIButton() {
    Game g1 = TestAccessor.GUI.getGUIGame(gui);
    TestAccessor.GUI.getHardAIButton(gui).doClick();
    Game g2 = TestAccessor.GUI.getGUIGame(gui);
    assertNotEquals(g1,g2);
    assertTrue(TestAccessor.GUI.getGUIAIMode(gui));
    assertTrue(TestAccessor.GUI.getGUIHardAIMode(gui));
  }
	
	@Test
	public final void testTwoPlayerButton() {
		Game g1 = TestAccessor.GUI.getGUIGame(gui);
		TestAccessor.GUI.getComputerButton(gui).doClick();
		TestAccessor.GUI.getTwoPlayerButton(gui).doClick();
		Game g2 = TestAccessor.GUI.getGUIGame(gui);
		assertNotEquals(g1,g2);
		assertFalse(TestAccessor.GUI.getGUIAIMode(gui));
	}
	
	@Test
	public final void testPlacePiece() {
		MouseEvent me = new MouseEvent(TestAccessor.GUI.getCenterPanel(gui),
				MouseEvent.MOUSE_PRESSED,1,0,30,30,0,false);
		gui.mousePressed(me);
		Game game = TestAccessor.GUI.getGUIGame(gui);
		Board board = TestAccessor.Game.getBoard(game);
		Node[] nodes = TestAccessor.Board.getNodes(board);
		assertEquals(nodes[0].getPlayer(), TestAccessor.Game.getPlayer1(game));
	}
	
	@Test
  public final void testMovePiece() {
	  Player player1 = TestAccessor.Game.getPlayer1(game);
	  TestAccessor.Game.setExpectedMove(game, TestAccessor.Game.Move);
	  board.getNode(0).setPlayer(player1);
    MouseEvent me = new MouseEvent(TestAccessor.GUI.getCenterPanel(gui),
        MouseEvent.MOUSE_PRESSED,1,0,30,30,0,false);
    gui.mousePressed(me);
    me = new MouseEvent(TestAccessor.GUI.getCenterPanel(gui),
        MouseEvent.MOUSE_PRESSED,1,0,320,30,0,false);
    gui.mousePressed(me);
    Node[] nodes = TestAccessor.Board.getNodes(board);
    assertNull(nodes[0].getPlayer());
    assertEquals(nodes[1].getPlayer(), player1);
  }
	
	@Test
  public final void testRemovePiece() {
    Player player2 = TestAccessor.Game.getPlayer2(game);
    TestAccessor.Game.setExpectedMove(game, TestAccessor.Game.Remove);
    board.getNode(0).setPlayer(player2);
    Node[] nodes = TestAccessor.Board.getNodes(board);
    assertEquals(nodes[0].getPlayer(), player2);
    
    MouseEvent me = new MouseEvent(TestAccessor.GUI.getCenterPanel(gui),
        MouseEvent.MOUSE_PRESSED,1,0,30,30,0,false);
    gui.mousePressed(me);
    nodes = TestAccessor.Board.getNodes(board);
    assertNull(nodes[0].getPlayer());
  }
	
	@Before
	public void setUp() throws Exception {
	  gui = new GUI();
	  game = TestAccessor.GUI.getGUIGame(gui);
	  board = TestAccessor.Game.getBoard(game);
	}

	@After
	public void tearDown() throws Exception {
	}
}

