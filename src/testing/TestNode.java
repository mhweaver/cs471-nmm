package testing;

import static org.junit.Assert.*;
import main.Node;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNode {
  Node n1, n2, n3, n4, n5, n6, n7, n8, n9;
  @Before
  public void setUp() throws Exception {
    //n1===n2===n3
    //||   ||   ||
    //n4===n5===n6
    //||   ||   ||
    //n7===n8===n9
    
    n1 = new Node(25);
    n2 = new Node(26);
    n3 = new Node(27);
    n4 = new Node(28);
    n5 = new Node(29);
    
    n1.setDown(n3);
    
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public final void testNode() {
    assertEquals(n1.test.getIndex(), 25);
  }

  @Test
  public final void testSetLeft() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testSetRight() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testSetUp() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testSetDown() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testGetPlayer() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testSetPlayer() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testSetCoords() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testGetXCoord() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testGetYCoord() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testGetIndex() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testMill() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testIsInRegion() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testIsNeighbor() {
    fail("Not yet implemented"); // TODO
  }

}
