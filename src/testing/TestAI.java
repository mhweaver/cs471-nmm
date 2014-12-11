package testing;

import static org.junit.Assert.*;

import java.util.LinkedList;

import main.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAI {
  
  AI ai;
  Game game;
  Player aiPlayer;
  
  @Before
  public void setUp() throws Exception {
    game = new Game();
    aiPlayer = TestAccessor.Game.getPlayer2(game);
    
    ai = new AI(aiPlayer, game, true);
  }

  @After
  public void tearDown() throws Exception {
  }


  @Test
  public final void testIsPotentialMill() {
    Board b = TestAccessor.Game.getBoard(game);
    Node[] nodes = TestAccessor.Board.getNodes(b);
    
    // Set up board:
    //  AI------------AI------------AI
    //  |             |              |
    //  |    03-------AI------AI     |
    //  |    |        |        |     |
    //  |    |    06--07--08   |     |
    //  |    |    |        |   |     |
    //  AI---10---AI      AI---13---14
    //  |    |    |        |   |     |
    //  |    |    15--16--17   |     |
    //  |    |        |        |     |
    //  |    AI-------AI------20     |
    //  |             |              |
    //  21------------AI------------23
    
    nodes[0].setPlayer(aiPlayer);
    nodes[1].setPlayer(aiPlayer);
    nodes[2].setPlayer(aiPlayer);
    nodes[4].setPlayer(aiPlayer);
    nodes[5].setPlayer(aiPlayer);
    nodes[9].setPlayer(aiPlayer);
    nodes[11].setPlayer(aiPlayer);
    nodes[12].setPlayer(aiPlayer);
    nodes[18].setPlayer(aiPlayer);
    nodes[19].setPlayer(aiPlayer);
    nodes[22].setPlayer(aiPlayer);
    
    // Potential mills: 3, 7, 10, 16, 20, 21
    assertTrue(TestAccessor.AI.isPotentialMill(ai, 3, aiPlayer));
    assertTrue(TestAccessor.AI.isPotentialMill(ai, 7, aiPlayer));
    assertTrue(TestAccessor.AI.isPotentialMill(ai, 10, aiPlayer));
    assertTrue(TestAccessor.AI.isPotentialMill(ai, 16, aiPlayer));
    assertTrue(TestAccessor.AI.isPotentialMill(ai, 20, aiPlayer));
    assertTrue(TestAccessor.AI.isPotentialMill(ai, 21, aiPlayer));
    
    // Non-potential mills: 0-2, 4-6, 8, 9, 11-15, 17-19, 22, 23
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 0, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 1, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 2, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 4, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 5, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 6, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 8, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 9, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 11, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 12, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 13, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 14, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 15, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 17, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 18, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 19, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 22, aiPlayer));
    assertFalse(TestAccessor.AI.isPotentialMill(ai, 23, aiPlayer));
  }

  @Test
  public final void testGetPlayer() {
    assertEquals(aiPlayer, ai.getPlayer());
  }
  
  @Test
  public final void testGetOpponent() {
    Player player1 = TestAccessor.Game.getPlayer1(game);
    Player opponent = TestAccessor.AI.getOpponent(ai);
    assertEquals(player1, opponent);
  }
  
  @Test
  public final void testMyNodesEmpty() {
    LinkedList<Node> mynodes = TestAccessor.AI.myNodes(ai);
    assertEquals(mynodes.size(), 0);
  }
  
  @Test
  public final void testMyNodes() {
    Board b = TestAccessor.Game.getBoard(game);
    Node[] nodes = TestAccessor.Board.getNodes(b);
    
    // Set up board:
    nodes[0].setPlayer(aiPlayer);
    nodes[1].setPlayer(aiPlayer);
    nodes[2].setPlayer(aiPlayer);
    nodes[4].setPlayer(aiPlayer);
    nodes[5].setPlayer(aiPlayer);
    nodes[9].setPlayer(aiPlayer);
    nodes[11].setPlayer(aiPlayer);
    nodes[12].setPlayer(aiPlayer);
    nodes[18].setPlayer(aiPlayer);
    nodes[19].setPlayer(aiPlayer);
    nodes[22].setPlayer(aiPlayer);
    
    LinkedList<Node> mynodes = TestAccessor.AI.myNodes(ai);
    assertEquals(mynodes.size(), 11);
    assertTrue(mynodes.contains(nodes[0]));
    assertTrue(mynodes.contains(nodes[1]));
    assertTrue(mynodes.contains(nodes[2]));
    assertFalse(mynodes.contains(nodes[3]));
    assertTrue(mynodes.contains(nodes[4]));
    assertTrue(mynodes.contains(nodes[5]));
    assertFalse(mynodes.contains(nodes[6]));
    
  }

}
