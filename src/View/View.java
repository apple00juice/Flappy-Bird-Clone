package View;

import javax.swing.JPanel;

import Control.Control;

public class View extends JPanel {

	Control c;

	public ViewGame viewgame;

	public ViewMenu viewmenu;
	
	public int xoffset;
	public int yoffset;
	public float scale;

	public View(Control c) {
		this.setDoubleBuffered(true);
		this.c = c;
		

		setLayout(null);

		viewmenu = new ViewMenu(c, this);
		viewmenu.setBounds(0, 0, c.getWidth(), c.getHeight());
		add(viewmenu);

		viewgame = new ViewGame(c, this);
		viewgame.setBounds(0, 0, c.getWidth(), c.getHeight());
		add(viewgame);

	}
	
	public void render() {
		
		if (getWidth() * c.getHeight() / c.getWidth() < getHeight()) {

			xoffset = 0;
			yoffset = (getHeight() - (getWidth() * c.getHeight() / c.getWidth())) / 2;
			scale = getWidth() / (c.getWidth() * 1f);
		} else {
			xoffset = (getWidth() - (getHeight() * c.getWidth() / c.getHeight())) / 2;
			yoffset = 0;
			scale = getHeight() / (c.getHeight() * 1f);

		}

		switch (c.gameState) {
		case Menu:
			viewmenu.requestFocus();
			viewmenu.setVisible(true);
			viewmenu.setEnabled(true);

			viewgame.setVisible(false);
			viewgame.setEnabled(false);

			viewmenu.setBounds(0, 0, getWidth(), getHeight());
			viewmenu.repaint();

			break;

		case Game:
			c.frame.requestFocus();
			viewmenu.setVisible(false);
			viewmenu.setEnabled(false);

			viewgame.setVisible(true);
			viewgame.setEnabled(true);

			viewgame.setBounds(0, 0, getWidth(), getHeight());
			viewgame.repaint();

			break;

		default:
			break;
		}

	}

}
