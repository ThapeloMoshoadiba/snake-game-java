package snakeGame;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Snake extends JFrame {
	
	public Snake () {
		
		add (new Board ());
		setResizable (false);
		pack();
		
		setTitle ("Thapelo's Snake Game");
		setLocationRelativeTo (null);
		setDefaultCloseOperation (EXIT_ON_CLOSE);
		
	}
	
	public static void main (String [] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run () {
				
				JFrame ex = new Snake ();
				ex.setVisible(true);
				
			}
			
		});
		
	}

}
