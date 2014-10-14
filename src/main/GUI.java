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
	private JFrame mainFrame;
	private JPanel centerPanel;
	private JPanel westPanel;
	private JPanel southPanel;
	private JLabel boardLabel;
	private JTextField statusField;
	
	private JLabel[] nodeLabels;
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
		westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(15,1));
		westPanel.add(newGameButton);
		westPanel.add(exitGameButton);
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
			e.printStackTrace();
		}
	}
	
	public void removePiece(int x, int y) {
		Node n = game.board.getNode(x,y);
		if(n == null)
			return;
		try {
			game.removePiece(n.getIndex());
			
		} catch (IllegalMoveException e) {
			e.printStackTrace();
		}
	}
	
	public void movePiece(int x, int y) {
		
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
		/*nodeLabels[0].setBounds(30-nodeRadius,30-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[1].setBounds(320-nodeRadius,30-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[2].setBounds(610-nodeRadius,30-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[3].setBounds(126-nodeRadius,126-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[4].setBounds(320-nodeRadius,126-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[5].setBounds(514-nodeRadius,126-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[6].setBounds(223-nodeRadius,223-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[7].setBounds(320-nodeRadius,223-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[8].setBounds(417-nodeRadius,223-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[9].setBounds(30-nodeRadius,320-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[10].setBounds(126-nodeRadius,320-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[11].setBounds(223-nodeRadius,320-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[12].setBounds(417-nodeRadius,320-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[13].setBounds(514-nodeRadius,320-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[14].setBounds(610-nodeRadius,320-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[15].setBounds(223-nodeRadius,417-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[16].setBounds(320-nodeRadius,417-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[17].setBounds(417-nodeRadius,417-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[18].setBounds(126-nodeRadius,514-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[19].setBounds(320-nodeRadius,514-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[20].setBounds(514-nodeRadius,514-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[21].setBounds(30-nodeRadius,610-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[22].setBounds(320-nodeRadius,610-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabels[23].setBounds(610-nodeRadius,610-nodeRadius,nodeDiameter,nodeDiameter);*/
	}
	
	public void setStatus(String s) {
		statusField.setText(s); 
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(exitGameButton))
			System.exit(0);
		if(ae.getSource().equals(newGameButton))
			newGame();
	}
	
	public void mousePressed(MouseEvent me) {
		for (int i =0; i<24; i++) {
			if(game.board.getNode(i).isInRegion(me.getX(), me.getY())) {
				//System.out.println(i);
				if(game.expectedMove == Game.Move.Place) {
					addPiece(me.getX(), me.getY());
				}
				else if(game.expectedMove == Game.Move.Move) {
					//selectPiece.(me.getX(), me.getY());
					if(selectedNode == null && game.currentPlayer == game.board.getNode(i).getPlayer())
						selectPiece(me.getX(), me.getY());
					else {
						try {
							game.movePiece(selectedNode.getIndex(), game.board.getNode(i).getIndex());
							selectedNode = null;
							game.board.unSelectAll();
						} catch (IllegalMoveException e) {
							e.printStackTrace();
						}
					}
				}
				else if(game.expectedMove == Game.Move.Remove) {
					removePiece(me.getX(), me.getY());
				}
				if(game.expectedMove == Game.Move.None) {
					String winner = game.getWinner().name;
					JOptionPane.showMessageDialog(mainFrame, winner+ " wins!");
					setStatus("Click new Game to play again");
				}
			}
		}
		redrawBoard();
		
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
