
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Control implements Runnable, MouseListener {

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
		view = new View(model.ObstacleList, model.bird);
		view.setPreferredSize(new Dimension(width, height));
		
		frame.add(view);
		frame.addMouseListener(this);

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
		view.repaint();

	}

	@Override
	public void run() {
		long lasttime = System.currentTimeMillis();
		while (true) {
			long currenttime = System.currentTimeMillis();
			double dt = (currenttime - lasttime) / 1000.0;

			model.tick(dt);
			updateView();

			lasttime = currenttime;
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		model.mousePressed();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
