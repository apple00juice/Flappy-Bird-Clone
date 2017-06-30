package View;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Control.Control;

public class View extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Control c;

	public ViewGame viewgame;

	public ViewMenu viewmenu;

	public JFrame frame;

	public int xoffset;
	public int yoffset;
	public float scale;
	
	static String font = "Lucida Console";

	public View(Control c, String title, int width, int height) {
		this.setDoubleBuffered(true);
		this.c = c;

		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

		this.setPreferredSize(new Dimension(width, height));
		setLayout(null);

		viewmenu = new ViewMenu(c, this);
		viewmenu.setBounds(0, 0, c.width, c.height);
		add(viewmenu);

		viewgame = new ViewGame(c, this);
		viewgame.setBounds(0, 0, c.width, c.height);
		add(viewgame);

		frame.add(this);
		frame.addKeyListener(c);

		frame.setPreferredSize(frame.getPreferredSize());
		frame.pack();
		frame.setVisible(true);
		
	}

	public void render() {

		if (getWidth() * c.height / c.width < getHeight()) {

			xoffset = 0;
			yoffset = (getHeight() - (getWidth() * c.height / c.width)) / 2;
			scale = getWidth() / (c.width * 1f);
		} else {
			xoffset = (getWidth() - (getHeight() * c.width/ c.height)) / 2;
			yoffset = 0;
			scale = getHeight() / (c.height * 1f);

		}
		
		viewmenu.setBounds(0, 0, getWidth(), getHeight());
		viewgame.setBounds(0, 0, getWidth(), getHeight());
		
		switch (c.gameState) {
		case Menu:
			viewmenu.requestFocus();
			viewmenu.setVisible(true);
			viewgame.JLcounter.setVisible(false);

			
			
			viewmenu.repaint();
			

			break;

		case Game:
			frame.requestFocus();
			
			viewmenu.setVisible(false);
			viewgame.JLcounter.setVisible(true);

			viewgame.repaint();

			break;

		default:
			break;
		}

	}

}
