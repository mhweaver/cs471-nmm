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
	
	private JButton newGameButton;
	private JButton exitGameButton;
	private JRadioButton twoPlayer;
	private JRadioButton computer;
	private ButtonGroup modeChoice;
	private JFrame mainFrame;
	private JPanel centerPanel;
	private JPanel westPanel;
	private JPanel southPanel;
	private JLabel boardLabel;
	private JTextField statusField;
	private JLabel[] nodeLabels;
	private JLabel remainBlackLabel;
	private JLabel remainWhiteLabel;
	
	private boolean AIMode;
	
	private Game game;
	
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
		statusField.setSize(15,boardLength);
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
		redrawBoard();
	}
	
	public void addPiece(int x, int y) {
		Node n = game.board.getNode(x,y);
		Player p;
		if(n == null)
			return;
		
		try {
			p = game.placePiece(n.getIndex());
			
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
		int whites = game.player1.totalPieces();
		int blacks = game.player2.totalPieces();
		
		remainWhiteLabel.setText(" x " + game.player1.unplacedPieces());
		remainBlackLabel.setText(" x " + game.player2.unplacedPieces());
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
			if(!AIMode)
				setStatus("2 Player Enabled");
		}
		if(ae.getSource().equals(computer)) {
			AIMode = true;
			newGame();
			if(AIMode)
				setStatus("AIMode Enabled");
		}
	}
	
	public void mousePressed(MouseEvent me) {
		for (int i =0; i<24; i++) {
			if(game.board.getNode(i).isInRegion(me.getX(), me.getY())) {
				//System.out.println(i);
				if(game.expectedMove == Game.Move.Place) {
					addPiece(me.getX(), me.getY());
					redrawBoard();
					// ai move call
				}
				else if(game.expectedMove == Game.Move.Move) {
					//selectPiece.(me.getX(), me.getY());
					if(selectedNode == null || game.currentPlayer == game.board.getNode(i).getPlayer()) {
						if(game.currentPlayer != game.board.getNode(i).getPlayer())
							return;
						selectPiece(me.getX(), me.getY());
						redrawBoard();
						// ai move call
					}
					else {
						try {
							game.movePiece(selectedNode.getIndex(), game.board.getNode(i).getIndex());
							selectedNode = null;
							game.board.unSelectAll();
							redrawBoard();
							// ai move call
						} catch (IllegalMoveException e) {
							setStatus(e.getMessage());
						}
					}
				}
				else if(game.expectedMove == Game.Move.Remove) {
					removePiece(me.getX(), me.getY());
					redrawBoard();
					// ai move call
				}
				if(game.expectedMove == Game.Move.None) {
					String winner = game.getWinner().name;
					JOptionPane.showMessageDialog(mainFrame, winner+ " wins!");
					setStatus("Click new Game to play again");
				}
			}
		}
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
