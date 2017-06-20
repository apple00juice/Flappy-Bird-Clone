package Control;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Model.Model;
import View.View;

public class Control implements Runnable, KeyListener, ActionListener {

	public JFrame frame;

	private String title = "Flappy Bird Clon";
	private int width = 800, height = 600;

	public Model model;
	private View view;

	private boolean showFPS = true;

	public enum GameStates {
		Menu, Game;
	}

	public GameStates gameState;

	public Control() {
		gameState = GameStates.Menu;

		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

		model = new Model(this);
		view = new View(this);
		view.setPreferredSize(new Dimension(width, height));

		frame.add(view);
		frame.addKeyListener(this);

		frame.setPreferredSize(frame.getPreferredSize());
		frame.pack();
		frame.setVisible(true);

		this.run();

	}

	public static void main(String[] args) {
		new Control();
	}

	private void updateView() {
		if (!model.isUpdatingmap()) {
			if (view.viewgame.ObstacleList != model.ObstacleList)
				view.viewgame.ObstacleList = model.ObstacleList;

			view.viewgame.bird = model.bird;
			view.viewgame.setCounter(model.getCounter());

			view.render();
		}

	}

	@Override
	public void run() {
		long lasttime = System.currentTimeMillis();
		long countingtime = lasttime;
		int frames = 0;
		while (true) {
			long currenttime = System.currentTimeMillis();

			if (currenttime - countingtime >= 1000 && showFPS) {
				countingtime = currenttime;
				frame.setTitle(title + " - " + frames + " FPS");
				frames = 0;
			}

			double dt = (currenttime - lasttime) / 1000.0;

			model.tick(dt);
			updateView();
			frames++;

			lasttime = currenttime;
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}

		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private boolean released = true;

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == e.VK_SPACE && released) {
			model.birdJump();
		}

		if (e.getKeyCode() == e.VK_R) {
			model.resetMap();

		}

		if (e.getKeyCode() == e.VK_G) {
			gameState = GameStates.Game;

		} else if (e.getKeyCode() == e.VK_M) {
			gameState = GameStates.Menu;
		}

		released = false;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		released = true;

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == view.viewmenu.startbutton) {
			released = true;
			model.resetMap();
			gameState = GameStates.Game;
		} else if (e.getSource() == view.viewmenu.exitbutton) {
			frame.setVisible(false);
			frame.dispose();
			System.exit(1);
		}

	}

}
