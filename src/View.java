import java.awt.Color;
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);


		// Draw Bird
		g.setColor(Color.red);
		g.fillOval((int) bird.getX(), (int) bird.getY(), (int) bird.getWidth(), (int) bird.getHeight());

		// Draw Obstacles
		for (int i = 0; i < ObstacleList.size(); i++) {
			Obstacle tempObject = ObstacleList.get(i);
			g.setColor(Color.GREEN);

			g.fillRect((int) tempObject.getX(), (int) tempObject.getY(), (int) tempObject.getWidth(),
					(int) tempObject.getHeight());
		}

	}

}
