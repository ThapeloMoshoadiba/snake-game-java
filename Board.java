package snakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	
	private final int B_WIDTH = 900; // The breadth of the board
	private final int B_HEIGHT = 900; // The length of the board
	private final int DOT_SIZE = 10; // The size of a single dot
	private final int ALL_DOTS = 1800; // The total no. of dots a snake can have
	private final int RAND_POS = 29; // Used to designate the random apple position
	private final int DELAY = 140; // This defines the speed of the game
	
	// below are the arrays that store the coordinates of all joints of the snake:
	private final int x [] = new int [ALL_DOTS];
	private final int y [] = new int [ALL_DOTS];
	
	private int dots;
	private int apple_x;
	private int apple_y;
	
	// below determines the direction the snake will be moving at the start of the game (i.e. right)
	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;
	
	private Timer timer;
	private Image dot;
	private Image apple;
	private Image head;
	
	public Board() { // Constructor for the Board class
		
		addKeyListener (new TAdapter());
		setBackground (Color.black);
		setFocusable (true);
		setPreferredSize (new Dimension (B_WIDTH, B_HEIGHT));
		loadImages();
		initGame();
		
	}
	
	private void loadImages() { // this method loads the images used in the game (i.e. dot, head, and apple)
		
		// the below items must be in a folder called 'res'; that folder must be in the same folder as this java file
		ImageIcon iid = new ImageIcon (getClass ().getResource("/res/dot.png"));
		dot = iid.getImage();
		
		ImageIcon iia = new ImageIcon (getClass ().getResource("/res/apple.png"));
		apple = iia.getImage();
		
		ImageIcon iib = new ImageIcon (getClass ().getResource("/res/head.png"));
		head = iib.getImage();
		
	}
	
	private void initGame () {
		
		dots = 3; // the snake at the beginning of the game will have 3 dots
		
		for (int z = 0; z < dots; z ++) {
			
			x [z] = 50 - z * 10;
			y [z] = 50;
			
		}
		
		locateApple();
		
		timer = new Timer (DELAY, this);
		timer.restart();
		
	}
	
	@Override
	protected void paintComponent (Graphics g) {
		
		super.paintComponent (g);
		doDrawing (g);
		
	}
	
	private void doDrawing (Graphics g) {
		
		if (inGame) {
			
			g.drawImage (apple, apple_x, apple_y, this);
			
			for (int z = 0; z < dots; z++) {
				
				if (z == 0) {
					
					g.drawImage(head, x [z], y [z], this);
					
				} else {
					
					g.drawImage(dot, x [z], y [z], this);
					
				}
				
			}
			
		} else {
			
			gameOver (g);
			
		}
		
	}
	
	private void gameOver (Graphics g) {
		
		String msg = "Game Over";
		Font small = new Font ("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics (small);
		
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - metr.stringWidth (msg) ) / 2, B_HEIGHT / 2);
		
	}
	
	private void checkApple () { // This method defines what happens when a snake eats an apple
		
		if ((x [0] == apple_x) && (y [0] == apple_y)) {
			
			dots ++; // The length of the snake will increase by one extra dot
			locateApple ();
			
		}
		
	}
	
	// The method below controls how the snake moves. When the head goes somewhere (right, left, up, down or forward), the 1st dot moves to where the
	// head was and the 2nd dot moves to where the 1st dot was etc.
	private void move () {
		
		for (int z = dots; z > 0; z--) {
			
			x [z] = x [z-1];
			y [z] = y [z-1];
			
		}
		
		if (leftDirection) {
			
			x [0] -= DOT_SIZE;
			
		}
		
		if (rightDirection) {
			
			x [0] += DOT_SIZE;
			
		}
		
		if (upDirection) {
			
			y [0] -= DOT_SIZE;
			
		}
		
		if (downDirection) {
			
			y [0] += DOT_SIZE;
			
		}
		
	}
	
	private void checkCollision () { // this method ends the game if the snake collides into itself or a wall
		
		for (int z = dots; z > 0; z--) {
			
			if ((z > 4) && (x [0] == x [z]) && (y [0] == y [z])) {
				
				inGame = false;
				
			}
			
		}
		
		if (y [0] >= B_HEIGHT) {
			
			inGame = false;
			
		}
		
		if (y [0] < 0) {
			
			inGame = false;
			
		}
		
		if (x [0] >= B_WIDTH) {
			
			inGame = false;
			
		}
		
		if (x [0] < 0) {
			
			inGame = false;
			
		}
		
		if (!inGame) {
			
			timer.stop();
			
		}
		
	}
	
	private void locateApple ( ) { // this method randomly places an apple on the board
		
		int r = (int) (Math.random() * RAND_POS);
		apple_x = r * DOT_SIZE;
		
		r = (int)(Math.random() * RAND_POS);
		apple_y = r * DOT_SIZE;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (inGame) {
			
			checkApple();
			checkCollision();
			move();
			
		}
		
		repaint();
		
	}
	
	private class TAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed (KeyEvent e) {
			
			int key = e.getKeyCode();
			
			if (key == KeyEvent.VK_LEFT && !rightDirection) {
				
				leftDirection = true;
				upDirection = false;
				downDirection = false;
				
			}
			
			if (key == KeyEvent.VK_RIGHT && !leftDirection) {
				
				rightDirection = true;
				upDirection = false;
				downDirection = false;
				
			}
			
			if (key == KeyEvent.VK_UP && !downDirection) {
				
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
				
			}
			
			if (key == KeyEvent.VK_DOWN && !upDirection) {
				
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
				
			}
			
		}
		
	}

}
