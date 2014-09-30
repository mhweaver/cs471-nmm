package main;
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
    
    // adding x and y coordinates to all nodes
    nodes[0].setCoords(30,30);
    nodes[1].setCoords(320,30);
    nodes[2].setCoords(610,30);
    nodes[3].setCoords(126,126);
    nodes[4].setCoords(320,126);
    nodes[5].setCoords(514,126);
    nodes[6].setCoords(223,223);
    nodes[7].setCoords(320,223);
    nodes[8].setCoords(417,223);
    nodes[9].setCoords(30,320);
    nodes[10].setCoords(126,320);
    nodes[11].setCoords(223,320);
    nodes[12].setCoords(417,320);
    nodes[13].setCoords(514,320);
    nodes[14].setCoords(610,320);
    nodes[15].setCoords(223,417);
    nodes[16].setCoords(320,417);
    nodes[17].setCoords(417,417);
    nodes[18].setCoords(126,514);
    nodes[19].setCoords(320,514);
    nodes[20].setCoords(514,514);
    nodes[21].setCoords(30,610);
    nodes[22].setCoords(320,610);
    nodes[23].setCoords(610,610);
    
  }
  
  public Node getNode(int i) {
    return nodes[i];
  }
  
  public Node getNode(int x, int y) {
	  for(int i=0; i<24; i++) {
		  if(nodes[i].isInRegion(x, y))
			  return nodes[i];
	  }
	  return null;
  }
}
