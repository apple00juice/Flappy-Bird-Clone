import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class View extends JPanel {

	Control c;
	
	Bird bird;
	
	List<Obstacle> ObstacleList;

	public View(List<Obstacle> ObstacleList, Bird bird, Control c) {
		this.c = c;
		this.bird = bird;
		this.ObstacleList = ObstacleList;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int xoffset;
		int yoffset;
		float scale;

		if (getWidth() * 6 / 8 < getHeight()) {

			xoffset = 0;
			yoffset = (getHeight() - (getWidth() * 6 / 8)) / 2;
			scale = getWidth() / c.getWidth()*1f;
		} else {
			xoffset = (getWidth() - (getHeight() * 8 / 6)) / 2;
			yoffset = 0;
			scale = getHeight() / c.getHeight()*1f;

		}

		g.setColor(Color.BLACK);
		g.drawRect(xoffset, yoffset, (int) (c.getWidth() * scale), (int) (c.getHeight() * scale));

		// Draw Bird
		g.setColor(Color.red);
		g.fillOval((int) (xoffset + bird.getX() * scale), (int) (yoffset + bird.getY() * scale),
				(int) (bird.getWidth() * scale), (int) (bird.getHeight() * scale));

		// Draw Obstacles
		for (int i = 0; i < ObstacleList.size(); i++) {
			Obstacle tempObject = ObstacleList.get(i);
			
			g.setColor(Color.GREEN);
			
		
			
			if (tempObject.getX() <= 0) {
				float obstacleWidth = tempObject.getX()+tempObject.getWidth();
				
				g.fillRect((int) (xoffset), (int) (yoffset + tempObject.getY() * scale),
						(int) (obstacleWidth * scale), (int) (tempObject.getHeight() * scale));
			} else {
				float obstacleWidth = tempObject.getX() + tempObject.getWidth() > 800 ? 800 - tempObject.getX()
						: tempObject.getWidth();
				g.fillRect((int) (xoffset + tempObject.getX() * scale), (int) (yoffset + tempObject.getY() * scale),
						(int) (obstacleWidth * scale), (int) (tempObject.getHeight() * scale));
			}

			
		}

	}

}
