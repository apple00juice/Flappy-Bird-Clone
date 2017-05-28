import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class View extends JPanel {

	Control c;

	Bird bird;

	List<Obstacle> ObstacleList;

	int counter = 0;

	private JLabel JLcounter;
	
	private float[][] xyh;

	private boolean collisionbox = false;

	public View(List<Obstacle> ObstacleList, Bird bird, Control c, int objects) {
		this.setDoubleBuffered(true);
		this.c = c;
		this.bird = bird;
		this.ObstacleList = ObstacleList;

		xyh = new float[objects*2][3];
		
		setLayout(null);

		JLcounter = new JLabel(Integer.toString(counter));
		JLcounter.setFont(new Font("Arial", 10, 100));
		JLcounter.setForeground(Color.ORANGE);

		add(JLcounter);

	}

	public void setCounter(int counter) {
		JLcounter.setText(Integer.toString(counter));
		JLcounter.setSize(JLcounter.getPreferredSize());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		int xoffset;
		int yoffset;
		float scale;

		if (getWidth() * c.getHeight() / c.getWidth() < getHeight()) {

			xoffset = 0;
			yoffset = (getHeight() - (getWidth() * c.getHeight() / c.getWidth())) / 2;
			scale = getWidth() / (c.getWidth() * 1f);
		} else {
			xoffset = (getWidth() - (getHeight() * c.getWidth() / c.getHeight())) / 2;
			yoffset = 0;
			scale = getHeight() / (c.getHeight() * 1f);

		}

		// update counter
		JLcounter.setFont(new Font("Arial", 10, (int) (100 * scale)));
		JLcounter.setBounds((int) ((getWidth() - JLcounter.getPreferredSize().getWidth()) / 2),
				(int) (getHeight() / 2 - c.getHeight() / 5 * 2 * scale), (int) JLcounter.getPreferredSize().getWidth(),
				(int) JLcounter.getPreferredSize().getHeight());

		// Draw Bird
		g.setColor(Color.red);
		g.fillOval((int) (xoffset + bird.getX() * scale), (int) (yoffset + bird.getY() * scale),
				(int) (bird.getWidth() * scale), (int) (bird.getHeight() * scale));

		// Draw Obstacles
		for (int i = 0; i < ObstacleList.size(); i++) {
			Obstacle tempObject = ObstacleList.get(i);

			float tempscale = tempObject.getImagescale() * scale;

			BufferedImage tempimage = tempObject.getImage();
			BufferedImage tempfillerimage = tempimage.getSubimage(0, tempimage.getHeight() - 1, tempimage.getWidth(),
					1);

			if (tempObject.getY() == 0) {

				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-tempimage.getWidth(), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				tempimage = op.filter(tempimage, null);

				AffineTransform af = AffineTransform.getTranslateInstance(xoffset + tempObject.getX() * scale,
						yoffset + (tempObject.getHeight() * scale - tempimage.getHeight() * tempscale));
				af.scale(tempscale, tempscale);
				af.rotate(Math.PI, tempimage.getWidth() / 2f, tempimage.getHeight() / 2f);

				g2.drawImage(tempimage, af, null);

				float yspace = (tempObject.getHeight() - tempimage.getHeight() * tempObject.getImagescale());

				for (int p = 0; p < yspace; p++) {
					af = AffineTransform.getTranslateInstance(xoffset + tempObject.getX() * scale, yoffset + p * scale);
					af.scale(tempscale, tempscale);

					g2.drawImage(tempfillerimage, af, null);
				}

			} else {

				AffineTransform af = AffineTransform.getTranslateInstance(xoffset + tempObject.getX() * scale,
						yoffset + tempObject.getY() * scale);
				af.scale(tempscale, tempscale);
				g2.drawImage(tempimage, af, null);

				float yspace = c.getHeight() - (tempObject.getY() + tempimage.getHeight());

				for (int p = 0; p < yspace; p++) {
					af = AffineTransform.getTranslateInstance(xoffset + tempObject.getX() * scale,
							yoffset + (tempObject.getY() * scale + tempimage.getHeight() * tempscale) + p * scale);
					af.scale(tempscale, tempscale);
					g2.drawImage(tempfillerimage, af, null);
				}

			}

			if (collisionbox) {
				g.setColor(Color.red);
				g.drawRect((int) (xoffset + tempObject.getX() * scale), (int) (yoffset + tempObject.getY() * scale),
						(int) (tempObject.getWidth() * scale), (int) (tempObject.getHeight() * scale));
			}

		}

		g.setColor(Color.BLACK);
		// Sichtschutz
		// Oben und unten
		g.fillRect(0, 0, (int) getWidth(), yoffset);
		g.fillRect(0, (int) (yoffset + c.getHeight() * scale), (int) getWidth(), getHeight());
		// rechts links
		g.fillRect(0, 0, xoffset, getHeight());
		g.fillRect((int) (xoffset + c.getWidth() * scale), 0, (int) getWidth(), getHeight());

	}

	public float[][] getXyh() {
		return xyh;
	}

	public void setXyh(float[][] xyh) {
		this.xyh = xyh;
	}

}
