package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Control.Control;

public class ViewMenu extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JButton startbutton;

	public JButton exitbutton;

	private JLabel title;

	Control c;
	View v;

	private float buttonWidth;
	private float buttonHeight;

	public ViewMenu(Control c, View v) {
		this.c = c;
		this.v = v;

		setLayout(null);

		buttonWidth = c.width / 3;
		buttonHeight = c.width / 10;

		title = new JLabel("<html><b>Flappy Bird</b></html>");
		title.setFont(new Font(View.font, 10, 100));
		title.setForeground(new Color(	255, 162, 74));
		this.add(title);

		startbutton = new JButton("Start");
		startbutton.setBackground(new Color(222,215,148));
		startbutton.setForeground(new Color(255, 121, 90));
		startbutton.setEnabled(true);
		this.add(startbutton);
		startbutton.addActionListener(c);

		exitbutton = new JButton("Exit");
		exitbutton.setBackground(new Color(222,215,148));
		exitbutton.setForeground(new Color(255, 121, 90));
		exitbutton.setEnabled(true);
		this.add(exitbutton);
		exitbutton.addActionListener(c);

		this.setBackground(null);
		this.setOpaque(false);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

		float scale = v.scale;

		title.setFont(new Font(View.font, 10, (int) (100 * scale)));

		title.setBounds((int) ((getWidth() - title.getPreferredSize().getWidth())/2), (int)(getHeight() *0.1),
				(int)title.getPreferredSize().getWidth(), (int)title.getPreferredSize().getHeight());

		startbutton.setFont(new Font(View.font, 10, (int) (50 * scale)));
		startbutton.setBounds((int) (getWidth() - buttonWidth * scale) / 2, getHeight() / 2,
				(int) (buttonWidth * scale), (int) (buttonHeight * scale));

		exitbutton.setFont(new Font(View.font, 10, (int) (50 * scale)));
		exitbutton.setBounds((int) (getWidth() - buttonWidth * scale) / 2, getHeight() / 4 * 3,
				(int) (buttonWidth * scale), (int) (buttonHeight * scale));

	}

}
