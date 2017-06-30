package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Control.Control;
import Model.Background;
import Model.Bird;
import Model.Obstacle;

public class ViewGame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Control c;

	View v;

	public Bird bird;

	public List<Obstacle> ObstacleList;
	public List<Background> BackgroundList;
	public List<Background> GroundList;

	int counter = 0;

	public JLabel JLcounter;

	public JLabel controlles;

	private boolean collisionbox = false;

	BufferedImage backgroundImage;
	BufferedImage groundImage;
	BufferedImage obstacleImage;
	BufferedImage birdImage;

	public JPanel resultPanel;

	public JPanel result;
	public JLabel score;
	public JLabel highScore;
	public JLabel scoreInfo;
	public JLabel bestInfo;

	public JPanel buttonPanel;
	public JButton menuButton;
	public JButton restartButton;

	public ViewGame(Control c, View v) {
		this.setDoubleBuffered(true);
		this.c = c;
		this.v = v;

		try {
			backgroundImage = ImageIO.read(getClass().getResource("/FBC-Background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			groundImage = ImageIO.read(getClass().getResource("/FBC-Ground.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			obstacleImage = ImageIO.read(getClass().getResource("/FBC-Tube.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			birdImage = ImageIO.read(getClass().getResource("/FBC-Bird.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setLayout(null);

		JLcounter = new JLabel(Integer.toString(counter));
		JLcounter.setFont(new Font(View.font, Font.BOLD, 100));
		JLcounter.setForeground(Color.WHITE);

		add(JLcounter);

		controlles = new JLabel("<html><p align='center'><b>Get Ready!<br><br>Press Space to Start</b></p></html>");
		controlles.setFont(new Font(View.font, 10, 40));
		controlles.setForeground(new Color(	255, 162, 74));
		controlles.setVisible(false);

		add(controlles);

		resultPanel = new JPanel();
		resultPanel.setFont(new Font(View.font, 10, 25));
		resultPanel.setForeground(new Color(222,215,148));
		resultPanel.setVisible(false);
		resultPanel.setOpaque(false);
		resultPanel.setLayout(new BorderLayout());

		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);

		restartButton = new JButton("Restart");
		restartButton.setBackground(new Color(222,215,148));
		restartButton.setForeground(new Color(255, 121, 90));
		restartButton.addActionListener(c);
		buttonPanel.add(restartButton);

		menuButton = new JButton("Menu");
		menuButton.setBackground(new Color(222,215,148));
		menuButton.setForeground(new Color(255, 121, 90));
		menuButton.addActionListener(c);

		buttonPanel.add(menuButton);

		resultPanel.add(buttonPanel, BorderLayout.SOUTH);

		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout());
		scorePanel.setBackground(new Color(222,215,148));

		result = new JPanel();
		result.setOpaque(false);
		result.setLayout(new GridLayout(4, 1));

		scoreInfo = new JLabel("SCORE");
		scoreInfo.setForeground(new Color(255, 121, 90));
		result.add(scoreInfo);

		score = new JLabel("0");
		score.setForeground(Color.WHITE);

		result.add(score);

		bestInfo = new JLabel("BEST");
		bestInfo.setForeground(new Color(255, 121, 90));
		result.add(bestInfo);

		highScore = new JLabel("0");
		highScore.setForeground(Color.WHITE);

		result.add(highScore);

		scorePanel.add(result);

		resultPanel.add(scorePanel, BorderLayout.CENTER);

		add(resultPanel);

		ObstacleList = new ArrayList<Obstacle>();
		BackgroundList = new ArrayList<Background>();
		GroundList = new ArrayList<Background>();

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

		g.setColor(new Color(125, 175, 237));
		g2.fillRect(0, 0, getWidth(), getHeight());

		int xoffset = v.xoffset;
		int yoffset = v.yoffset;
		float scale = v.scale;
		
		

		for (int i = 0; i < BackgroundList.size(); i++) {

			Background tempBg = BackgroundList.get(i);

			AffineTransform bgAf = AffineTransform.getTranslateInstance(xoffset + tempBg.x * scale,
					yoffset + tempBg.y * scale);
			bgAf.scale(tempBg.width / backgroundImage.getWidth() * scale, tempBg.height / backgroundImage.getHeight() * scale);

			g2.drawImage(backgroundImage, bgAf, null);

		}

		// update counter
		JLcounter.setFont(new Font(View.font, 10, (int) (100 * scale)));
		JLcounter.setBounds((int) ((getWidth() - JLcounter.getPreferredSize().getWidth()) / 2),
				(int) (getHeight() / 2 - c.height / 5 * 2 * scale), (int) JLcounter.getPreferredSize().getWidth(),
				(int) JLcounter.getPreferredSize().getHeight());

		// update controlles
		controlles.setFont(new Font(View.font, 10, (int) (25 * scale)));
		controlles.setBounds(
				(int) ((xoffset + bird.x * scale
						+ (bird.width * scale - controlles.getPreferredSize().getWidth()) / 2)),
				(int) (getHeight() / 2 - c.height * scale / 10), (int) controlles.getPreferredSize().getWidth(),
				(int) controlles.getPreferredSize().getHeight());

		// Draw Bird

		float birdXScale = bird.width / birdImage.getWidth() * scale;
		float birdYScale = bird.height / birdImage.getHeight() * scale;

		AffineTransform Birdaf = AffineTransform.getTranslateInstance(xoffset + bird.x * scale,
				yoffset + bird.y * scale);
		Birdaf.scale(birdXScale, birdYScale);
		Birdaf.rotate(Math.toRadians(bird.rot), birdImage.getWidth() / 2, birdImage.getHeight() / 2);
		g2.drawImage(birdImage, Birdaf, null);

		if (collisionbox) {
			g.setColor(Color.red);
			g.drawRect((int) (xoffset + bird.getBound().x * scale), (int) (yoffset + bird.getBound().y * scale),
					(int) (bird.getBound().width * scale), (int) (bird.getBound().height * scale));

		}

		// Draw Obstacles
		for (int i = 0; i < ObstacleList.size(); i++) {
			Obstacle tempObject = ObstacleList.get(i);


			

			BufferedImage tempimage = obstacleImage;
			BufferedImage tempfillerimage = tempimage.getSubimage(0, tempimage.getHeight() - 2, tempimage.getWidth(),
					2);
			
			float xscale = tempObject.width/tempimage.getWidth()*scale;
			float yscale = tempObject.width/tempimage.getWidth()*scale;

			if (tempObject.y == 0) {

				float x = xoffset + tempObject.x * scale;

				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-tempimage.getWidth(), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				tempimage = op.filter(tempimage, null);

				AffineTransform af = AffineTransform.getTranslateInstance(x,
						yoffset + (tempObject.height * scale - tempimage.getHeight() * xscale));
				af.scale(xscale, yscale);
				af.rotate(Math.PI, tempimage.getWidth() / 2f, tempimage.getHeight() / 2f);

				g2.drawImage(tempimage, af, null);

				float yspace = (tempObject.height - tempimage.getHeight()*(xscale/scale))
						/ tempfillerimage.getHeight();

				for (int p = 0; p < yspace; p++) {
					af = AffineTransform.getTranslateInstance(x, yoffset + p * tempfillerimage.getHeight() * scale);
					af.scale(tempObject.width/tempimage.getWidth()*scale, scale);

					g2.drawImage(tempfillerimage, af, null);
				}

			} else {

				float x = xoffset + tempObject.x * scale;
				
				
				AffineTransform af = AffineTransform.getTranslateInstance(x, yoffset + tempObject.y * scale);
				af.scale(xscale, yscale);
				g2.drawImage(tempimage, af, null);

				float yspace = (c.height - (tempObject.y + tempimage.getHeight()))
						/ tempfillerimage.getHeight();

				for (int p = 0; p < yspace; p += tempfillerimage.getHeight()) {
					af = AffineTransform.getTranslateInstance(x,
							yoffset + (tempObject.y * scale + tempimage.getHeight() * yscale)
									+ p * tempfillerimage.getHeight() * scale);
					af.scale(tempObject.width/tempfillerimage.getWidth()*scale, tempObject.width/tempfillerimage.getWidth()*scale);
					g2.drawImage(tempfillerimage, af, null);
				}

			}

			if (collisionbox) {
				g.setColor(Color.red);
				g.drawRect((int) (xoffset + tempObject.x * scale), (int) (yoffset + tempObject.y * scale),
						(int) (tempObject.width * scale), (int) (tempObject.height * scale));

			}

		}
		
		for (int i = 0; i < GroundList.size(); i++) {

			Background tempG = GroundList.get(i);
			
			AffineTransform GAf = AffineTransform.getTranslateInstance(xoffset + tempG.x * scale,
					yoffset + tempG.y * scale);
			GAf.scale(tempG.width / groundImage.getWidth() * scale, tempG.height / groundImage.getHeight() * scale);

			g2.drawImage(groundImage, GAf, null);

		}
		
		

		resultPanel.setBounds((int) (xoffset + (c.width - c.width / 2) / 2 * scale),
				(int) (yoffset + (c.height * 0.8 - c.height / 2) * scale), (int) (c.width / 2 * scale),
				(int) (c.height * 0.6 * scale));

		restartButton.setFont(new Font(View.font, 10, (int) (45 * scale)));
		restartButton.setSize(restartButton.getPreferredSize());

		menuButton.setFont(new Font(View.font, 10, (int) (45 * scale)));
		menuButton.setSize(menuButton.getPreferredSize());
		
		scoreInfo.setFont(new Font(View.font, 10, (int) (55 * scale)));
		scoreInfo.setSize(scoreInfo.getPreferredSize());
		
		score.setFont(new Font(View.font, 10, (int) (50 * scale)));
		score.setSize(score.getPreferredSize());
		
		bestInfo.setFont(new Font(View.font, 10, (int) (55 * scale)));
		bestInfo.setSize(bestInfo.getPreferredSize());
		
		highScore.setFont(new Font(View.font, 10, (int) (50 * scale)));
		highScore.setSize(highScore.getPreferredSize());
		
		

		g.setColor(Color.BLACK);
		// Sichtschutz
		// Oben und unten
		g.fillRect(0, 0, (int) getWidth(), yoffset);
		g.fillRect(0, (int) (yoffset + c.height * scale), (int) getWidth(), getHeight());
		// rechts links
		g.fillRect(0, 0, xoffset, getHeight());
		g.fillRect((int) (xoffset + c.width * scale), 0, (int) getWidth(), getHeight());

	}

}
