package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI implements ActionListener{

	// GUI Variables
	private String boardName;
	private String imagePath;
	private String newGameText;
	private String exitGameText;
	private String whitePath;
	private String blackPath;
	private ImageIcon boardIcon;
	private ImageIcon whiteIcon;
	private ImageIcon blackIcon;
	private int nodeRadius;
	private int nodeDiameter;
	private int boardLength;
	
	private JButton newGameButton;
	private JButton exitGameButton;
	private JFrame mainFrame;
	private JPanel centerPanel;
	private JPanel westPanel;
	private JLabel boardLabel;
	
	private JLabel nodeLabel0;
	private JLabel nodeLabel1;
	
	public GUI() {
		//Initialize Strings
		boardName = "Nine Men's Morris";
		imagePath = "resources/board.png";
		whitePath = "resources/white.png";
		blackPath = "resources/black.png";
		newGameText = "New Game";
		exitGameText = "Exit";
		boardIcon = new ImageIcon(imagePath);
		whiteIcon = new ImageIcon(whitePath);
		blackIcon = new ImageIcon(blackPath);
		nodeRadius = whiteIcon.getIconHeight()/2;
		nodeDiameter = whiteIcon.getIconHeight();
		boardLength = boardIcon.getIconHeight();
		
		
		//Create West Panel
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
		
		//Create Center Panel
		boardLabel = new JLabel("", boardIcon, JLabel.CENTER);
		nodeLabel0 = new JLabel("", whiteIcon, JLabel.CENTER);
		nodeLabel1 = new JLabel("", blackIcon, JLabel.CENTER);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(null);
		centerPanel.add(nodeLabel0);
		centerPanel.add(nodeLabel1);
		centerPanel.add(boardLabel);
		boardLabel.setBounds(0,0,boardLength,boardLength);
		nodeLabel0.setBounds(30-nodeRadius,30-nodeRadius,nodeDiameter,nodeDiameter);
		nodeLabel1.setBounds(320-nodeRadius,30-nodeRadius,nodeDiameter,nodeDiameter);
		boardLabel.setOpaque(true);
		centerPanel.repaint();
		
		//Create Main Frame
		mainFrame = new JFrame(boardName);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(westPanel, BorderLayout.WEST);
		mainFrame.add(centerPanel, BorderLayout.CENTER);
		mainFrame.setPreferredSize(new Dimension(760, 670));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {

	}

}
