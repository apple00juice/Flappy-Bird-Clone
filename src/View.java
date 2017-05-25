import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class View extends JPanel {

	Control c;
	
	Bird bird;
	
	List<Obstacle> ObstacleList;
	
	int counter = 0;

	private JLabel JLcounter;
	
	public View(List<Obstacle> ObstacleList, Bird bird, Control c) {
		this.c = c;
		this.bird = bird;
		this.ObstacleList = ObstacleList;
		
		JLcounter = new JLabel(Integer.toString(counter));
		JLcounter.setFont(new Font("Arial", 10, 100));
		JLcounter.setForeground(Color.ORANGE);
		JLcounter.setBounds((getWidth()-JLcounter.getWidth()) / 2, getHeight() / 3 , 0, 0);
		JLcounter.setSize(JLcounter.getPreferredSize());
		
		add(JLcounter);
	}
	
	public void setCounter(int counter){
		JLcounter.setText(Integer.toString(counter));
		JLcounter.setSize(JLcounter.getPreferredSize());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int xoffset;
		int yoffset;
		float scale;

		if (getWidth() * c.getHeight() / c.getWidth() < getHeight()) {

			xoffset = 0;
			yoffset = (getHeight() - (getWidth() * c.getHeight() / c.getWidth())) / 2;
			scale = getWidth() / (c.getWidth()*1f);
		} else {
			xoffset = (getWidth() - (getHeight() * c.getWidth() / c.getHeight())) / 2;
			yoffset = 0;
			scale = getHeight() / (c.getHeight()*1f);

		}
		
		// Draw counter
		
		

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
				float obstacleWidth = tempObject.getX() + tempObject.getWidth() > c.getWidth() ? c.getWidth() - tempObject.getX()
						: tempObject.getWidth();
				g.fillRect((int) (xoffset + tempObject.getX() * scale), (int) (yoffset + tempObject.getY() * scale),
						(int) (obstacleWidth * scale), (int) (tempObject.getHeight() * scale));
			}

			
		}
		
		g.setColor(Color.BLACK);
		g.drawRect(xoffset, yoffset, (int) (c.getWidth() * scale), (int) (c.getHeight() * scale));

	}

}
