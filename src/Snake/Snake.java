package Snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

/*
 * The class for the whole game.
 */

public class Snake implements ActionListener, KeyListener {
	
	public static int WIDTH = 810;
	public static int HEIGHT = 700;
	public static Snake SNAKE_GAME;
	
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	public static final int SCALE = 10;
	
	public Random r;
	public Point head;
	public Point blueFood, redFood, orangeFood;			//added
	
	private JFrame frame;
	private Dimension dim;
	private Toolkit toolKit;
	private Timer timer = new Timer(20, this);
	
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	private int ticks = 0;
	private int direction;
	private int tailLength;
	private int score = 0, highscore = 0;
	private int selectFood;			
	private boolean gameOver = false;
	private boolean paused;
	
	private RenderPanel renderPanel;
	
	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame("Snake Game");
		renderPanel = new RenderPanel();
		
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		frame.setLocationRelativeTo(null);
		frame.add(renderPanel);
		frame.addKeyListener(this);
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		startGame();
	}
	
	public int getScore() {
		return score;
	}
	
	public int getHighscore() {
		return highscore;
	}
	
	public int getTailLength() {
		return tailLength;
	}
	
	public int getSelectFood() {
		return selectFood;
	}
	
	public void startGame() {
		gameOver = false;
		paused = false;
		tailLength = 5;
		score = 0;
		direction = DOWN;
		head = new Point(0, 0);
		r = new Random();
		snakeParts.clear();
		
		selectFood();

		timer.start();		//executes actionPerformed
	}
	
	public void selectFood() {
		selectFood = r.nextInt(3);
		
		if (selectFood == 0) {
			blueFood = new Point(r.nextInt(79), r.nextInt(66));
		} else if (selectFood == 1) {
			redFood = new Point(r.nextInt(79), r.nextInt(66));
		} else if (selectFood == 2) {
			orangeFood = new Point(r.nextInt(79), r.nextInt(66));
		}
	}
	
	public boolean noTailAt(int x, int y) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(x, y))) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		renderPanel.repaint();
		ticks++;
		
		if (ticks % 3 == 0 && head != null && !gameOver && !paused) {
			if (score > highscore) {
				highscore = score;
			}
			
			snakeParts.add(new Point(head.x, head.y));
			if (direction == UP) { 
				if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1)) {
					head = new Point(head.x, head.y - 1);
				} else {
					gameOver = true;
				}
			}
			if (direction == DOWN) {
				if (head.y + 1 < 68 && noTailAt(head.x, head.y + 1)) {
					head = new Point(head.x, head.y + 1);
				} else {
					gameOver = true;
				}
			}
			if (direction == LEFT) {
				if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y)) {
					head = new Point(head.x - 1, head.y);
				} else {
					gameOver = true;
				}
			}
			if (direction == RIGHT) {
				if (head.x + 1 < 81 && noTailAt(head.x + 1, head.y)) {
					head = new Point(head.x + 1, head.y);
				} else {
					gameOver = true;
				}
			}
			
			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);
			}
			
			if (blueFood != null || redFood != null || orangeFood != null) {
				if (head.equals(blueFood)) {
					score += 10;
					tailLength++;
					selectFood();
				}
				if (head.equals(redFood)) {			//all added
					score += 20;
					tailLength++;
					selectFood();
				}
				if (head.equals(orangeFood)) {		//all added
					score += 50;	
					tailLength++;
					selectFood();
				}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		//unused
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyPressed = e.getKeyCode();

		if (keyPressed == KeyEvent.VK_A && direction != RIGHT) {
			direction = LEFT;
		}
		if (keyPressed == KeyEvent.VK_D && direction != LEFT) {
			direction = RIGHT;
		}
		if (keyPressed == KeyEvent.VK_W && direction != DOWN) {
			direction = UP;
		}
		if (keyPressed == KeyEvent.VK_S && direction != UP) {
			direction = DOWN;
		}
		if (keyPressed == KeyEvent.VK_SPACE)
			if (gameOver) {
				startGame();
			} else {
				paused = !paused;
			}
		if (keyPressed == KeyEvent.VK_ESCAPE) {
			frame.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//unused since the snake can't stop moving in a snake game
	}
	
	public static void main(String[] args) {
		SNAKE_GAME = new Snake();
	}

}
