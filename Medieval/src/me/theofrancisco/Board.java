package me.theofrancisco;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Board {

	private JFrame frame;
	private Token[][] tokens = new Token[8][8];
	private int[][] initBoard = { { 2, 3, 2, 3, 2, 3, 2, 3 }, { 1, 0, 1, 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 1, 0, 1, 6, 7, 0, 1, 0 }, { 0, 1, 0, 7, 6, 1, 0, 1 }, { 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 0, 1, 0, 1, 0, 1, 0, 1 }, { 5, 4, 5, 4, 5, 4, 5, 4 } };

	private JButton[][] buttons = new JButton[8][8];

	private Token token;
	private JButton button;

	private final int[] xJumps = { -2, 2, -1, 1, -2, -1, 1, 2 };
	private final int[] yJumps = { -1, -1, -2, -2, 1, 2, 2, 1 };
	private boolean playingComputer = false;
	private int computer;
	private int human;
	// start position to move
	private boolean sourceSelected = false;
	private int startX;
	private int startY;
	private JLabel lblComputer;
	private JLabel lblHuman;
	
	public Board(JFrame frame, JLabel _lblComputer, JLabel _lblHuman) {

		this.frame = frame;
		lblComputer = _lblComputer;
		lblHuman = _lblHuman;
		
		playingComputer = false;
		computer=0;
		human=0;		
		sourceSelected = false;
		startX=-1;
		startY=-1;
		
		int x = 1;
		int y = 1;
		// Initialized the Board
		for (int i = 0; i < 8; i++) {
			x = 0;
			for (int j = 0; j < 8; j++) {
				tokens[i][j] = new Token(initBoard[i][j]);
				buttons[i][j] = new JButton("");
				buttons[i][j].setPreferredSize(new Dimension(64, 64));
				buttons[i][j].setMinimumSize(new Dimension(64, 64));
				buttons[i][j].setMaximumSize(new Dimension(64, 64));
				buttons[i][j].setBounds(x, y, 64, 64);
				buttons[i][j].setIcon(new ImageIcon(getClass().getResource("/green_square.png")));
				buttons[i][j].setBorderPainted(false);
				TokenListener tl = new TokenListener(i, j);
				buttons[i][j].addActionListener(tl);
				this.frame.getContentPane().add(buttons[i][j]);
				x += 64;
			}
			y += 64;
		}
	}
	
	public void resetBoard(){
		for (int i = 0; i < 8; i++) 	
			for (int j = 0; j < 8; j++) 
				tokens[i][j].initToken(initBoard[i][j]);	
		computer = 0;
		human = 0;
		playingComputer = false;
		sourceSelected = false;
	}

	public void showBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				token = tokens[i][j];
				button = buttons[i][j];

				if (token.getTeam() == Token.HUMAN) {
					if (token.isOnWhite()) {
						if (token.isSelected()) {
							button.setIcon(new ImageIcon(getClass().getResource("/blue_on_white_selected.png")));
						} else {
							button.setIcon(new ImageIcon(getClass().getResource("/blue_on_white.png")));
						}
					} else {
						if (token.isSelected()) {
							button.setIcon(new ImageIcon(getClass().getResource("/blue_on_green_selected.png")));
						} else {
							button.setIcon(new ImageIcon(getClass().getResource("/blue_on_green.png")));
						}

					}
				}

				if (token.getTeam() == Token.COMPUTER) {
					if (token.isOnWhite()) {
						button.setIcon(new ImageIcon(getClass().getResource("/black_on_white.png")));
					} else {
						button.setIcon(new ImageIcon(getClass().getResource("/black_on_green.png")));
					}
				}

				if (token.getTeam() == Token.FLAG) {
					if (token.isOnWhite()) {
						button.setIcon(new ImageIcon(getClass().getResource("/flag_on_white.png")));
					} else {
						button.setIcon(new ImageIcon(getClass().getResource("/flag_on_green.png")));
					}
				}

				if (token.getTeam() == Token.EMPTY_SQUARE) {
					if (token.isOnWhite()) {
						button.setIcon(new ImageIcon(getClass().getResource("/white_square.png")));
					} else {
						button.setIcon(new ImageIcon(getClass().getResource("/green_square.png")));
					}
				}

			}

		}
		lblComputer.setText("Computer: "+computer);
		lblHuman.setText("Human: "+human);		
	}

	// check if is a valid move
	// loop over the possible jumps, if it find
	// one equals then the move is right
	private boolean isValidMove(int x, int y, int nx, int ny) {
		int xtemp, ytemp;
		for (int i = 0; i < 8; i++) {
			xtemp = x + xJumps[i];
			ytemp = y + yJumps[i];
			if ((xtemp == nx) && (ytemp == ny)) {
				return true;
			}
		}
		return false;
	}

	private void moveTo(int x, int y, int xOther, int yOther) {
		tokens[xOther][yOther].setTeam(tokens[x][y].getTeam());
		tokens[x][y].setTeam(Token.EMPTY_SQUARE);
		showBoard();
	}

	// return -1 is can not move to the next position
	// return 100 if takes a flag
	// return 20 if is a enemy
	// return 0 if is free cell
	private int chkMoveTo(int x, int y, int xOther, int yOther) {
		// if the next position in not a valid jump then return -1;
		if (!isValidMove(x, y, xOther, yOther))
			return -1;

		// get the team for x,y, if the team is the same, then can not move
		int sourceTeam = tokens[x][y].getTeam();
		int destTeam = tokens[xOther][yOther].getTeam();
		if (sourceTeam == destTeam)
			return -1;

		// if its a flag the return 100
		if (destTeam == Token.FLAG)
			return 100;

		// if its a empty cell then return 0
		if (destTeam == Token.EMPTY_SQUARE)
			return 0;

		if ((sourceTeam == Token.HUMAN) && (destTeam == Token.COMPUTER))
			return 20;

		if ((sourceTeam == Token.COMPUTER) && (destTeam == Token.HUMAN))
			return 20;

		return -1;
	}

	private boolean isValidPos(int x, int y) {
		return ((x >= 0) && (x <= 7) && (y >= 0) && (y <= 7));
	}

	private void playComputer() {

		class PCToken implements Comparable<PCToken> {
			int x, y, xd, yd, v;

			PCToken(int _x, int _y, int _xd, int _yd, int _v) {
				x = _x;
				xd = _xd;
				y = _y;
				yd = _yd;
				v = _v;
			}

			@Override
			public int compareTo(PCToken other) {
				return other.v - v;
			}

		}		
		playingComputer = true;
		ArrayList<PCToken> choosen = new ArrayList<>();
		// loop over all the board searching for computer tokens
		// get the token with the highest mov value and move it.
		int xDest = -1;
		int yDest = -1;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				// check if there a pc token in the cell x,y
				if (tokens[x][y].getTeam() == Token.COMPUTER) {
					
					// check all the possibles jumps
					for (int j = 0; j < 8; j++) {
						xDest = x + xJumps[j];
						yDest = y + yJumps[j];
						if (isValidPos(xDest, yDest)) {							
							int v = chkMoveTo(x, y, xDest, yDest);
							// if can move to dest, then add to the list
							if (v >= 0) {								
								choosen.add(new PCToken(x, y, xDest, yDest, v));
							}
						}
					}
				}
			}
		}
		Collections.sort(choosen);		
		
		// if we have token to move then
		if (!choosen.isEmpty()) {
			//keep the first with the first value
			int v = choosen.get(0).v;
			ArrayList<PCToken> first = new ArrayList<>();
			for (PCToken t : choosen) {
				if (t.v==v) first.add(t);
			}
			//Shuffle the list
			Collections.shuffle(first);
			// get the first on the shuffle's list and move it
			PCToken p = first.get(0);
			computer+=v;
			moveTo(p.x, p.y, p.xd, p.yd);
		}
		playingComputer = false;		

	}

	class TokenListener implements ActionListener {
		private int x, y;

		TokenListener(int x, int y) {
			this.x = x;
			this.y = y;
		}

		private void helper(int x, int y, boolean b) {
			sourceSelected = b;
			tokens[x][y].setSelected(b);
			showBoard();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!playingComputer) {
				if (sourceSelected) {
					int chk = chkMoveTo(startX, startY, x, y);
					if (chk >= 0) {
						human+=chk;
						moveTo(startX, startY, x, y);
						helper(startX, startY, false);
						playComputer();
					} else {
						helper(startX, startY, false);
					}
				} else

				if (tokens[x][y].getTeam() == Token.HUMAN) {
					startX = x;
					startY = y;
					helper(x, y, true);					
				}

			}
		}
	}

}
