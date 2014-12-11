package testing;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import main.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGUI {
	GUI gui;

	@Test
	public final void testNewGameButton() {
		gui = new GUI();
		Game g1 = TestAccessor.GUI.getGUIGame(gui);
		TestAccessor.GUI.getNewGameButton(gui).doClick();
		Game g2 = TestAccessor.GUI.getGUIGame(gui);
		assertNotEquals(g1,g2);
	}
	
	@Test
	public final void testEasyAIButton() {
		gui = new GUI();
		Game g1 = TestAccessor.GUI.getGUIGame(gui);
		TestAccessor.GUI.getComputerButton(gui).doClick();
		Game g2 = TestAccessor.GUI.getGUIGame(gui);
		assertNotEquals(g1,g2);
		assertTrue(TestAccessor.GUI.getGUIAIMode(gui));
	}
	
	@Test
  public final void testHardAIButton() {
    gui = new GUI();
    Game g1 = TestAccessor.GUI.getGUIGame(gui);
    TestAccessor.GUI.getHardAIButton(gui).doClick();
    Game g2 = TestAccessor.GUI.getGUIGame(gui);
    assertNotEquals(g1,g2);
    assertTrue(TestAccessor.GUI.getGUIAIMode(gui));
    assertTrue(TestAccessor.GUI.getGUIHardAIMode(gui));
  }
	
	@Test
	public final void testTwoPlayerButton() {
		gui = new GUI();
		Game g1 = TestAccessor.GUI.getGUIGame(gui);
		TestAccessor.GUI.getComputerButton(gui).doClick();
		TestAccessor.GUI.getTwoPlayerButton(gui).doClick();
		Game g2 = TestAccessor.GUI.getGUIGame(gui);
		assertNotEquals(g1,g2);
		assertFalse(TestAccessor.GUI.getGUIAIMode(gui));
	}
	
	@Test
	public final void testPlacePiece() {
		gui = new GUI();
		MouseEvent me = new MouseEvent(TestAccessor.GUI.getCenterPanel(gui),
				MouseEvent.MOUSE_PRESSED,1,0,30,30,0,false);
		gui.mousePressed(me);
		Game game = TestAccessor.GUI.getGUIGame(gui);
		Board board = TestAccessor.Game.getBoard(game);
		Node[] nodes = TestAccessor.Board.getNodes(board);
		assertEquals(nodes[0].getPlayer(), TestAccessor.Game.getPlayer1(game));
	}
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
}

