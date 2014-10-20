package testing;

import static org.junit.Assert.*;
import main.*;

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
    Node[] nodes = TestAccessor.Board.getNodes(b);
    assertNotNull(nodes);
  }

  @Test
  public final void testGetNodeInt() {
    Node[] nodes = TestAccessor.Board.getNodes(b);
    for (int i = 0; i<24; i++) {
      assertEquals(b.getNode(i), nodes[i]);
    }
  }

  @Test
  public final void testGetNodeIntInt() {
    Node[] nodes = TestAccessor.Board.getNodes(b);
    assertEquals(b.getNode(35, 40), nodes[0]);
    assertEquals(b.getNode(223, 330), nodes[11]);
    assertEquals(b.getNode(60, 30), nodes[0]);
    assertEquals(b.getNode(30, 60), nodes[0]);
    assertNull(b.getNode(30, 61));
  }

}
