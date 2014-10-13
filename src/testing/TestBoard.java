package testing;

import static org.junit.Assert.*;
import main.Board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBoard {
  Board b;
  
  @Before
  public void setUp() throws Exception {
    b = new Board();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public final void testBoard() {
    assertNotNull(b.test.getNodes());
  }

  @Test
  public final void testGetNodeInt() {
    for (int i = 0; i<24; i++) {
      assertEquals(b.getNode(i), b.test.getNodes()[i]);
    }
  }

  @Test
  public final void testGetNodeIntInt() {
    assertEquals(b.getNode(35, 40), b.test.getNodes()[0]);
    assertEquals(b.getNode(223, 330), b.test.getNodes()[11]);
    assertEquals(b.getNode(60, 30), b.test.getNodes()[0]);
    assertEquals(b.getNode(30, 60), b.test.getNodes()[0]);
    assertNull(b.getNode(30, 61));
  }

}
