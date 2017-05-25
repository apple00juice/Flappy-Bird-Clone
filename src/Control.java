
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Control implements Runnable, KeyListener {

	private JFrame frame;

	private String title = "Flappy Bird Clon";
	private int width = 800, height = 600;

	private Model model;
	private View view;

	Thread thread;

	public Control() {
		frame = new JFrame(title);
		frame.pack();

		model = new Model(this);
		view = new View(model.ObstacleList, model.bird, this);
		view.setPreferredSize(new Dimension(width, height));

		frame.add(view);
		frame.addKeyListener(this);

		frame.setPreferredSize(frame.getPreferredSize());
		frame.pack();
		frame.setVisible(true);

		thread = new Thread(this);
		thread.start();

	}

	public static void main(String[] args) {
		new Control();
	}

	private void updateView() {
		view.ObstacleList = model.ObstacleList;
		view.setCounter(model.getCounter());
		view.repaint();

	}

	@Override
	public void run() {
		long lasttime = System.currentTimeMillis();
		long countingtime = lasttime;
		int frames = 0;
		while (true) {
			long currenttime = System.currentTimeMillis();

			if (currenttime - countingtime >= 1000) {
				countingtime = currenttime;
				frame.setTitle(title + " - " + frames + " FPS");
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
		if(e.getKeyCode() == e.VK_SPACE && released){
			model.SpacePressed();
		}
		
		if(e.getKeyCode() == e.VK_R){
			model.resetMap();
			
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

}
