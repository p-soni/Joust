//Prabjote Garcha (pkg5hw)
//Puja Soni (pds7yx)

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bird {

	// / imgs: default storage for the pictures of the bird
	private BufferedImage[] imgs;

	// TODO: add your own fields here

	private double x;
	private double y;
	private int i;
	private double vx;
	private double vy;
	private Rectangle cbox;
	private Rectangle obstacle;
	private int score;

	/**
	 * Creates a bird object with the given image set
	 * 
	 * @param basename should be "birdg" or "birdr" (assuming you use the provided images)
	 *            
	 */
	public Bird(String basename, double x, double y, int i) {
		// You may change this method if you wish, including adding
		// parameters if you want; however, the existing image code works as is.

		this.x = x;
		this.y = y;
		this.i = i;
		this.cbox = new Rectangle((int) x, (int) y, 20, 20);
		this.score = 0;

		this.imgs = new BufferedImage[6];
		try {
			// 0-2: right-facing (folded, back, and forward wings)
			this.imgs[0] = ImageIO.read(new File(basename + ".png"));
			this.imgs[1] = ImageIO.read(new File(basename + "f.png"));
			this.imgs[2] = ImageIO.read(new File(basename + "b.png"));
			// 3-5: left-facing (folded, back, and forward wings)
			this.imgs[3] = Bird.makeFlipped(this.imgs[0]);
			this.imgs[4] = Bird.makeFlipped(this.imgs[1]);
			this.imgs[5] = Bird.makeFlipped(this.imgs[2]);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * A helper method for flipping in image left-to-right into a mirror image.
	 * There is no reason to change this method.
	 * 
	 * @param original
	 *            The image to flip
	 * @return A left-right mirrored copy of the original image
	 */
	private static BufferedImage makeFlipped(BufferedImage original) {
		AffineTransform af = AffineTransform.getScaleInstance(-1, 1);
		af.translate(-original.getWidth(), 0);
		BufferedImage ans = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());
		Graphics2D g = (Graphics2D) ans.getGraphics();
		g.drawImage(original, af, null);
		return ans;
	}

	public void makeFlipped(Bird otherbird) {
		if (cbox.getX() > otherbird.getX()) {
			this.i = 3;
		}
		if (cbox.getX() < otherbird.getX()) {
			this.i = 0;
		}
	}

	/**
	 * Draws this bird
	 * 
	 * @param g the paintbrush to use for the drawing
	 */

	public void draw(Graphics g) {

		this.i = i; // between 0 and 6, depending on facing and wing state //0-2
					// faces right, 3-5 faces left
		this.x = x; // where to center the picture (0,0) is upper left corner
					// //increasing x moves right
		this.y = y; // increasing y moves bird down

		// TODO: find the right x, y, and i instead of the examples given here
		g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
				(int) y - this.imgs[i].getHeight() / 2, null);
		// g.drawRect((int)x-25, (int)y-25, 50, 50);
		// g.setColor(Color.BLACK);
	}

	public void move() {
		this.x = this.x + ((this.vx) * (0.1));
		this.y = this.y + ((this.vy) * (0.1));
		cbox.setLocation((int) x, (int) y);
	}

	public void flapLeft() {
		this.vx = -30;
		this.vy = -30;
	}

	public void flapRight() {
		this.vx = 30;
		this.vy = -30;
	}

	public void applyDrag() {
		if (vx > 0) {
			vx = vx - 1;
		}
		if (vx < 0) {
			vx = vx + 1;
		}

		if (vx >= -5 && vx <= 5) {
			vx = 0;
		}
	}

	public void gravity() {
		if (vy < 0) {
			vy = vy + 2;
		}
		if (vy >= 0) {
			vy = vy + 2;
		}
		move();
	}

	public void bounceIfOutsideOf(Rectangle boundry, double bounciness) {

		if (x >= 780) {
			x = 779;
			vx = -1 * Math.abs(bounciness * vx);
		}
		if (x <= 20) {
			x = 21;
			vx = Math.abs(bounciness * vx);
		}

		if (y >= 580) {
			y = 579;
			vy = -1 * Math.abs(bounciness * vy);
		}

		if (y <= 20) {
			y = 21;
			vy = Math.abs(bounciness * vy);
		}

	}

	public void setY(double y) {
		this.y = y;
	}

	public double getY() {
		return y;
	}

	public void setvy(double vy) {
		this.vy = vy;
	}

	public double getvy() {
		return vy;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public void setvx(double vx) {
		this.x = x;
	}

	public double getvx() {
		return vx;
	}

	public Rectangle getCbox() {
		return cbox;
	}

	public void bounceIfInsideOf(Rectangle obstacle, double bounciness) {
		if (obstacle.intersects(cbox)) {
			// System.out.println("b loc: " + x + " "+ y +"\nBox location" +
			// cbox.getX() + " "+ cbox.getY());
			Rectangle intersection = obstacle.intersection(cbox);
			if (intersection.getHeight() < intersection.getWidth()) {
				// it's hitting from the top or bottom
				if (cbox.getY() > obstacle.getY()) { // hitting from the bottom
					// System.out.println("INSIDE");
					this.y = y + intersection.getHeight() + 30;
					this.vy = -vy;
				} else { // hitting from the top
					this.vy = -vy;
				}
			}
			if (intersection.getHeight() > intersection.getWidth()) {
				if (cbox.getX() > obstacle.getX()) {
					this.vx = -vx;
					this.x = x + intersection.getWidth() + 30;// hitting from
																// right
				} else {
					this.vx = -vx; // hitting form the left
				}
			}
		}
	}

	public void bounceIfBirdHitsBird(Bird otherBird, double bounciness) { // when bird collides change vx = -vx when side by side
																			
		if (this.cbox.intersects(otherBird.getCbox())) {
			Rectangle intersection = this.cbox
					.intersection(otherBird.getCbox());
			if (intersection.getHeight() < intersection.getWidth()
					&& (cbox.y < otherBird.getCbox().y)) {
				otherBird.y = 0;
				this.score++;

			}
			if (intersection.getHeight() > intersection.getWidth()) {
				if (cbox.getX() > otherBird.getCbox().getX()) {
					this.vx = -vx * bounciness;
					this.x = x + intersection.getWidth();// hitting from right
					// if this.vx is negative, this.x-=as;djf
					// else this.x+= akjsdf
					otherBird.vx = -vx * bounciness;
					otherBird.x = x + intersection.getWidth() + 20;  // hitting from right												
				} else {
					this.vx = -vx * bounciness; // hitting form the left
					otherBird.vx = -vx * bounciness; // hitting form the left
				}
			}
		}
	}

	public int getScore() {
		return score;
	}

}