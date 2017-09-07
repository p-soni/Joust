//Prabjote Garcha (pkg5hw)
//Puja Soni (pds7yx)

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;




public class JoustScreen extends KeyAdapter implements ActionListener {

	/**
	 * A simple method to make the game runnable. You should not modify 
	 * this main method: it should print out a list of extras you added
	 * and then say "new JoustScreen();" -- nothing more than that.
	 */
	public static void main(String[] args) {
		// add a list of all extras you did, such as
		// System.out.println("Moving obstacles");
		// System.out.println("Birds leave trails of glowing faerie dust");
		// System.out.println("Press left-right-left-left-down to open a game of Mahjong");
		System.out.println("Game over mechanism");
		new JoustScreen();
	}

	
	// DO NOT CHANGE the next four fields (the window and timer)
	private JFrame window;         // the window itself
	private BufferedImage content; // the current game graphics
	private Graphics2D paintbrush; // for drawing things in the window
	private Timer gameTimer;       // for keeping track of time passing
	// DO NOT CHANGE the previous four fields (the window and timer)
	
	
	// TODO: add your own fields here
	private Bird bird;
	private Bird bird2;
	private Rectangle boundry;
	private Rectangle Obstacle1;
	private Rectangle Obstacle2;	
	
	
	public JoustScreen() {
		// DO NOT CHANGE the window, content, and paintbrush lines below
		this.window = new JFrame("Joust Clone");
		this.content = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		this.paintbrush = (Graphics2D)this.content.getGraphics();
		this.window.setContentPane(new JLabel(new ImageIcon(this.content)));
		this.window.pack();
		this.window.setVisible(true);
		this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.window.addKeyListener(this);
		// DO NOT CHANGE the window, content, and paintbrush lines above


		// TODO: add anything else you might need (e.g., a couple of Bird objects, some walls)
		this.bird = new Bird("birdr", 50, 30, 0);
		this.bird2 = new Bird("birdg", 750, 30, 3);
		this.Obstacle1 = new Rectangle(170,150,40,250);
		this.Obstacle2 = new Rectangle(400,350,220,50);
		
		
		this.boundry = new Rectangle(800, 600);
		
		

		// DO NOT CHANGE the next two lines nor add lines after them
		this.gameTimer = new Timer(20, this); // tick at 1000/20 fps
		this.gameTimer.start();               // and start ticking now
		// DO NOT CHANGE the previous two lines nor add lines after them
	}

	/**
	 * This method gets called each time a player presses a key.
	 * You can find out what key the pressed by comparing event.getKeyCode() with KeyEvent.VK_...
	 */
	public void keyPressed(KeyEvent event) {
		
		// TODO: handle the keys you want to use to run your game

		if (event.getKeyCode() == KeyEvent.VK_A) { // example
			bird.move();
			bird.flapLeft();
			
		}
		if (event.getKeyCode() == KeyEvent.VK_S) { // example
			bird.move();
			bird.flapRight();
			
		}
		if (event.getKeyCode() == KeyEvent.VK_L) { // example
			bird2.move();
			bird2.flapRight();
			
			
			
		}
		if (event.getKeyCode() == KeyEvent.VK_K) { // example
			bird2.move();
			bird2.flapLeft();
			
			
		}
		
	}

	/**
	 * Java will call this every time the gameTimer ticks (50 times a second).
	 * If you want to stop the game, invoke this.gameTimer.stop() in this method.
	 */
	public void actionPerformed(ActionEvent event) {
		// DO NOT CHANGE the next four lines, and add nothing above them
		if (! this.window.isValid()) { // the "close window" button
			this.gameTimer.stop();     // should stop the timer
			return;                    // and stop doing anything else
		}                              
		// DO NOT CHANGE the previous four lines
		
		
		
		// TODO: add every-frame logic in here (gravity, momentum, collisions, etc)

		bird.applyDrag();
		bird2.applyDrag();
		bird.gravity();
		bird2.gravity();
		bird.bounceIfOutsideOf(boundry, 0.5);
		bird2.bounceIfOutsideOf(boundry, 0.5);
		bird.bounceIfInsideOf(Obstacle1, 0.5);
		bird2.bounceIfInsideOf(Obstacle1, 0.5);
		bird.bounceIfInsideOf(Obstacle2, 0.5);
		bird2.bounceIfInsideOf(Obstacle2, 0.5);
		bird.bounceIfBirdHitsBird(bird2, 0.5);
		bird2.bounceIfBirdHitsBird(bird, 0.5);
		bird.makeFlipped(bird2);
		bird2.makeFlipped(bird);
		
		
		
		// DO NOT CHANGE the next line; it must be last in this method
		this.refreshScreen(); // redraws the screen after things move
		// DO NOT CHANGE the above line; it must be last in this method
	}

	/**
	 * Re-draws the screen. You should erase the old image and draw a 
	 * new one, but you should not change anything in this method
	 * (use actionPerformed instead if you need something to change).
	 */
	private void refreshScreen() {
		this.paintbrush.setColor(new Color(150, 210, 255)); // pale blue
		this.paintbrush.fillRect(0, 0, this.content.getWidth(), this.content.getHeight()); // erases the previous frame

		
		// TODO: replace the following example code with code that does 
		// the right thing (i.e., draw the birds, walls, and score)
		
		
		
		
		// example bird drawing; replace with something sensible instead of making a new bird every frame
		bird.draw(this.paintbrush);
		bird2.draw(this.paintbrush);
		
		
		
	
		// example wall drawing; replace with something sensible instead of making a new wall every frame
		this.paintbrush.setColor(Color.MAGENTA);
		this.paintbrush.fillRect(170,150,20,230);
		
		this.paintbrush.fillRect(400,350,200,30);
		//this.paintbrush.fill(Obstacle2);
		

		// example text drawing, for scores and/or other messages
		Font f = new Font(Font.SANS_SERIF, Font.BOLD, 20);
		this.paintbrush.setFont(f);
		this.paintbrush.setColor(new Color(127,0,0)); // dark red
		this.paintbrush.drawString(bird.getScore()+"", 30, 30);
		this.paintbrush.setColor(new Color(0,127,0)); // dark green
		this.paintbrush.drawString(bird2.getScore()+"", 760, 30);
		
		if(bird.getScore()==10){
			String msg = "Gameover. Red wins!";
			
			
			this.paintbrush.setFont(f);
			f = new Font(Font.SANS_SERIF, Font.BOLD, 40);
			Rectangle2D r = f.getStringBounds(msg, this.paintbrush.getFontRenderContext());
			this.paintbrush.setFont(f);
			this.paintbrush.setColor(Color.ORANGE);
			this.paintbrush.drawString(msg, 400-(int)r.getWidth()/2, 300);
			this.gameTimer.stop(); 
		}
		
		
		
		else if(bird2.getScore()==10){
			String msg = "Gameover. Green wins!";
			
			this.paintbrush.setFont(f);
			f = new Font(Font.SANS_SERIF, Font.BOLD, 40);
			Rectangle2D r = f.getStringBounds(msg, this.paintbrush.getFontRenderContext());
			this.paintbrush.setFont(f);
			this.paintbrush.setColor(Color.ORANGE);
			this.paintbrush.drawString(msg, 400-(int)r.getWidth()/2, 300);
			this.gameTimer.stop(); 
		}
		
		/*
		String msg = "Unimplemented"; //who wins. In demo it says red or green wins!
		f = new Font(Font.SANS_SERIF, Font.BOLD, 90);
		Rectangle2D r = f.getStringBounds(msg, this.paintbrush.getFontRenderContext());
		this.paintbrush.setFont(f);
		this.paintbrush.setColor(Color.BLUE);
		this.paintbrush.drawString(msg, 400-(int)r.getWidth()/2, 300);
		*/
		
		
		// DO NOT CHANGE the next line; it must be last in this method
		this.window.repaint();  // displays the frame to the screen
		// DO NOT CHANGE the above line; it must be last in this method
	}
	


}