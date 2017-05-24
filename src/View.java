import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class View extends JPanel {

	Bird bird;
	List<Obstacle> ObstacleList;

	public View(List<Obstacle> ObstacleList, Bird bird) {
		this.bird = bird;
		this.ObstacleList = ObstacleList;
	}

	private float getScale() {
		return (getWidth() / 800f) < (getHeight() / 600f) ? (getWidth() / 800f) : (getHeight() / 600f);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw Bird
		g.setColor(Color.red);
		g.fillOval((int) (bird.getX() * getScale()), (int) (bird.getY() * getScale()),
				(int) (bird.getWidth() * getScale()), (int) (bird.getHeight() * getScale()));

		// Draw Obstacles
		for (int i = 0; i < ObstacleList.size(); i++) {
			Obstacle tempObject = ObstacleList.get(i);
			g.setColor(Color.GREEN);
			
			g.fillRect((int) (tempObject.getX()* getScale()), (int) (tempObject.getY()* getScale()), (int) (tempObject.getWidth()* getScale()),
					(int) (tempObject.getHeight()* getScale()));
		}

	}

}
