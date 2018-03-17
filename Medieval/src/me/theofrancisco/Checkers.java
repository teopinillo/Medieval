package me.theofrancisco;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Rectangle;

public class Checkers {

	private Board board;
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Checkers window = new Checkers();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Checkers() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Dimension dimension = new Dimension(530, 612);
		frame.getContentPane().setSize(dimension);
		frame.getContentPane().setPreferredSize(dimension);
		frame.getContentPane().setBounds(new Rectangle(150, 150, 512, 612));
		frame.getContentPane().setMinimumSize(dimension);
		frame.getContentPane().setMaximumSize(dimension);
		frame.setMinimumSize(dimension);
		frame.setMaximumSize(dimension);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblComputer = new JLabel("Computer: 0");
		lblComputer.setBounds(10, 549, 136, 14);
		
		JLabel lblHuman = new JLabel("Human: 0");
		lblHuman.setBounds(156, 549, 136, 14);
		frame.getContentPane().add(lblComputer);
		frame.getContentPane().add(lblHuman);		
		
		JButton btnStart = new JButton("Reset");
		btnStart.setBounds(387, 540, 117, 23);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				board.resetBoard();		
				board.showBoard();
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnStart);			
		
		board = new Board(frame, lblComputer, lblHuman);
		board.showBoard();
				
	}
}
