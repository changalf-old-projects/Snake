package Snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class RenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static Color customColor = new Color(0, 51, 153);

	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(customColor);
		g.fillRect(0, 0, Snake.WIDTH, Snake.WIDTH);
		
		Snake snake = Snake.SNAKE_GAME;
		
		g.setColor(new Color(0, 153, 51));
		
		for (Point point : snake.snakeParts) {
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, 
					Snake.SCALE, Snake.SCALE);
		}
		
		g.fillRect(snake.head.x * Snake.SCALE, snake.head.y * Snake.SCALE, 
				Snake.SCALE, Snake.SCALE);
		
		int selectFood = snake.getSelectFood();		//randomly choose food 
		
		if (selectFood == 0) {
			g.setColor(new Color(194, 255, 255));
			g.fillOval(snake.blueFood.x * Snake.SCALE , snake.blueFood.y * Snake.SCALE, 
					Snake.SCALE, Snake.SCALE);
		} else if (selectFood == 1) {
			g.setColor(Color.RED);
			g.fillOval(snake.redFood.x * Snake.SCALE , snake.redFood.y * Snake.SCALE, 
					Snake.SCALE, Snake.SCALE);
		} else if (selectFood == 2) {
			g.setColor(Color.ORANGE);
			g.fillOval(snake.orangeFood.x * Snake.SCALE , snake.orangeFood.y * Snake.SCALE, 
					Snake.SCALE, Snake.SCALE);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial Black", Font.BOLD, 24));
		g.drawString("Highscore: " + snake.getHighscore(), 10, 30);
		g.drawString("Score: " + snake.getScore(), 10, 60);
		g.drawString("Tail length: " + snake.getTailLength(), 10, 90);
	}
	
}
