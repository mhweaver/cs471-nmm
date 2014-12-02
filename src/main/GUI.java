package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class GUI implements ActionListener, MouseListener {

	// GUI Variables
	private String boardName;
	private String imagePath;
	private String newGameText;
	private String exitGameText;
	private String whitePath;
	private String blackPath;
	private String selectPath;
	private String blankPath;
	private String blackAvailablePath;
	private String whiteAvailablePath;
	private ImageIcon boardIcon;
	private ImageIcon whiteIcon;
	private ImageIcon blackIcon;
	private ImageIcon blankIcon;
	private ImageIcon selectIcon;
	private ImageIcon blackAvailableIcon;
	private ImageIcon whiteAvailableIcon;
	private int nodeRadius;
	private int nodeDiameter;
	private int boardLength;
	private Node selectedNode;
	
	protected JButton newGameButton;
	private JButton exitGameButton;
	protected JRadioButton twoPlayer;
	protected JRadioButton computer;
	private ButtonGroup modeChoice;
	private JFrame mainFrame;
	protected JPanel centerPanel;
	private JPanel westPanel;
	private JPanel southPanel;
	private JLabel boardLabel;
	private JTextField statusField;
	private JLabel[] nodeLabels;
	private JLabel remainBlackLabel;
	private JLabel remainWhiteLabel;
	
	protected boolean AIMode;
	private AI ai;
	private boolean movesBlocked = false;
	private final int AI_DELAY = 1000;
	
	protected Game game;
	
	public GUI() {
		
		//Initialize Strings and images
		boardName = "Nine Men's Morris";
		imagePath = "resources/board.png";
		whitePath = "resources/white.png";
		blackPath = "resources/black.png";
		selectPath = "resources/select.png";
		blankPath = "resources/blank.png";
		blackAvailablePath = "resources/blacks.png";
		whiteAvailablePath = "resources/whites.png";
		newGameText = "New Game";
		exitGameText = "Exit";
		boardIcon = new ImageIcon(imagePath);
		whiteIcon = new ImageIcon(whitePath);
		blackIcon = new ImageIcon(blackPath);
		blankIcon = new ImageIcon(blankPath);
		selectIcon = new ImageIcon(selectPath);
		blackAvailableIcon = new ImageIcon(blackAvailablePath);
		whiteAvailableIcon = new ImageIcon(whiteAvailablePath);
		nodeRadius = whiteIcon.getIconHeight()/2;
		nodeDiameter = whiteIcon.getIconHeight();
		boardLength = boardIcon.getIconHeight();
		nodeLabels = new JLabel[24];
		AIMode = false;
		game = new Game();
		
		
		//Create West Panel
		initWestPanel();
		
		//Create Center Panel
		initCenterPanel();
		
		//Create South Panel
		initSouthPanel();
		
		//Create Main Frame
		initMainFrame();
	}
	
	public void initCenterPanel() {
		
		centerPanel = new JPanel();
		centerPanel.setLayout(null);
		boardLabel = new JLabel("", boardIcon, JLabel.CENTER);
		for(int i=0;i<24;i++){
			nodeLabels[i] = new JLabel("", blankIcon, JLabel.CENTER);
			centerPanel.add(nodeLabels[i]);
		}
		centerPanel.add(boardLabel);
		boardLabel.setBounds(0,0,boardLength,boardLength);
		setNodeLabelBounds();
		boardLabel.setOpaque(true);
		centerPanel.addMouseListener(this);
		centerPanel.repaint();

	}
	
	public void initWestPanel() {
		newGameButton = new JButton();
		newGameButton.setText(newGameText);
		newGameButton.addActionListener(this);
		exitGameButton = new JButton();
		exitGameButton.setText(exitGameText);
		exitGameButton.addActionListener(this);
		twoPlayer = new JRadioButton("2 Player");
		twoPlayer.addActionListener(this);
		twoPlayer.setSelected(true);
		computer = new JRadioButton("Computer");
		computer.addActionListener(this);
		modeChoice = new ButtonGroup();
		modeChoice.add(twoPlayer);
		modeChoice.add(computer);
		remainWhiteLabel = new JLabel(" x 9", whiteIcon, JLabel.LEFT);
		remainBlackLabel = new JLabel(" x 9", blackIcon, JLabel.LEFT);
		westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(10,1));
		westPanel.add(newGameButton);
		westPanel.add(exitGameButton);
		westPanel.add(twoPlayer);
		westPanel.add(computer);
		westPanel.add(remainWhiteLabel);
		westPanel.add(remainBlackLabel);
	}
	
	public void initSouthPanel() {
		southPanel = new JPanel(new BorderLayout());
		statusField = new JTextField("");
		setStatus(game.currentPlayer.name + ":" + game.expectedMove.toString());
		statusField.setSize(50,boardLength);
		statusField.setFont(statusField.getFont().deriveFont(25.0f));
		southPanel.add(statusField, BorderLayout.SOUTH);
		
	}
	
	public void initMainFrame() {
		mainFrame = new JFrame(boardName);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(westPanel, BorderLayout.WEST);
		mainFrame.add(centerPanel, BorderLayout.CENTER);
		mainFrame.add(southPanel, BorderLayout.SOUTH);
		mainFrame.setPreferredSize(new Dimension(760, 690));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
	}
	
	public void newGame() {
		game = new Game();
		movesBlocked = false;
		ai = new AI(game.player2, game);
		redrawBoard();
	}
	
	public void addPiece(int x, int y) {
		Node n = game.board.getNode(x,y);
		if(n == null)
			return;
		
		try {
			game.placePiece(n.getIndex());
			redrawBoard();
			
		} catch (IllegalMoveException e) {
			setStatus(e.getMessage());
		}
	}
	
	public void removePiece(int x, int y) {
		Node n = game.board.getNode(x,y);
		if(n == null)
			return;
		try {
			game.removePiece(n.getIndex());
			redrawBoard();
		} catch (IllegalMoveException e) {
			setStatus(e.getMessage());
		}
	}
	
	public void selectPiece(int x, int y) {
		selectedNode = game.board.getNode(x,y);
		game.board.unSelectAll();
		selectedNode.select();
	}
	
	public void redrawBoard() {
		for(int i=0;i<24;i++) {
			if(game.board.getNode(i).getPlayer() == null) {
				if (game.board.getNode(i).isSelected()) setNodeLabelBlankAvail(i);
				else setNodeLabelBlank(i);
			}
			else if(game.board.getNode(i).getPlayer().color == Player.WHITE) {
				if (game.board.getNode(i).isSelected()) setNodeLabelWhiteAvail(i);
				else setNodeLabelWhite(i);
			}
			else if(game.board.getNode(i).getPlayer().color == Player.BLACK) {
				if (game.board.getNode(i).isSelected()) setNodeLabelBlackAvail(i);
				else setNodeLabelBlack(i);
			}
		}
		setStatus(game.currentPlayer.name + ":" + game.expectedMove.toString());
		//int whites = game.player1.totalPieces();
		//int blacks = game.player2.totalPieces();
		if (game.player1.unplacedPieces()>0) remainWhiteLabel.setText(" x " + game.player1.unplacedPieces());
		else remainWhiteLabel.setText(" x " + game.player1.piecesOnBoard());
		if (game.player2.unplacedPieces()>0) remainBlackLabel.setText(" x " + game.player2.unplacedPieces());
		else remainBlackLabel.setText(" x " + game.player2.piecesOnBoard());
		westPanel.repaint();
	}
	
	public void setNodeLabelBlack(int index) {
		nodeLabels[index].setIcon(blackIcon);
	}
	
	public void setNodeLabelWhite(int index) {
		nodeLabels[index].setIcon(whiteIcon);
	}
	
	public void setNodeLabelBlank(int index) {
		nodeLabels[index].setIcon(blankIcon);
	}
	
	public void setNodeLabelBlackAvail(int index) {
		nodeLabels[index].setIcon(blackAvailableIcon);
	}
	
	public void  setNodeLabelWhiteAvail(int index) {
		nodeLabels[index].setIcon(whiteAvailableIcon);
	}
	
	public void setNodeLabelBlankAvail(int index) {
		nodeLabels[index].setIcon(selectIcon);
	}
	
	public void setNodeLabelBounds() {
		int i;
		for (i = 0; i < 24; i++) {
			nodeLabels[i].setBounds(game.board.getNode(i).getXCoord()-nodeRadius,
					game.board.getNode(i).getYCoord()-nodeRadius,nodeDiameter,nodeDiameter);
		}
	}
	
	public void setStatus(String s) {
		statusField.setText(s); 
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(exitGameButton))
			System.exit(0);
		if(ae.getSource().equals(newGameButton))
			newGame();
		if(ae.getSource().equals(twoPlayer)) {
			AIMode = false;
			newGame();
		}
		if(ae.getSource().equals(computer)) {
			AIMode = true;
			newGame();
		}
	}
	
	public void mousePressed(MouseEvent me) {
	  if (movesBlocked) return;
	  
		for (int i =0; i<24; i++) {
			if(game.board.getNode(i).isInRegion(me.getX(), me.getY())) {
				//System.out.println(i);
				if(game.expectedMove == Game.Move.Place) {
					addPiece(me.getX(), me.getY());
				}
				else if(game.expectedMove == Game.Move.Move) {
					//selectPiece.(me.getX(), me.getY());
					if(selectedNode == null || game.currentPlayer == game.board.getNode(i).getPlayer()) {
						if(game.currentPlayer != game.board.getNode(i).getPlayer())
							return;
						selectPiece(me.getX(), me.getY());
						redrawBoard();
					}
					else {
						try {
							game.movePiece(selectedNode.getIndex(), game.board.getNode(i).getIndex());
							selectedNode = null;
							redrawBoard();
						} catch (IllegalMoveException e) {
							setStatus(e.getMessage());
						}
					}
				}
				else if(game.expectedMove == Game.Move.Remove) {
					removePiece(me.getX(), me.getY());
				}
				if(game.expectedMove == Game.Move.None) {
					gameOver();
				}
			}
		}
		
		// GUI player just made a move. Do AI move, if needed
		if (AIMode && game.currentPlayer == ai.getPlayer() && game.expectedMove != Game.Move.None) {
		  doAIMove();
		}
	}
	
	private void doAIMove() {
	  if (AIMode && game.currentPlayer == ai.getPlayer()) {
	    redrawBoard();
      setStatus("Computer player is thinking...");
      movesBlocked = true;
      
      // Pause for 1 second, then do the move
      Timer timer = new Timer(AI_DELAY, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           try {
             // Make the AI moves
             while (game.currentPlayer == ai.getPlayer() && game.expectedMove != Game.Move.None) {
               ai.doNextMove();
             }
            
             redrawBoard();
             movesBlocked = false;

             // Check to see if someone won
             if (game.expectedMove == Game.Move.None) {
               gameOver();
             }
          } catch (IllegalMoveException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
        }
      });
      
      timer.setRepeats(false);
      timer.start();      
      
    }
	  
	  
	}
	
	private void gameOver() {
	  String winner = game.getWinner().name;
    String s = "resources/dancing_peng.gif";
    ImageIcon ii = new ImageIcon(s);
    JOptionPane.showMessageDialog(mainFrame, winner + " wins!", "About", JOptionPane.INFORMATION_MESSAGE, ii);
    setStatus("Click new Game to play again");
    movesBlocked = true;
	}
	
	// Unused methods inherited from MouseEvent
	public void mouseClicked(MouseEvent me) {	
	}
	public void mouseEntered(MouseEvent arg0) {		
	}
	public void mouseExited(MouseEvent arg0) {	
	}
	public void mouseReleased(MouseEvent arg0) {	
	}

}
