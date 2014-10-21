package testing;

import static org.junit.Assert.*;
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
	public final void testComputerButton() {
		gui = new GUI();
		TestAccessor.GUI.getComputerButton(gui).doClick();
		assertTrue(TestAccessor.GUI.getGUIAIMode(gui));
	}
	
	@Test
	public final void testTwoPlayerButton() {
		gui = new GUI();
		TestAccessor.GUI.getComputerButton(gui).doClick();
		TestAccessor.GUI.getTwoPlayerButton(gui).doClick();
		assertFalse(TestAccessor.GUI.getGUIAIMode(gui));
	}
	
	
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
}

