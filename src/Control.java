import javax.swing.JFrame;

public class Control implements Runnable{

	private JFrame frame;

	private String title = "Flappy Bird Clon";
	private int width = 800, height = 600;
	
	private Model model;

	public Control() {
		model = new Model();
		
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setVisible(true);
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
}
