package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import Model.Model;
import View.View;

public class Control implements Runnable, KeyListener, ActionListener {

	private String title = "Flappy Bird Clon";
	public int width = 800, height = 600;

	public Model model;
	private View view;

	private boolean showFPS = true;

	public enum GameStates {
		Menu, Game;
	}

	public GameStates gameState;

	String path = "";
	File highScore;

	int highScoreNumber = 0;
	boolean highScoreUpdated;
	boolean canUpdateHighscoreFile;

	private boolean released = true;

	public void setHighScorePath() {
		String os = System.getProperty("os.name").toLowerCase();
		canUpdateHighscoreFile = true;

		if (os.indexOf("win") >= 0) {

			String user = System.getProperty("user.name");

			path = "C:/Users/" + user + "/AppData/Roaming" + "/FlappyBirdClon";

			File dir = new File(path);
			dir.mkdir();

			path += "/highScore.txt";

		}  else {
			System.err.println("Local Highscore saving not support!!");
			canUpdateHighscoreFile = false;
		}

	}

	public Control() {
		gameState = GameStates.Menu;

		setHighScorePath();

		highScoreNumber = 0;

		if (canUpdateHighscoreFile) {

			highScore = new File(path);
			if (!highScore.exists()) {
				try {
					highScore.createNewFile();
					updateScoreFile(0);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			loadHighScore();
		}

		highScoreUpdated = false;

		model = new Model(this);
		view = new View(this, title, width, height);
		view.viewgame.BackgroundList = model.BackgroundList;
		view.viewgame.GroundList = model.GroundList;
		this.run();

	}

	public void loadHighScore() {

		Scanner scan;
		try {
			scan = new Scanner(highScore);
			highScoreNumber = scan.nextInt();
			scan.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Control();
	}

	private void updateView() {
		if (!model.updatingMap) {
			if (view.viewgame.ObstacleList != model.ObstacleList)

				view.viewgame.ObstacleList = model.ObstacleList;

			view.viewgame.bird = model.bird;
			if (!model.bird.dead)
				view.viewgame.setCounter(model.counter);
			view.render();
		}

	}

	public void updateScoreFile(int highScore) {
		this.highScoreNumber = highScore;
		try {
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.println(Integer.toString(highScore));
			writer.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
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
				view.frame.setTitle(title + " - " + frames + " FPS");
				frames = 0;
			}

			double dt = (currenttime - lasttime) / 1000.0;

			model.tick(dt);
			view.viewgame.controlles.setVisible(!model.run && (gameState != GameStates.Menu));
			view.viewgame.resultPanel.setVisible(model.bird.dead && (gameState != GameStates.Menu));

			if (model.bird.dead && (gameState != GameStates.Menu)) {
				if (!highScoreUpdated) {
					if (model.counter > highScoreNumber) {
						
						if (canUpdateHighscoreFile)
							updateScoreFile(model.counter);
						
						highScoreNumber = model.counter;
						view.viewgame.bestInfo.setText("NEW BEST!");
					} else {
						view.viewgame.bestInfo.setText("BEST");
					}

					highScoreUpdated = true;
				}
				view.viewgame.JLcounter.setText("GAME OVER");
				view.viewgame.score.setText(Integer.toString(model.counter));
				view.viewgame.highScore.setText(Integer.toString(highScoreNumber));
			}

			updateView();
			frames++;

			lasttime = currenttime;
			try {
				Thread.sleep(1000L / 60);
			} catch (Exception e) {
			}

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE && released) {
			model.birdJump();
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
			gameState = GameStates.Game;
			highScoreUpdated = false;
		} else if (e.getSource() == view.viewmenu.exitbutton) {
			view.frame.setVisible(false);
			view.frame.dispose();
			System.exit(1);
		} else if (e.getSource() == view.viewgame.menuButton) {
			model.resetMap();
			gameState = GameStates.Menu;
		} else if (e.getSource() == view.viewgame.restartButton) {
			released = true;
			model.resetMap();
			highScoreUpdated = false;
		}

	}

}
