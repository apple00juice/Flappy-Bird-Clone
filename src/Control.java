

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Control implements Runnable,MouseListener{

	private JFrame frame;

	private String title = "Flappy Bird Clon";
	private int width = 800, height = 600;
	
	private Model model;
	
	Thread thread;

	public Control() {
		model = new Model();
		
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setVisible(true);
		
		thread = new Thread(this);
		thread.start();
		
	}
	
	
	

	public static void main(String[] args) {
		new Control();
	}
	
	


	@Override
	public void run() {
		long lasttime = System.currentTimeMillis();
		while (true) {
			long currenttime = System.currentTimeMillis();
			double dt = (currenttime - lasttime) / 1000.0;
			
			model.tick(dt);	
			
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
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
