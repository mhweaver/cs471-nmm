/*

00------------01------------02
|             |              |
|    03-------04------05     |
|    |        |        |     |
|    |    06--07--08   |     |
|    |    |        |   |     |
09---10---11      12---13---14
|    |    |        |   |     |
|    |    15--16--17   |     |
|    |        |        |     |
|    18-------19------20     |
|             |              |
21------------22------------23


*/


public class Board {
  private Node[] nodes;
  public Board() {
    initNodes();
  }
  private void initNodes() {
    nodes = new Node[24];
    for (int i = 0; i <  24; i++) {
      nodes[i] = new Node();
    }
    
    // Set up the board layout
    nodes[0].setRight(nodes[1]);
    nodes[0].setDown(nodes[9]);   // 0 done
    nodes[1].setRight(nodes[2]);
    nodes[1].setDown(nodes[4]);   // 1 done
    nodes[2].setDown(nodes[14]);  // 2 done
    nodes[3].setRight(nodes[4]);
    nodes[3].setDown(nodes[10]);  // 3 done
    nodes[4].setRight(nodes[5]); 
    nodes[4].setDown(nodes[7]);   // 4 done
    nodes[5].setDown(nodes[13]);  // 5 done
    nodes[6].setRight(nodes[7]);
    nodes[6].setDown(nodes[11]);  // 6 done
    nodes[7].setRight(nodes[8]);  // 7 done
    nodes[8].setDown(nodes[12]);  // 8 done
    nodes[9].setRight(nodes[10]);
    nodes[9].setDown(nodes[21]);  // 9 done
    nodes[10].setRight(nodes[11]);
    nodes[10].setDown(nodes[18]); // 10 done
    nodes[11].setDown(nodes[15]); // 11 done
    nodes[12].setRight(nodes[13]); 
    nodes[12].setDown(nodes[17]); // 12 done
    nodes[13].setRight(nodes[14]);
    nodes[13].setDown(nodes[20]); // 13 done
    nodes[14].setDown(nodes[23]); // 14 done
    nodes[15].setRight(nodes[16]);// 15 done
    nodes[16].setRight(nodes[17]);// 16 & 17 done
    nodes[18].setRight(nodes[19]);// 18 done
    nodes[19].setRight(nodes[20]);// 20 done
    nodes[19].setDown(nodes[22]); // 19 done
    nodes[21].setRight(nodes[22]);// 21 done
    nodes[22].setRight(nodes[23]);// 22 & 23 done
    
  }
}
