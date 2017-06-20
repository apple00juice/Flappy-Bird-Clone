package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Control.Control;
import Model.Bird;
import Model.Obstacle;

public class ViewGame extends JPanel {

	Control c;
	
	View v;

	public Bird bird;

	public List<Obstacle> ObstacleList;

	int counter = 0;

	private JLabel JLcounter;

	private boolean collisionbox = false;
	
	

	public ViewGame(Control c, View v) {
		this.setDoubleBuffered(true);
		this.c = c;
		this.v = v;

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
		if (bird == null || ObstacleList == null)
			return;
		
		Graphics2D g2 = (Graphics2D) g;

		g.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());

		int xoffset = v.xoffset;
		int yoffset = v.yoffset;
		float scale = v.scale;

		// update counter
		JLcounter.setFont(new Font("Arial", 10, (int) (100 * scale)));
		JLcounter.setBounds((int) ((getWidth() - JLcounter.getPreferredSize().getWidth()) / 2),
				(int) (getHeight() / 2 - c.getHeight() / 5 * 2 * scale), (int) JLcounter.getPreferredSize().getWidth(),
				(int) JLcounter.getPreferredSize().getHeight());

		// Draw Bird

		float birdtempscale = bird.getImagescale() * scale;

		AffineTransform Birdaf = AffineTransform.getTranslateInstance(xoffset + bird.getX() * scale,
				yoffset + bird.getY() * scale);
		Birdaf.scale(birdtempscale, birdtempscale);
		Birdaf.rotate(Math.toRadians(bird.getRot()), bird.getImage().getWidth() / 2, bird.getImage().getHeight() / 2);
		g2.drawImage(bird.getImage(), Birdaf, null);

		if (collisionbox) {
			g.setColor(Color.red);
			g.drawRect((int) (xoffset + bird.getBound().x * scale), (int) (yoffset + bird.getBound().y * scale),
					(int) (bird.getBound().width * scale), (int) (bird.getBound().height * scale));

		}

		// Draw Obstacles
		for (int i = 0; i < ObstacleList.size(); i++) {
			Obstacle tempObject = ObstacleList.get(i);

			float tempscale = tempObject.getImagescale() * scale;

			BufferedImage tempimage = tempObject.getImage();
			BufferedImage tempfillerimage = tempimage.getSubimage(0, tempimage.getHeight() - 2, tempimage.getWidth(),
					2);

			if (tempObject.getY() == 0) {

				float x = xoffset + tempObject.getX() * scale;

				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-tempimage.getWidth(), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				tempimage = op.filter(tempimage, null);

				AffineTransform af = AffineTransform.getTranslateInstance(x,
						yoffset + (tempObject.getHeight() * scale - tempimage.getHeight() * tempscale));
				af.scale(tempscale, tempscale);
				af.rotate(Math.PI, tempimage.getWidth() / 2f, tempimage.getHeight() / 2f);

				g2.drawImage(tempimage, af, null);

				float yspace = (tempObject.getHeight() - tempimage.getHeight() * tempObject.getImagescale())
						/ tempfillerimage.getHeight();

				for (int p = 0; p < yspace; p++) {
					af = AffineTransform.getTranslateInstance(x, yoffset + p * tempfillerimage.getHeight() * scale);
					af.scale(tempscale, tempscale);

					g2.drawImage(tempfillerimage, af, null);
				}

			} else {

				float x = xoffset + tempObject.getX() * scale;

				AffineTransform af = AffineTransform.getTranslateInstance(x, yoffset + tempObject.getY() * scale);
				af.scale(tempscale, tempscale);
				g2.drawImage(tempimage, af, null);

				float yspace = (c.getHeight() - (tempObject.getY() + tempimage.getHeight()))
						/ tempfillerimage.getHeight();

				for (int p = 0; p < yspace; p += tempfillerimage.getHeight()) {
					af = AffineTransform.getTranslateInstance(x,
							yoffset + (tempObject.getY() * scale + tempimage.getHeight() * tempscale)
									+ p * tempfillerimage.getHeight() * scale);
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

}
