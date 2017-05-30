package View;
import java.awt.Color;
import java.awt.Dimension;
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

import Control.Control;


public class View extends JPanel {

	Control c;

	public ViewGame viewgame;

	private ViewMenu viewmenu;

	public View(Control c) {
		this.setDoubleBuffered(true);
		this.c = c;

		setLayout(null);

		viewmenu = new ViewMenu();
		viewmenu.setBounds(0, 0, c.getWidth(), c.getHeight());
		add(viewmenu);

		viewgame = new ViewGame(c);
		viewgame.setBounds(0, 0, c.getWidth(), c.getHeight());
		add(viewgame);

	}

	public void render() {

		viewmenu.setVisible(false);
		viewgame.setVisible(false);
		
		switch (c.gameState) {
		case Menu:
			
			viewmenu.setVisible(true);
			viewmenu.setBounds(0, 0, getWidth(), getHeight());
			viewmenu.repaint();

			break;

		case Game:

			viewgame.setVisible(true);
			viewgame.setBounds(0, 0, getWidth(), getHeight());
			viewgame.repaint();

			break;

		default:
			break;
		}

	}

}
