package View;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ViewMenu extends JPanel {

	public ViewMenu() {
		setLayout(null);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

}
