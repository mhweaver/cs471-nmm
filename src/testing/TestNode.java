package testing;

import static org.junit.Assert.*;
import main.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNode {
  Node n1, n2, n3, n4, n5, n6, n7, n8, n9;
  Player p1, p2;
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
    n6 = new Node(30);
    n7 = new Node(31);
    n8 = new Node(32);
    n9 = new Node(33);
    
    n1.setRight(n2);
    n1.setDown(n4);
    n2.setRight(n3);
    n2.setDown(n5);
    n3.setDown(n6);
    n4.setRight(n5);
    n4.setDown(n7);
    n5.setRight(n6);
    n5.setDown(n8);
    n6.setDown(n9);
    n7.setRight(n8);
    n8.setRight(n9);
    
    p1 = new Player();
    p2 = new Player();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public final void testNode() {
    assertEquals(n1.getIndex(), 25);
  }

  @Test
  public final void testSetLeft() {
    n2.setLeft(n1);
    assertEquals(TestAccessor.Node.getLeft(n2), n1);
  }

  @Test
  public final void testSetRight() {
    n1.setRight(n2);
    assertEquals(TestAccessor.Node.getRight(n1), n2);
  }

  @Test
  public final void testSetUp() {
    n4.setUp(n1);
    assertEquals(TestAccessor.Node.getUp(n4), n1);
  }

  @Test
  public final void testSetDown() {
    n1.setDown(n4);
    assertEquals(TestAccessor.Node.getDown(n1), n4);
  }

  @Test
  public final void testGetPlayer() {
    n1.setPlayer(p1);
    assertEquals(n1.getPlayer(), p1);
  }

  @Test
  public final void testSetCoords() {
    n1.setCoords(30, 40);
    assertEquals(n1.getXCoord(), 30);
    assertEquals(n1.getYCoord(), 40);
  }

  @Test
  public final void testGetIndex() {
    assertEquals(n1.getIndex(), 25);
  }

  @Test
  public final void testMill() {
    assertFalse(n1.mill());
    assertFalse(n2.mill());
    assertFalse(n3.mill());
    n1.setPlayer(p1);
    n2.setPlayer(p1);
    n3.setPlayer(p1);
    assertTrue(n1.mill());
    assertTrue(n2.mill());
    assertTrue(n3.mill());
    assertFalse(n4.mill());
    
    n3.setPlayer(p2);
    assertFalse(n1.mill());
    assertFalse(n2.mill());
    assertFalse(n3.mill());
    n6.setPlayer(p2);
    n9.setPlayer(p2);
    assertTrue(n3.mill());
    assertTrue(n6.mill());
    assertTrue(n9.mill());
  }

  @Test
  public final void testIsInRegion() {
    n1.setCoords(30,30);
    assertTrue(n1.isInRegion(30,30));
    assertTrue(n1.isInRegion(60,30));
    assertTrue(n1.isInRegion(30,60));
    assertFalse(n1.isInRegion(61,30));
    assertFalse(n1.isInRegion(30,61));
  }

  @Test
  public final void testIsNeighbor() {
    assertTrue(n1.isNeighbor(n2));
    assertTrue(n1.isNeighbor(n4));
    assertFalse(n1.isNeighbor(n3));
  }

}
